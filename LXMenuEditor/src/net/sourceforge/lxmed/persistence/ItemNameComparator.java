package net.sourceforge.lxmed.persistence;

import java.util.Comparator;
import net.sourceforge.lxmed.model.MenuItem;

/**
 * Comparator which compares menu items by it's name.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ItemNameComparator implements Comparator<MenuItem> {

    public int compare(MenuItem o1, MenuItem o2) {
        if (o1.getName() == null && o2.getName() == null) {
            return 0;
        }
        if (o1.getName() == null) {
            return 1;
        }
        if (o2.getName() == null) {
            return -1;
        }
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
