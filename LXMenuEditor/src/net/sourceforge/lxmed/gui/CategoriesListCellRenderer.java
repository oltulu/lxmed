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
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CategoriesListCellRenderer extends DefaultListCellRenderer {

    private Map<String, Icon> icons = new HashMap<String, Icon>();

    public CategoriesListCellRenderer(List<Category> categories) {
        for (Category categorie : categories) {
            Icon icon = new ImageIcon(getClass().getResource("/images/categories/" + categorie.getIcon()));
            this.icons.put(categorie.getCodeName(), icon);
        }
    }

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
