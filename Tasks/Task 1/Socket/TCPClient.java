import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ByteArrayOutputStream baos;
    Scanner scanner = new Scanner(System.in);

    public TCPClient() {
        this.socket = null;
        this.bufferedReader = null;
        this.bufferedWriter = null;
        this.baos = new ByteArrayOutputStream();
    }

    public byte[] askServer(String hostname, int port, byte[] bytesToServer) throws IOException {
        // Connect to the server
        this.socket = new Socket(hostname, port);

        // Ask the user for input
        System.out.print("Input: ");
        String userInput = scanner.nextLine();

        // Write the user input to the ByteArrayOutputStream
        this.baos.write(userInput.getBytes());

        // Initialize the reader and writer
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

        // Write the bytes to the server
        this.bufferedWriter.write(new String(bytesToServer));
        this.bufferedWriter.flush();
        // Read the response from the server

        // Write the response to the ByteArrayOutputStream
        return this.baos.toByteArray();
    }

    public byte[] listenToServer(String hostname, int port, byte[] bytesToServer) throws IOException {
        // Connect to the server
        this.socket = new Socket(hostname, port);

        // Initialize the reader and writer
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

        // Write the bytes to the server
        this.bufferedWriter.write(new String(bytesToServer));
        this.bufferedWriter.flush();
        // Read the response from the server

        // Write the response to the ByteArrayOutputStream
        return this.baos.toByteArray();
    }

    public static void main(String[] args) throws IOException {

        TCPClient client = new TCPClient();
        byte[] response = client.askServer("localhost", 8080, "Hello, Server!".getBytes());
        System.out.println(new String(response));

    }

}

public class TCPAsk {
    public static void main(String[] args) throws InterruptedException {
        String hostname = localhost;
        int port = 8080;
        String userInput = null;

        try {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
            if (args.length >= 3) {
                // Collect remaining arguments into a single string
                StringBuilder builder = new StringBuilder();
                boolean first = true;
                for (int i = 2; i < args.length; i++) {
                    if (first)
                        first = false;
                    else
                        builder.append(" ");
                    builder.append(args[i]);
                }
                builder.append("\n");
                userInput = builder.toString();
            }
        } catch (Exception ex) {
            System.err.println("Usage: TCPAsk host port <data to server>");
            System.exit(1);
        }
        try {
            String serverOutput;
            if (userInput != null)
                serverOutput = TCPClient.askServer(hostname, port, userInput);
            else
                serverOutput = TCPClient.askServer(hostname, port);
            System.out.printf("%s:%d says:\n%s", hostname, port, serverOutput);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }
}
