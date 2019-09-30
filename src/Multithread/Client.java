package Multithread;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * Chat system der tillader flere klienter/brugere at kunne skrive med hinanden
 *
 * @author Leopold berggreen nguessan
 *
 */

// Client class
public class Client {

    int ServerPort;
    // getting localhost ip
    InetAddress ip;



    public Client(int serverPort, InetAddress ip) {
        ServerPort = serverPort;
        this.ip = ip;
    }


    /**
     * Metoden for klienten
     * Klienterne får tildelt en socket fra clientmain
     * Således bliver der også oprettede input og outputstream til at kunne sende og modtage beskeder
     *
     * @throws IOException
     */
    public void runClient() throws IOException {
        Scanner skanner = new Scanner(System.in);

        // Skaber forbindelse med serverporten og beder om dit userName
        Socket s = new Socket(ip, ServerPort);
        System.out.println("Enter your username");

        // instantierer input og outputstream
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());


        //tråd til at sende beskeder
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        String msg = skanner.nextLine();

                        dos.writeUTF(msg);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //tråd til at modtage beskeder
        Thread readMessage = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    try {

                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {

                        System.out.println("User has left your channel");
                        break;

                    }
                }
            }
        });

        //For at starte begge tråde
        sendMessage.start();
        readMessage.start();

    }

}
