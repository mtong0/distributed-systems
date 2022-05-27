<?-- from https://canvasjs.com/jsp-charts/ -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page import="com.mongodb.client.MongoClients" %>
<%@ page import="org.bson.codecs.configuration.CodecProvider" %>
<%@ page import="org.bson.codecs.pojo.PojoCodecProvider" %>
<%@ page import="static org.bson.codecs.configuration.CodecRegistries.fromRegistries" %>
<%@ page import="static com.mongodb.MongoClientSettings.getDefaultCodecRegistry" %>
<%@ page import="static org.bson.codecs.configuration.CodecRegistries.fromProviders" %>
<%@ page import="com.mongodb.client.MongoClient" %>
<%@ page import="org.bson.codecs.configuration.CodecRegistry" %>
<%@ page import="com.mongodb.client.MongoDatabase" %>
<%@ page import="cmu.edu.muyut.*" %>

<%
    Gson gsonObj = new Gson();
    Map<Object,Object> map = null;
    List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
    String url = "mongodb://muyut:cmumuyut@cluster0-shard-00-00.kbe6k.mongodb.net:27017," +
            "cluster0-shard-00-01.kbe6k.mongodb.net:27017,cluster0-shard-00-02.kbe6k.mongodb.net:27017/" +
            "test?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1";
    MongoClient mc = MongoClients.create(url);
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    MongoDatabase md = mc.getDatabase("test").withCodecRegistry(pojoCodecRegistry);
    List<CountryCount> countryCounts = new MD(md).getTopCountries();
    for (int i = 0; i < countryCounts.size(); i++){
        map = new HashMap<Object,Object>();
        map.put("label", countryCounts.get(i).getCountry());
        map.put("y", countryCounts.get(i).getCount()); list.add(map);
    }

    String dataPoints = gsonObj.toJson(list);

    List<Map<Object,Object>> list2 = new ArrayList<Map<Object,Object>>();
    List<AskCount> askCounts = new MD(md).getAskCount();
    for (int i = 0; i < askCounts.size(); i++) {
        map = new HashMap<Object,Object>();
        map.put("label", askCounts.get(i).getObj());
        map.put("y", askCounts.get(i).getCount()); list2.add(map);
    }
    String dataPoints2 = gsonObj.toJson(list2);

    List<Map<Object,Object>> list3 = new ArrayList<Map<Object,Object>>();
    int avgLatency = new MD(md).getAvgLatency();

    map = new HashMap<Object,Object>();
    map.put("label", "average latency");
    map.put("y",avgLatency); list3.add(map);

    String dataPoints3 = gsonObj.toJson(list3);

    List<Log> logs = new MD(md).getLogs();
    StringBuilder logss = new StringBuilder();
    for(Log log: logs) {
        logss.append(log.getMessage()).append("</br>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <script type="text/javascript">
        window.onload = function() {

            var chart = new CanvasJS.Chart("chartContainer", {
                title: {
                    text: "Where Most IP Requests Locate"
                },
                axisX: {
                    title: "Country"
                },
                axisY: {
                    title: "Number of Requests",
                    includeZero: true
                },
                data: [{
                    type: "column",
                    yValueFormatString: "$#,##0.0# billion",
                    dataPoints: <%out.print(dataPoints);%>
                }]
            });
            chart.render();

            var chart2 = new CanvasJS.Chart("chartContainer2", {
                title: {
                    text: "Total Number of Requests"
                },
                axisX: {
                    title: "Whom"
                },
                axisY: {
                    title: "Number of Requests",
                    includeZero: true
                },
                data: [{
                    type: "column",
                    yValueFormatString: "$#,##0.0# billion",
                    dataPoints: <%out.print(dataPoints2);%>
                }]
            });
            chart2.render();

            var chart3 = new CanvasJS.Chart("chartContainer3", {
                title: {
                    text: "Average Latency"
                },
                axisX: {
                    title: "Average Latency"
                },
                axisY: {
                    title: "Milliseconds",
                    includeZero: true
                },
                data: [{
                    type: "column",
                    yValueFormatString: "$#,##0.0# billion",
                    dataPoints: <%out.print(dataPoints3);%>
                }]
            });
            chart3.render();
        }
    </script>
</head>
<body>
<h1><%= "Dashboard" %>
</h1>
<br/>
<?-- from https://canvasjs.com/jsp-charts/ -->
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<div id="chartContainer2" style="height: 370px; width: 100%;"></div>
<div id="chartContainer3" style="height: 370px; width: 100%;"></div>
</br>
LOGS
<div style="height: 370px; width: 100%; overflow: scroll">
    <%
        out.print(logss.toString());
    %>
</div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>