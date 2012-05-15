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

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import net.sourceforge.lxmed.model.Category;

/**
 * Renderer for Categories items in main form.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CategoriesListCellRenderer extends DefaultListCellRenderer {

    /**
     * Cache for all category icons.
     */
    private Map<String, Icon> icons = new HashMap<String, Icon>();

    /**
     * Creates a new cell renderer.
     *
     * @param categories existing categories
     */
    public CategoriesListCellRenderer(List<Category> categories) {
        for (Category categorie : categories) {
            Icon icon = new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/categories/" + categorie.getIcon()));
            this.icons.put(categorie.getCodeName(), icon);
        }
    }

    /**
     * Returns a rendered {@link Component} thus containing an icon left to
     * categoriy's name.
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label =
                (JLabel) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        Category c = (Category) value;
        Icon icon = icons.get(c.getCodeName());
        label.setIcon(icon);
        return label;
    }
}
