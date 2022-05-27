# Project 5

Name: Muyu Tong

Andrew ID: muyut

Email: muyut@andrew.cmu.edu



# Part 2 Program

```
/**
 * Name: muyu Tong
 * Andrew ID: muyut*/
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Scanner;
//https://github.com/CMU-Heinz-95702/lab9-MapReduceAndSpark
public class TempestAnalytics {

    //task 0
    private static void countLine(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        //        Split by lines

        JavaRDD<String> linesFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("\n")));

        double count = linesFromFile.count();
        System.out.println("the total number of lines is "+ count);
    }

    //task1
    private static void wordCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("[^a-zA-Z]+")));

        double count = wordsFromFile.count();

        System.out.println("the total number of words is "+count);
    }

    //task2
    private static void distinctWordCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("[^a-zA-Z]+")));

        double count = wordsFromFile.distinct().count();

        System.out.println("the total number of distinct word is " +count);
    }

    //task3
    private static void symbolCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> symbols = inputFile.flatMap(content -> Arrays.asList(content.split("")));

        double count = symbols.count();

        System.out.println("the total number of symbols is "+count);
    }

    //task4
    private static void distinctSymbolCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> symbols = inputFile.flatMap(content -> Arrays.asList(content.split("")));

        double count = symbols.distinct().count();

        System.out.println("the total number of distinct symbols is "+ count);
    }

    //task5
    private static void distinctLetterCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> lettersFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("")));
        lettersFromFile = lettersFromFile.filter(k -> (k.matches("[a-zA-Z]")));
        double count = lettersFromFile.distinct().count();

        System.out.println("the total number of distinct letter is " + count);
    }

    //task6
    private static void show(String fileName) {
        System.out.println("Please enter the search word: ");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("TempestAnalytics");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> lines = inputFile.flatMap(content -> Arrays.asList(content.split("\n")));
        lines = lines.filter(k -> (k.contains(word)));
        for (String line: lines.collect()) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No files provided.");
            System.exit(0);
        }
        show(args[0]);
    }

```



## Excution of Part 2 Task 6

![Screen Shot 2022-04-24 at 10.20.28 PM](/Users/tongmuyu/Desktop/Screen Shot 2022-04-24 at 10.20.28 PM.png)

![Screen Shot 2022-04-24 at 10.20.28 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-04-24 at 10.21.52 PM.png)

![Screen Shot 2022-04-24 at 10.20.28 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-04-24 at 10.22.05 PM.png)



## ID and Password

199

Lkdmw26@