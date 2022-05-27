package edu.cmu.muyut2;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class LocateIP {
    MainActivity activity;
    String ip;
    Gson gson;
    String message;
    Location location;
    int code;

    public LocateIP(MainActivity activity, String ip) {
        this.activity = activity;
        this.ip = ip;
    }

    public void locate() {
        if (ip.matches("^([0-9]\\.){3}[0-9]$")) {
//            activity.setView(400, "Illegal ip format", null);
        } else {
            new BackgroundTask().start();
        }
    }

    private class BackgroundTask {
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
//                            activity.setView(code, message, location);
                        }
                    });
                }
            });
        }

        private void getLocation(String ip) throws IOException {
            URL url = new URL("http://frozen-wildwood-43510.herokuapp.com/ip-servlet/?ip="+ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder strB = new StringBuilder();
            String str;
            while((str = in.readLine())!=null) {
                strB.append(str);
            }

            HashMap<String, String> rep = gson.fromJson(strB.toString(), HashMap.class);
            code = Integer.parseInt(rep.get("code"));

            if (code == 200) {
                location = gson.fromJson(rep.get("location"), Location.class);
                message = "success";
            } else {
                message = rep.get("message");
            }
        }
    }
}
