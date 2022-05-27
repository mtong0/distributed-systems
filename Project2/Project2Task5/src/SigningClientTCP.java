import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class SigningClientTCP {
    public Socket socket;
    public int portNum;
    RSA rsa;
    String id;
    Scanner scanner;
    public SigningClientTCP() {
        System.out.println("The client is running.");
        rsa = new RSA(); // create a rsa key pair
        System.out.println("Please enter server port: ");
        scanner = new Scanner(System.in);
        portNum = scanner.nextInt();
        scanner.nextLine();

        try {
            id = Utils.getID(rsa.getE().toString()+ rsa.getN().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void init() {
        String s;
        while (true) {
            System.out.println("""
                    1. Add a value to your sum.
                    2. Subtract a value from your sum.
                    3. Get your sum.
                    4. Exit client""");
            s = scanner.nextLine();
            int choice = Integer.parseInt(s);

            int num;
            int res;

            if (choice == 1) {
                System.out.println("Enter the value to add: ");
                num = Integer.parseInt(scanner.nextLine());
                res = add(num, id);
            } else if (choice == 2) {
                System.out.println("Enter the value to subtract:");
                num = Integer.parseInt(scanner.nextLine());
                res = subtract(num, id);
            } else if (choice == 3) {
                System.out.println("Enter your ID: ");
                res = get(id);
            } else {
                return;
            }

            System.out.println("The result is: " + res);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        SigningClientTCP client = new SigningClientTCP();
        client.init();
    }

    public int add(int i, String id) {
        String request = "add,"+i+","+id;
        return request(request);
    }

    public int subtract(int i, String id) {
        String request = "subtract,"+i+","+id;
        return request(request);
    }

    public int get(String id) {
        String request = "get,"+0+","+id;
        return request(request);
    }

    public int request(String requestBody) {
        BigInteger signature = rsa.sign(requestBody);
        String request =  requestBody +";"+signature+";"+rsa.publicKey();
        try {
            socket = new Socket("localhost", portNum);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println(request);
            out.println();
            out.flush();
            String res = in.readLine();
            System.out.println(res);
            return Integer.parseInt(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
