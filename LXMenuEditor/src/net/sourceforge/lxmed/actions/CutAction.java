package net.sourceforge.lxmed.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import net.sourceforge.lxmed.clipboard.LxmedClipboard;
import net.sourceforge.lxmed.commands.CommandManager;
import net.sourceforge.lxmed.commands.DeleteItemCommand;
import net.sourceforge.lxmed.gui.MainFrame;
import net.sourceforge.lxmed.model.MenuItem;

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
        LxmedClipboard lc = LxmedClipboard.getClipboard();

        MenuItem selected = MainFrame.getInstance().getSelectedMenuItem();
        if (selected == null) {
            return;
        }

        lc.toClipboard(selected);
        CommandManager.getInstance().addCommand(new DeleteItemCommand(selected));
        lc.setForCut(true);
        MainFrame.getInstance().updateCliboardButtons();
    }
}
