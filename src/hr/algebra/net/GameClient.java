/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import hr.algebra.controller.CardTableController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author IgorKvakan
 */
public class GameClient extends Thread {

    private static final String HOST = "localhost";
    private static final int PORT = 1089;

    private CardTableController tableController;

    public GameClient(CardTableController tableController) {
        this.tableController = tableController;

    }

  
    @Override
    public void run() {

        try (Socket clientSocket = new Socket(HOST, PORT)) {

            //String msg = "Client connection established";

            sendMessageToServer(clientSocket, "Client connection established");

            tableController.testClient(recieveMessage(clientSocket));
            
            

        } catch (Exception e) {
            e.printStackTrace(); //maknuti da ne smeta jer nema konekcije

        }

    }

    private void sendMessageToServer(Socket client, String msg) throws IOException {
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        //DataInputStream dis = new DataInputStream(client.getInputStream());
        dos.writeUTF(msg);
        
        System.out.println(msg);
    }

    private String recieveMessage(Socket client) throws IOException {
        //DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String msg = (String) dis.readUTF();

        return msg;

    }

}
