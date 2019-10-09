package fmi.uni.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private String name;
    private static final int SERVER_PORT = 4444;
    private String userName;

    public Client(String name) {
        this.name = name;
    }

    public void start() {


        try (Socket socket = new Socket("localhost", SERVER_PORT);) {


            System.out.println("Client " + socket + " connected to server");
            new threadReader(socket, this).start();
            new threadWriter(socket, this).start();

        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + SERVER_PORT);
        }


    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(hostname);
        client.start();
    }

}