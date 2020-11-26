package org.oldo.file;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static org.guppy4j.io.PathType.FILE;
import static org.guppy4j.log.Log.Level.info;

/**
 * Implements text buffers using file system storage
 */
public final class TextFiles implements TextBuffers {

    private final Log log;
    private final ApplicationFiles applicationFiles;

    public TextFiles(LogProvider logProvider,
                     ApplicationFiles applicationFiles) {
        log = logProvider.getLog(getClass());
        this.applicationFiles = applicationFiles;
    }

    @Override
    public void save(String text, int i) {
        final Path path = getBufferPath(i);
        writeString(text, path);
        log.as(info, "Saved buffer {}", path);
    }

    @Override
    public String load(int i) {
        final Path path = getBufferPath(i);
        final String content = readAsString(path);
        log.as(info, "Loaded buffer {}", path);
        return content;
    }

    private static void writeString(String text, Path path) {
        try {
            write(path, text.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String readAsString(Path buffer) {
        try {
            return new String(readAllBytes(buffer));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getBufferPath(int i) {
        return applicationFiles.findOrCreate(i + ".txt", FILE);
    }
}
