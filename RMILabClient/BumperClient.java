import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class BumperClient {
   public static void main(String args[]) throws Exception {
        Bumper b  = (Bumper) Naming.lookup("//localhost/CoolBumper");
 	    System.out.println("Found bumper. Enter ! to quit");

        BigInteger str = new BigInteger("0");
        BigInteger n = new BigInteger("10000");
   
        long start = System.currentTimeMillis(); 
        while(!str.equals(n)) {
            try {
                b.bump();
                str = b.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        long stop = System.currentTimeMillis(); 

        System.out.println("The value hold by remote server is: " + b.get());
        System.out.println("Total time cost" + (stop - start));
    }
}
