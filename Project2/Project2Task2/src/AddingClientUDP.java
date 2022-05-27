import java.io.IOException;
import java.net.*;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class AddingClientUDP {
    public static DatagramSocket socket;
    public static int portNum;
    public static void main(String[] args) {
        System.out.println("The client is running.");
        try {
            socket = new DatagramSocket();
            System.out.println("Please enter server port: ");
            Scanner scanner = new Scanner(System.in);
            portNum = scanner.nextInt();scanner.nextLine();

            String s;
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (s.equals("halt!")) {
                    System.out.println("Client side quitting.");
                    break;
                }
                int num = Integer.parseInt(s);
                int res = add(num);
                System.out.println("The server returned " + res + ".");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public static int add(int i) {
        String num = String.valueOf(i);
        byte[] m = num.getBytes();
        byte[] buffer = new byte[1000];
        try {
            DatagramPacket request = new DatagramPacket(m, m.length,InetAddress.getByName("localhost"),portNum);
            socket.send(request);
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] message = new byte[reply.getLength()];
            System.arraycopy(reply.getData(), 0, message, 0, reply.getLength());
            String res = new String(message);
            return Integer.parseInt(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
