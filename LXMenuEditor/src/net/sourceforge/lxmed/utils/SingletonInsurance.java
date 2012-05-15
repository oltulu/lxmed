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
