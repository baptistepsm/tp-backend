import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {

    public static void main(String[] args) throws IOException {

        //Variable w scanners
        Socket clientSocket;
        Scanner sc = new Scanner(System.in);
        BufferedReader in;
        PrintWriter out;

        //initialiser le socket client
        clientSocket = new Socket("localhost", 1234);
        System.out.println("Client connecté au serveur");
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));

        //Thread pour envoyer des messages
        Thread envoi = new Thread(() -> {
            while (true) {
                System.out.println("Entrez un message client : ");
                String message = sc.nextLine();
                out.println(message);
                out.flush();
            }
        });
        envoi.start();

        //Thread pour recevoir des messages
        Thread reception = new Thread(() -> {
            try {
                String message = in.readLine();
                while (message != null) {
                    System.out.println("serveur : " + message);
                    message = in.readLine();
                }
                sc.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        reception.start();

    }

}
