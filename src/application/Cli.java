
package application;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cli {

    public static void main(String args[]) {

        String host = "10.236.25.78";
        int port = 4000;

        try (Socket socket = new Socket(host,port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String line = null;

            System.out.println(in.readLine());

            while(!"disconnect".equalsIgnoreCase(line)) {
                //Get move object  from board
               // line = scanner.nextLine();
                out.println("READY");
                out.flush();
                line = in.readLine();
                if(line.equals("OK")){
                    continue;
                } else if(line.equals("TIME")){
                    System.out.println("TOOK TOO LONG");
                    out.println("disconnect");

                } else if(line.equals("WINNER")){
                    System.out.println("YOU WON");
                    out.println("disconnect");
                } else if(line.equals("LOSER") ) {
                    System.out.println("YOU LOST");
                    out.println("disconnect");
                }
               // System.out.println("Server replied " + in.readLine());


            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
