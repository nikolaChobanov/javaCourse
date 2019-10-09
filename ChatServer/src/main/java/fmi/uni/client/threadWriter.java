package fmi.uni.client;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class threadWriter extends Thread {

    private Socket socket;
    private PrintWriter writer;
    private Client client;

    public threadWriter(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Console console = System.console();
        String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);

        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }

}
