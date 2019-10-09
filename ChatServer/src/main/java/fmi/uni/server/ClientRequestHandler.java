package fmi.uni.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler extends Thread {

    private Socket socket;
    private PrintWriter writer;
    private ChatServer server;

    public ClientRequestHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer = new PrintWriter(socket.getOutputStream(), true);
            printClients();

            String userName = reader.readLine();
            server.addClient(userName);

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]" + clientMessage;
                server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));

            server.removeClient(userName, this);
            socket.close();

            serverMessage = userName + " has quited. ";
            server.broadcast(serverMessage, this);
        } catch (IOException e) {
            System.out.println("Error in handling client request: " + e.getMessage());
            e.printStackTrace();
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
        writer.println(message);
    }
}
