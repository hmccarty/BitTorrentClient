import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class PeerManager {
    private Torrent torrent;
    private ArrayList<HashMap<String, Object>> peerList;

    public PeerManager(Torrent torrent, ArrayList<HashMap<String, Object>> peerList) {
        this.torrent = torrent;
        this.peerList = peerList;
    }

    public void connect() throws UnknownHostException {
        for (HashMap<String, Object> peer : peerList) {
            InetAddress ip = InetAddress.getByName((String) peer.get("ip"));
            int port = ((Long) peer.get("port")).intValue();
            PeerHandler peerHandler = new PeerHandler(torrent, ip, port);
            Thread thread = new Thread(peerHandler);
            thread.start();
        }
    }
}
