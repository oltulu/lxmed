package net.sourceforge.lxmed.model;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import net.sourceforge.lxmed.LxmedException;

/**
 * Menu item in LXDE's main menu.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class MenuItem implements Cloneable {

    public static String CATEGORIES = "Categories";
    /**
     * File path of this menu item's .desktop file.
     */
    protected File path;
    /**
     * Menu item's category
     */
    protected Category category;
    /**
     * Original string of Categories property.
     */
    protected String originalCategories;
    /**
     * Original content in .desktop file before loading it into MenuItem object.
     */
    protected String originalCode;
    /**
     * Weather user can edit this menu item.
     */
    protected boolean readonly = true;
    /**
     * Content map used to store values from .desktop file.
     */
    protected Map<String, String> content = new LinkedHashMap<String, String>();

    /**
     * Empty constructor.
     */
    public MenuItem() {
    }

    /**
     * Constructor which receives item's name as parameter.
     *
     * @param name menu item's name
     */
    public MenuItem(String name) {
        content.put("Name", name);
    }

    /**
     * Copy constructor
     *
     * @param mi menu item to copy
     */
    public MenuItem(MenuItem mi) {
        this.path = mi.path;
        this.category = mi.category;
        this.originalCategories = mi.originalCategories;
        this.originalCode = mi.originalCode;
        this.readonly = mi.readonly;
        for (String string : mi.content.keySet()) {
            this.content.put(string, mi.content.get(string));
        }
    }

    /**
     * Menu item's comment.
     */
    public String getComment() {
        return content.get("Comment");
    }

    /**
     * Sets menu item's comment.
     *
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
     *
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
     *
     * @param genericName new generic name
     */
    public void setGenericName(String genericName) {
        content.put("GenericName", genericName);
    }

    /**
     * Menu item's .desktop file Icon string value.
     */
    public String getIconStr() {
        return content.get("Icon");
    }

    /**
     * Sets new value for Icon in .desktop file.
     *
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
     *
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
     *
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
     *
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
     *
     * @param category new category
     */
    public void setCategory(Category newCategory) {
        if (this.category == null || !this.category.equals(newCategory)) {
            if (this.category != null) {
                Category oldCategory = this.category;
                this.category = null;
                content.remove(CATEGORIES);
                oldCategory.remove(this);
            }
            if (newCategory != null) {
                this.category = newCategory;
                content.put(CATEGORIES, newCategory.getCodeName());
                this.category.add(this);
            }
        }
    }

    /**
     * True if this menu item can be edited only by root user.
     */
    public boolean isReadOnly() {
        return readonly;
    }

    /**
     * Sets access permission for this menu item.
     *
     * @param onlyForAdmin true if only root user can edit this menu item
     */
    public void setReadOnly(boolean readonly) {
        this.readonly = readonly;
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
     *
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
     *
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
     *
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

        if (!content.containsKey("Type")) {
            content.put("Type", "Application");
        }

        if (!content.containsKey(CATEGORIES) && category != null) {
            content.put(CATEGORIES, category.getCodeName());
        }

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
     *
     * @throws LxmedException if there is an error in data
     */
    private void checkItem() throws LxmedException {
        Object[] mandatories = new Object[]{getName(), category};

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
     *
     * @param newMenuItem menu item from which data is cloned
     */
    public void cloneData(MenuItem newMenuItem) {
        content.clear();
        if (newMenuItem.getCategory() != null) {
            setCategory(newMenuItem.getCategory());
        }

        for (String key : newMenuItem.getContent().keySet()) {
            content.put(key, newMenuItem.getContent().get(key));
        }

        setOriginalCategories(newMenuItem.getOriginalCategories());
        setReadOnly(newMenuItem.readonly);

        if (newMenuItem.getPath() != null) {
            setPath(newMenuItem.getPath());
        }
    }

    public boolean isVisible() {
        if (content.containsKey("OnlyShowIn")) {
            String[] values = content.get("OnlyShowIn").split(";");
            for (String s : values) {
                if (s.trim().equals("LXDE")) {
                    return true;
                }
            }
            return false;
        }

        if (content.containsKey("NotShowIn")) {
            String[] values = content.get("NotShowIn").split(";");
            for (String s : values) {
                if (s.trim().equals("LXDE")) {
                    return false;
                }
            }
            return true;
        }

        return !isNoDisplay();
    }

    public String putToContentMap(String key, String value) {
        return content.put(key, value);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MenuItem ret = new MenuItem();
        ret.setCategory(category);
        ret.setOriginalCategories(originalCategories);
        ret.setOriginalCode(originalCode);
        ret.setReadOnly(readonly);
        ret.setPath(new File(path.getAbsolutePath()));

        for (String key : content.keySet()) {
            ret.content.put(key, content.get(key));
        }

        return ret;
    }
}
