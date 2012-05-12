package net.sourceforge.lxmed.actions;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class LxmedAbstractAction extends AbstractAction {

    /**
     * Kreira ikonu na osnovu naziva fajla zadatog relativno u odnosu na ovu klasu
     * @param fileName Naziv fajla slike sa relativnom putanjom
     * @return ikonicu traženog fajla
     */
    public Icon loadIcon(String fileName) {
        URL imageURL = getClass().getResource(fileName);
        Icon icon = null;

        if (imageURL != null) {
            icon = new ImageIcon(imageURL);
        } else {
            System.err.println("Resource not found: " + fileName);
        }

        return icon;
    }
}
