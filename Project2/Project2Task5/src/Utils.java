import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//source: https://github.com/CMU-Heinz-95702/Project-2-Client-Server
//source: stack overflow
public class Utils {
    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] getHashBytes(String message) {
        return md.digest(message.getBytes(StandardCharsets.UTF_8));
    }

    public static String getID(String s) throws NoSuchAlgorithmException {
        byte[] hash = Utils.getHashBytes(s);
        byte[] idCode = new byte[20];
        System.arraycopy(hash, 0, idCode, 0, 20);
        return Utils.bytesToHex(idCode);
    }
}
