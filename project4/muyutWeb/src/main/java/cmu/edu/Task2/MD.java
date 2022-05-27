package cmu.edu.Task2;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
//https://www.baeldung.com/mongodb-bson
//https://www.mongodb.com/docs/drivers/java/sync/current/usage-examples/updateOne/
public class MD {
    protected MongoDatabase md;

    public MD(MongoDatabase md) {
        this.md = md;
    }

    public void log(String info) {
        MongoCollection<Document> collection = md.getCollection("log");
        collection.insertOne(new Document().append("message", info));
    }

    public void countCountry(String country) {
        MongoCollection<Document> collection = md.getCollection("count");
        Document document
                = collection.find(eq("country", country))
                    .first();
        if (document == null) {
            document = new Document().append("country", country).append("count", 0);
        }
        int count = document.getInteger("count") + 1;


        Bson updates = Updates.combine(Updates.set("count", count));
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(new Document().append("country", country), updates, options);
    }

    public void whoAsk(String who) {
        MongoCollection<AskCount> collection = md.getCollection("askCount", AskCount.class);
        AskCount askCount
                = collection.find(eq("who", who))
                .first();
        if (askCount == null) {
            askCount = new AskCount(who, 0);
        }
        askCount.addOne();
        Bson updates = Updates.combine(Updates.set("count", askCount.getCount()));
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(new Document().append("who", who), updates, options);
    }

    public void addLatency(long latency) {
        MongoCollection<Document> collection = md.getCollection("latency");
        collection.insertOne(new Document().append("latency", latency));
    }

    public List<CountryCount> getTopCountries() {
        MongoCollection<CountryCount> collection = md.getCollection("count", CountryCount.class);
        MongoIterable<CountryCount> iterable = collection.find().sort(Sorts.descending("count")).limit(3);

        List<CountryCount> res = new ArrayList<>();
        iterable.into(res);

        return res;
    }

    public List<AskCount> getAskCount() {
        System.out.println("askCount");
        MongoCollection<AskCount> collection = md.getCollection("askCount", AskCount.class);
        MongoIterable<AskCount> iterable = collection.find().sort(Sorts.descending("count"));

        List<AskCount> res = new ArrayList<>();
        iterable.into(res);

        return res;
    }

    public int getAvgLatency() {
        MongoCollection<Latency> collection = md.getCollection("latency", Latency.class);
        MongoIterable<Latency> iterable = collection.find();

        List<Latency> res = new ArrayList<>();
        iterable.into(res);

        List<Long> lats = new ArrayList<>();
        for (Latency latency: res) {
            lats.add(latency.getLatency());
        }

        //https://stackoverflow.com/questions/10791568/calculating-average-of-an-array-list
        return (int) lats.stream()
                .mapToLong(a -> a)
                .average().orElse(0.0);
    }

    public List<Log> getLogs() {
        MongoCollection<Log> collection = md.getCollection("log", Log.class);
        MongoIterable<Log> iterable = collection.find();

        List<Log> res = new ArrayList<>();
        iterable.into(res);

        return res;
    }
}
