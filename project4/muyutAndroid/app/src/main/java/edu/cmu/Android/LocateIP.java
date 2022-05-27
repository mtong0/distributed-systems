package edu.cmu.Android;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Name: Muyu Tong
 * AndrewID: muyut
 * */

//ref : https://github.com/CMU-Heinz-95702/lab8-Android
public class LocateIP {
    MainActivity activity;
    String ip;
    Gson gson;
    String message;
    Location location;
    int code;

    public LocateIP(MainActivity activity, String ip) {
        gson = new Gson();
        this.activity = activity;
        this.ip = ip;
    }

    public void locate() {
        if (!ip.matches("^([0-9]{0,3}\\.){3}[0-9]{0,3}$")) {
            activity.setView(400, "Illegal ip format", null);
        } else {
            new BackgroundTask().start();
        }
    }

    private class BackgroundTask {
        // run the HTTP request task in a separate thread
        public void start() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        getLocation(ip);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.setView(code, message, location);
                        }
                    });
                }
            }).start();
        }

        private void getLocation(String ip) throws IOException {
            //request the heroku to ask the IP
            System.out.println("get location");
            URL url = new URL("https://pacific-everglades-38530.herokuapp.com/ip-servlet/getIP?ip="+ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder strB = new StringBuilder();
            String str;
            while((str = in.readLine())!=null) {
                strB.append(str);
            }

            //parse the response to Location if there is one
            Rep rep = gson.fromJson(strB.toString(), Rep.class);
            code = rep.getCode();
            if (code == 200) {
                location = rep.getLocation();
                System.out.println(location.toString());
                message = "success";
            } else {
                message = rep.getMessage();
            }
        }
    }
}
