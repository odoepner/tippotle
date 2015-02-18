package net.doepner.ui.text;

import net.doepner.ui.CharStyler;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.StyledDocument;

import static net.doepner.ui.text.DocEvents.getText;

/**
 * Sets text attributes (currently mainly colors)
 * only newly inserted text
 */
public final class TextStyler extends DocUpdateAdapter {

    private final CharStyler charStyler;

    /**
     * @param charStyler The character styler
     */
    public TextStyler(CharStyler charStyler) {
        this.charStyler = charStyler;
    }

    @Override
    public void handleUpdate(DocumentEvent event) {
        final StyledDocument doc = (StyledDocument) event.getDocument();

        final int offset = event.getOffset();
        final String text = getText(event);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < text.length(); i++) {
                    doc.setCharacterAttributes(offset + i, 1,
                            charStyler.getAttribs(text.charAt(i)), true);
                }
            }
        });
    }
}
