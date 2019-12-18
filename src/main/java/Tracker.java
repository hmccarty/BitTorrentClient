import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Tracker {
    HashMap<String, Object> latestResponse;

    public void processRequest(String httpRequest) {
        System.out.println(httpRequest);
        try {
            HttpGet request = new HttpGet(httpRequest);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);

            Bencode bencode = new Bencode();
            latestResponse = (HashMap) bencode.decode(read(response), Type.DICTIONARY);
            System.out.println(latestResponse);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] read(HttpResponse response) throws IOException {
        InputStream input = null;
        byte[] data = new byte[1024];
        try {
            input = response.getEntity().getContent();
            int bytesRead = input.read(data);
            if (bytesRead != -1) {
                throw new ResponseOverflowException("Http Tracker Response");
            }
            System.out.println(data.toString());
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
