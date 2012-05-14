package net.sourceforge.lxmed.test;

import java.io.File;
import junit.framework.TestCase;
import net.sourceforge.lxmed.commands.*;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;
import net.sourceforge.lxmed.utils.Configuration;

/**
 * Tests command manager and generally undo/redo actions. new(a) a1 a2 a(change
 * category) new(b) b1 del(a) b2 b2(change category) b3 del(b)
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CommandsTest extends TestCase {

    public void testCommandManager() throws CloneNotSupportedException {
        MenuItem a = create("a");

        addCommand(new NewItemCommand(a));

        changeNameTo("a1", a);
        changeNameTo("a2", a);

        changeCategory(a);

        MenuItem b = create("b");
        addCommand(new NewItemCommand(b));
        changeNameTo("b1", b);

        addCommand(new DeleteItemCommand(a));

        changeNameTo("b2", b);
        changeCategory(b);
        changeNameTo("b3", b);

        addCommand(new DeleteItemCommand(b));

        // all items are deleted
        for (Category category : Model.getModel().getCategories()) {
            assertEquals(true, category.getItems().isEmpty());
        }

        CommandManager.getInstance().undoCommand();

        // count all items in all categories. value should now be 1
        int items = 0;
        for (Category category : Model.getModel().getCategories()) {
            for (MenuItem menuItem : category.getItems()) {
                items++;
            }
        }
        assertEquals(1, items);

        CommandManager.getInstance().undoCommand();
        CommandManager.getInstance().undoCommand();
        CommandManager.getInstance().undoCommand();
        CommandManager.getInstance().undoCommand();

        // count all items in all categories. value should now be 2
        items = 0;
        for (Category category : Model.getModel().getCategories()) {
            for (MenuItem menuItem : category.getItems()) {
                items++;
            }
        }
        assertEquals(2, items);

        // after these undo commands, b should be named 'b1' and placed in first category (Accessories)
        assertEquals("b1", Model.getModel().getCategories().get(0).getItems().get(0).getName());
        // after these undo commands, a should be named 'a2' and placed in second category (Education)
        assertEquals("a2", Model.getModel().getCategories().get(1).getItems().get(0).getName());
    }

    private void addCommand(LxmedCommand command) {
        CommandManager.getInstance().addCommand(command);
    }

    private MenuItem create(String name) {
        MenuItem ret = new MenuItem(name);
        ret.setExec(name);
        ret.setCategory(Model.getModel().getCategoryByCode("Utility"));
        String path = Configuration.USER_APPS + "/" + name + ".desktop";
        ret.setPath(new File(path));
        return ret;
    }

    private void changeNameTo(String name, MenuItem original) throws CloneNotSupportedException {
        MenuItem orig = (MenuItem) original.clone();
        original.setName(name);
        addCommand(new EditItemCommand(orig, original));
    }

    private void changeCategory(MenuItem item) throws CloneNotSupportedException {
        MenuItem orig = (MenuItem) item.clone();
        item.setCategory(Model.getModel().getCategoryByCode("Education"));
        addCommand(new EditItemCommand(orig, item));
    }
}
