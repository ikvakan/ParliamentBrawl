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
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author IgorKvakan
 */
public class GameClient extends Thread {

    private static final String HOST = "localhost";
    private static final int PORT = 1089;

    private boolean objectMoved = false;

    private static CardTableController tableController;

    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public GameClient(CardTableController tableController) {
        this.tableController = tableController;

    }

    private static final LinkedBlockingDeque<GameStateModel> gameState = new LinkedBlockingDeque<>();

    public static void trigger(GameStateModel gameStateModel) {
        gameState.add(gameStateModel);
    }

    public static CardTableController getController() {
        return tableController;
    }

    @Override
    public void run() { // ispisuje podatke koje dobia od handlera na kontroler

        try (Socket clientSocket = new Socket(HOST, PORT)) {

            initIOStream(clientSocket);
            DataHandler dataHandler = new DataHandler(clientSocket, tableController, ois, oos);
            dataHandler.start();

            while (true) {

                if (!gameState.isEmpty()) {
                    sendDataToServer();

                }

            }

        } catch (Exception e) {

            e.printStackTrace(); //maknuti da ne smeta dok nema konekcije
            System.out.println("Crklo: GameClient");

        }

    }

    private  void sendDataToServer() throws IOException {

        oos.writeObject(gameState.getFirst());
        gameState.clear();
        oos.flush();

    }

    private void initIOStream(Socket clientSocket) throws IOException {

        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
    }

}
