/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IgorKvakan
 */
public class ClientHandler extends Thread {

    private static Socket client;
  
    

    public ClientHandler(Socket client,DataInputStream dis,DataOutputStream dos) {
        this.client = client;
        
    }

    @Override
    public void run() {
      
        
    }

   
    

   

    public static String showClientConnectionInfo() {

        String message = "Client connected from port: " + client.getPort() + " " + client.getInetAddress();

        System.out.println(message);

        return message;

    }

    ;

}
