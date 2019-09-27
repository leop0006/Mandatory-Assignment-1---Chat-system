package Multithread;

import javax.naming.Name;
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Server {

    private static Server instance;

    private static Server getInstance() {

        if(instance == null){
            instance = new Server();
        }

        return instance;
    }

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
                String userName = dis.readUTF();

                System.out.println("Assigning new thread for this client");

                ClientHandler clientHandler = new ClientHandler(s, userName, dis, dos);

                // create a new thread object
                Thread t = new Thread(clientHandler);

                System.out.println("New user has been added to your channel");
                vc.add(clientHandler);
                dos.writeUTF("J_OK");


                // Starter tråden(thread)
                t.start();


        }
    }
}

