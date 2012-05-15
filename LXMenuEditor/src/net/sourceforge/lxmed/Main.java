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
package net.sourceforge.lxmed;

import net.sourceforge.lxmed.gui.MainFrame;
import net.sourceforge.lxmed.model.Model;
import net.sourceforge.lxmed.persistence.ModelLoader;
import net.sourceforge.lxmed.utils.UserDeterminator;

/**
 * Starts the application.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class Main {

    /**
     * Loads main frame. Checks if another instance is running. Determines user
     * (regular or sudo). Loads a model by reading a file system structure which
     * contains application launchers. Finally, starts MainFrame instance.
     *
     * @param args console arguments
     */
    public static void main(String[] args) {

        // check singleton instance
//        if (!SingletonInsurance.permissionGranted(Configuration.APP_PORT)) {
//            JOptionPane.showMessageDialog(null,
//                    "Another instance of application is still running.",
//                    "Another instance exists",
//                    JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        }

        // determine whether user is root or not
        UserDeterminator.determineUser();

        // load model from file system
        ModelLoader.load();

        // start GUI
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Model.getModel().addObserver(MainFrame.getInstance());
                MainFrame.getInstance().setVisible(true);
            }
        });
    }
}
