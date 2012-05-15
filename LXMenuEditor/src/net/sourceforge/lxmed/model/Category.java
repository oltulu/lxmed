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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Category in main menu. This class implements List interface so it can be seen
 * as list of menu items belonging to category.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Category implements Iterable<MenuItem>/*
 * implements List<MenuItem>
 */ {

    /**
     * Category display name.
     */
    protected String displayName;
    /**
     * Category code name.
     */
    protected String codeName;
    /**
     * Category icon name.
     */
    protected String icon;
    /**
     * Menu items belonging to category.
     */
    protected List<MenuItem> items = new ArrayList<MenuItem>();

    /**
     * Constructor.
     *
     * @param displayName categorie's display name
     * @param codeName categorie's code name
     * @param icon categorie's icon name
     */
    public Category(String displayName, String codeName, String icon) {
        this.displayName = displayName;
        this.codeName = codeName;
        this.icon = icon;
    }

    public void add(MenuItem newMenuItem) {
        if (newMenuItem == null) {
            return;
        }
        if (this.items == null) {
            this.items = new java.util.ArrayList<MenuItem>();
        }
        if (!this.items.contains(newMenuItem)) {
            this.items.add(newMenuItem);
            newMenuItem.setCategory(this);
        }
    }

    public void remove(MenuItem oldMenuItem) {
        if (oldMenuItem == null) {
            return;
        }
        if (this.items != null) {
            if (this.items.contains(oldMenuItem)) {
                this.items.remove(oldMenuItem);
                oldMenuItem.setCategory((Category) null);
            }
        }
    }

    /**
     * Categorie's code name.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Sets categorie's code name.
     *
     * @param codeName new code name
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Categorie's display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets categorie's display name.
     *
     * @param displayName new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets categorie's menu items.
     *
     * @param items new list of menu items
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * Categorie's icon name.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets categorie's icon name.
     *
     * @param icon new icon name
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Returns a list of initialized categories which are found in most LXDE
     * systems.
     */
    public static List<Category> init() {
        List<Category> ret = new ArrayList<Category>();

        ret.add(new Category("Accessories", "Utility", "accessories.png"));
        ret.add(new Category("Education", "Education", "science.png"));
        ret.add(new Category("Games", "Game", "games.png"));
        ret.add(new Category("Graphics", "Graphics", "graphics.png"));
        ret.add(new Category("Internet", "Network", "internet.png"));
        ret.add(new Category("Office", "Office", "office.png"));
        ret.add(new Category("Other", "", "other.png"));
        ret.add(new Category("Programming", "Development", "development.png"));
        ret.add(new Category("Sound & Video", "AudioVideo", "multimedia.png"));
        ret.add(new Category("System Tools", "System", "system.png"));
        ret.add(new Category("Preferences", "Settings", "preferences.png"));

        return ret;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public Iterator<MenuItem> iterator() {
        return items.iterator();
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
