package net.sourceforge.lxmed.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CutAction extends LxmedAbstractAction {

    public CutAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        putValue(SMALL_ICON, loadIcon("/net/sourceforge/lxmed/images/dialogs/edit-cut.png"));
        putValue(NAME, "Cut");
        putValue(SHORT_DESCRIPTION, "Cut selected menu item");
    }

    public void actionPerformed(ActionEvent e) {
        ActionManager.getInstance().getPasteAction().setEnabled(true);
        ActionManager.getInstance().getPasteAction().forCut = true;
    }
}
