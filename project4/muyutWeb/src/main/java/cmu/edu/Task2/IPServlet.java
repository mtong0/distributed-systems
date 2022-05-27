package cmu.edu.Task2;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLoggerFactory;
//ref: https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
//ref: https://www.mongodb.com/docs/drivers/java/sync/v4.3/quick-start/;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
@WebServlet(name = "ipServlet", urlPatterns = {"/ip-servlet/getIP", "/ip-servlet/stats"})
public class IPServlet extends HttpServlet {
    private Gson gson;
    private DateFormat dateFormat;
    private Logger logger;
    public static MongoDatabase md;
    public static MongoClient mc;
    public static MD mongoHandler;
    public static CodecRegistry pojoCodecRegistry;
    public void init() {
        gson = new Gson();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        logger = LoggerFactory.getLogger(SimpleLoggerFactory.class);

        //connect to mongodb
        String url = "mongodb://muyut:cmumuyut@cluster0-shard-00-00.kbe6k.mongodb.net:27017," +
                "cluster0-shard-00-01.kbe6k.mongodb.net:27017,cluster0-shard-00-02.kbe6k.mongodb.net:27017/" +
                "test?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1";

        mc = MongoClients.create(url);
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        md = mc.getDatabase("test").withCodecRegistry(pojoCodecRegistry);
        mongoHandler = new MD(md);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().matches("^.*/ip-servlet/getIP.*$")) {
            queryIP(request, response);
        } else if (request.getRequestURI().matches("^.*/ip-servlet/stats.*$")) {
            queryStats(request, response);
        }
    }


    public void queryIP (HttpServletRequest request, HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        String query = request.getQueryString();

        //create request log and upload to mongo
        String logInfo = String.format("[Time: %s] GET request from %s, querying \"%s\""
                ,dateFormat.format(new Date()), request.getRemoteAddr(), query);
        mongoHandler.log(logInfo);
        System.out.println(logInfo);

        if (query==null || query.split("=").length < 2) return;
        String ipAddress = query.split("=")[1];

        ResBody resBody = IP.locateIP(ipAddress);

        System.out.println(request.getRemoteAddr());
        if (ipAddress.equals(request.getRemoteAddr())) {
            mongoHandler.whoAsk("self");
        } else {
            mongoHandler.whoAsk("others");
        }

        PrintWriter out = response.getWriter();
        out.println(gson.toJson(resBody));
        response.setContentType("text/html");

        //create response log and upload to mongo
        logInfo = String.format("[Time: %s] Response to client %s with content: %s",
                dateFormat.format(new Date()), request.getRemoteAddr(), gson.toJson(resBody));
        mongoHandler.log(logInfo);
        System.out.println(logInfo);
        long end = System.currentTimeMillis();
        mongoHandler.addLatency(end-start);
    }
    public void queryStats(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getQueryString();
        String[] l = query.split("=|\\?|&");
        String q = l[1];
        if (q.equals("count")) {
            List<CountryCount> tops = mongoHandler.getTopCountries();
            PrintWriter out = response.getWriter();
            out.println(gson.toJson(tops));
            response.setContentType("text/html");
        } else if (q.equals("whoask")) {
            List<AskCount> askCounts = mongoHandler.getAskCount();
            PrintWriter out = response.getWriter();
            out.println(gson.toJson(askCounts));
            response.setContentType("text/html");
        } else if (q.equals("latency")) {
            int latency = mongoHandler.getAvgLatency();
            PrintWriter out = response.getWriter();
            out.println(gson.toJson(latency));
            response.setContentType("text/html");
        }
    }

    public void destroy() {
    }
}