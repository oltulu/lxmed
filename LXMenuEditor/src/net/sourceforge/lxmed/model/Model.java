package net.sourceforge.lxmed.model;

import java.util.List;

/**
 * Data structure representing main menu.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Model {

    /** Main menu categories. */
    protected List<Categorie> categories;
    /** Singleton instance. */
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
        categories = Categorie.init();
    }

    /**
     * Returns main menu's categories.
     */
    public List<Categorie> getCategories() {
        return categories;
    }

    /**
     * Sets main menu's categories.
     * @param categories new categories
     */
    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    /**
     * Checks if main menu contains specific category.
     * @param categoryCode categorie's code name
     * @return true if exists, otherwise false
     */
    public boolean containsCategory(String categoryCode) {
        for (Categorie categorie : categories) {
            if (categorie.getCodeName().trim().equals(categoryCode.trim())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns category by it's code name
     * @param categoryCode categorie's code name
     */
    public Categorie getCategoryByCode(String categoryCode) {
        for (Categorie categorie : categories) {
            if (categorie.getCodeName().trim().equals(categoryCode.trim())) {
                return categorie;
            }
        }

        return null;
    }
}
