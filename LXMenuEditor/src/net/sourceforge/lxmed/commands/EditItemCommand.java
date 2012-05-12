package net.sourceforge.lxmed.commands;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class EditItemCommand extends LxmedAbstractCommand {

    private MenuItem previous;
    private MenuItem next;
    private MenuItem real;
    private Category previousCategory;
    private Category nextCategory;

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

    @Override
    public void doCommand() {
        real.setCategory(nextCategory);
        real.setOriginalCode(next.getOriginalCode());
        real.setOriginalCategories(next.getOriginalCategories());
        real.setReadOnly(next.isReadOnly());
        real.setContent(next.getContent());
        try {
            Model.getModel().updateMenuItem(real);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditItemCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void undoCommand() {
        real.setCategory(previousCategory);
        real.setOriginalCode(previous.getOriginalCode());
        real.setOriginalCategories(previous.getOriginalCategories());
        real.setReadOnly(previous.isReadOnly());
        real.setContent(previous.getContent());
        try {
            Model.getModel().updateMenuItem(real);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditItemCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
