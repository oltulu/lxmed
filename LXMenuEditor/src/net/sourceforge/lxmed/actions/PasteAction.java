package net.sourceforge.lxmed.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class PasteAction extends LxmedAbstractAction {

    boolean forCut = false;

    public PasteAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_V);
        putValue(SMALL_ICON, loadIcon("/net/sourceforge/lxmed/images/dialogs/edit-paste.png"));
        putValue(NAME, "Paste");
        putValue(SHORT_DESCRIPTION, "Paste menu item");
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (forCut) {
            forCut = false;
            setEnabled(false);
        }
    }
}
