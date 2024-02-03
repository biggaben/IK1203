/*
 * Call function: java TCPAsk [--shutdown] [--timeout <milliseconds>] [--limit <bytes>]  <hostname> <port> [<data for server>] 
 * Ex: java TCPAsk --timeout 300 whois.iis.se 43 kth.se
*/


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class TCPClient {

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        int dataLimit = limit; // The maximum number of bytes to read from the server
        int timeOut = timeout; // The maximum time to wait for a response from the server
        boolean shutDown = shutdown; // Whether to shut down the connection after the data has been sent to the server
    }

    // Method for handling three arguments (hostname, port and userInput)
    public static String askServer(String hostname, int port, String userInputString) throws IOException {
        try (
            Socket socket = new Socket(hostname, port); // Create a new Socket and connect it to the server
            OutputStream outputStream = socket.getOutputStream(); // Get the output stream from the socket
            InputStream inputStream = socket.getInputStream(); // Get the input stream from the socket
        ){
            int bufferSize = 1024;
            byte[] bufferServer = new byte[bufferSize];

            //# If the userInputString is longer than the buffer size, then only send the first bufferSize characters
            byte[] userInputArray;
            userInputArray = userInputString.getBytes(StandardCharsets.UTF_8);
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
    }
}