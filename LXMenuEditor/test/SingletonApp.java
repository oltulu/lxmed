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

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class SingletonApp {

    private static final int PORT = 12345;		// random large port number
    private static ServerSocket s;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("idemo");
        try {
            s = new ServerSocket(PORT, 10, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            // shouldn't happen for localhost
        } catch (IOException e) {
            // port taken, so app is already running
            System.out.println("umirem");
            System.exit(0);
        }
        while (true) {
            Thread.sleep(1000);
            System.out.println("ja sam tu");
        }
    }
}
