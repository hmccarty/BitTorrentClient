import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Torrent {
    private HashMap<String, Object> decodedBencode;
    private byte[] infoHashBytes;
    private String infoHashHex;
    private String announceURL;
    private TorrentFile[] torrentFiles;
    private long totalLength;
    private long totalDownloaded;
    private long totalUploaded;
    private String event;
    private int compact;
    private String peerId;

    public Torrent(File file) throws IOException {
        Bencode bencode = new Bencode(true);
        FileInputStream input = new FileInputStream(file);
        byte[] data = IOUtils.toByteArray(input);
        decodedBencode = (HashMap) bencode.decode(data, Type.DICTIONARY);

        announceURL = new String(((ByteBuffer) decodedBencode.get("announce")).array(), "UTF-8");
        totalLength = (long) ((HashMap) decodedBencode.get("info")).get("length");
        totalDownloaded = 0;
        totalUploaded = 0;
        event = "started";
        compact = 0;

        byte[] info_dict = bencode.encode((HashMap) decodedBencode.get("info"));
        infoHashBytes = TorrentUtils.calculateHash(info_dict);
        infoHashHex = TorrentUtils.calculateHexFromBytes(infoHashBytes);
        peerId = infoHashHex;
    }

    public String getAnnounceURL() {
        return announceURL;
    }

    public String getInfoHashHex() {
        return infoHashHex;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public long getTotalDownloaded() {
        return totalDownloaded;
    }

    public long getTotalUploaded() {
        return totalUploaded;
    }

    public long getLeftToDownload() {
        return totalLength - totalDownloaded;
    }

    public int getCompact() {
        return compact;
    }

    public String getEvent() {
        return event;
    }

    public String getPeerId() {
        return peerId;
    }

    public class TorrentFile {
        long fileLength;
        long downloaded;
        long uploaded;
        String path;
    }
}
