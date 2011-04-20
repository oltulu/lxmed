package net.sourceforge.lxmed.utils;

/**
 * Global variables needed for application.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Configuration {

    /** User application's shortcuts folder. */
    public static String USER_APPS = System.getProperty("user.home") + "/.local/share/applications";
    /** User application's icons folder. */
    public static String USER_ICONS = System.getProperty("user.home") + "/.local/share/icons";
    /** Root's application's shortcuts folder. */
    public static String ROOT_APPS = "/usr/share/applications";
    /** Root's application's icons folder. */
    public static String ROOT_ICONS = "/usr/share/applications";
    /** Root's application's shortcuts local folder. */
    public static String ROOT_LOCAL_APPS = "/usr/local/share/applications";
    /** Root's application's icons local folder. */
    public static String ROOT_LOCAL_ICONS = "/usr/local/share/icons";
    /** True if user using application is root, otherwise false. */
    public static boolean IS_ROOT = false;

    /**
     * Returns appropriate application's shortcuts folder, depending on wheather
     * a user is root or regular user.
     */
    public static String getAppsFolder() {
        if (IS_ROOT) {
            return ROOT_LOCAL_APPS;
        } else {
            return USER_APPS;
        }
    }

    public static String getIconsFolder() {
        // TODO: implement
        return null;
    }

    /**
     * Return's folder which require root's permission to write.
     */
    public static String[] getAdminFolders() {
        String[] ret = new String[]{ROOT_APPS, ROOT_LOCAL_APPS};
        return ret;
    }

    /**
     * Static block which check's user type and sets IS_ROOT variable.
     */
    static {
        String strId = ProcessExecutor.execute("id -ru");
        int id = Integer.parseInt(strId);

        if (id == 0) {
            IS_ROOT = true;
        } else {
            IS_ROOT = false;
        }
    }
}
