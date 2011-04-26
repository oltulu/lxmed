package net.sourceforge.lxmed.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * Class that serves as insurance that only one instance of application is
 * running. When a client calls permissionGranted method, the method tries
 * to take a given port. If it succeeds, than this is the only instance of
 * application. If it fails, then another instance of application may exist.
 * <p>
 * <b>Warning:</b> The only thing you need to take care about, is that a
 * selected port is a random large number.
 * </p>
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class SingletonInsurance {

    /** Server socket for port taking. */
    private static ServerSocket s;

    /**
     * Returns true if this is the only instance of application, otherwise
     * false.
     */
    public static boolean permissionGranted(int port) {
        try {
            s = new ServerSocket(port, 10, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            // shouldn't happen for localhost
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
