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

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;

/**
 * Edites exisint menu item in structure, thus enabling it's recovery to previous
 * state.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class EditItemCommand implements LxmedCommand {

    /**
     * Menu item's previous state.
     */
    private MenuItem previous;
    /**
     * Menu item's changed state.
     */
    private MenuItem next;
    /**
     * Real menu item in structure.
     */
    private MenuItem real;
    /**
     * Menu item's previous category.
     */
    private Category previousCategory;
    /**
     * Menu item's changed category.
     */
    private Category nextCategory;

    /**
     * Creates a new edit command.
     *
     * @param original original menu item with previous state
     * @param edited changed menu item
     */
    public EditItemCommand(MenuItem original, MenuItem edited) {
        try {
            previous = original;
            real = edited;
            next = (MenuItem) edited.clone();

            previousCategory = previous.getCategory();
            nextCategory = next.getCategory();

            original.setCategory(null);
            previous.setCategory(null);
            next.setCategory(null);
            edited.setCategory(null);
        } catch (CloneNotSupportedException ex) {
        }
    }

    /**
     * Sets appropriate properties to real menu item and then addresses to
     * {@link Model} to update changes.
     */
    @Override
    public void doCommand() {
        real.setCategory(nextCategory);
        real.setOriginalCode(next.getOriginalCode());
        real.setOriginalCategories(next.getOriginalCategories());
        real.setReadOnly(next.isReadOnly());
        real.getContent().clear();
        for (String key : next.getContent().keySet()) {
            real.getContent().put(key, next.getContent().get(key));
        }
        try {
            Model.getModel().updateMenuItem(real);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditItemCommand.class.getName()).log(Level.SEVERE, "edit redo error", ex);
        }
    }

    /**
     * Sets appropriate properties to real menu item previous state and then
     * addresses to {@link Model} to update changes.
     */
    @Override
    public void undoCommand() {
        real.setCategory(previousCategory);
        real.setOriginalCode(previous.getOriginalCode());
        real.setOriginalCategories(previous.getOriginalCategories());
        real.setReadOnly(previous.isReadOnly());
        real.getContent().clear();
        for (String key : previous.getContent().keySet()) {
            real.getContent().put(key, previous.getContent().get(key));
        }
        try {
            Model.getModel().updateMenuItem(real);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditItemCommand.class.getName()).log(Level.SEVERE, "edit undo error", ex);
        }
    }
}
