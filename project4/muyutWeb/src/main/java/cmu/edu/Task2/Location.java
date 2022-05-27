package cmu.edu.Task2;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
public class Location {
    private String country;
    private String regionName;
    private String city;

    public Location() {}

    public Location(String country, String regionName, String city) {
        this.country = country;
        this.regionName = regionName;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
