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
 * Cell renderer for menu items list in main form.
 * @author Marko Čičak <cicakmarko@yahoo.com>
 */
public class MenuItemsListCellRenderer extends DefaultListCellRenderer {

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

    private boolean rootOnly(MenuItem mi) {
        if (!Configuration.IS_ROOT
                && (mi.getPath().getAbsolutePath().trim().startsWith(Configuration.ROOT_APPS)
                || mi.getPath().getAbsolutePath().trim().startsWith(Configuration.ROOT_LOCAL_APPS))) {
            return true;
        }
        return false;
    }
}
