package bg.sofia.uni.fmi.mjt.chat.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {


    private static final int SERVER_PORT = 4444;

    public void start() {
        ExecutorService executorReader = Executors.newCachedThreadPool();
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            ClientReader newReader = new ClientReader(reader, this);
            executorReader.execute(newReader);


            while (true) {
                System.out.print("Enter line: ");
                String line = scanner.nextLine(); // read a line from the console

                writer.println(line);

                if ("disconnect".equals(line)) {
                    executorReader.shutdown();
                    System.exit(1);
                    break;
                }

                String arr[] = line.split(" ", 2);


            }
        } catch (IOException e) {
            System.out.println("Problem with Client connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executorReader.shutdown();
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {

        ChatClient ck = new ChatClient();
        ck.start();
    }
}
