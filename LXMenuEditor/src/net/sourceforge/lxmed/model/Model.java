package net.sourceforge.lxmed.model;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import net.sourceforge.lxmed.persistence.FileUtil;

/**
 * Data structure representing main menu.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Model extends Observable {

    /**
     * Main menu categories.
     */
    protected List<Category> categories;
    /**
     * Singleton instance.
     */
    private static Model model;

    /**
     * Returns singleton instance of the model.
     */
    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /**
     * Private constructor. Initializes categories.
     */
    private Model() {
        categories = Category.init();
    }

    /**
     * Returns main menu's categories.
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets main menu's categories.
     *
     * @param categories new categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Checks if main menu contains specific category.
     *
     * @param categoryCode categorie's code name
     * @return true if exists, otherwise false
     */
    public boolean containsCategory(String categoryCode) {
        for (Category categorie : categories) {
            if (categorie.getCodeName().trim().equals(categoryCode.trim())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns category by it's code name
     *
     * @param categoryCode categorie's code name
     */
    public Category getCategoryByCode(String categoryCode) {
        for (Category categorie : categories) {
            if (categorie.getCodeName().trim().equals(categoryCode.trim())) {
                return categorie;
            }
        }

        return null;
    }

    public void addMenuItem(MenuItem toBeAdded) throws FileNotFoundException {
        if (FileUtil.save(toBeAdded)) {
            setChanged();
            notifyObservers();
        } else {
            Category c = toBeAdded.getCategory();
            toBeAdded.setCategory(null);
            c.remove(toBeAdded);
        }
    }

    public void updateMenuItem(MenuItem menuItem) throws FileNotFoundException {
        if (FileUtil.save(menuItem)) {
            setChanged();
            notifyObservers();
        }
    }

    public void deleteMenuItem(MenuItem menuItem) {
        if (FileUtil.delete(menuItem)) {
            menuItem.setCategory(null);
            for (Category category : categories) {
                Iterator<MenuItem> it = category.iterator();
                while (it.hasNext()) {
                    MenuItem item = it.next();
                    if (item.equals(menuItem)) {
                        it.remove();
                    }
                }
            }
            setChanged();
            notifyObservers();
        }
    }

    public void fireStructureChanged() {
        setChanged();
        notifyObservers();
    }
}
