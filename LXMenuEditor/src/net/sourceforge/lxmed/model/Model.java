package net.sourceforge.lxmed.model;

import java.util.List;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Model {

    protected List<Categorie> categories;
    private static Model model;

    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    private Model() {
        categories = Categorie.init();
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
}
