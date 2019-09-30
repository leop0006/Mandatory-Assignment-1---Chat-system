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

// ClientHandler class
public class ClientHandler implements Runnable {

    String name;
    DataInputStream dis;
    DataOutputStream dos;
    Socket s;
    boolean isloggedin;


    // Constructor
    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.isloggedin = true;
    }

    //run metode til oprettelsen af objekt af klassen
    public void run() {
        String received;

        while (true) {

            try {

                //modtager besked
                received = dis.readUTF();

                //Disconnector en bruger hvis de skriver QUIT(protokol)
                if (received.equals("QUIT")) {
                    this.isloggedin = false;
                    this.s.close();
                    System.out.println(name + " has disconnected");
                    break;
                }

                //StringTokenizer deler en string op og angiver den en delimiter
                StringTokenizer stringTokenizer = new StringTokenizer(received, ":");
                String MsgToSend = stringTokenizer.nextToken();
                String name = stringTokenizer.nextToken();


                //Tjekker for alle clienthandler objekter i vector
                for(ClientHandler ch : Server.vc){
                    if(ch.name.equals(name) && ch.isloggedin == true){
                        ch.dos.writeUTF(this.name + " : " + MsgToSend);
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // closing resources
                this.dis.close();
                this.dos.close();

            } catch (IOException e) {
                e.printStackTrace();

            }

         }

      }

    }
