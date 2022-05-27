import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class AddingServerUDP {
    public static int sum = 0;
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(6789);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true) {
            byte[] buffer = new byte[1000];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(request);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] message = new byte[request.getLength()];
            System.arraycopy(request.getData(), 0, message, 0, request.getLength());
            int num = Integer.parseInt(new String(message));
            System.out.printf("Adding %d to %d\n", num, sum);
            add(num);
            System.out.printf("Returning sum of %d to client\n", sum);
            byte[] res = String.valueOf(sum).getBytes();
            DatagramPacket reply = new DatagramPacket(res, res.length, request.getAddress(), request.getPort());
            try {
                socket.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void add(int i) {
        sum += i;
    }
}
