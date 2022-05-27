import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Set;

public class TempestAnalytics {

    private static void lineCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

//        Split by lines
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("\n")));

        wordsFromFile.foreach(System.out::println);

        double countData = wordsFromFile.count();
        System.out.println(countData);
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No files provided.");
            System.exit(0);
        }

        lineCount(args[0]);
    }
}