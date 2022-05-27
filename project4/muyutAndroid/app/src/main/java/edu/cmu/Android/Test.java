package edu.cmu.Android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Name: Muyu Tong
 * Andrew ID: Muyut
 * */
public class Test {
    public static void main(String[] args) throws IOException {
        URL url = null;

        url = new URL("mighty-journey-24359.herokuapp.com/currency?from=USD&to=RMB");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder strB = new StringBuilder();
        String str;
        while((str = in.readLine())!=null) {
            strB.append(str);
        }
        System.out.println(strB.toString());
    }
}
