import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 6868;

        try (Socket socket = new Socket(hostname, port)){
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

//            Console console= System.console();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String text;

            do {
                System.out.println("Enter text: ");
                text = br.readLine();
                writer.println(text);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(reader.readLine());
            } while ( text!= null && !text.equals("bye"));

            socket.close();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
