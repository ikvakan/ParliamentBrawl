/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.rmi.client;

import hr.algebra.controller.ClientChatController;
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
public class ChatClient {
      
    private static final String RMI_CLIENT = "client";
    private static final String RMI_SERVER = "server";
    private static final int REMOTE_PORT = 1099;
    private static final int RANDOM_PORT_HINT = 0;

    //private static final String RMI_URL = "rmi://localhost:1099";

 
    private RemoteService client;
    private RemoteService server;
    private Registry registry;

    private final ClientChatController chatController;

    public ChatClient(ClientChatController chatController) {
        this.chatController = chatController;
        publishClient();
        fetchServer();
    }

    public void publishClient() {
        client = new RemoteService() {
            @Override
            public void sendMessage(String msg) throws RemoteException {
                chatController.postMessage(msg);
            }
            
        };
        try {
            registry = LocateRegistry.getRegistry(REMOTE_PORT);
            RemoteService stub = (RemoteService) UnicastRemoteObject.exportObject(client, RANDOM_PORT_HINT);
            registry.rebind(RMI_CLIENT, stub);

        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fetchServer() {

        try {
            server = (RemoteService) registry.lookup(RMI_SERVER);
        } catch (NotBoundException | RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void send(String message) {
        try {
            server.sendMessage(message);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
