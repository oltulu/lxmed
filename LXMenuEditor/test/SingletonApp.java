
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
