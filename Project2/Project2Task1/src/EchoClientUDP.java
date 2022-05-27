import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class EchoClientUDP {
    public static void main(String args[]){
        // args give message contents and server hostname
        System.out.println("The client is running");
        DatagramSocket aSocket = null;
        try {
            InetAddress aHost = InetAddress.getByName("localhost");

            //prompt the port number
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the port number: ");
            int serverPort = scanner.nextInt();

            aSocket = new DatagramSocket();
            String nextLine;
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            while ((nextLine = typed.readLine()) != null) {
                byte [] m = nextLine.getBytes();
                DatagramPacket request = new DatagramPacket(m,  m.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                byte[] message = new byte[reply.getLength()];
                System.arraycopy(reply.getData(), 0, message, 0, message.length);
                System.out.println("Reply: " + new String(message));
                if (new String(m).equals("halt!")) {
                    System.out.println("Client side quitting");
                    break;
                }
            }
        }catch (SocketException e) {System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}
