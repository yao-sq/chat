import utils.Arguments;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static utils.Arguments.appArgs;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute(){
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName(){
        return this.userName;
    }

    public static void main(String[] args) {
//        if (args.length <2 ) return;

        appArgs = Arguments.parse(args);

        ChatClient client = new ChatClient(appArgs.getOrDefault("-cca", "localhost"), Integer.parseInt( appArgs.getOrDefault("-ccp", "14001")));
        client.execute();

    }
}
