package edu.cmu.andrew.muyut;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        String s = "Hello World";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes(StandardCharsets.UTF_8));
            System.out.println("Hello World");
            System.out.println(bytesToHex(md.digest()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    /**
     * exercise 2
     * get the same answer
     * */
}
