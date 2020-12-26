/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import hr.algebra.controller.ServerController;
import hr.algebra.model.Card;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author IgorKvakan
 */
public class GameServer extends Thread {

    private static final int PORT = 1089;

    private final ServerController serverController;

    // ExecutorService pool= Executors.newFixedThreadPool(2);
    private LinkedList<Socket> clients;
    //private LinkedList<ClientHandler> clients;

    public GameServer(ServerController serverController) {
        this.serverController = serverController;
        clients = new LinkedList<>();
    }

    @Override // mozda maknuti Thread
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client port: " + clientSocket.getPort());

                serverController.connectionInfo(showClientConnectionInfo(clientSocket));

                serverController.connectionInfo(readMessageFromClient(clientSocket));

                sendMessageFromServer(clientSocket);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;


     public static String showClientConnectionInfo(Socket client) {

        String message = "Client connected from port: " + client.getPort() + " " + client.getInetAddress();

        System.out.println(message);

        return message;

    }

    ;

    private String readMessageFromClient(Socket client) throws IOException {

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());

        String msg = dis.readUTF();

        return msg;

    }

    private void sendMessageFromServer(Socket client) throws IOException {
        String msg = "[Server] Message sent.)";

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        dos.writeUTF(msg);
        System.out.println(msg);

    }

}
