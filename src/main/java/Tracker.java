import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class Tracker {
    public void sendConnectionRequest(String httpRequest) {
        System.out.println(httpRequest);
        try {
            HttpGet request = new HttpGet(httpRequest);//"https://torrent.ubuntu.com/file?info_hash=" + TorrentUtils.encodeHash(infoHash));
            URI uri = new URIBuilder(request.getURI())
                    //.setParameter("info_hash", TorrentUtils.encodeHash(infoHash))
                    //.setParameter("peer_id", TorrentUtils.encodeHash(infoHash))
                    //.setParameter("port", "6882")
                    //.setParameter("uploaded", "0")
                    //.setParameter("downloaded", "0")
                    //.setParameter("left", Long.toString(fileSize))
                    //.setParameter("compact", "1")
                    //.setParameter("event", "started")
                    .build();
            //request.setURI(uri);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);
            InputStream input = response.getEntity().getContent();
            Scanner inputStream = new Scanner(input);
            String content = "";
            while (inputStream.hasNextLine()) {
                content += inputStream.nextLine();
            }
            System.out.println(content);

        } catch (URISyntaxException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
