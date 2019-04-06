
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    // Array to keep track of all connected clients
    static List<ClientHandler> ar = new ArrayList<>();

    public static void main(String args[])  {

        ServerSocket server = null;

        try {
            // Server is listening on port 3000
            server = new ServerSocket(Integer.parseInt(args[0]));

            // Main thread will be accepting connections through infinite loop
            Socket client = null;
            while(true) {

                client = server.accept();

                if (ar.size() < Integer.parseInt(args[1])) {

                    System.out.println("New Client Connected: " + client);

                    ClientHandler clientSock = new ClientHandler(client, args[2]);
                    clientSock.ID = client.getPort();
                    // Thread will handle each client
                    Thread t = new Thread(clientSock);

                    System.out.println("Adding this client to active client list");

                    // Add client handler to client list
                    ar.add(clientSock);

                    t.start();
                } else {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                    out.println("Server is full come again later!");

                    out.close();
                    client.close();
                }


            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        public int ID;
        public String welcome;

        public ClientHandler(Socket socket, String welcome) {
            this.clientSocket = socket;
            this.welcome = welcome;
        }

        @Override
        public void run() {
            PrintWriter out;
            BufferedReader in;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.println(welcome);

                String line;
                while((line = in.readLine()) != null) {
                    System.out.printf("Sent from the client: %s\n", line);
                    if(line.equals("disconnect")) {
                        for (int i = 0; i < ar.size(); i++) {
                            if (ar.get(i).ID == ID) {
                                ar.remove(i);
                                break;
                            }
                        }
                        System.out.println("There are only " + ar.size() + " hosts connected");
                    }

                    out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


