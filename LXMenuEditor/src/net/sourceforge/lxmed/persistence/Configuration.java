package net.sourceforge.lxmed.persistence;

/**
 * Global variables needed for application.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Configuration {

    /**  */
    public static String USER_APPS = System.getProperty("user.home") + "/.local/share/applications";
    public static String USER_ICONS = System.getProperty("user.home") + "/.local/share/icons";
    public static String ROOT_APPS = "/usr/share/applications";
    public static String ROOT_ICONS = "/usr/share/applications";
    public static String ROOT_LOCAL_APPS = "/usr/local/share/applications";
    public static String ROOT_LOCAL_ICONS = "/usr/local/share/icons";
    public static boolean IS_ROOT = false;

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

    public static String[] getAdminFolders() {
        String[] ret = new String[]{ROOT_APPS, ROOT_LOCAL_APPS};
        return ret;
    }

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
