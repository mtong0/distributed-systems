import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.TreeMap;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class VerifyingServerTCP {
    public TreeMap<String, Integer> map;
    Socket socket;
    ServerSocket listenSocket;
    RSA rsa;

    public VerifyingServerTCP() {
        map = new TreeMap<>();
        socket = null;
        listenSocket = null;
        rsa = new RSA();

        try {
            listenSocket = new ServerSocket(6789);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void init() {
        while (true) {
            try {
                socket = listenSocket.accept();
                Scanner in = new Scanner(socket.getInputStream());
                StringBuilder request = new StringBuilder();
                String s;
                while (in.hasNextLine()) {
                    s = in.nextLine();
                    if (s.equals("")) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                        String requestBody = request.toString();
                        requestBody = verify(requestBody);
                        if (requestBody.equals("Error in request")) {
                            out.println("Error in request");
                            break;
                        }
                        int sum = exec(requestBody);

                        byte[] res = String.valueOf(sum).getBytes();
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

    public String verify(String request) {
        //display all the info of client
        String[] s = request.split(";");
        String message = s[0];
        String signature = s[1];
        String[] pubKey = s[2].split(",");
        BigInteger e = new BigInteger(pubKey[0]);
        BigInteger n = new BigInteger(pubKey[1]);
        System.out.println();
        System.out.println("message: " + message);
        System.out.println("signature: " + signature);
        System.out.println("client's public key: " + e+","+n);

        String id = message.split(",")[2];
        String testId = "";
        try {//use the public key to verify the id
            testId = Utils.getID(e.toString()+n.toString());
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        //decrypt the message to get the hashed string
        String testMessage = rsa.decryptMessage(new BigInteger(signature), e, n);

        //hashed the message
        String hashMessage = Utils.bytesToHex(Utils.getHashBytes(message));

        if (id.equals(testId)) {
            System.out.println("id: "+ testId +" is valid");
        } else {
            return "Error in request";
        }
        if (testMessage.equals(hashMessage)) {
            System.out.println("Signature is valid");
        } else {
            return "Error in request";
        }
        return message;
    }

    public int exec(String requestBody) {
        String[] params = requestBody.split(",");
        String method = params[0];
        int num = Integer.parseInt(params[1]);
        String id = params[2];
        if (!map.containsKey(id)) map.put(id, 0);
        int res;
        if (method.equals("get")) {
            res = get(id);
        } else if (method.equals("add")) {
            res = add(id, num);
        } else {
            res = subtract(id, num);
        }
        System.out.printf("Visitor id: %s, method: %s, returned value: %d\n", id, method, res);
        return map.get(id);
    }

    public int add(String id, int num) {
        map.put(id,map.get(id)+num);
        return map.get(id);
    }

    public int subtract(String id, int num) {
        map.put(id,map.get(id)-num);
        return map.get(id);
    }

    public int get(String id) {
        return map.get(id);
    }

    public static void main(String[] args) {
        VerifyingServerTCP server = new VerifyingServerTCP();
        server.init();
    }
}
