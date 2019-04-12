
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

            while(!"exit".equalsIgnoreCase(line)) {
                line = scanner.nextLine();
                out.println(line);
                out.flush();
                System.out.println("Server replied " + in.readLine());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
