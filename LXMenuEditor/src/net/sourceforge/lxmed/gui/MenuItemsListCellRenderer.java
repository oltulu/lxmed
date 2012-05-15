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
package net.sourceforge.lxmed.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.utils.Configuration;

/**
 * Cell renderer for menu items list in main form. If a user doesn't have access
 * to item, item is painted red. If item is a <i>wine</i> launcher, item is
 * painted yellow, otherwise item is painted green.<br/> If item is visible, it
 * is in bold style, otherwise it is in regular style.
 *
 * @author Marko Čičak <cicakmarko@yahoo.com>
 */
public class MenuItemsListCellRenderer extends DefaultListCellRenderer {

    /**
     * Returns a rendered label for menu item list. If a user doesn't have access
     * to item, item is painted red. If item is a <i>wine</i> launcher, item is
     * painted yellow, otherwise item is painted green.<br/> If item is visible,
     * it is in bold style, otherwise it is in regular style.
     */
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        // Get the renderer component from parent class
        JLabel label =
                (JLabel) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        MenuItem mi = (MenuItem) list.getModel().getElementAt(index);

        try {

            if (mi != null && rootOnly(mi)) {
                label.setForeground(new Color(170, 0, 0)); // red
            } else if (mi.getExec().toLowerCase().contains("wine")) {
                label.setForeground(new Color(170, 170, 0)); // yellow
            } else {
                label.setForeground(new Color(0, 170, 0)); // green
            }

            if (mi != null && mi.isVisible()) {
                label.setFont(label.getFont().deriveFont(Font.BOLD));
            }

        } catch (Exception e) {
        }
        // Get icon to use for the list item value

        //Icon icon = icons.get(value);

        // Set icon to display for value

        //label.setIcon(icon);
        return label;
    }

    /**
     * Checks if menu item can only be edited by root user.
     *
     * @param mi menu item
     * @return true if menu item can only be edited by root user, otherwise false
     */
    private boolean rootOnly(MenuItem mi) {
        if (!Configuration.IS_ROOT
                && (mi.getPath().getAbsolutePath().trim().startsWith(Configuration.ROOT_APPS)
                || mi.getPath().getAbsolutePath().trim().startsWith(Configuration.ROOT_LOCAL_APPS))) {
            return true;
        }
        return false;
    }
}
