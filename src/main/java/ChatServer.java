import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void execute(){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                UserThread userThread = new UserThread(socket, this);
                userThreads.add(userThread);
                userThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt( args[0]);
        ChatServer chatServer = new ChatServer(port);
        chatServer.execute();
    }

    void broadcast(String message, UserThread excludeUserThread){
        for (UserThread userThread: userThreads) {
            if (userThread != excludeUserThread) {
                userThread.sendMessage(message);
            }
        }
    }

    void addUserName(String userName){
        userNames.add(userName);
    }

    void removeUser(String userName, UserThread userThread){
        boolean removed = userNames.remove(userName);
        if (removed){
            userThreads.remove(userThread);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames(){
        return this.userNames;
    }

    boolean hasUsers(){
        return !this.userNames.isEmpty();
    }



}
