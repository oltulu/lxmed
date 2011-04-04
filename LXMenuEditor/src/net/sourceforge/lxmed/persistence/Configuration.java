package net.sourceforge.lxmed.persistence;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Configuration {

    public static String USER_APPS = System.getProperty("user.home") + "/.local/share/applications";
    public static String USER_ICONS = System.getProperty("user.home") + "/.local/share/icons";
    public static String ROOT_APPS = "/usr/share/applications";
    public static String ROOT_ICONS = "/usr/share/applications";
    public String LOCAL_ROOT_APPS = "/usr/local/share/applications";
    public String LOCAL_ROOT_ICONS = "/usr/local/share/icons";
    public static boolean IS_ROOT = false;

    public String getAppsFolder() {
        // TODO: implement
        return null;
    }

    public String getIconsFolder() {
        // TODO: implement
        return null;
    }
}
