package cmu.edu.Task2;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
public class ResBody {
    int code;
    String message;
    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
