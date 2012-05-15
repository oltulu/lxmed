package net.sourceforge.lxmed.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.KeyStroke;
import net.sourceforge.lxmed.clipboard.LxmedClipboard;
import net.sourceforge.lxmed.commands.CommandManager;
import net.sourceforge.lxmed.commands.NewItemCommand;
import net.sourceforge.lxmed.gui.MainFrame;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.persistence.FileUtil;
import net.sourceforge.lxmed.utils.Configuration;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class PasteAction extends LxmedAbstractAction {

    public PasteAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_V);
        putValue(SMALL_ICON, loadIcon("/net/sourceforge/lxmed/images/dialogs/edit-paste.png"));
        putValue(NAME, "Paste");
        putValue(SHORT_DESCRIPTION, "Paste menu item");
    }

    public void actionPerformed(ActionEvent e) {
        LxmedClipboard lc = LxmedClipboard.getClipboard();

        Category category = MainFrame.getInstance().getSelectedCategory();

        if (lc.isEmpty() || category == null) {
            return;
        }

        MenuItem toPaste = lc.getToPaste();
        toPaste.setCategory(category);

        if (Configuration.IS_ROOT) {
            toPaste.setPath(new File(Configuration.ROOT_APPS + "/" + FileUtil.getTimestampedFileName(toPaste.getName())));
        } else {
            toPaste.setPath(new File(Configuration.USER_APPS + "/" + FileUtil.getTimestampedFileName(toPaste.getName())));
        }

        NewItemCommand nic = new NewItemCommand(toPaste);
        CommandManager.getInstance().addCommand(nic);

        if (lc.isForCut()) {
            lc.emptyClipboard();
            lc.setForCut(false);
        }

        MainFrame.getInstance().updateCliboardButtons();
    }
}
