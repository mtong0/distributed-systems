package cmu.edu.Task2;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
public class CountryCount {
    String country;
    int count;
    public CountryCount(){}
    public CountryCount(String country, int count) {
        this.country = country;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addOne() {
        count += 1;
    }

    @Override
    public String toString() {
        return "CountryCount{" +
                "country='" + country + '\'' +
                ", count=" + count +
                '}';
    }
}
