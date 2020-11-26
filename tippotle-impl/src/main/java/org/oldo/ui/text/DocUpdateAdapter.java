package org.oldo.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Adapter between document events and text change events
 */
public abstract class DocUpdateAdapter implements DocumentListener {

    protected abstract void handleUpdate(DocumentEvent e);

    @Override
    public final void insertUpdate(DocumentEvent e) {
        handleUpdate(e);
    }

    @Override
    public final void removeUpdate(DocumentEvent e) {
        // nothing to do
    }

    @Override
    public final void changedUpdate(DocumentEvent e) {
        // nothing to do
    }
}
