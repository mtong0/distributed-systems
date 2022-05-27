import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.TreeMap;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RemoteVariableServerTCP {
    public static TreeMap<Integer, Integer> map = new TreeMap<>();
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket listenSocket = null;
        try {
            listenSocket = new ServerSocket(6789);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = listenSocket.accept();
                Scanner in = new Scanner(socket.getInputStream());
                StringBuilder request = new StringBuilder();
                String s;
                while (in.hasNextLine()) {
                    s = in.nextLine();
                    if (s.equals("")) {
                        String requestBody = request.toString();

                        int sum = exec(requestBody);

                        byte[] res = String.valueOf(sum).getBytes();
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                        out.println(new String(res));
                        out.flush();
                        socket.close();
                    } else {
                        request.append(s);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int exec(String requestBody) {
        String[] params = requestBody.split(",");
        String method = params[0];
        int num = Integer.parseInt(params[1]);
        int id = Integer.parseInt(params[2]);
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
