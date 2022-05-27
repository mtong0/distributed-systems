import java.net.*;
import java.io.*;
import java.util.Scanner;
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class EchoServerUDP{
    public static void main(String args[]){
        System.out.println("The server is running");
        DatagramSocket aSocket = null;
        byte[] buffer = new byte[1000];
        try{
            //prompt the port number
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the port number: ");
            int serverPort = scanner.nextInt();
            scanner.close();

            aSocket = new DatagramSocket(serverPort);
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            while(true){
                //each round receive a request from the client
                aSocket.receive(request);
                DatagramPacket reply = new DatagramPacket(request.getData(),
                        request.getLength(), request.getAddress(), request.getPort());
                byte[] message = new byte[request.getLength()];
                System.arraycopy(request.getData(), 0, message, 0, message.length);
                String requestString = new String(message);
                System.out.println("Echoing: "+requestString);
                aSocket.send(reply);
                if (requestString.equals("halt!")) {
                    System.out.println("Server side quitting");
                    break;
                }
            }
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}
