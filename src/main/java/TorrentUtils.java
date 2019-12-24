import org.apache.commons.codec.digest.DigestUtils;

public class TorrentUtils {
    private final static char[] HEX_SYMBOLS = "0123456789ABCDEF".toCharArray();


    public static byte[] calculateHash(byte[] data) {
        return DigestUtils.sha(data);
    }

    public static String calculateHexFromBytes(byte[] arr) {
        char[] hexChars = new char[arr.length * 2];
        for (int j = 0; j <arr.length; j++) {
            int v = arr[j] & 0xFF;
            hexChars[j * 2] = HEX_SYMBOLS[v >>> 4];
            hexChars[j * 2 + 1] = HEX_SYMBOLS[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String encodeHash(String hash) {
        String encodedHash = "";
        for (int i = 0; i < hash.length(); i = i + 2) {
            String hexValue = hash.substring(i, i + 2);
            int asciiValue = Integer.valueOf(hexValue, 16);
            String asciiSymbol = Character.toString(((char) asciiValue));
            if (!asciiSymbol.matches("[0-9]?|[a-z]?|[A-Z]?")) {
                encodedHash += "%" + hexValue.toUpperCase();
            } else {
                encodedHash += asciiSymbol;
            }
        }
        return encodedHash;
    }

    public static int byteArrayToInt(byte[] data) {
        return 0x00 << 24 | 0x00 << 16 | (data[0] & 0xff) << 8 | (data[1] & 0xff);
    }
}
