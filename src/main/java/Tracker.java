import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Tracker {
    HashMap<String, Object> latestResponse;

    public ArrayList<HashMap<String, Object>> processRequest(String httpRequest) {
        System.out.println(httpRequest);
        try {
            HttpGet request = new HttpGet(httpRequest);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);

            Bencode bencode = new Bencode();
            latestResponse = (HashMap<String, Object>) bencode.decode(read(response), Type.DICTIONARY);
            System.out.println(latestResponse);

            if (latestResponse.get("peers") instanceof String) {
                byte[] peers = ((String)latestResponse.get("peers")).getBytes();
                ArrayList<HashMap<String, Object>> peerList = new ArrayList<>();
                for (int i = 0; i < peers.length; i += 6) {
                    HashMap<String, Object> peerInfo = new HashMap<>();
                    peerInfo.put("peer id", null);
                    byte[] ipInBytes = Arrays.copyOfRange(peers, i, i + 4);
                    InetAddress ip = InetAddress.getByAddress(ipInBytes);
                    byte[] portInBytes = Arrays.copyOfRange(peers, i + 4, i + 6);
                    int port = TorrentUtils.byteArrayToInt(portInBytes);
                    peerInfo.put("ip", ip);
                    peerInfo.put("port", port);
                    peerList.add(peerInfo);
                }
                return peerList;
            } else {
                return (ArrayList<HashMap<String, Object>>)latestResponse.get("peers");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] read(HttpResponse response) throws IOException {
        InputStream input = null;
        byte[] data = new byte[1024];
        try {
            input = response.getEntity().getContent();
            int bytesRead = input.read(data);

            if (bytesRead == 1024) {
                throw new ResponseOverflowException("Http Tracker Response");
            }

            input.close();
        } catch (IOException | ResponseOverflowException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return data;
    }
}
