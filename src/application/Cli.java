
package application;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cli implements ChessMoveMakeable {
    // connect to server once and maintain connection

    // public class client implements ChessMoveMakeable {}

    // this will enforce Move getMove(int[][] state)

    // use the chess AI class to create the move.

    // then, pass that over the server.

    int pieceValue;
    int port;
    String host;
    ChessAi ai = new ChessAi(pieceValue);
    int [][] state;

    public Cli(String host, int port, int pieceValue) {
        this.host = host;
        this.port = port;
        this.pieceValue = pieceValue;
    }

    public void start() {

//         host = "10.236.25.78";
//         port = 4000;

        try (Socket socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String line = null;

            System.out.println(in.readLine());
            out.println("READY");

            while (!"disconnect".equalsIgnoreCase(line)) {
                //Get move object  from board
                Move move = getMove(state);
                line = NetworkMessage.MoveToMoveMessage(move);
                // line = scanner.nextLine();
                out.println(line);
                out.flush();

                line = in.readLine();
                if (line.equals("OK")) {
                    continue;
                } else if (line.equals("TIME")) {
                    System.out.println("TOOK TOO LONG");
                    out.println("disconnect");

                } else if (line.equals("WINNER")) {
                    System.out.println("YOU WON");
                    out.println("disconnect");
                } else if (line.equals("LOSER")) {
                    System.out.println("YOU LOST");
                    out.println("disconnect");
                } else {
                    Move opponentMove = NetworkMessage.MoveMessageToMove(line);
                    state = ChessMovement.applyMove(state,opponentMove);
                    //update GUI with controller
                }
                // System.out.println("Server replied " + in.readLine());


            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override public Move getMove ( int[][] state){
        // generate move through Ai,
        Move move = ai.getMove(state);

        // do server and client handshakes, etc.

        return move;
    }
}
