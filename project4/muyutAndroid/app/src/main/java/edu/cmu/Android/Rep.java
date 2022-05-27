package edu.cmu.Android;
/**
 * Name: Muyu Tong
 * Andrew ID: Muyut
 * */
public class Rep {
    int code;
    Location location;
    String message;
    public Rep(int code, Location location, String message) {
        this.code = code;
        this.location = location;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
