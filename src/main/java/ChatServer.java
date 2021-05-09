import utils.Arguments;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static utils.Arguments.appArgs;

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
        appArgs = Arguments.parse(args);
        int port = Integer.parseInt( appArgs.getOrDefault("-csp", "14001"));
        ChatServer chatServer = new ChatServer(port);
        chatServer.execute();
        chatServer.listenForStopCommand();
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

    public void listenForStopCommand(){
        new Thread( () -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ( "EXIT".equals(line)){
//                    stop();
                    System.exit(0);
                    break;
                }
            }
        } , "Server stop listener").start();
    }

}
