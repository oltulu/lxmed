package net.sourceforge.lxmed.persistence;

import java.util.Comparator;
import net.sourceforge.lxmed.model.MenuItem;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ItemNameComparator implements Comparator<MenuItem> {

    public int compare(MenuItem o1, MenuItem o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
