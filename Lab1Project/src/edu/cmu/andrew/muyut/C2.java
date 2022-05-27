package edu.cmu.andrew.muyut;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class C2 {
    public String solveBlock() {
        int n = 0;
        MessageDigest md;
        String res = "";
        String s = ",4,19,Pink,Orange,002fdb16086d97e03613fa0caa87b280eca956216e61a35400408bdd3a449e45";
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((n+s).getBytes(StandardCharsets.UTF_8));
            res = bytesToHex(md.digest());
            while (!res.matches("00.*")) {
                n++;
                md.update((n+s).getBytes(StandardCharsets.UTF_8));
                res = bytesToHex(md.digest());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("nounce: "+n);
        return res;
    }

    // from https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
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

    public static void main(String[] args) {
        System.out.println(new C2().solveBlock());
    }
}
