import java.net.*;
import java.io.*;
import java.util.Scanner;

public class HTTPServer {

    public static void main(String args[]) {
        Socket clientSocket = null;
        try {
            int serverPort = 7777; // the server port we are using

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);

            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making
             * the socket ready for reading and writing.
             */

            // If we get here, then we are now connected to a client.

            // Set up "inFromSocket" to read from the client socket


            // Set up "outToSocket" to write to the client socket


            /*
             * Forever,
             *   read a line from the socket
             *   print it to the console
             *   echo it (i.e. write it) back to the client
             */

            while (true) {
                StringBuilder request = new StringBuilder();
                clientSocket = listenSocket.accept();
                Scanner inFromSocket;
                inFromSocket = new Scanner(clientSocket.getInputStream());
                PrintWriter outToSocket;
                outToSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                while (inFromSocket.hasNextLine()) {
                    String data = inFromSocket.nextLine();
                    if (data.equals("")) {
                        MyRequest myRequest = parseRequest(request.toString());
                        System.out.println(myRequest.path);
                        respond(myRequest, outToSocket);

                        clientSocket.close();
                    } else {
                        request.append(data).append("\n");
                    }
                }
            }

            // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }

    public static MyRequest parseRequest(String requestString) {
        String methodLine = requestString.split("\n")[0];
        String[] re = methodLine.split("\\s");
        String method = re[0];
        String path = re[1];
        return new MyRequest(method, path);
    }

    public static void respond(MyRequest myRequest,PrintWriter outToSocket) {
        File file = new File("."+myRequest.path);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            outToSocket.println("HTTP/1.1 200 OK\n");
            while (scanner.hasNextLine()) {
//                System.out.println(scanner.nextLine());
                outToSocket.println(scanner.nextLine());
            }
            outToSocket.flush();
        } catch (FileNotFoundException e) {
            outToSocket.println("HTTP/1.1 404 NOT FOUND");
            outToSocket.println("Connection: close\n");
            outToSocket.flush();
        }
    }
}