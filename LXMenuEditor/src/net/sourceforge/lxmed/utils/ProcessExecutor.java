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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Executes any process and returns it's output.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ProcessExecutor {

    /**
     * Executes any process and returns it's output.
     * @param cmd command to execute
     * @return command output
     */
    public static String execute(String cmd) {
        String ret = "";

        try {
            // start up the command in child process
            Process child = Runtime.getRuntime().exec(cmd);

            // hook up child process output to parent
            InputStream lsOut = child.getInputStream();
            InputStreamReader r = new InputStreamReader(lsOut);
            BufferedReader in = new BufferedReader(r);

            // read the child process' output
            String line;
            while ((line = in.readLine()) != null) {
                ret += line + "\n";
            }
            ret = ret.trim();

        } catch (Exception e) { // exception thrown

            System.out.println("Command failed!");

        }

        return ret;
    }
}
