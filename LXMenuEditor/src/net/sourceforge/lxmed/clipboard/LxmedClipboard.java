package net.sourceforge.lxmed.clipboard;

import net.sourceforge.lxmed.model.MenuItem;

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
        return toPaste;
    }

    /**
     * Sets new menu item to clipboard. If there was an old one, it is replaced
     * by the new menu item.
     *
     * @param menuItem menu item to be set on clipboard.
     */
    public void toClipboard(MenuItem menuItem) {
        toPaste = menuItem;
    }
}
