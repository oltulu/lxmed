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
