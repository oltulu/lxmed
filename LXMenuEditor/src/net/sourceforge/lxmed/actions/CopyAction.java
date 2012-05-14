package net.sourceforge.lxmed.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import net.sourceforge.lxmed.clipboard.LxmedClipboard;
import net.sourceforge.lxmed.gui.MainFrame;
import net.sourceforge.lxmed.model.MenuItem;

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
        MenuItem selected = MainFrame.getInstance().getSelectedMenuItem();

        if (selected == null) {
            return;
        }

        LxmedClipboard.getClipboard().toClipboard(selected);
    }
}
