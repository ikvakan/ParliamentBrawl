/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import hr.algebra.utils.MessageUtils;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author IgorKvakan
 */
public class Main extends Application {

    private static final String TABLE = "view/Table.fxml";
    private static final String STAGE_TITLE = "ParliamentBrawl";

  

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(TABLE));

            Scene scene = new Scene(root, 1200, 800);

            primaryStage.setTitle(STAGE_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(e ->System.exit(0));
            primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
