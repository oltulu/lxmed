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

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.metal.MetalIconFactory;

public class IconListRenderer
        extends DefaultListCellRenderer {

    private Map<Object, Icon> icons = null;

    public IconListRenderer(Map<Object, Icon> icons) {
        this.icons = icons;
    }

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        // Get the renderer component from parent class

        JLabel label =
                (JLabel) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);
        if(index==1)label.setFont(new Font("Times New Roman", Font.BOLD, 12));

        // Get icon to use for the list item value

        Icon icon = icons.get(value);

        // Set icon to display for value

        label.setIcon(icon);
        return label;
    }

    public static void main(String[] args) {

        // setup mappings for which icon to use for each value

        Map<Object, Icon> icons = new HashMap<Object, Icon>();
        icons.put("details",
                MetalIconFactory.getFileChooserDetailViewIcon());
        icons.put("folder",
                MetalIconFactory.getTreeFolderIcon());
        icons.put("computer",
                MetalIconFactory.getTreeComputerIcon());

        JFrame frame = new JFrame("Icon List");
        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        // create a list with some test data

        JList list = new JList(
                new Object[]{
                    "details", "computer", "folder", "computer"});

        // create a cell renderer to add the appropriate icon

        list.setCellRenderer(new IconListRenderer(icons));
        frame.add(list);
        frame.pack();
        frame.setVisible(true);
    }
}
