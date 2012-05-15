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
package net.sourceforge.lxmed.persistence;

import net.sourceforge.lxmed.utils.Configuration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;

/**
 * Loads the entire model reading it's content from hard disk and then creates
 * data structure (package
 * <code>model</code>).
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ModelLoader {

    /**
     * Method which loads the model.
     *
     * @return the Model.
     * @see Model
     */
    public static Model load() {
        Model model = Model.getModel();

        File[] paths = new File[]{
            new File(Configuration.ROOT_APPS),
            new File(Configuration.ROOT_LOCAL_APPS),
            new File(Configuration.USER_APPS)};

        for (File path : paths) {
            if (path.exists()) {
                for (File file : path.listFiles()) {
                    if (file.getName().endsWith(".desktop")) {
                        loadDesktopFile(file);
                    }
                }
            }
        }

        // sort all items by name
        for (Category categorie : Model.getModel().getCategories()) {
            sortItemsByName(categorie);
        }

        return model;
    }

    /**
     * Loads specific file and makes MenuItem of it.
     *
     * @param file file to load
     * @return MenuItem derived from file
     */
    public static MenuItem loadDesktopFile(File file) {
        String code = "";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("=")) {
                    code = code.concat(line.trim()).concat("\n");
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelLoader.class.getName()).log(Level.SEVERE, "Error on loading desktop file", ex);
        } catch (IOException ioe) {
            Logger.getLogger(ModelLoader.class.getName()).log(Level.SEVERE, "Error no. 2 on loading desktop file", ioe);
        }

        MenuItem mi = loadData(code);
        mi.setPath(file);

        if (!Configuration.IS_ROOT) {
            for (String string : Configuration.getAdminFolders()) {
                if (mi.getPath().getParent().trim().equals(string.trim())) {
                    mi.setReadOnly(true);
                    break;
                } else {
                    mi.setReadOnly(false);
                }
            }
        } else {
            mi.setReadOnly(false);
        }

        extractCategorie(mi, mi.getOriginalCategories());

        return null;
    }

    /**
     * Returns MenuItem by given string which contains data describing it as
     * regular .desktop file.
     *
     * @param fileContent data string
     */
    public static MenuItem loadData(String fileContent) {
        Map<String, String> values = new LinkedHashMap<String, String>();

        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            if (line.contains("=")) {
                String[] strs = line.split("=");
                String rest = "";
                for (int i = 1; i < strs.length; i++) { // building value
                    rest += "=" + strs[i];
                }
                if (rest.length() > 0) {
                    rest = rest.substring(1);
                }

                values.put(strs[0], rest);
            }
        }

        // MenuItem creation based on extracted values
        MenuItem mi = new MenuItem();
        mi.setContent(values);
        mi.setOriginalCategories(values.get("Categories"));
        mi.setOriginalCode(fileContent);

        return mi;
    }

    /**
     * Extract menu item's category. This is tricky part, since .desktop file can
     * have more than one category value. However, this method extracts only the
     * first regular category and classifies item as that category.
     *
     * @param mi menu item
     * @param cat categories read from .desktop file
     */
    private static void extractCategorie(MenuItem mi, String cat) {
        if (cat == null || cat.trim().equals("")) {
            Model.getModel().getCategoryByCode("").add(mi);
            return;
        }

        String[] cats = cat.split(";");

        // check if contains Settings first
        for (String string : cats) {
            if (string.equals("Settings")) {
                Model.getModel().getCategoryByCode(string).add(mi);
                return;
            }
        }

        for (String string : cats) {
            if (Model.getModel().getCategoryByCode(string) != null) {
                Model.getModel().getCategoryByCode(string).add(mi);
                return;
            }
        }

        Model.getModel().getCategoryByCode("").add(mi);
    }

    /**
     * Sort all menu items in given category by menu item's name.
     *
     * @param c category to sort
     */
    public static void sortItemsByName(Category c) {
        Collections.sort(c.getItems(), new ItemNameComparator());
    }
}
