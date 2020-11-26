package org.oldo.speech;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static org.guppy4j.BaseUtil.not;
import static org.guppy4j.log.Log.Level.error;
import static org.guppy4j.log.Log.Level.warn;
import static org.oldo.ui.SwingUtil.doInBackground;

/**
 * Delegates to the currently selected speaker (among the available ones)
 */
public final class ManagedSpeakers implements IterableSpeakers {

    private final Iterable<Speaker> speakers;

    private Iterator<Speaker> iterator;
    private Speaker current;

    public ManagedSpeakers(LogProvider logProvider,
                           TestableSpeaker... speakers) {
        final Log log = logProvider.getLog(getClass());

        final Collection<Speaker> speakerList = new LinkedList<>();
        for (TestableSpeaker speaker : speakers) {
            try {
                speaker.test();
                speakerList.add(speaker);

            } catch (IllegalStateException e) {
                log.as(warn, "Speaker '{}' not functional. Error: {}",
                        speaker.name(), e.getMessage());
            }
        }
        if (speakerList.isEmpty()) {
            log.as(error, "No functional speakers available. Speech will be disabled.");
            speakerList.add(Speaker.NONE);
        }
        this.speakers = speakerList;
        next();
    }

    @Override
    public String name() {
        return current == null ? "unknown" : current.name();
    }

    @Override
    public void stopAll() {
        speakers.forEach(Speaker::stopAll);
    }

    @Override
    public void speak(final String text) {
        doInBackground(() -> current.speak(text));
    }

    @Override
    public void next() {
        if (iterator == null || not(iterator.hasNext())) {
            iterator = speakers.iterator();
        }
        // we assume there is always at least one speaker,
        // even if it is the Speaker.NONE
        current = iterator.next();
    }
}
