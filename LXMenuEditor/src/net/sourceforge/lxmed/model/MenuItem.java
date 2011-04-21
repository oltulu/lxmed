package net.sourceforge.lxmed.model;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.Icon;
import net.sourceforge.lxmed.LxmedException;

/**
 * Menu item in LXDE's main menu.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class MenuItem {

    /** File path of this menu item's .desktop file. */
    protected File path;
    /** Menu Item's icon. */
    protected Icon icon;
    /** Menu item's category */
    protected Category category;
    protected String originalCategories;
    protected String originalCode;
    protected boolean onlyForAdmin = true;
    protected Map<String, String> content = new LinkedHashMap<String, String>();

    /**
     * Empty constructor.
     */
    public MenuItem() {
    }

    /**
     * Constructor which receives item's name as parameter.
     * @param name menu item's name
     */
    public MenuItem(String name) {
        content.put("Name", name);
    }

    /**
     * Menu item's comment.
     */
    public String getComment() {
        return content.get("Comment");
    }

    /**
     * Sets menu item's comment.
     * @param comment new comment
     */
    public void setComment(String comment) {
        content.put("Comment", comment);
    }

    /**
     * Menu item's execution command.
     */
    public String getExec() {
        return content.get("Exec");
    }

    /**
     * Sets menu item's execution command.
     * @param exec new execution command
     */
    public void setExec(String exec) {
        content.put("Exec", exec);
    }

    /**
     * Menu item's generic name.
     */
    public String getGenericName() {
        return content.get("GenericName");
    }

    /**
     * Sets menu item's generic name.
     * @param genericName new generic name
     */
    public void setGenericName(String genericName) {
        content.put("GenericName", genericName);
    }

    /**
     * Menu item's icon.
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Sets menu item's icon.
     * @param icon new icon
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * Menu item's .desktop file Icon string value.
     */
    public String getIconStr() {
        return content.get("Icon");
    }

    /**
     * Sets new value for Icon in .desktop file.
     * @param iconStr new icon string
     */
    public void setIconStr(String iconStr) {
        content.put("Icon", iconStr);
    }

    /**
     * Menu item's name.
     */
    public String getName() {
        return content.get("Name");
    }

    /**
     * Sets menu item's name.
     * @param name new name
     */
    public void setName(String name) {
        content.put("Name", name);
    }

    /**
     * Menu item's no-display attribute. True if item is not visible in menu.
     */
    public boolean isNoDisplay() {
        String str = content.get("NoDisplay");
        if (str != null && str.toLowerCase().trim().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets item's visibility in main menu.
     * @param noDisplay true if item should be hidden, false if item should be
     * visible
     */
    public void setNoDisplay(boolean noDisplay) {
        content.put("NoDisplay", Boolean.toString(noDisplay));
    }

    /**
     * Menu item's .desktop file path.
     */
    public File getPath() {
        return path;
    }

    /**
     * Sets menu item's .desktop file path.
     * @param path new file
     */
    public void setPath(File path) {
        this.path = path;
    }

    /**
     * Menu item's category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets menu item's category.
     * @param category new category
     */
    public void setCategory(Category category) {
        if (!category.contains(this)) {
            if (this.category != null) {
                this.category.remove(this);
            }
            category.add(this);
        }
        this.category = category;
        content.put("Categories", category.getCodeName());
    }

    /**
     * True if this menu item can be edited only by root user.
     */
    public boolean isOnlyForAdmin() {
        return onlyForAdmin;
    }

    /**
     * Sets access permission for this menu item.
     * @param onlyForAdmin true if only root user can edit this menu item
     */
    public void setOnlyForAdmin(boolean onlyForAdmin) {
        this.onlyForAdmin = onlyForAdmin;
    }

    /**
     * Menu item's original categories (even multiple categories are shown if
     * they were first loaded)
     */
    public String getOriginalCategories() {
        return originalCategories;
    }

    /**
     * Sets menu item's original categories.
     * @param originalCategories new original categories
     */
    public void setOriginalCategories(String originalCategories) {
        this.originalCategories = originalCategories;
    }

    /**
     * Menu item's original code.
     */
    public String getOriginalCode() {
        return originalCode;
    }

    /**
     * Sets menu item's original code.
     * @param originalCode new original code
     */
    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    /**
     * Menu item's content map. Contains all values from .desktop file in this
     * map.
     */
    public Map<String, String> getContent() {
        return content;
    }

    /**
     * Sets menu item's content map.
     * @param content new content map
     */
    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    /**
     * Code which will be written to .desktop file upon save.
     */
    public String getDesktopCode() {
        checkItem();

        String ret = "[Desktop Entry]\n";

        for (String key : content.keySet()) {
            ret += key + "=" + content.get(key) + "\n";
        }

        return ret;
    }

    /**
     * Returns menu item's name.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Checks wheather mandatory values are not null or empty strings.
     * @throws LxmedException if there is an error in data
     */
    private void checkItem() throws LxmedException {
        Object[] mandatories = new Object[]{getName(), /*exec,*/ category};

        for (Object object : mandatories) {
            if (object == null) {
                throw new LxmedException("Null reference in MenuItem mandatory field.");
            }

            if (object instanceof String) {
                if (((String) object).trim().equals("")) {
                    throw new LxmedException("Empty string.");
                }
            } else if (object instanceof Category) {
//                if (((Categorie) object).getCodeName().trim().equals("")) {
//                    throw new LxmedException("Empty string in categorie.");
//                }
            }
        }
    }

    /**
     * Clones data from given menu item into this menu item.
     * @param newMenuItem menu item from which data is cloned
     */
    public void cloneData(MenuItem newMenuItem) {
        if (newMenuItem.getCategory() != null) {
            setCategory(newMenuItem.getCategory());
        }

        for (String key : newMenuItem.getContent().keySet()) {
            content.put(key, newMenuItem.getContent().get(key));
        }

        setIcon(newMenuItem.getIcon());
        setOriginalCategories(newMenuItem.getOriginalCategories());

        if (newMenuItem.getPath() != null) {
            setPath(newMenuItem.getPath());
        }
    }
}
