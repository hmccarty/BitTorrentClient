import java.io.IOException;
import java.net.*;

public class PeerHandler implements Runnable {
    private Torrent torrent;
    private InetAddress ip;
    private int port;

    public PeerHandler(Torrent torrent, InetAddress ip, int port) {
        this.torrent = torrent;
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
