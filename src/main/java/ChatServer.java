import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ChatServer {
    public static void main(String[] args) {
        int port = 6868;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                String text;
                do {
                    text = reader.readLine();
                    String reverseText = new StringBuilder(text).reverse().toString();
                    writer.println("Server: " + reverseText);
                } while (text != null && !text.equals("bye"));

                socket.close();
            }

//            // Read data from the client
//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String line = reader.readLine();
//
//            // Send data to the client
//            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
//            writer.println("This is a message sent to the server");
//
//            // Close the client connection
//            socket.close();
//
//            // Terminate the server
//            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
