package fmi.java.server.client;

import fmi.java.server.nio.CommandExecutionServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BasicClient {
    private InetAddress remoteHost = null;
    private int remotePort = 0;

    public BasicClient(InetAddress host, int port) {
        this.remoteHost = host;
        this.remotePort = port;
    }

    public void start() {
        try (Socket socket = new Socket(remoteHost, remotePort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             Scanner console = new Scanner(System.in)) {
            System.out.println("Client " + socket + " connected to server");

            String consoleInput = null;
            while ((consoleInput = console.nextLine()) != null) {

                if ("quit".equalsIgnoreCase(consoleInput.trim())) {
                    break;
                }
                out.println(consoleInput);
                out.flush();
                String response = in.readLine();
                System.out.println("Server responded with: " + response);
            }
        } catch (IOException e) {
            System.err.println("An error has occured. " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Client stopped");
    }

    public static void main(String[] args) throws UnknownHostException {
        BasicClient ec = new BasicClient(InetAddress.getByName("localhost"), CommandExecutionServer.SERVER_PORT);
        ec.start();
    }
}
