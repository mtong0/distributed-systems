package com.example.Project1Task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Scrapper {
    //ref: https://jsoup.org/
    public static Dog scrapInfo(String breed) throws IOException {
        breed = breed.toLowerCase();
        int friendly = 0;
        int intelligence = 0;
        String url = Dog.infoCredit+"/"+breed+"#/side/1"; //get url of the breed
        Document doc = Jsoup.connect(url).get();
        Elements starsEle = doc.getElementsByClass("characteristic-title");
        String className;
        for (Element element: starsEle) {
            if (element.text().equals("All Around Friendliness")) {//scrape friendliness
                className = element.siblingElements().get(0).child(0).className();
                friendly = className.charAt(className.length()-1) - 48;
            } else if (element.text().equals("Intelligence")) {//scrape intelligence
                className = element.siblingElements().get(0).child(0).className();
                intelligence = className.charAt(className.length()-1) - 48;
            }
        }
        //scrape weight
        Element weightEle = doc.getElementsByClass("vital-stat-title vital-stat-weight").get(0).parent();
        String weight = weightEle.text();

        //scrape height
        Element heightEle = doc.getElementsByClass("vital-stat-title vital-stat-height").get(0).parent();
        String height = heightEle.text();

        //scrape lifespan
        Element lifespanEle = doc.getElementsByClass("vital-stat-title vital-stat-lifespan").get(0).parent();
        String lifespan = lifespanEle.text();
        Dog dog = new Dog(breed, friendly, intelligence, height, weight, lifespan);
        return dog;
    }

    //https://www.tutorialspoint.com/gson/gson_quick_guide.htm
    //get the url of the random image
    public static String getImage(String breed) throws IOException {
        String url = "https://dog.ceo/api/breed/"+breed+"/images/random";
        URLConnection connection = new URL(url).openConnection();
        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String responseBody = scanner.useDelimiter("\\A").next();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        ImageResponse re = gson.fromJson(responseBody, ImageResponse.class);

        return re.getMessage();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(Scrapper.scrapInfo("chihuahua"));
    }
}
