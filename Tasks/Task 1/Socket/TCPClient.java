import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
public class TCPClient {
    
    // Method for handling two arguments (hostname and port)
    public static String askServer(String hostname, int port) throws IOException {
        try (
            Socket socket = new Socket(hostname, port); // Create a new Socket and connect it to the server
            OutputStream outputStream = socket.getOutputStream(); // Get the output stream from the socket
            InputStream inputStream = socket.getInputStream(); // Get the input stream from the socket
        ){
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream(); // Create a new ByteArrayOutputStream to store the response from the server
            byte[] buffer = new byte[1024]; // Create a buffer to store the response from the server
            int bytesRead;  // Create a variable to store the number of bytes read from the server
        
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    responseStream.write(buffer, 0, bytesRead);   
                }
            } catch (IOException e) {
                System.err.println(e);
            }

            byte[] outputData = responseStream.toByteArray();
            return new String(outputData, StandardCharsets.UTF_8);
        }
    }

    public static String askServer(String hostname, int port, String stringToServer) throws IOException {
        try (
            Socket socket = new Socket(hostname, port); // Create a new Socket and connect it to the server
            OutputStream outputStream = socket.getOutputStream(); // Get the output stream from the socket
            InputStream inputStream = socket.getInputStream(); // Get the input stream from the socket
        ){
            byte[] bytesToServer = stringToServer.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytesToServer); // Send bytes to server
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream(); // Create a new ByteArrayOutputStream to store the response from the server
            byte[] buffer = new byte[1024]; // Create a buffer to store the response from the server
            int bytesRead;  // Create a variable to store the number of bytes read from the server
        
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    responseStream.write(buffer, 0, bytesRead);   
                }
            } catch (IOException e) {
                System.err.println(e);
            }

            byte[] outputData = responseStream.toByteArray();
            return new String(outputData, StandardCharsets.UTF_8);
        }
    }
}