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
import javax.swing.KeyStroke;
import net.sourceforge.lxmed.clipboard.LxmedClipboard;
import net.sourceforge.lxmed.gui.MainFrame;
import net.sourceforge.lxmed.model.MenuItem;

/**
 * Copy action clones a selected menu item to clipboard.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CopyAction extends LxmedAbstractAction {

    /**
     * Creates a copy action.
     */
    public CopyAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        putValue(SMALL_ICON, loadIcon("/net/sourceforge/lxmed/images/dialogs/edit-copy.png"));
        putValue(NAME, "Copy");
        putValue(SHORT_DESCRIPTION, "Copy selected menu item");
    }

    /**
     * Copies selected menu item to clipboard. Then, it udpates clipboard buttons
     * on MainFrame.
     *
     * @param e not used
     */
    public void actionPerformed(ActionEvent e) {
        MenuItem selected = MainFrame.getInstance().getSelectedMenuItem();

        if (selected == null) {
            return;
        }

        LxmedClipboard.getClipboard().toClipboard(selected);
        MainFrame.getInstance().updateCliboardButtons();
    }
}
