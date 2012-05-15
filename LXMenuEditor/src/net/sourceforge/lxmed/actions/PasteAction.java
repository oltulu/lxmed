// lxmed - LXDE Main Menu Editor
// Copyright (C) 2011  Marko Čičak
//
// This file is part of lxmed.
//
// lxmed is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// lxmed is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with lxmed.  If not, see <http://www.gnu.org/licenses/>.
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
 * Paste action copies a menu item from clipboard to selected category.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class PasteAction extends LxmedAbstractAction {

    /**
     * Creates an action.
     */
    public PasteAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_V);
        putValue(SMALL_ICON, loadIcon("/net/sourceforge/lxmed/images/dialogs/edit-paste.png"));
        putValue(NAME, "Paste");
        putValue(SHORT_DESCRIPTION, "Paste menu item");
    }

    /**
     * Copies menu item, if any, from clipboard to selected category. Determines
     * selected category. Checks if it contains any menu item. If it does, item's
     * path is set depending on whether a user is root or regular user. Then it
     * creates a {@link NewItemCommand} with menu item from clipboard as new
     * item. Last, if a Pasted item was created by Cut action, Cliboard is
     * emptied.
     *
     * @param e not used
     */
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
