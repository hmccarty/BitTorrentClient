import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Client {
    public Client() {
    }

    public static void main(String args[]) {
        try {
            Torrent torrent = new Torrent(new File( "C:\\Users\\harri\\Downloads\\kubuntu-16.04.6-desktop-amd64.iso.torrent"));
            Tracker tracker = new Tracker();
            tracker.sendConnectionRequest(HttpRequestBuilder.create(torrent));
        } catch (IOException | NoAvaliablePortException e) {
            e.printStackTrace();
        }
        //System.out.println(torrent.getSHAPieces());
    }
}
