package cmu.edu.Task2;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
public class IP {

    public static ResBody locateIP(String ip) throws IOException {
        ResBody resBody = new ResBody();
        Gson gson = new Gson();

        if (ip.matches("^([0-9]{0,3}\\.){3}[0-9]{0,3}$")) {
            //if the ip format is valid, connect the api and get the result map
            URL url = new URL("http://ip-api.com/json/" + ip);

            //connect to the third party API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder strB = new StringBuilder();
            String str;
            while((str = in.readLine())!=null) {
                strB.append(str);
            }
            in.close();
            HashMap<String, String> result = gson.fromJson(strB.toString(), HashMap.class);

            String status = result.get("status");

            //when success, create the location object
            if (status.equals("success")) {
                String country = result.get("country");
                String regionName = result.get("regionName");
                String city = result.get("city");
                Location location = new Location(country, regionName, city);
                resBody.setLocation(location);
                resBody.setCode(200);
            } else {
                String message = result.get("message");
                resBody.setMessage(message);
                resBody.setCode(400);
            }

        }
        else {
            resBody.setCode(400);
            resBody.setMessage("Invalid IP format");
        }
        return resBody;
    }
}
