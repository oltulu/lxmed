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
public class NewItemCommand extends LxmedAbstractCommand {

    private MenuItem menuItem;
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

    @Override
    public void undoCommand() {
        try {
            Model.getModel().deleteMenuItem(menuItem);
        } catch (Exception e) {
        }
    }
}
