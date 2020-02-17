package bg.sofia.uni.fmi.mjt.chat.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private ChatServer server;
    private String name;
    private volatile boolean running=true;


    public ClientRequestHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public String getUserName() {
        return name;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer = new PrintWriter(socket.getOutputStream(), true);


            String line;
            String userName = null;

            while ((line = reader.readLine()) != null) {

                String serverMessage;

                if ("list-users".equals(line)) {
                    printClients();
                }

                String arr[] = line.split(" ", 2);



                if (arr[0].equals("nick")) {
                    server.addClient(arr[1]);
                    userName = arr[1];
                    name = arr[1];
                    writer.println("UserName set to " + arr[1]);

                }

                if (arr[0].equals("disconnect")) {
                    running=false;
                    server.removeClient(userName,this);
                    Thread.currentThread().interrupt();
                }


                if (arr[0].equals("send-all")) {
                    System.out.println("Sending line <" + line + "> to the server...");
                    writer.println(line); // send the line to the server
                    serverMessage = "[" + userName + "]" + arr[1];
                    server.broadcast(serverMessage, userName);
                }

                if (arr[0].equals("send")) {
                    String message[] = line.split(" ", 3);
                    System.out.println("Sending line <" + line + "> to the server...");
                    writer.println(message); // send the line to the server
                    serverMessage = "[" + userName + "]" + message[2];
                    server.broadcast(serverMessage, this, message[1]);
                }
            }

            server.removeClient(userName, this);
            socket.close();

            String serverMessage = userName + " has quited. ";
            server.broadcast(serverMessage, this, "END");
        } catch (IOException e) {
            System.out.println("Error in handling client request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket could not close:  " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    void printClients() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.flush();
        writer.println(message);
    }


}
