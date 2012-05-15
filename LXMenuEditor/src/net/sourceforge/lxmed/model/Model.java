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
package net.sourceforge.lxmed.model;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import net.sourceforge.lxmed.persistence.FileUtil;

/**
 * Data structure representing main menu.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Model extends Observable {

    /**
     * Main menu categories.
     */
    protected List<Category> categories;
    /**
     * Singleton instance.
     */
    private static Model model;

    /**
     * Returns singleton instance of the model.
     */
    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /**
     * Private constructor. Initializes categories.
     */
    private Model() {
        categories = Category.init();
    }

    /**
     * Returns main menu's categories.
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets main menu's categories.
     *
     * @param categories new categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Checks if main menu contains specific category.
     *
     * @param categoryCode categorie's code name
     * @return true if exists, otherwise false
     */
    public boolean containsCategory(String categoryCode) {
        for (Category categorie : categories) {
            if (categorie.getCodeName().trim().equals(categoryCode.trim())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns category by it's code name
     *
     * @param categoryCode categorie's code name
     */
    public Category getCategoryByCode(String categoryCode) {
        for (Category categorie : categories) {
            if (categorie.getCodeName().trim().equals(categoryCode.trim())) {
                return categorie;
            }
        }

        return null;
    }

    /**
     * Adds new menu item to model. Addresses file system to save new file
     * describing new menu item.
     *
     * @param toBeAdded menu item to be added
     * @throws FileNotFoundException should never be thrown
     */
    public void addMenuItem(MenuItem toBeAdded) throws FileNotFoundException {
        if (FileUtil.save(toBeAdded)) {
            fireStructureChanged();
        } else {
            Category c = toBeAdded.getCategory();
            toBeAdded.setCategory(null);
            c.remove(toBeAdded);
        }
    }

    /**
     * Model addresses file system to save menu item's file with new content.
     *
     * @param menuItem menu item to be updated in file system
     * @throws FileNotFoundException if file is not found
     */
    public void updateMenuItem(MenuItem menuItem) throws FileNotFoundException {
        if (FileUtil.save(menuItem)) {
            fireStructureChanged();
        }
    }

    /**
     * Deletes menu item from model. Model addresses file system to delete
     * appropriate file.
     *
     * @param menuItem menu item to be deleted
     */
    public void deleteMenuItem(MenuItem menuItem) {
        if (FileUtil.delete(menuItem)) {
            menuItem.setCategory(null);
            fireStructureChanged();
        }
    }

    /**
     * Sets model status to changed and notifies all listeners to update it's
     * state corresponding to Model.
     */
    private void fireStructureChanged() {
        setChanged();
        notifyObservers();
    }
}
