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
package net.sourceforge.lxmed.commands;

import net.sourceforge.lxmed.LxmedException;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;

/**
 * Command which adds a new menu item to LXDE menu structure.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class NewItemCommand implements LxmedCommand {

    /**
     * Menu item which will be added on redo.
     */
    private MenuItem menuItem;
    /**
     * Category which will contain menu item on redo.
     */
    private Category category;

    /**
     * Creates new menu item command.
     *
     * @param mi menu item to be added
     */
    public NewItemCommand(MenuItem newMenuItem) {
        menuItem = newMenuItem;
        category = menuItem.getCategory();
    }

    /**
     * Sets a category to menu item and addresses {@link Model} to add menu item
     * which saves menu item to file system.
     */
    @Override
    public void doCommand() {
        try {
            menuItem.setCategory(category);
            Model.getModel().addMenuItem(menuItem);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LxmedException(e.getMessage());
        }
    }

    /**
     * Addresses a {@link Model} to delete menu item from structure.
     */
    @Override
    public void undoCommand() {
        try {
            Model.getModel().deleteMenuItem(menuItem);
        } catch (Exception e) {
        }
    }
}
