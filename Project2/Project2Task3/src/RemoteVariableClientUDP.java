import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RemoteVariableClientUDP {
    public static DatagramSocket socket;
    public static int portNum;
    public static void main(String[] args) {
        System.out.println("The client is running.");
        try {
            socket = new DatagramSocket();
            System.out.println("Please enter server port: ");
            Scanner scanner = new Scanner(System.in);
            portNum = scanner.nextInt(); scanner.nextLine();

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
        } catch (SocketException e) {
            e.printStackTrace();
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
        byte[] m = requestBody.getBytes();
        byte[] buffer = new byte[1000];
        try {
            DatagramPacket request = new DatagramPacket(m, m.length, InetAddress.getByName("localhost"),portNum);
            socket.send(request);
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] message = new byte[reply.getLength()];
            System.arraycopy(reply.getData(), 0, message, 0, reply.getLength());
            String res = new String(message);
            System.out.println(res);
            return Integer.parseInt(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
