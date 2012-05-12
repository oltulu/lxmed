package net.sourceforge.lxmed.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CopyAction extends LxmedAbstractAction {

    public CopyAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        putValue(SMALL_ICON, loadIcon("/net/sourceforge/lxmed/images/dialogs/edit-copy.png"));
        putValue(NAME, "Copy");
        putValue(SHORT_DESCRIPTION, "Copy selected menu item");
    }

    public void actionPerformed(ActionEvent e) {
        ActionManager.getInstance().getPasteAction().setEnabled(true);
    }
}
