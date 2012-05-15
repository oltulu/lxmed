// lxmed - LXDE Main Menu Editor
// Copyright (C) 2011  Marko Čičak
//
// This file is part of lxmed.
//
// lxmed is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// lxmed is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with lxmed.  If not, see <http://www.gnu.org/licenses/>.
package net.sourceforge.lxmed.utils;

/**
 * Global variables needed for application.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Configuration {

    /**
     * User application's shortcuts folder.
     */
    public static String USER_APPS = System.getProperty("user.home") + "/.local/share/applications";
    /**
     * User application's icons folder.
     */
    public static String USER_ICONS = System.getProperty("user.home") + "/.local/share/icons";
    /**
     * Root's application's shortcuts folder.
     */
    public static String ROOT_APPS = "/usr/share/applications";
    /**
     * Root's application's icons folder.
     */
    public static String ROOT_ICONS = "/usr/share/applications";
    /**
     * Root's application's shortcuts local folder.
     */
    public static String ROOT_LOCAL_APPS = "/usr/local/share/applications";
    /**
     * Root's application's icons local folder.
     */
    public static String ROOT_LOCAL_ICONS = "/usr/local/share/icons";
    /**
     * True if user using application is root, otherwise false.
     */
    public static boolean IS_ROOT = false;
    /**
     * Port that serves as insurance that only one instance of application is
     * running.
     */
    public static final int APP_PORT = 52011;

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

    /**
     * Returns a folder containing application icons. NOTE: Currently not
     * implemented.
     */
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
}
