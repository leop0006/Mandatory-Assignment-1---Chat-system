package Multithread;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 *
 * Chat system der tillader flere klienter/brugere at kunne skrive med hinanden
 *
 * @author Leopold berggreen nguessan
 *
 */

public class Server {

    private final static int MAX_CHAR_NAME = 12;


    static Vector<ClientHandler> vc = new Vector<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Server is running...");

        try {
            // server is listening on port 1414
            ServerSocket ss = new ServerSocket(1414);

            System.out.println("Server is currently listening on port " + ss);

            Socket s;

            // running infinite loop for getting
            // client request
            while (true) {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                String name = dis.readUTF();

                System.out.println("Assigning new thread for this client");

                //skaber et nyt handler objekt for håndtering af request
                ClientHandler clientHandler = new ClientHandler(s, name, dis, dos);

                // create a new thread object
                Thread t = new Thread(clientHandler);

                System.out.println("New user has been added to your channel");

                /*for (ClientHandler ch : Server.vc) {
                    if (ch.name.length() <= MAX_CHAR_NAME) {
                        dos.writeUTF("J_OK");

                    }

                   else {
                        dos.writeUTF("J_ER : Your name is too long. Try to reconnect with a shorter name");
                    }

                }*/


                //tilføjer til listen af aktiver clienter

                if (clientHandler.name.length() <= MAX_CHAR_NAME) {
                    vc.add(clientHandler);
                    dos.writeUTF("J_OK");
                } else {
                    dos.writeUTF("J_ER : Your name is too long. Reconnect with a name under 12 characters");
                }


                // Starter tråden(thread)
                t.start();


            }

        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();

        }
    }
}

