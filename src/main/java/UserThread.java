import utils.Arguments;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread{
    private Socket socket;
    private ChatServer chatServer;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    public void run(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            printUsers();

            String userName = reader.readLine();
            chatServer.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            chatServer.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                String template = Arguments.appArgs.getOrDefault("template", "[%s]: %s");
                serverMessage = String.format(template, userName, clientMessage);
                chatServer.broadcast(serverMessage, this);
            } while ( clientMessage!= null && !clientMessage.equals("bye"));

            chatServer.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            chatServer.broadcast(serverMessage, this);
        } catch (IOException e) {
            System.out.println("Error in UserThread " + e.getMessage());
            e.printStackTrace();
        }
    }

    void printUsers() {
        if (chatServer.hasUsers()){
            writer.println("Connected users: " + chatServer.getUserNames());
        }
        else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}
