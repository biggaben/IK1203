import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ByteArrayOutputStream baos;

    public TCPClient() {
        this.socket = null;
        this.bufferedReader = null;
        this.bufferedWriter = null;
        this.baos = new ByteArrayOutputStream();
    }

    public byte[] askServer(String hostname, int port, byte[] bytesToServer) throws IOException {
        // Connect to the server
        this.socket = new Socket(hostname, port);

        // Initialize the reader and writer
        this.bufferedReader = this.socket.InputStream();
        this.bufferedWriter = this.socket.getOutputStream();

        // Write the bytes to the server
        this.bufferedWriter.write(new String(bytesToServer));
        this.bufferedWriter.flush();

        // Read the response from the serv

        // Write the response to the ByteArrayOutputStream
        return this.baos.toByteArray();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        TCPClient client = new TCPClient();
        byte[] response = client.askServer("localhost", 8080, "Hello, Server!");
        System.out.println(new String(response));
    }

}
