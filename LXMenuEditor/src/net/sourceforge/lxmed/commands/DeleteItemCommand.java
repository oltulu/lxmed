package net.sourceforge.lxmed.commands;

import net.sourceforge.lxmed.LxmedException;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class DeleteItemCommand extends LxmedAbstractCommand {

    private MenuItem menuItem;
    private Category category;

    public DeleteItemCommand(MenuItem toDelete) {
        this.menuItem = toDelete;
        category = toDelete.getCategory();
    }

    @Override
    public void doCommand() {
        try {
            Model.getModel().deleteMenuItem(menuItem);
        } catch (Exception e) {
        }
    }

    @Override
    public void undoCommand() {
        try {
            menuItem.setCategory(category);
            Model.getModel().addMenuItem(menuItem);
        } catch (Exception e) {
            // TODO: text
            throw new LxmedException("error");
        }
    }
}
