import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class TCPClient {

    // Method for handling two arguments (hostname and port)
    public byte[] askServer(String hostname, int port) throws IOException {
        return askServer(hostname, port, new String(new byte[0], StandardCharsets.UTF_8));
    }
    // Method for handling three arguments (hostname, port and userInput)
    public static String askServer(String hostname, int port, byte[] userInput) throws IOException {
        try (
            Socket socket = new Socket(hostname, port); // Create a new Socket and connect it to the server
            OutputStream outputStream = socket.getOutputStream(); // Get the output stream from the socket
            InputStream inputStream = socket.getInputStream(); // Get the input stream from the socket
        ){
            int bufferSize = 1024;
            byte[] bufferServer = new byte[bufferSize];

            //# If the userInputString is longer than the buffer size, then only send the first bufferSize characters
            byte[] userInputArray = userInput;
            outputStream.write(userInputArray);
            outputStream.flush();

            // Create a new ByteArrayOutputStream to store the response from the server
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

            try {
                while (inputStream.read(bufferServer) != -1) {
                    responseStream.write(bufferServer, 0, 1024); 
                }
            } catch (IOException e) {
                System.err.println(e);
            }


            byte[] outputData = responseStream.toByteArray();
            return new String(outputData, StandardCharsets.UTF_8);
        }
    }



/*     
    // Method for handling two arguments (hostname and port)
    public static String askServer(String hostname, int port) throws IOException {
        try (
            Socket socket = new Socket(hostname, port); // Create a new Socket and connect it to the server
            OutputStream outputStream = socket.getOutputStream(); // Get the output stream from the socket
            InputStream inputStream = socket.getInputStream(); // Get the input stream from the socket
        ){
            int bufferSize = 1024;
            byte[] bufferServer = new byte[bufferSize]; // Create a buffer to store the response from the server
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream(); // Create a new ByteArrayOutputStream to store the response from the server

            try {
                while (inputStream.read(bufferServer) != -1) {
                    responseStream.write(bufferServer, 0, 1024);   
                }
            } catch (IOException e) {
                System.err.println(e);
            }

            byte[] outputData = responseStream.toByteArray();
            return new String(outputData, StandardCharsets.UTF_8);
        }
    } */
}