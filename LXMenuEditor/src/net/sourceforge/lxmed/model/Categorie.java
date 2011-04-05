package net.sourceforge.lxmed.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Categorie implements List<MenuItem> {

    protected String displayName;
    protected String codeName;
    protected List<MenuItem> items = new ArrayList<MenuItem>();

    public Categorie(String displayName, String codeName) {
        this.displayName = displayName;
        this.codeName = codeName;
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

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public static List<Categorie> init() {
        List<Categorie> ret = new ArrayList<Categorie>();

        ret.add(new Categorie("Accessories", "Utility"));
        ret.add(new Categorie("Education", "Education"));
        ret.add(new Categorie("Games", "Game"));
        ret.add(new Categorie("Graphics", "Graphics"));
        ret.add(new Categorie("Internet", "Network"));
        ret.add(new Categorie("Office", "Office"));
        ret.add(new Categorie("Other", ""));
        ret.add(new Categorie("Programming", "Development"));
        ret.add(new Categorie("Sound & Video", "AudioVideo"));
        ret.add(new Categorie("System Tools", "System"));
        ret.add(new Categorie("Preferences", "Settings"));

        return ret;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
