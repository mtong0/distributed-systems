import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
//https://github.com/CMU-Heinz-95702/lab4-http-server/tree/master/Example-Socket-Code
public class BlockchainClient {
    public void init() throws IOException {
        System.out.println("Client running");
        Gson gson = new Gson();
        Socket socket = new Socket("localhost", 7777);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            RequestMessage req = new RequestMessage();
            System.out.println("0. View basic blockchain status.\n" +
                    "1. Add a transaction to the blockchain.\n" +
                    "2. Verify the blockchain.\n" +
                    "3. View the blockchain.\n" +
                    "4. corrupt the chain.\n" +
                    "5. Hide the corruption by repairing the chain.\n" +
                    "6. Exit");
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 0:
                    req.setSelection(0);
                    break;
                case 1:
                    System.out.println("Enter difficulty > 0");
                    int diff = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter transaction");
                    String transaction = scanner.nextLine();
                    req = new RequestMessage();
                    req.setSelection(1);
                    req.setDifficulty(diff);
                    req.setMessage(transaction);
                    break;
                case 2:
                    req.setSelection(2);
                    break;
                case 3:
                    req.setSelection(3);
                    break;
                case 4:
                    System.out.println("Corrupt the Blockchain");
                    System.out.println("Enter block ID of block to corrupt");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.printf("Enter new data for block %d\n", id);
                    String d = scanner.nextLine();
                    req.setSelection(4);
                    req.setId(id);
                    req.setMessage(d);
                    break;
                case 5:
                    req.setSelection(5);
                    break;
                case 6:
                    req.setSelection(6);
                    break;
                default:
                    break;
            }
            out.println(gson.toJson(req));
            out.flush();
            ResponseMessage res = gson.fromJson(in.readLine(), ResponseMessage.class);
            System.out.println(res);
        } while (option != 6);
    }
    public static void main(String[] args) {
        BlockchainClient blockchainClient = new BlockchainClient();
        try {
            blockchainClient.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
