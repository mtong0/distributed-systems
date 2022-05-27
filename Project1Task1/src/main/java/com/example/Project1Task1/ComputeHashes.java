package com.example.Project1Task1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

@WebServlet(name = "computeHashes", value = "/compute-hashes")
public class ComputeHashes extends HttpServlet {
    String message;
    public void init() {}
    //https://stackoverflow.com/questions/32374375/
    //how-to-read-value-of-a-input-field-in-jsp-form-through-a-servlet-using-id-of-tha
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");

        MessageDigest md;


        String s = request.getParameter("s");   //input string
        String hash = request.getParameter("hash"); //hash method

        String hexText;
        String base64Text;
        try {//hash the string using the relevant method
            md = MessageDigest.getInstance(hash);
            md.update(s.getBytes(StandardCharsets.UTF_8));

            hexText = DatatypeConverter.printHexBinary(md.digest());
            base64Text = DatatypeConverter.printBase64Binary(md.digest());
            message = "hashed string: " + s + "</br> hex output: " + hexText + "</br> base64 output: " + base64Text;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // print the hash result
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
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
}