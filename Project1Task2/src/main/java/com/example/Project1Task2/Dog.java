package com.example.Project1Task2;

public class Dog {
    public String breed;
    public static String infoCredit = "https://dogtime.com/dog-breeds/profiles";
    public int friendly;
    public int intelligence;
    public String height;
    public String weight;
    public String lifespan;

    public Dog(String breed, int friendly, int intelligence, String height, String weight, String lifespan) {
        this.breed = breed;
        this.friendly = friendly;
        this.intelligence = intelligence;
        this.height = height;
        this.weight = weight;
        this.lifespan = lifespan;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "breed='" + breed + '\'' +
                ", friendly=" + friendly +
                ", intelligence=" + intelligence +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", lifespan='" + lifespan + '\'' +
                '}';
    }
}
