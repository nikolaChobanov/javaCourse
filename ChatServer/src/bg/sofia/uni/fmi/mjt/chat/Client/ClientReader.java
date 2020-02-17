package bg.sofia.uni.fmi.mjt.chat.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReader implements Runnable {


    private BufferedReader reader;
    private ChatClient client;

    public ClientReader(BufferedReader socket, ChatClient client) {
        this.reader = socket;
        this.client = client;
    }

    @Override
    public void run() {


        while (true) {
            String message = null;
            try {
                message = reader.readLine();
                client.printMessage(message);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
