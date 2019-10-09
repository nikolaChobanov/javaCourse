package fmi.uni.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    public static final int SERVER_PORT = 4444;
    private Set<String> userNames = new HashSet<>();
    private Set<ClientRequestHandler> clientThreads = new HashSet<>();


    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Chat server is listening on port " + SERVER_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                ClientRequestHandler newClient = new ClientRequestHandler(socket, this);
                clientThreads.add(newClient);
                newClient.start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }

        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

    void broadcast(String message, ClientRequestHandler us) {
        for (ClientRequestHandler user : clientThreads) {
            if (us != user) {
                user.sendMessage(message);
            }
        }
    }

    void addClient(String client) {
        userNames.add(client);
    }

    void removeClient(String userName, ClientRequestHandler client) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            clientThreads.remove(client);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
