package net.sourceforge.lxmed.model;

import java.io.File;
import javax.swing.Icon;
import net.sourceforge.lxmed.LxmedException;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class MenuItem {

    protected String name;
    protected String genericName;
    protected String exec;
    protected String comment;
    protected File path;
    protected String iconStr;
    protected Icon icon;
    protected boolean noDisplay;
    protected Categorie categorie;

    public MenuItem() {
    }

    public MenuItem(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getIconStr() {
        return iconStr;
    }

    public void setIconStr(String iconStr) {
        this.iconStr = iconStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNoDisplay() {
        return noDisplay;
    }

    public void setNoDisplay(boolean noDisplay) {
        this.noDisplay = noDisplay;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getDesktopCode() {
        checkItem();

        String ret = "[Desktop Entry]\n";
        ret += "Encoding=UTF-8\n";
        ret += "Name=" + name + "\n";
        if (stringOk(comment)) {
            ret += "Comment=" + comment + "\n";
        }
        if (stringOk(exec)) {
            ret += "Exec=" + exec + "\n";
        }
        if (stringOk(iconStr)) {
            ret += "Icon=" + iconStr + "\n";
        }
        ret += "Type=Application\n";
        ret += "Terminal=false\n";
        ret += "StartNotify=false\n";
        ret += "NoDisplay=" + noDisplay + "\n";
        ret += "Categories=" + categorie.getCodeName();

        return ret;
    }

    @Override
    public String toString() {
        return name;
    }

    private boolean stringOk(String str) {
        if (str != null && !str.trim().equals("")) {
            return true;
        }
        return false;
    }

    private void checkItem() throws LxmedException {
        Object[] mandatories = new Object[]{name, /*exec,*/ categorie};

        for (Object object : mandatories) {
            if (object == null) {
                throw new LxmedException("Null reference in MenuItem mandatory field.");
            }

            if (object instanceof String) {
                if (((String) object).trim().equals("")) {
                    throw new LxmedException("Empty string.");
                }
            } else if (object instanceof Categorie) {
                if (((Categorie) object).getCodeName().trim().equals("")) {
                    throw new LxmedException("Empty string in categorie.");
                }
            }
        }
    }
}
