import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient chatClient;

    public ReadThread(Socket socket, ChatClient chatClient) {
        this.socket = socket;
        this.chatClient = chatClient;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){
        while (true) {
            try {
//                if (chatClient.getUserName() != null) {
//                    System.out.print("[" + chatClient.getUserName() + "]: ");
//                }

                String response = reader.readLine();
                System.out.println( response);

            }
            catch ( SocketException e ) {
                System.out.println("Client " +  chatClient.getUserName() +" is disconnected");
                break;
            }
            catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
}
