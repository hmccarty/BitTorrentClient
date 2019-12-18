import java.io.File;
import java.io.IOException;

public class Client {
    public Client() { }

    public static void main(String args[]) {
        try {
            Torrent torrent = new Torrent(new File( "C:\\Users\\harri\\Downloads\\kubuntu-16.04.6-desktop-amd64.iso.torrent"));
            Tracker tracker = new Tracker();
            tracker.processRequest(HttpRequestBuilder.create(torrent));
        } catch (IOException | NoAvailablePortException e) {
            e.printStackTrace();
        }
    }
}
