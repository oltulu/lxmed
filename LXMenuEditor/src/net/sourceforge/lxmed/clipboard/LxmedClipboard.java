package net.sourceforge.lxmed.clipboard;

import java.io.File;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.persistence.FileUtil;
import net.sourceforge.lxmed.utils.Configuration;

/**
 * Lxmed Clipboard can persist up to one pasted menu item.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class LxmedClipboard {

    /**
     * Pasted item.
     */
    private MenuItem toPaste = null;
    /**
     * Singleton instance.
     */
    private static LxmedClipboard clipboard;
    /**
     * If a clipboard is filled with cut action, this is set to true.
     */
    private boolean forCut = false;

    /**
     * Private constructor.
     */
    private LxmedClipboard() {
    }

    /**
     * Lxmed Clipbaord.
     */
    public static LxmedClipboard getClipboard() {
        if (clipboard == null) {
            clipboard = new LxmedClipboard();
        }
        return clipboard;
    }

    /**
     * Menu item to paste.
     *
     * @return null if clipboard is empty, otherwise returns menu item to paste
     */
    public MenuItem getToPaste() {
        try {
            return (MenuItem) toPaste.clone();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Sets new menu item to clipboard. If there was an old one, it is replaced
     * by the new menu item.
     *
     * @param menuItem menu item to be set on clipboard.
     */
    public void toClipboard(MenuItem menuItem) {
        try {
            toPaste = (MenuItem) menuItem.clone();
            toPaste.setReadOnly(false);
            toPaste.setCategory(null);
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }

    public void emptyClipboard() {
        forCut = false;
        toPaste = null;
    }

    public boolean isEmpty() {
        return toPaste == null;
    }

    public boolean isForCut() {
        return forCut;
    }

    public void setForCut(boolean forCut) {
        this.forCut = forCut;
    }
}
