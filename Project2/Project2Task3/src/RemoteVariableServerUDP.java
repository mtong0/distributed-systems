import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.TreeMap;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RemoteVariableServerUDP {
    public static TreeMap<Integer, Integer> map = new TreeMap<>();
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
            String requestBody = new String(message);
            String[] params = requestBody.split(",");
            String method = params[0];
            int num = Integer.parseInt(params[1]);
            int id = Integer.parseInt(params[2]);

            int sum = exec(method, id, num);

            byte[] res = String.valueOf(sum).getBytes();
            DatagramPacket reply = new DatagramPacket(res, res.length, request.getAddress(), request.getPort());
            try {
                socket.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int exec(String method, int id, int num) {
        if (!map.containsKey(id)) map.put(id, 0);
        int res;
        if (method.equals("get")) {
            res = get(id);
        } else if (method.equals("add")) {
            res = add(id, num);
        } else {
            res = subtract(id, num);
        }
        System.out.printf("Visitor id: %d, method: %s, returned value: %d\n", id, method, res);
        return map.get(id);
    }

    public static int add(int id, int num) {
        map.put(id,map.get(id)+num);
        return map.get(id);
    }

    public static int subtract(int id, int num) {
        map.put(id,map.get(id)-num);
        return map.get(id);
    }

    public static int get(int id) {
        return map.get(id);
    }
}
