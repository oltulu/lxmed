package net.sourceforge.lxmed.model;

import java.io.File;
import javax.swing.Icon;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class MenuItem {

    private String name;
    private String genericName;
    private String exec;
    private String comment;
    private File path;
    private String iconStr;
    private Icon icon;
    private boolean noDisplay;

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

    @Override
    public String toString() {
        return name;
    }
}
