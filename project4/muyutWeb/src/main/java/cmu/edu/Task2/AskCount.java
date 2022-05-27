package cmu.edu.Task2;
/**
 * Name: Muyu Tong
 * Andrew ID: muyut
 * */
public class AskCount {
    String obj;
    int count;

    public AskCount(){}
    public AskCount(String obj, int count) {
        this.obj = obj;
        this.count = count;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addOne() {
        this.count += 1;
    }
}
