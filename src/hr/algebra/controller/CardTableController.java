/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.factory.DeckFactory;
import hr.algebra.factory.HandFactory;
import hr.algebra.model.Card;
import hr.algebra.model.Deck;
import hr.algebra.model.Hand;
import hr.algebra.model.Player;
import hr.algebra.utils.NodeUtils;
import hr.algebra.dal.repo.Repository;
import hr.algebra.dal.repo.RepositoryFactory;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
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
    private Hand playerHand;

    private Deck opponentDeck;
    private Hand opponentHand;

    private List<Card> cards;

    Integer columnIndex = 0;
    Integer rowIndex = 0;

    @FXML
    private Pane pnOpponent, pnPlayer;
    @FXML
    private Button btnPlayerDeck, btnOpponentDeck1;
    @FXML
    private GridPane gridPlayer, gridField, gridOpponent;

    @FXML
    private Label lbOpponentName, lbOpponentHealth, lbPlayerName, lbPlayerHealtj;
    @FXML
    private ImageView imageOpponent, imagePlayer;

    @FXML
    private Pane playerPosition1, playerPosition2, playerPosition3;
    @FXML
    private Pane opponentPosition1,opponentPosition2,opponentPosition3;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Optional<ButtonType> result =MessageUtils.ConfirmMessage("Start", "Start the game ?").showAndWait();
        try {
            initCardRepository();
            initObjects();
            initDragAndDrop();
            //createCard();
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
            //playerDeck = new Deck();
            //playerHand = new Hand();
            //opponentDeck = new Deck();
            //opponentHand = new Hand();

            playerDeck = DeckFactory.createDeck(Deck.class.getName());
            playerHand = HandFactory.createHand(Hand.class.getName());
            opponentDeck = DeckFactory.createDeck(Deck.class.getName());
            opponentHand = HandFactory.createHand(Hand.class.getName());

            player = new Player("Player 1");
            opponent = new Player("Player 2");

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | InvocationTargetException | IllegalArgumentException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initDragAndDrop() {

        gridField.setOnDragOver((DragEvent event) -> {

            dragOver(event);

        });

        gridField.setOnDragDropped((event) -> {

            dragDropped(event);

        });

    }

    private void dragOver(DragEvent event) {

        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(NodeUtils.CARD)) {
            event.acceptTransferModes(TransferMode.ANY);
            
        }
      
      

        event.consume();
    }

    private void dragDropped(DragEvent event) {
        boolean dragCompleted = false;

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(NodeUtils.CARD)) {

            Card copy = (Card) dragboard.getContent(NodeUtils.CARD);

            try {
                Card card = new Card(copy.getTitle(), copy.getAttack(), copy.getDefense(), copy.getPicturePath());
                VBox vbox = createCard(card);

                gridField.add(vbox, columnIndex, rowIndex);
                dragCompleted = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        event.setDropCompleted(dragCompleted);
        event.consume();
    }

    private void populateDeck() throws FileNotFoundException, Exception {

        cards = repository.selectCards();
        //deck.populateDeck(repository.selectCards());
        playerDeck = new Deck(cards);
        playerDeck.shuffleCards();
        opponentDeck = new Deck(cards);
        opponentDeck.shuffleCards();

    }

    private void populateStartHand() {
        playerHand.setStartHand(playerDeck);
        playerDeck.setDeckAfterPopulatingHand(playerHand);
        opponentHand.setStartHand(opponentDeck);
        opponentDeck.setDeckAfterPopulatingHand(opponentHand);

    }

    private void createStartHand() {

        createHand(playerHand, gridPlayer);
        createHand(opponentHand, gridOpponent);

    }

    private void createHand(Hand hand, GridPane grid) {
        int column = 0;
        int row = 0;

        for (Card card : hand.getHand()) {
            VBox vBox = createCard(card);

            grid.add(vBox, column++, row);

        }
    }

    private VBox createCard(Card card) {
        VBox vBox = NodeUtils.createVBox();
        Label title = NodeUtils.createTitle(card.getTitle());
        ImageView imageView = NodeUtils.createImageView(card.getImage(), card.getPicturePath());
        Label attackDefense = NodeUtils.createAttDef(card.getAttack(), card.getDefense());
        vBox.getChildren().setAll(title, imageView, attackDefense);
        return vBox;
    }

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
    private void handleCardOnDragEntered(DragEvent event) {
        
        Node node = (Node) event.getSource();

        columnIndex = GridPane.getColumnIndex(node);
        rowIndex = GridPane.getRowIndex(node);

        if (columnIndex == null ) {
            columnIndex = 0;
           
        }
         if (rowIndex == null) {
            rowIndex=0;
        }

        System.out.println(columnIndex + " " + rowIndex);
    }

   
}
