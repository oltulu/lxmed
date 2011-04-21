package net.sourceforge.lxmed.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import net.sourceforge.lxmed.model.MenuItem;

/**
 * Class that provides operations for saving menu items.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class DesktopFileSaver {

    /**
     * Saves given menu item.
     * @param item menu item to save
     * @return true if successfully saved, otherwise false
     * @throws FileNotFoundException if file was not found.
     */
    public static boolean save(MenuItem item) throws FileNotFoundException {
        System.out.println(item.getDesktopCode());
        /*
        File path = item.getPath();
        try {
            PrintWriter pout = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

            pout.print(item.getDesktopCode());

            pout.close();
        } catch (UnsupportedEncodingException ex) {
            return false;
        }*/
        return true;
    }
}
