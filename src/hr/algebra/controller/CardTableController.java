/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.factory.DeckFactory;
import hr.algebra.model.Card;
import hr.algebra.model.Deck;
import hr.algebra.model.Player;
import hr.algebra.utils.NodeUtils;
import hr.algebra.dal.repo.Repository;
import hr.algebra.dal.repo.RepositoryFactory;
import hr.algebra.utils.HandleDragEvents;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author IgorKvakan
 */
public class CardTableController implements Initializable {

    private Repository repository;

    private Player player;
    private Player opponent;

    private Deck playerDeck;
    private Deck opponentDeck;

    Integer columnIndex = 0;
    Integer rowIndex = 0;

    Integer lastColIndexPlayer;
    Integer lastRowIndexPlayer;

    Integer lastColIndexOpponent;
    Integer lastRowIndexOpponent;
    
    List<Pane> panes;

    @FXML
    private Pane pnOpponent, pnPlayer;
    @FXML
    private Button btnPlayerDeck, btnOpponentDeck;
    @FXML
    private GridPane gridPlayer, gridField, gridOpponent;

    @FXML
    private Label lbOpponentName, lbOpponentHealth, lbPlayerName, lbPlayerHealtj;
    @FXML
    private ImageView imageOpponent, imagePlayer;

    @FXML
    private Pane playerPosition1, playerPosition2, playerPosition3,opponentPosition1, opponentPosition2, opponentPosition3;
    
    @FXML
    private Button btnTest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Optional<ButtonType> result =MessageUtils.ConfirmMessage("Start", "Start the game ?").showAndWait();
        try {
            initCardRepository();
            initObjects();
            initDragAndDrop();
            populateDeck();
            populateStartHand();
            createPlayers();
            createStartHand();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initCardRepository() {
        repository = RepositoryFactory.getRepository();

    }

    private void initObjects() throws FileNotFoundException {

        try {
            // playerDeck = new Deck();
//           
            //opponentDeck = new Deck();
//          

            playerDeck = DeckFactory.createDeck(Deck.class.getName());
            opponentDeck = DeckFactory.createDeck(Deck.class.getName());

            player = new Player("Player 1");
            opponent = new Player("Player 2");

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | InvocationTargetException | IllegalArgumentException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initDragAndDrop() {

        
        ObservableList<Node> panes = gridField.getChildren();

        panes.forEach(p -> p.setOnDragOver((DragEvent event) -> {
            dragOver(event);
        }));

        panes.forEach(p -> p.setOnDragDropped((event) -> {
            dragDropped(event);

        }));


    }

    private void dragOver(DragEvent event) {

        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(NodeUtils.CARD)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    @FXML
    private void handleCardOnDragEntered(DragEvent event) {

        Node node = (Node) event.getSource();
        columnIndex = GridPane.getColumnIndex(node);
        rowIndex = GridPane.getRowIndex(node);

        VBox source = (VBox) event.getGestureSource();
        Parent root = source.getParent();

        if (root instanceof GridPane && root.getId().contentEquals("gridPlayer")) {

            lastColIndexPlayer = GridPane.getColumnIndex(source);
            lastRowIndexPlayer = GridPane.getRowIndex(source);

        } else if (root instanceof GridPane && root.getId().contentEquals("gridOpponent")) {
            lastColIndexOpponent = GridPane.getColumnIndex(source);
            lastRowIndexOpponent = GridPane.getRowIndex(source);

        }

        if (columnIndex == null) {
            columnIndex = 0;

        }
        if (rowIndex == null) {
            rowIndex = 0;
        }

        System.out.println(columnIndex + " " + rowIndex);
    }

    private void dragDropped(DragEvent event) {
        boolean dragCompleted = false;

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(NodeUtils.CARD)) {

            Card copy = (Card) dragboard.getContent(NodeUtils.CARD);

            try {
                Card card = new Card(copy.getTitle(), copy.getAttack(), copy.getDefense(), copy.getPicturePath());
                VBox vbox = NodeUtils.createCard(card);

                gridField.add(vbox, columnIndex, rowIndex);

                HandleDragEvents.setDragOverToNone(event);

                VBox source = (VBox) event.getGestureSource();
                //System.out.println(source);
                Parent root = source.getParent();
                //System.out.println(root);

                if (root instanceof GridPane && root.getId().contentEquals("gridPlayer")) {

                    gridPlayer.getChildren().remove(source); //get column and row
                  

                } else if (root instanceof GridPane && root.getId().contentEquals("gridOpponent")) {

                    gridOpponent.getChildren().remove(source); //get column and row
                   
                }

                dragCompleted = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        event.setDropCompleted(dragCompleted);

        event.consume();
    }

//    private void setDragOverToNone(DragEvent event) {
//        Pane target = (Pane) event.getGestureTarget();
//        target.setOnDragOver((e) -> {
//            e.acceptTransferModes(TransferMode.NONE);
//        });
//    }

    private void populateDeck() throws FileNotFoundException, Exception {

        playerDeck.setDeck(repository.selectCards());
        playerDeck.shuffleCards();

        opponentDeck.setDeck(repository.selectCards());
        opponentDeck.shuffleCards();

    }

    private void populateStartHand() {

        playerDeck.createHand(playerDeck.getDeck());
        opponentDeck.createHand(opponentDeck.getDeck());

        System.out.println("p deck" + playerDeck.getDeck().size());

    }

    private void createStartHand() {

        createHand(playerDeck.getHand(), gridPlayer);
        createHand(opponentDeck.getHand(), gridOpponent);

    }

    private void createHand(List<Card> deck, GridPane grid) {
        int column = 0;
        int row = 0;

        for (Card card : deck) {
            VBox vBox = NodeUtils.createCard(card);

            grid.add(vBox, column++, row);

        }
    }

//    private VBox createCard(Card card) {
//        VBox vBox = NodeUtils.createVBox();
//        Label title = NodeUtils.createTitle(card.getTitle());
//        ImageView imageView = NodeUtils.createImageView(card.getImage(), card.getPicturePath());
//        Label attackDefense = NodeUtils.createAttDef(card.getAttack(), card.getDefense());
//        vBox.getChildren().setAll(title, imageView, attackDefense);
//        return vBox;
//    }

    private void createPlayers() {
        createPlayer(player);
        createOpponent(opponent);

    }

    private void createPlayer(Player player) {
        lbPlayerName.setText(player.getName());
        lbPlayerName.setStyle("-fx-background-color:#DFFF;");
        imagePlayer.setImage(player.getImage());
        lbPlayerHealtj.setText(String.valueOf(player.getHealth()));
    }

    private void createOpponent(Player opponent) {
        lbOpponentName.setText(opponent.getName());
        lbOpponentName.setStyle("-fx-background-color:#DFFF;");
        imageOpponent.setImage(opponent.getImage());
        lbOpponentHealth.setText(String.valueOf(opponent.getHealth()));

    }

    @FXML
    private void testSave(ActionEvent event) {

    }

    @FXML
    private void handleDeckOnAction(ActionEvent event) {

        if ((gridPlayer.getChildren().size() == 5 && gridOpponent.getChildren().size() == 5) || (playerDeck.getDeck().isEmpty() && opponentDeck.getDeck().isEmpty())) {
            event.consume();
            return;
        }

        Button source = (Button) event.getSource();

        if (source instanceof Button && source.getId().contains("btnPlayerDeck")) {

            VBox vBox = NodeUtils.createCard(playerDeck.getCardForHand());
            gridPlayer.add(vBox, lastColIndexPlayer, lastRowIndexPlayer);
            
            
            System.out.println(playerDeck.getDeck().size());

        } else if (source instanceof Button && source.getId().contains("btnOpponentDeck")) {

            VBox vBox = NodeUtils.createCard(opponentDeck.getCardForHand());
            gridOpponent.add(vBox, lastColIndexOpponent, lastRowIndexOpponent);
            
            
            System.out.println(opponentDeck.getDeck().size());

        }

    }

    private void resetLastIndex() {
        lastColIndexPlayer = null;
        lastRowIndexPlayer = null;
        lastColIndexOpponent = null;
        lastRowIndexOpponent = null;
    }

}
