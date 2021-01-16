/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;


import hr.algebra.rmi.client.ChatClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author IgorKvakan
 */
public class ClientChatController implements Initializable {

    
    private static final int MESSAGE_LENGTH = 78;
    private static final int FONT_SIZE = 15;
   
    
    private ObservableList<Node> messages;

    private ChatClient client;
    
    @FXML
    private TextField tfMessage;
    @FXML
    private ScrollPane spContainer;
    @FXML
    private VBox vbMessages;
    @FXML
    private Button btnSendMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        client= new ChatClient(this);
        
        messages = FXCollections.observableArrayList();
        Bindings.bindContentBidirectional(messages, vbMessages.getChildren());
        tfMessage.textProperty().addListener(
                (observable, oldValue, newValue) -> { 
                    if (newValue.length() >= MESSAGE_LENGTH) {
                        ((StringProperty) observable).setValue(oldValue);
                    }
                }
        );

    }

   

    @FXML
    private void send(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }

    }

    @FXML
    private void sendMessage() {
        if (tfMessage.getText().trim().length() > 0) {
            
            client.send(tfMessage.getText().trim());
            
            addMessage(tfMessage.getText().trim());
            tfMessage.clear();
        }

    }

    public void postMessage(String message) {
        Platform.runLater(() -> {
            addMessage(message);
        });
    }

    private void addMessage(String message) {
        Label label = new Label();
        label.setFont(new Font(FONT_SIZE));

        label.setText(message);
        messages.add(label);
        moveScrollPane();
    }

    private void moveScrollPane() {
        spContainer.applyCss();
        spContainer.layout();
        spContainer.setVvalue(1D);
    }


      
}
