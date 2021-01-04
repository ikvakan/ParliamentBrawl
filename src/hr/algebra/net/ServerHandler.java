/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import hr.algebra.controller.CardTableController;
import hr.algebra.model.GameStateModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IgorKvakan
 */
public class ServerHandler extends Thread {

    private CardTableController tableController;

    private Socket client;
   
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerHandler(Socket client, CardTableController tableController, ObjectInputStream ois, ObjectOutputStream oos) {
        this.client = client;
       
        this.ois=ois;
        this.oos=oos;
        this.tableController = tableController;
    }

    @Override
    public void run() {

        try {

            while (true) {

                recieveData();

            }

        } catch (Exception e) {
            try {
                
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();

        }

    }

    private void recieveData() throws IOException, ClassNotFoundException {

        GameStateModel gameStateModel=(GameStateModel) ois.readObject();
        
        tableController.refreshGameState(gameStateModel,tableController);
      // tableController.refreshGameState(gameStateModel);
        
        

    }


}
