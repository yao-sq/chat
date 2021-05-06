import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread{
    private PrintWriter writer;
    private Socket socket;
    private ChatClient chatClient;

    public WriteThread(Socket socket, ChatClient chatClient) {
        this.socket = socket;
        this.chatClient = chatClient;

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("\nEnter your name: ");
            String userName;
            try {
                userName = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            chatClient.setUserName(userName);
            writer.println(userName);

            String text;

            do {
//                System.out.print("[" + userName + "]: ");
                try {
                    text = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                writer.println(text);
            } while ( text!= null && !text.equals("bye"));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("The current user " + userName + "exited the conversation");
//            System.out.println("Error writing to server: " + e.getMessage());
        }
    }
}
