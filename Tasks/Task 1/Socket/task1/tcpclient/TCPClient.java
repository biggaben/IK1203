package tcpclient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    private static final int BUFFERSIZE = 1024; // Set the buffer size to 1024 bytes

    public TCPClient() { // Constructor
    }

    public byte[] askServer(String hostname, int port) throws IOException {
        return askServer(hostname, port, new byte[0]); // Call the askServer method with an empty byte array
    }

    public byte[] askServer(String hostname, int port, byte[] userInputBytes) throws IOException {
        try (
            Socket socket = new Socket(hostname, port); // Create a new Socket and connect it to the server
            OutputStream outputStream = socket.getOutputStream(); // Get the output stream from the socket
            InputStream inputStream = socket.getInputStream(); // Get the input stream from the socket
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream(); // Create a new ByteArrayOutputStream to store the response from the server
        ){
            byte[] serverBuffer = new byte[BUFFERSIZE];
            outputStream.write(userInputBytes); // Write the user input to the server
            outputStream.flush(); // Flush the output stream to ensure all data is sent to the server
            try {
                while (inputStream.read(serverBuffer) != -1) {
                    responseStream.write(serverBuffer, 0, 1024);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
            return responseStream.toByteArray(); // Return the response from the server as a byte array
        }
    }
}