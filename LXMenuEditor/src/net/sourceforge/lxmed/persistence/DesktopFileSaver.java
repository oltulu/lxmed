package net.sourceforge.lxmed.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import net.sourceforge.lxmed.model.MenuItem;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class DesktopFileSaver {

    public static boolean save(MenuItem item) throws FileNotFoundException {
        File path = item.getPath();
        try {
            PrintWriter pout = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

            pout.print(item.getDesktopCode());
            pout.close();
        } catch (UnsupportedEncodingException ex) {
            return false;
        }
        return true;
    }
}
