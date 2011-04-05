package net.sourceforge.lxmed.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ModelLoader {

    public static Model load() {
        Model model = Model.getModel();

        File[] paths = new File[]{
            new File(Configuration.ROOT_APPS),
            new File(Configuration.ROOT_LOCAL_APPS),
            new File(Configuration.USER_APPS)};

        for (File path : paths) {
            if (path.exists()) {
                for (File file : path.listFiles()) {
                    if (file.getName().endsWith(".desktop")) {
                        loadDesktopFile(file);
                    }
                }
            }
        }

        return model;
    }

    public static MenuItem loadDesktopFile(File file) {
        Map<String, String> values = new HashMap<String, String>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;

            while ((line = br.readLine()) != null) {
                if (line.contains("=")) {
                    String[] strs = line.split("=");
                    String rest = "";
                    for (int i = 1; i < strs.length; i++) { // building value
                        rest += "=" + strs[i];
                    }
                    if (rest.length() > 0) {
                        rest = rest.substring(1);
                    }

                    values.put(strs[0], rest);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // MenuItem creation based on extracted values
        MenuItem mi = new MenuItem();
        mi.setName(values.get("Name"));
        mi.setExec(values.get("Exec"));
        mi.setComment(values.get("Comment"));
        mi.setGenericName(values.get("GenericName"));
        mi.setIconStr(values.get("Icon"));
        mi.setPath(file);
        mi.setNoDisplay(Boolean.parseBoolean(values.get("NoDisplay")));
        mi.setOriginalCategories(values.get("Categories"));

        for (String string : Configuration.getAdminFolders()) {
            if (mi.getPath().getParent().trim().equals(string.trim())) {
                mi.setOnlyForAdmin(true);
                break;
            } else {
                mi.setOnlyForAdmin(false);
            }
        }

        extractCategorie(mi, values.get("Categories"));

        return null;
    }

    private static void extractCategorie(MenuItem mi, String cat) {
        if (cat == null || cat.trim().equals("")) {
            return;
        }

        String[] cats = cat.split(";");

        for (String string : cats) {
            if (Model.getModel().getCategoryByCode(string) != null) {
                Model.getModel().getCategoryByCode(string).add(mi);
            }
        }
    }
}
