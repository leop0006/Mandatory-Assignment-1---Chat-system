package Multithread;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.UnknownHostException;


/**
 *
 * Chat system der tillader flere klienter/brugere at kunne skrive med hinanden
 *
 * @author Leopold berggreen nguessan
 *
 */

public class ClientRun {

    /**
     * Main metode til at køre klienter
     * @param args
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {


        Scanner skanner = new Scanner(System.in);

        //Modtage input fra brugeren(følger kun protokol)
        System.out.println("JOIN ip:serverport");
        String line = skanner.nextLine();

        //token til at dele input
        String[] tokens = line.split(" :");

        //Tester input om det overholder protokol
        if(tokens[0].equals("JOIN")){

            InetAddress ip = InetAddress.getByName("");

            int serverPort = 1414;

            //instantierer og kører clienten med serverport og ip
            Client client = new Client(serverPort, ip);
            client.runClient();
        }
    }

}
