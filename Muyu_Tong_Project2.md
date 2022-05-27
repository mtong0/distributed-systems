Name: Muyu Tong
Email: muyut@andrew.cmu.edu

## Project 2 Task 0

**Project2Task0Client**

```java
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class EchoClientUDP{
    public static void main(String args[]){
        // args give message contents and server hostname
        System.out.println("The client is running");
        DatagramSocket aSocket = null;
        try {
            InetAddress aHost = InetAddress.getByName("localhost");

            //prompt the port number
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the port number: ");
            int serverPort = scanner.nextInt();

            aSocket = new DatagramSocket();
            String nextLine;
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            while ((nextLine = typed.readLine()) != null) {
                //each loop read from the console and send it to the server
                byte [] m = nextLine.getBytes();
                DatagramPacket request = new DatagramPacket(m,  m.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                byte[] message = new byte[reply.getLength()];
                System.arraycopy(reply.getData(), 0, message, 0, message.length);
                System.out.println("Reply: " + new String(message));
                if (new String(message).equals("halt!")) {
                    System.out.println("Client side quitting");
                    break;
                }
            }

        }catch (SocketException e) {System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}

```

**Project2Task0Server**

```java
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
```



**Project2Task0ClientConsole**
![image-20220225210046699](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210046699.png)

**Project2Task0ServerConsole**
![image-20220225210028731](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210028731.png)****

## Project 2 Task 1

**EavesdropperUDP.java**

```java
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
```

**Project2Task1ThreeConsoles**
![image-20220225210400421](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210400421.png)
![image-20220225210407081](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210407081.png)
![image-20220225210412853](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210412853.png)

## Project 2 Task 2

**Project2Task2Client**

```java
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class AddingClientUDP {
    public static DatagramSocket socket;
    public static int portNum;
    public static void main(String[] args) {
        System.out.println("The client is running.");
        try {
            socket = new DatagramSocket();
            System.out.println("Please enter server port: ");
            Scanner scanner = new Scanner(System.in);
            portNum = scanner.nextInt();scanner.nextLine();

            String s;
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (s.equals("halt!")) {
                    System.out.println("Client side quitting.");
                    break;
                }
                int num = Integer.parseInt(s);
                int res = add(num);
                System.out.println("The server returned " + res + ".");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public static int add(int i) {
        String num = String.valueOf(i);
        byte[] m = num.getBytes();
        byte[] buffer = new byte[1000];
        try {
            DatagramPacket request = new DatagramPacket(m, m.length,InetAddress.getByName("localhost"),portNum);
            socket.send(request);
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] message = new byte[reply.getLength()];
            System.arraycopy(reply.getData(), 0, message, 0, reply.getLength());
            String res = new String(message);
            return Integer.parseInt(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
```

**Project2Task2Server**

```java
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
```

**Project2Task2ClientConsole**
![image-20220225210702748](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210702748.png)
![image-20220225210709885](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210709885.png)

**Project2Task2ServerConsole**
![image-20220225210817810](/Users/tongmuyu/Library/Application Support/typora-user-images/image-20220225210817810.png)

## Project 2 Task 3

**Project2Task3Client**

```java
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RemoteVariableClientUDP {
    public static DatagramSocket socket;
    public static int portNum;

    public RemoteVariableClientUDP() {
    }

    public static void main(String[] args) {
        System.out.println("The client is running.");

        try {
            socket = new DatagramSocket();
            System.out.println("Please enter server port: ");
            Scanner scanner = new Scanner(System.in);
            portNum = scanner.nextInt();
            scanner.nextLine();

            while(true) {
                System.out.println("1. Add a value to your sum.\n2. Subtract a value from your sum.\n3. Get your sum.\n4. Exit client");
                String s = scanner.nextLine();
                int choice = Integer.parseInt(s);
                int num;
                int res;
                int id;
                if (choice == 1) {
                    System.out.println("Enter the value to add: ");
                    num = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter your ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    res = add(num, id);
                } else if (choice == 2) {
                    System.out.println("Enter the value to subtract:");
                    num = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter your ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    res = subtract(num, id);
                } else {
                    if (choice != 3) {
                        return;
                    }

                    System.out.println("Enter your ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    res = get(id);
                }

                System.out.println("The result is: " + res);
                System.out.println();
            }
        } catch (SocketException var7) {
            var7.printStackTrace();
        }
    }

    public static int add(int i, int id) {
        String request = "\"add\"," + i + "," + id;
        return request(request);
    }

    public static int subtract(int i, int id) {
        String request = "\"subtract\"," + i + "," + id;
        return request(request);
    }

    public static int get(int id) {
        String request = "\"get\",0," + id;
        return request(request);
    }

    public static int request(String requestBody) {
        byte[] m = requestBody.getBytes();
        byte[] buffer = new byte[1000];

        try {
            DatagramPacket request = new DatagramPacket(m, m.length, InetAddress.getByName("localhost"), portNum);
            socket.send(request);
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] message = new byte[reply.getLength()];
            System.arraycopy(reply.getData(), 0, message, 0, reply.getLength());
            String res = new String(message);
            System.out.println(res);
            return Integer.parseInt(res);
        } catch (IOException var7) {
            var7.printStackTrace();
            return 0;
        }
    }
}

```

**Project2Task3Server**

```java
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

```

**Project2Task3ClientConsole**

<img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.28.16 PM.png" alt="Screen Shot 2022-02-25 at 9.28.16 PM" style="zoom:67%;" /> <img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.28.52 PM.png" alt="Screen Shot 2022-02-25 at 9.28.52 PM" style="zoom:67%;" /><img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.27.14 PM.png" alt="Screen Shot 2022-02-25 at 9.27.14 PM" style="zoom:67%;" />

<img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.33.04 PM.png" alt="Screen Shot 2022-02-25 at 9.33.04 PM" style="zoom:67%;" /> <img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.34.02 PM.png" alt="Screen Shot 2022-02-25 at 9.34.02 PM" style="zoom:67%;" /><img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.33.35 PM.png" alt="Screen Shot 2022-02-25 at 9.33.35 PM" style="zoom:67%;" />

**Project2Task3ServerConsole**
![Screen Shot 2022-02-25 at 9.34.54 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.34.54 PM.png)

## Project 2 Task 4

**Project2Task4Client**

```java
//source https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RemoteVariableClientTCP {
    public static Socket socket;
    public static int portNum = 0;
    public static void main(String[] args) {
        System.out.println("The client is running.");
        System.out.println("Please enter server port: ");
        Scanner scanner = new Scanner(System.in);
        portNum = scanner.nextInt();
        scanner.nextLine();

        String s;
        while (true) {
            System.out.println("""
                1. Add a value to your sum.
                2. Subtract a value from your sum.
                3. Get your sum.
                4. Exit client""");
            s = scanner.nextLine();
            int choice = Integer.parseInt(s);

            int num;
            int res;
            int id;
            if (choice == 1) {
                System.out.println("Enter the value to add: ");
                num = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter your ID: ");
                id = Integer.parseInt(scanner.nextLine());
                res = add(num, id);
            } else if (choice == 2) {
                System.out.println("Enter the value to subtract:");
                num = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter your ID: ");
                id = Integer.parseInt(scanner.nextLine());
                res = subtract(num, id);
            } else if (choice == 3) {
                System.out.println("Enter your ID: ");
                id = Integer.parseInt(scanner.nextLine());
                res = get(id);
            } else {
                return;
            }

            System.out.println("The result is: " + res);
            System.out.println();
        }
    }

    public static int add(int i, int id) {
        String request = "add,"+i+","+id;
        return request(request);
    }

    public static int subtract(int i, int id) {
        String request = "subtract,"+i+","+id;
        return request(request);
    }

    public static int get(int id) {
        String request = "get,"+0+","+id;
        return request(request);
    }

    public static int request(String requestBody) {
        try {
            socket = new Socket("localhost", portNum);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println(requestBody);
            out.println();
            out.flush();
            String res = in.readLine();
            System.out.println(res);
            return Integer.parseInt(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

```

Project2Task4Server
```java
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
                        System.out.println(s);
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

```

**Project2Task4ClientConsole**

<img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.43.54 PM.png" alt="Screen Shot 2022-02-25 at 9.43.54 PM" style="zoom:67%;" /> <img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.44.43 PM.png" alt="Screen Shot 2022-02-25 at 9.44.43 PM" style="zoom:67%;" /><img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.44.14 PM.png" alt="Screen Shot 2022-02-25 at 9.44.14 PM" style="zoom:67%;" />

<img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.45.15 PM.png" alt="Screen Shot 2022-02-25 at 9.45.15 PM" style="zoom:67%;" /> <img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.46.14 PM.png" alt="Screen Shot 2022-02-25 at 9.46.14 PM" style="zoom:67%;" /><img src="/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.45.51 PM.png" alt="Screen Shot 2022-02-25 at 9.45.51 PM" style="zoom:67%;" />

**Project2Task4ServerConsole**
![Screen Shot 2022-02-25 at 9.46.47 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 9.46.47 PM.png)

## Project 2 Task 5

Project2Task5Client
```java
public class SigningClientTCP {
    public Socket socket;
    public int portNum;
    RSA rsa;
    String id;
    Scanner scanner;
    public SigningClientTCP() {
        System.out.println("The client is running.");
        rsa = new RSA(); // create a rsa key pair
        System.out.println("Please enter server port: ");
        scanner = new Scanner(System.in);
        portNum = scanner.nextInt();
        scanner.nextLine();

        try {
            id = Utils.getID(rsa.getE().toString()+ rsa.getN().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void init() {//start the tcp client
        String s;
        while (true) {
            System.out.println("""
                    1. Add a value to your sum.
                    2. Subtract a value from your sum.
                    3. Get your sum.
                    4. Exit client""");
            s = scanner.nextLine();
            int choice = Integer.parseInt(s);

            int num;
            int res;

            if (choice == 1) {
                System.out.println("Enter the value to add: ");
                num = Integer.parseInt(scanner.nextLine());
                res = add(num, id);
            } else if (choice == 2) {
                System.out.println("Enter the value to subtract:");
                num = Integer.parseInt(scanner.nextLine());
                res = subtract(num, id);
            } else if (choice == 3) {
                System.out.println("Enter your ID: ");
                res = get(id);
            } else {
                return;
            }

            System.out.println("The result is: " + res);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        SigningClientTCP client = new SigningClientTCP();
        client.init();
    }

    public int add(int i, String id) {
        String request = "add,"+i+","+id;
        return request(request);
    }

    public int subtract(int i, String id) {
        String request = "subtract,"+i+","+id;
        return request(request);
    }

    public int get(String id) {
        String request = "get,"+0+","+id;
        return request(request);
    }

    public int request(String requestBody) {
        BigInteger signature = rsa.sign(requestBody);
        String request =  requestBody +";"+signature+";"+rsa.publicKey();
        try {
            socket = new Socket("localhost", portNum);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println(request);
            out.println();
            out.flush();
            String res = in.readLine();
            System.out.println(res);
            return Integer.parseInt(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

                               
//source: https://github.com/CMU-Heinz-95702/Project-2-Client-Server
public class RSA {
    private BigInteger n; // n is the modulus for both the private and public keys
    private BigInteger e; // e is the exponent of the public key
    private BigInteger d; // d is the exponent of the private key
    private MessageDigest md;
    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public String publicKey() {
        return e+","+n;
    }

    public BigInteger encryptWithPrivate(String message) {
        //use the private key to encrypt the message, add leading 0
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] signBytes = new byte[messageBytes.length+1];
        signBytes[0] = 0;
        System.arraycopy(messageBytes, 0, signBytes, 1, signBytes.length - 1);
        BigInteger m = new BigInteger(signBytes);
        return m.modPow(d, n);
    }

    public BigInteger sign(String message) {
        //encrypt the message with private key to sign
        byte[] m = Utils.getHashBytes(message);
        return encryptWithPrivate(Utils.bytesToHex(m));
    }

    public String decryptMessage(BigInteger message, BigInteger e, BigInteger n) {
        //decrypt the message with key pari e + n
        byte[] mb = message.modPow(e, n).toByteArray();
        return new String(mb);
    }

    public RSA() {
        Random rnd = new Random();

        // Generate two large random primes.
        BigInteger p = new BigInteger(400, 100, rnd);
        BigInteger q = new BigInteger(400, 100, rnd);

        // Compute n by the equation n = p * q.
        n = p.multiply(q);

        // Compute phi(n) = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Select a small odd integer e that is relatively prime to phi(n).
        e = new BigInteger("65537");

        // Compute d as the multiplicative inverse of e modulo phi(n).
        d = e.modInverse(phi);

        try { // for hash
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        System.out.println(" e = " + e);  
        System.out.println(" d = " + d);  
        System.out.println(" n = " + n);
    }
}

//source: https://github.com/CMU-Heinz-95702/Project-2-Client-Server
//source: stack overflow
public class Utils {
    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {//convert byte to hex string
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] getHashBytes(String message) {//get the hashed byte of a string
        return md.digest(message.getBytes(StandardCharsets.UTF_8));
    }

    public static String getID(String s) throws NoSuchAlgorithmException {//get an ID using has
        byte[] hash = Utils.getHashBytes(s);
        byte[] idCode = new byte[20];
        System.arraycopy(hash, 0, idCode, 0, 20);
        return Utils.bytesToHex(idCode);
    }
}
```

Project2Task5Server

```java
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

    public void init() {//start the server
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
                        requestBody = verify(requestBody); // verify the message get the real request
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

    public String verify(String request) {//verify the request and return the real request info
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

```



Project2Task5ClientConsole
![Screen Shot 2022-02-25 at 10.02.28 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 10.02.28 PM.png)

**Project2Task5ServerConsole**
![Screen Shot 2022-02-25 at 10.03.35 PM](/Users/tongmuyu/Library/Application Support/typora-user-images/Screen Shot 2022-02-25 at 10.03.35 PM.png)