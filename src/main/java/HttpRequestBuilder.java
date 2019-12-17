import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpRequestBuilder {
    public static String create(Torrent torrent) throws NoAvailablePortException {
        int port = -1;
        for (int i = 6881; i <= 6889; i++) {
            System.out.println("Checking port " + i + "...");
            if (isPortAvailable(i)) {
                port = i;
                break;
            }
        }
        if (port == -1) {
            throw new NoAvailablePortException("Failed to create HttpRequest");
        }
        System.out.println("Using port " + port);

        URLBuilder urlBuilder = new URLBuilder(torrent.getAnnounceURL());
        urlBuilder.addParameter("info_hash", TorrentUtils.encodeHash(torrent.getInfoHashHex()))
                  .addParameter("peer_id", TorrentUtils.encodeHash(torrent.getPeerId()))
                  .addParameter("port", Integer.toString(port))
                  .addParameter("uploaded", Long.toString(torrent.getTotalUploaded()))
                  .addParameter("downloaded", Long.toString(torrent.getTotalDownloaded()))
                  .addParameter("left", Long.toString(torrent.getLeftToDownload()))
                  .addParameter("compact", Integer.toString(torrent.getCompact()))
                  .addParameter("event", torrent.getEvent());
        return urlBuilder.getURL();
    }

    public static boolean isPortAvailable(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
