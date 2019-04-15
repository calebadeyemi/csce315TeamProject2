package application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class ServerMain {
     static List<ClientHandler> ar = new ArrayList<>();
     private static int[][] state = new int[8][8];
    void start(int portNumber) {

        ServerSocket server = null;

        try {
            // Server is listening on port 3000
            server = new ServerSocket(portNumber);

            // Main thread will be accepting connections through infinite loop
            Socket client = null;
            while (true) {

                client = server.accept();

                if (ar.size() < 2) {

                    System.out.println("New Client Connected: " + client);
                    ClientHandler clientSock;
                    if(ar.size() < 1) {
                        clientSock = new ClientHandler(client, "WELCOME", "WHITE");
                    } else {
                        clientSock = new ClientHandler(client, "WELCOME", "BLACK");
                    }
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        public int ID;
        public String welcome;
        public String color;

        public ClientHandler(Socket socket, String welcome, String color) {
            this.clientSocket = socket;
            this.welcome = welcome;
            this.color = color;
        }

        @Override
        public void run() {
            PrintWriter out;
            BufferedReader in;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Scanner scanner = new Scanner(System.in);
                out.println(welcome);
                if(color == "WHITE") {
                    out.println("INFO 5000 WHITE");
                } else if(color == "BLACK") {
                    out.println("INFO 5000 BLACK");
                }
                String line;
                while ((line = in.readLine()) != null) {
                    if(line.equals("READY")){
                        state = ChessState.getInitialPieceState();
                        long beginTime = System.currentTimeMillis();
                        line = in.readLine();
                        long endTime = System.currentTimeMillis();
                        if(endTime - beginTime > 5000) {
                            out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println("TIME");
                            out.println("LOSER");
                            if (color == "WHITE") {
                                out = new PrintWriter(ar.get(1).clientSocket.getOutputStream(), true);
                                out.println("WINNER");
                                out.flush();

                            } else if(color == "BLACK"){
                                out = new PrintWriter(ar.get(0).clientSocket.getOutputStream(), true);
                                out.println("WINNER");
                                out.flush();
                            }
                            out.flush();
                            //game over
                            break;
                        }
                        Move move = NetworkMessage.MoveMessageToMove(line);

                        // to validate
                        //if (move in ChessMovement.getPotentialMovements(state, move.colFrom, move.rowFrom)) { then
                        // valid };
                        int prevStateSum = ChessState.sumState(state);
                        state = ChessMovement.applyMove(state, move);


                        // math is probably wrong
                        if (Math.abs(ChessState.sumState(state) - prevStateSum) == PieceValue.King) {
                            // white wins
                        } else if (ChessState.sumState(state) < -900) {
                            // black wins
                        }
                        //send move to GUI - controller and check for winner
                        if (color == "WHITE") {
                            out = new PrintWriter(ar.get(1).clientSocket.getOutputStream(), true);
                            out.println(line);
                            out.flush();
                        } else if(color == "BLACK"){
                            out = new PrintWriter(ar.get(0).clientSocket.getOutputStream(), true);
                            out.println(line);
                            out.flush();
                        }
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("OK");
                        out.flush();
                    }
                    //System.out.printf("Sent from the client: %s\n", line);
                     else if (line.equals("disconnect")) {
                        for (int i = 0; i < ar.size(); i++) {
                            if (ar.get(i).ID == ID) {
                                ar.remove(i);
                                break;
                            }
                        }
                        System.out.println("There are only " + ar.size() + " hosts connected");
                    } else {
                        line = in.readLine();
                        Move move = NetworkMessage.MoveMessageToMove(line);
                        ChessMovement.applyMove(state, move);
                        //send to GUI controller and check for winner
                        if (color == "WHITE") {
                            out = new PrintWriter(ar.get(1).clientSocket.getOutputStream(), true);
                            out.println(line);
                            out.flush();
                        } else if(color == "BLACK"){
                            out = new PrintWriter(ar.get(0).clientSocket.getOutputStream(), true);
                            out.println(line);
                            out.flush();
                        }
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("OK");
                        out.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
