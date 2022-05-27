import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Scanner;

//https://github.com/CMU-Heinz-95702/lab4-http-server/tree/master/Example-Socket-Code
public class BlockchainServer {
    BlockChain chain;
    Gson gson;
    public BlockchainServer() {
        gson = new Gson();
    }

    public ResponseMessage exec(RequestMessage req) {
        int selection = req.selection;
        ResponseMessage res = new ResponseMessage();
        res.setSelection(req.getSelection());
        String resMes = "";
        long start;
        long end;
        switch (selection) {
            case 0:
                res.setChainHash(chain.getChainHash());
                res.setHps(chain.getHashesPerSecond());
                res.setRecentNonce(chain.getLatestBlock().getNonce());
                res.setSize(chain.getChainSize());
                res.setTotalDiff(chain.getTotalDifficulty());
                res.setTotalHashes(chain.getTotalExpectedHashes());
                break;
            case 1:
                System.out.println("Adding a block");
                start = System.currentTimeMillis();
                Block newBlock = new Block(chain.getChainSize(), new Timestamp(System.currentTimeMillis()),
                        req.getMessage(), req.getDifficulty());
                newBlock.setPreviousHash(chain.chainHash);
                newBlock.proofOfWork();
                chain.addBlock(newBlock);
                end = System.currentTimeMillis();
                resMes = "Total execution time to add this block was "+ (end-start)+" milliseconds";
                break;
            case 2:
                start = System.currentTimeMillis();
                resMes = chain.isChainValid();
                resMes =  "Chain verification: " + resMes;
                System.out.println(resMes);
                end = System.currentTimeMillis();
                resMes += "\n"+"Total execution time to verify the chain was "+(end-start)+" milliseconds";
                break;
            case 3:
                System.out.println("View the Blockchain");
                resMes = chain.toString();
                break;
            case 4:
                System.out.println("Corrupt the Blockchain");
                int id = req.getId();
                String d = req.getMessage();
                chain.getBlock(id).setData(d);
                resMes = "Block "+id+" now holds "+d;
                break;
            case 5:
                start = System.currentTimeMillis();
                System.out.println("Repairing the entire chain");
                chain.repairChain();
                end = System.currentTimeMillis();
                resMes = "Total execution time required to repair the chain was "+(end-start)+" milliseconds";
                break;
            default:
                break;
        }
        System.out.println("Setting response to " + resMes);
        res.setResponse(resMes);
        System.out.println(gson.toJson(res));
        return res;
    }

    public void init() {
        Socket clientSocket = null;
        chain = new BlockChain();
        try {
            int serverPort = 7777;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Blockchain server running");
            while (true) {
                String request;
                clientSocket = listenSocket.accept();
                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter outToSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                System.out.println("We have a visitor");
                do {
                    request = scanner.nextLine();
                    RequestMessage req = gson.fromJson(request, RequestMessage.class);
                    ResponseMessage res = exec(req);
                    outToSocket.println(gson.toJson(res));
                    outToSocket.flush();
                    if (req.getSelection() == 6) break;
                } while (scanner.hasNextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        BlockchainServer blockchainServer = new BlockchainServer();
        blockchainServer.init();
    }
}
