package cmu.edu.ds;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

/*
Based on Coulouris UDP socket code
 */
public class UDPServer {
    private DatagramSocket socket = null;
    private InetAddress inetAddress = null;
    private int port;
    private BigInteger b;
    private final BigInteger g = new BigInteger("5");
    private BigInteger p = new BigInteger("294558318881405180764747479252007358319960875235150893513057100495960335262381639732" +
            "393624382991877148611640594583065379669231891214833093801938123911763243718214043283" +
            "060093720669049649181956712189051916260382176617240174711734510352477962712574583690" +
            "779486253846522009126482319144984230256476305809392243435136726060071627481596350642" +
            "241513558954925792693196456498326057846493955255568347280893811272095586783577349445" +
            "131066561096635908313303089526419052508796347391313473326110069433039169945763380273" +
            "958809155750154147725521635748917952339066093424140296680685333565455781078703656353" +
            "98276428848740477292742280559");
    private BigInteger A;
    private BigInteger B;
    private BigInteger s;

    public static void main(String[] args) {
        UDPServer udpServer = new UDPServer();
        udpServer.init(7272);
        int value = Integer.parseInt(udpServer.receive());
        System.out.println("Server received: " + value);
        value++;
        String message = Integer.toString(value);
        udpServer.send(message);
        System.out.println("Sending server's public key: " + udpServer.B);
        udpServer.send(udpServer.B.toString());
        udpServer.A = new BigInteger(udpServer.receive());
        udpServer.computeSecretKey();
        udpServer.close();
    }

    private void init(int portnumber) {
        try {
            socket = new DatagramSocket(portnumber);
            System.out.println("Server socket created");
            b = new BigInteger(2046, new Random());
            System.out.println("private key: " + b);
            B = g.modPow(b, p);
        } catch (SocketException e) {
            System.out.println("Socket error " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error " + e.getMessage());
        }
    }

    private void send(String message) {
        byte[] buffer = new byte[4096];
        buffer = message.getBytes();
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length, inetAddress, port);
        try {
            socket.send(reply);
        } catch (SocketException e) {
            System.out.println("Socket error " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error " + e.getMessage());
        }

    }

    private String receive() {
        byte[] buffer = new byte[4096];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);

        try {
            socket.receive(request);
            inetAddress = request.getAddress();
            port = request.getPort();
        } catch (SocketException e) {
            System.out.println("Socket error " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error " + e.getMessage());
        }
        return new String(request.getData(), 0, request.getLength());
    }

    private void computeSecretKey() {
        s = A.modPow(b, p);
        System.out.println("The secret key is: " + s);
    }

    private void close() {
        if (socket != null) socket.close();
    }
}
