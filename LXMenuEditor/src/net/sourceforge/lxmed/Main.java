package net.sourceforge.lxmed;

import net.sourceforge.lxmed.gui.MainFrame;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
