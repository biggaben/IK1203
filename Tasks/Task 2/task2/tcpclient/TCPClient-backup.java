package tcpclient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
    private static final int BUFFERSIZE = 1024; // Set the buffer size to 1024 bytes
    private final boolean shutdownAfterSend;
    private final Integer timeToWait;
    private final Integer maxReturnBytes;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        this.shutdownAfterSend = shutdown; // If the shutdown boolean parameter is true, TCPClient will shut down the connection in the outgoing direction (but only in that direction) after having sent the (optional) data to the server.
        this.timeToWait = timeout; // Max time to wait for data from server (null if no limit)
        this.maxReturnBytes = limit; // Max no. of bytes to receive from server (null if no limit)
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

            // If the shutdownAfterSend is true, shut down the connection in the outgoing direction
            if (shutdownAfterSend) {
                socket.shutdownOutput();
            }

            try {
                // If the timeToWait is not null, set the timeout for the input stream
                if (timeToWait != (null) && timeToWait > (0)) {
                    socket.setSoTimeout(timeToWait);
                }
                               
                int bytesRead = 0; // Number of bytes read from the server
                // Read the response from the server into the responseStream
                while ((inputStream.read(serverBuffer) != -1) && (maxReturnBytes == null || responseStream.size() < maxReturnBytes)){
                    if(maxReturnBytes != null && responseStream.size() + 1024 > maxReturnBytes){
                        responseStream.write(serverBuffer, 0, maxReturnBytes - responseStream.size());
                        break;
                    }
                    responseStream.write(serverBuffer, 0, 1024);
                    bytesRead = bytesRead + 1024;
                }

            } catch (SocketTimeoutException e) {
                System.err.println("Timeout while reading from server: " + e);
                throw e; // Re-throw the exception
            
            } catch (IOException e) {
                System.err.println("Error while reading from server: " + e);
                throw e; // Re-throw the exception
            }
            
            System.out.println("Size of response: " + responseStream.size());
            System.out.println("Size of response limit: " + maxReturnBytes);
            System.out.println("Cause of closing connection: " + socket.isOutputShutdown());
    
            return responseStream.toByteArray(); // Return the response from the server as a byte array
        }
    }
}

/*   ### GRAVEYARD ###*/