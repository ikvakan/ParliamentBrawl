/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.rmi.server;

import hr.algebra.controller.ServerChatController;
import hr.algebra.remote.RemoteService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author IgorKvakan
 */
public class ChatServer {
     
    private static final String RMI_CLIENT = "client";
    private static final String RMI_SERVER = "server";
    private static final int REMOTE_PORT = 1099;
    private static final int RANDOM_PORT_HINT = 0;


    
    private RemoteService server;
    private RemoteService client;
    private Registry registry;

    private final ServerChatController chatController;

    public ChatServer(ServerChatController chatController) {
        this.chatController = chatController;
        publishServer();
        waitForClient();
    }

    public void publishServer() {
        server = new RemoteService() {
            @Override
            public void sendMessage(String msg) throws RemoteException {
                chatController.postMessage(msg);
            }
            
        };
      
        try {

            registry = LocateRegistry.createRegistry(REMOTE_PORT);
            RemoteService stub = (RemoteService) UnicastRemoteObject.exportObject(server, RANDOM_PORT_HINT);
            registry.rebind(RMI_SERVER, stub);

        } catch (RemoteException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void waitForClient() {

        Thread thread = new Thread(() -> {
            while (client == null) {
                try {
                    client = (RemoteService) registry.lookup(RMI_CLIENT);
                } catch (RemoteException | NotBoundException ex) {
                    System.out.println("waiting for client");
                }
                System.out.println(client);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        thread.setDaemon(true);
        thread.start();

    }

    public void send(String message) {
        try {
            client.sendMessage(message);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
