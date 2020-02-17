package bg.sofia.uni.fmi.mjt.chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    public static final int SERVER_PORT = 4444;
    private Set<String> userNames = Collections.synchronizedSet(new HashSet<>());
    private Set<ClientRequestHandler> chatClients = Collections.synchronizedSet(new HashSet<>());
    private static final int MAX_EXECUTOR_THREADS = 10;


    void broadcast(String message, ClientRequestHandler us, String username) {
        for (ClientRequestHandler user : chatClients) {
            if (user.getUserName().equals(username)) {
                user.sendMessage(message);
            }
        }
    }

    void broadcast(String message, String username) {
        for (ClientRequestHandler user : chatClients) {
            if (user.getUserName().equals(username)) {
                continue;
            }
            user.sendMessage(message);
        }
    }


    public void addClient(String client) {
        userNames.add(client);
    }

    public void removeClient(String userName, ClientRequestHandler client) {

        userNames.remove(userName);
        chatClients.remove(client);
        System.out.println("The user " + userName + " quitted");

    }

    public Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }


    public void start() {

        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Chat server is listening on port " + SERVER_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New userr connected");

                ClientRequestHandler newClient = new ClientRequestHandler(socket, this);
                chatClients.add(newClient);
                executor.execute(newClient);


            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executor.shutdown();

        }
    }


    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}