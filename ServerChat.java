import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class ServerChat {

    public static void main(String[] args) throws IOException {
        //Variable w scanners
        Socket ClientSocket;
        Scanner sc = new Scanner(System.in);
        BufferedReader in;
        PrintWriter out;

        try{
            //initialiser le socket serveur
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur démarré");
            ClientSocket = serverSocket.accept();
            out = new PrintWriter(ClientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));

            //Thread pour envoyer des messages
            Thread envoie = new Thread(() -> {
                while (true) {
                    System.out.println("Entrez un message serveur : ");
                    String msg = sc.nextLine();
                    out.println(msg);
                    out.flush();
                }
            });
            envoie.start();

            //Thread pour recevoir des messages
            Thread reception = new Thread(() -> {
                try {
                    String message = in.readLine();
                    while (message != null) {
                        System.out.println("client > " + message);
                        message = in.readLine();
                    }
                    in.close();
                    out.close();
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            reception.start();
        }catch(Exception e){
            System.out.println("Exception in " + Thread.currentThread().getName() + "\nException:\n" + e);
        }
    }
}