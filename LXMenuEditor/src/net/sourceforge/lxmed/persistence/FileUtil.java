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
package net.sourceforge.lxmed.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import net.sourceforge.lxmed.LxmedException;
import net.sourceforge.lxmed.model.MenuItem;

/**
 * Class that provides operations for saving and deleting menu items.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class FileUtil {

    /**
     * Saves given menu item.
     *
     * @param item menu item to save
     * @return true if successfully saved, otherwise false
     * @throws FileNotFoundException if file was not found.
     */
    public static boolean save(MenuItem item) throws FileNotFoundException {
        File path = item.getPath();

        if (!path.getParentFile().exists()) {
            if (!path.getParentFile().mkdirs()) {
                throw new LxmedException("Directory not created: " + path.getParent());
            }
        }

        try {
            PrintWriter pout = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

            pout.print(item.getDesktopCode());

            pout.flush();
            pout.close();
        } catch (UnsupportedEncodingException ex) {
            return false;
        }

        return true;
    }

    /**
     * Deletes a file which represents a menu item.
     *
     * @param item item to be deleted
     * @return true if file is successfully deleted, otherwise false
     */
    public static boolean delete(MenuItem item) {
        return item.getPath().delete();
    }

    /**
     * Returns a file name for given menu item's name. Filename contains no
     * whitespaces or any <i>strange</i> characters.
     *
     * @param name menu item's name
     * @return filename for given menu item
     */
    public static String getFileName(String name) {
        String[] forbidden = new String[]{"/", "\\", "?", "%", "*", ":", "|",
            "\"", "<", ">", ".", ",", ";", "'", "(", ")", " "};

        String ret = name;

        for (String string : forbidden) {
            ret = ret.replace(string, "_");
        }

        return ret + ".desktop";
    }

    /**
     * Returns same as {@link FileUtil#getFileName(java.lang.String) } but with
     * timestamp (time in milliseconds) added to filename, ie:
     * mylauncher-1337086537263.desktop
     *
     * @param name menu item's name
     */
    public static String getTimestampedFileName(String name) {
        String[] forbidden = new String[]{"/", "\\", "?", "%", "*", ":", "|",
            "\"", "<", ">", ".", ",", ";", "'", "(", ")", " "};

        String ret = name;

        for (String string : forbidden) {
            ret = ret.replace(string, "_");
        }

        return ret + "-" + System.currentTimeMillis() + ".desktop";
    }
}
