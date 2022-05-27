package cmu.edu.Task2;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//ref: https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
//ref: https://www.mongodb.com/docs/drivers/java/sync/v4.3/quick-start/;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
@WebServlet(name = "ipServlet", value = "/ip-servlet")
public class IPServlet extends HttpServlet {
    private Gson gson;
    private DateFormat dateFormat;
    public void init() {
        gson = new Gson();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        queryIP(request, response);
    }


    public void queryIP (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getQueryString();

        //get the ip address
        if (query==null || query.split("=").length < 2) return;
        String ipAddress = query.split("=")[1];

        ResBody resBody = IP.locateIP(ipAddress);

        PrintWriter out = response.getWriter();
        out.println(gson.toJson(resBody));
        response.setContentType("text/html");
    }

    public void destroy() {
    }
}