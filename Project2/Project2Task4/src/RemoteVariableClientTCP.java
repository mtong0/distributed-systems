import java.io.*;
import java.net.*;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RemoteVariableClientTCP {
    public static Socket socket;
    public static int portNum = 0;
    public static void main(String[] args) {
        System.out.println("The client is running.");
        System.out.println("Please enter server port: ");
        Scanner scanner = new Scanner(System.in);
        portNum = scanner.nextInt();
        scanner.nextLine();

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
            int id;
            if (choice == 1) {
                System.out.println("Enter the value to add: ");
                num = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter your ID: ");
                id = Integer.parseInt(scanner.nextLine());
                res = add(num, id);
            } else if (choice == 2) {
                System.out.println("Enter the value to subtract:");
                num = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter your ID: ");
                id = Integer.parseInt(scanner.nextLine());
                res = subtract(num, id);
            } else if (choice == 3) {
                System.out.println("Enter your ID: ");
                id = Integer.parseInt(scanner.nextLine());
                res = get(id);
            } else {
                return;
            }

            System.out.println("The result is: " + res);
            System.out.println();
        }
    }

    public static int add(int i, int id) {
        String request = "add,"+i+","+id;
        return request(request);
    }

    public static int subtract(int i, int id) {
        String request = "subtract,"+i+","+id;
        return request(request);
    }

    public static int get(int id) {
        String request = "get,"+0+","+id;
        return request(request);
    }

    public static int request(String requestBody) {
        try {
            socket = new Socket("localhost", portNum);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println(requestBody);
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
