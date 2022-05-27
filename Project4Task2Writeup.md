Name: Muyu Tong

Andrew ID: Muyut

# Project4Task2Writeup

# 1

**Code pieces of logs**

When receive a request from the user and respond

Will contain timestamps, request address, request content, and response content.

```java
//create request log and upload to mongo
String logInfo = String.format("[Time: %s] GET request from %s, querying \"%s\""
                               ,dateFormat.format(new Date()), request.getRemoteAddr(), query);
mongoHandler.log(logInfo);
System.out.println(logInfo);

//create response log and upload to mongo
logInfo = String.format("[Time: %s] Response to client %s with content: %s",
                        dateFormat.format(new Date()), request.getRemoteAddr(), gson.toJson(resBody));
mongoHandler.log(logInfo);
System.out.println(logInfo);
```

When request the third party API and get the response from it.

Will contains timestamps, request content, response status.

```java
//upload request logInfo (API)
String logInfo = String.format("[TIme: %s] request to API, querying %s", dateFormat.format(new Date()), ip);
mongoHandler.log(logInfo);
System.out.println(logInfo);

//upload response logInfo (API)
logInfo = String.format("[TIme: %s] response from API with status %s", dateFormat.format(new Date()), status);
mongoHandler.log(logInfo);
System.out.println(logInfo);
```



## ![Screen Shot 2022-04-12 at 10.24.33 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-04-12 at 10.24.33 PM.png)2

The MongoHandler is a layer  that upload the information to mongoDB

A piece of it shows as follow

```java
public class MD {
    protected MongoDatabase md;

    public MD(MongoDatabase md) {
        this.md = md;
    }

    public void log(String info) {
        MongoCollection<Document> collection = md.getCollection("log");
        collection.insertOne(new Document().append("message", info));
    }
}    
```

The MongoDatabase is created when the IPServlet.class is loaded.

init function of IPServlet

```java
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
  mongoHandler = new MD(md);}
```

## 3

Dashboard link https://pacific-everglades-38530.herokuapp.com/dashboard.jsp

## 4

All request can be made to heroku

IP request:  https://pacific-everglades-38530.herokuapp.com/ip-servlet/getIP?ip=1.1.1.1

get top 3 countries:  https://pacific-everglades-38530.herokuapp.com/ip-servlet/stats?content=count

get request number:  https://pacific-everglades-38530.herokuapp.com/ip-servlet/stats?content=whoask

get request average latency: https://pacific-everglades-38530.herokuapp.com/ip-servlet/stats?content=latency