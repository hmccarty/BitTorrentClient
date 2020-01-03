import java.io.IOException;
import java.net.*;

public class PeerHandler implements Runnable {
    enum State {
        HANDSHAKE,
        KEEP_ALIVE,
        COMMUNICATE
    }

    private Torrent torrent;
    private Socket socket;
    private State currentState;
    private boolean clientChoked;
    private boolean clientInterested;
    private boolean peerChoked;
    private boolean peerInterested;

    public PeerHandler(Torrent torrent, InetAddress ip, int port) throws IOException {
        this.torrent = torrent;
        socket = null;
        try {
            socket = new Socket(ip, port);
            currentState = State.HANDSHAKE;
            clientChoked = true;
            peerChoked = true;
            clientInterested = false;
            peerInterested = false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (socket != null) socket.close();
        }
    }

    public byte[] craftHandshake() {
        byte[] handshakeMsg = new byte[68];

        byte pstrlen = 19;
        handshakeMsg[0] = pstrlen;

        byte[] pstr = "BitTorrent protocol".getBytes();
        for (int i = 1; i <= pstr.length; i++) {
            handshakeMsg[i] = pstr[i - 1];
        }

        for (int i = pstrlen + 1; i < pstrlen + 9; i++) {
            handshakeMsg[i] = 0;
        }

        byte[] infoHash = torrent.getInfoHashBytes();
        for (int i = pstrlen + 9; i < pstrlen + 30; i++) {
            handshakeMsg[i] = infoHash[i - pstrlen - 9];
        }

        byte[] id = new byte[20];
        byte[] idHeader = "-PO-".getBytes();
        return null;
    }

    public void run() {
        switch (currentState) {
            case HANDSHAKE:
                byte[] handshakeMsg = craftHandshake();
                break;
            default:
        }
    }
}
