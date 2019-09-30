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

    private static Server instance;

    private static Server getInstance() {

        if(instance == null){
            instance = new Server();
        }

        return instance;
    }

    static int i = 0;

    private Server(){

    }

    static Vector<ClientHandler> vc = new Vector<>();

    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1414
        ServerSocket ss = new ServerSocket(1414);

        Socket s;

        // running infinite loop for getting
        // client request
        while (true)
        {
            // socket object to receive incoming client requests
            s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                String name = dis.readUTF();

                System.out.println("Assigning new thread for this client");

                //skaber et nyt handler objekt for håndtering af request
                ClientHandler clientHandler = new ClientHandler(s, "client " + i, dis, dos);

                // create a new thread object
                Thread t = new Thread(clientHandler);

                System.out.println("New user has been added to your channel");
                vc.add(clientHandler);
                dos.writeUTF("J_OK");

                //tilføjer til listen af aktiver clienter
                vc.add(clientHandler);

                // Starter tråden(thread)
                t.start();

                //increment i for nye klienter
                i++;


        }
    }
}

