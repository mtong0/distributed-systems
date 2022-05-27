import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class EavesdropperUDP {
    DatagramSocket aSocket;
    DatagramSocket bSocket;
    int masPort;

    public EavesdropperUDP() throws SocketException {
        System.out.println("Eavesdropper is running");
        //prompt the port number
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the masquerading port number: ");
        masPort = scanner.nextInt(); scanner.nextLine();
        System.out.println("Enter the port number: ");
        int portNum = scanner.nextInt(); scanner.nextLine();
        scanner.close();

        aSocket = new DatagramSocket(portNum);  //socket listen to 6798
        bSocket = new DatagramSocket();

        byte[] buffer = new byte[1000];
        while (true) {
            //get request from client
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            try {
                aSocket.receive(request);
                byte[] message = new byte[request.getLength()];
                System.arraycopy(request.getData(), 0, message, 0, message.length);
                System.out.println("Eavesdrop from client: " + new String(message));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //transmit the data to server
            DatagramPacket transmit = new DatagramPacket(request.getData(),
                    request.getLength(), request.getAddress(), masPort);
            try {
                bSocket.send(transmit);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            //receive reply from the server and sent it back to the client
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            try {
                bSocket.receive(reply);
                byte[] message = new byte[reply.getLength()];
                System.arraycopy(reply.getData(), 0, message, 0, message.length);
                System.out.println("Eavesdrop from server: " + new String(message));
                reply.setPort(request.getPort());
                aSocket.send(reply);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new EavesdropperUDP();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
