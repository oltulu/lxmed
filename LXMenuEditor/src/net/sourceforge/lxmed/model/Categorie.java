package net.sourceforge.lxmed.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Category in main menu. This class implements List interface so it can be
 * seen as list of menu items belonging to category.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Categorie implements List<MenuItem> {

    /** Category display name. */
    protected String displayName;
    /** Category code name. */
    protected String codeName;
    /** Category icon name. */
    protected String icon;
    /** Menu items belonging to category. */
    protected List<MenuItem> items = new ArrayList<MenuItem>();

    /**
     * Constructor.
     * @param displayName categorie's display name
     * @param codeName categorie's code name
     * @param icon categorie's icon name
     */
    public Categorie(String displayName, String codeName, String icon) {
        this.displayName = displayName;
        this.codeName = codeName;
        this.icon = icon;
    }

    public <T> T[] toArray(T[] a) {
        return items.toArray(a);
    }

    public Object[] toArray() {
        return items.toArray();
    }

    public List<MenuItem> subList(int fromIndex, int toIndex) {
        return items.subList(fromIndex, toIndex);
    }

    public int size() {
        return items.size();
    }

    public MenuItem set(int index, MenuItem element) {
        return items.set(index, element);
    }

    public boolean retainAll(Collection<?> c) {
        return items.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return items.removeAll(c);
    }

    public MenuItem remove(int index) {
        return items.remove(index);
    }

    public boolean remove(Object o) {
        return items.remove(o);
    }

    public ListIterator<MenuItem> listIterator(int index) {
        return items.listIterator(index);
    }

    public ListIterator<MenuItem> listIterator() {
        return items.listIterator();
    }

    public int lastIndexOf(Object o) {
        return items.lastIndexOf(o);
    }

    public Iterator<MenuItem> iterator() {
        return items.iterator();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int indexOf(Object o) {
        return items.indexOf(o);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    public MenuItem get(int index) {
        return items.get(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categorie other = (Categorie) obj;
        if ((this.codeName == null) ? (other.codeName != null) : !this.codeName.equals(other.codeName)) {
            return false;
        }
        if (this.items != other.items && (this.items == null || !this.items.equals(other.items))) {
            return false;
        }
        return true;
    }

    public boolean containsAll(Collection<?> c) {
        return items.containsAll(c);
    }

    public boolean contains(Object o) {
        return items.contains(o);
    }

    public void clear() {
        items.clear();
    }

    public boolean addAll(int index, Collection<? extends MenuItem> c) {
        return items.addAll(index, c);
    }

    public boolean addAll(Collection<? extends MenuItem> c) {
        return items.addAll(c);
    }

    public void add(int index, MenuItem element) {
        items.add(index, element);
        element.setCategorie(this);
    }

    public boolean add(MenuItem e) {
        boolean ret = items.add(e);
        e.setCategorie(this);
        return ret;
    }

    /**
     * Categorie's code name.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Sets categorie's code name.
     * @param codeName new code name
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Categorie's display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets categorie's display name.
     * @param displayName new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets categorie's menu items.
     * @param items new list of menu items
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * Categorie's icon name.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets categorie's icon name.
     * @param icon new icon name
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Returns a list of initialized categories which are found in most LXDE
     * systems.
     */
    public static List<Categorie> init() {
        List<Categorie> ret = new ArrayList<Categorie>();

        ret.add(new Categorie("Accessories", "Utility", "accessories.png"));
        ret.add(new Categorie("Education", "Education", "science.png"));
        ret.add(new Categorie("Games", "Game", "games.png"));
        ret.add(new Categorie("Graphics", "Graphics", "graphics.png"));
        ret.add(new Categorie("Internet", "Network", "internet.png"));
        ret.add(new Categorie("Office", "Office", "office.png"));
        ret.add(new Categorie("Other", "", "other.png"));
        ret.add(new Categorie("Programming", "Development", "development.png"));
        ret.add(new Categorie("Sound & Video", "AudioVideo", "multimedia.png"));
        ret.add(new Categorie("System Tools", "System", "system.png"));
        ret.add(new Categorie("Preferences", "Settings", "preferences.png"));

        return ret;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
