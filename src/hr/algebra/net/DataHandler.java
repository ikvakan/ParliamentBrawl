/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import hr.algebra.controller.CardTableController;
import hr.algebra.model.GameStateModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.sound.midi.Soundbank;

/**
 *
 * @author IgorKvakan
 */
public class DataHandler extends Thread {

    private CardTableController tableController;

    private static final LinkedBlockingDeque<GameStateModel> gameState = new LinkedBlockingDeque<>();

    private Socket client;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public DataHandler(Socket client, CardTableController tableController, ObjectInputStream ois, ObjectOutputStream oos) {
        this.client = client;
        this.ois = ois;
        this.oos = oos;
        this.tableController = tableController;
    }

    @Override
    public void run() {

        try {

            while (true) {

                recieveData();

            }

        } catch (Exception e) {

//            try {
//                ois.close();
//            } catch (IOException ex) {
//                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
//            }
            e.printStackTrace();
            System.out.println("Crklo: recievedata()");

        } finally {
            try {

                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Crklo: DataHandler mainThread");

            }
        }

    }

    private  void recieveData() throws IOException, ClassNotFoundException {

        GameStateModel gameStateModel = (GameStateModel) ois.readObject();
         
        
        Platform.runLater(() -> {
            try {
                tableController.refreshGameState(gameStateModel, tableController);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Crklo: recievedata() DataHandler");
            }

        });

        //tableController.refreshGameState(gameStateModel,tableController);
        // tableController.refreshGameState(gameStateModel);
    }

}
