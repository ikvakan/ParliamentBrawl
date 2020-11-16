/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import enums.EventGesture;
import hr.algebra.factory.DeckFactory;
import hr.algebra.model.Card;
import hr.algebra.model.Deck;
import hr.algebra.model.Player;
import hr.algebra.utils.NodeUtils;
import hr.algebra.dal.repo.Repository;
import hr.algebra.dal.repo.RepositoryFactory;
import hr.algebra.dragEvents.HandleFieldDragEvents;
import hr.algebra.dragEvents.HandleIconDragEvents;
import hr.algebra.dragEvents.HelperDragMethods;
import hr.algebra.factory.PlayerFactory;
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
import javafx.scene.Group;
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

    List<Pane> playerPanes;
    List<Pane> opponentPanes;

    @FXML
    private Pane pnOpponent, pnPlayer;
    @FXML
    private Button btnPlayerDeck, btnOpponentDeck;
    @FXML
    private GridPane gridPlayer, gridField, gridOpponent;

    @FXML
    private Label lbOpponentHealth, lbPlayerName, lbPlayerHealth, lbOpponentName;
    @FXML
    private ImageView imageOpponent, imagePlayer;

    @FXML
    private Pane playerPosition1, playerPosition2, playerPosition3, opponentPosition1, opponentPosition2, opponentPosition3;

    @FXML
    private Button btnTest;

    @FXML
    private VBox playerIcon, opponentIcon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Optional<ButtonType> result =MessageUtils.ConfirmMessage("Start", "Start the game ?").showAndWait();
        try {
            initCardRepository();
            initObjects();
            initDragAndDrop();
            initGroupPanes();
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

            playerDeck = DeckFactory.createDeck(Deck.class.getName());
            opponentDeck = DeckFactory.createDeck(Deck.class.getName());

            player = new Player("Player 1");
            opponent = new Player("Player 2");
            
            
//            player = PlayerFactory.createPlayer(Player.class.getName());
//            opponent = PlayerFactory.createPlayer(Player.class.getName());

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | InvocationTargetException | IllegalArgumentException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initDragAndDrop() {

        ObservableList<Node> panes = gridField.getChildren();

        panes.forEach(p -> p.setOnDragOver((DragEvent event) -> {
            HandleFieldDragEvents.dragOver(event);
        }));

        panes.forEach(p -> p.setOnDragDropped((event) -> {
            HandleFieldDragEvents.dragDropped(event, columnIndex, rowIndex);

        }));

    }

     private void initGroupPanes() {
        playerPanes=Arrays.asList(playerPosition1,playerPosition2,playerPosition3);
        opponentPanes=Arrays.asList(opponentPosition1,opponentPosition2,opponentPosition3);
    }
    
    private void populateDeck() throws FileNotFoundException, Exception {

        playerDeck.setDeck(repository.selectCards());
        playerDeck.shuffleCards();

        opponentDeck.setDeck(repository.selectCards());
        opponentDeck.shuffleCards();

    }

    private void populateStartHand() {

        playerDeck.createHand(playerDeck.getDeck());
        opponentDeck.createHand(opponentDeck.getDeck());

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

    private void createPlayers() {
        
        
        createPlayer(player);
        createOpponent(opponent);

    }

    private void createPlayer(Player player) {
        lbPlayerName.setText(player.getName());
        lbPlayerName.setStyle("-fx-background-color:#DFFF;");
        imagePlayer.setImage(player.getImage());
        lbPlayerHealth.setText(String.valueOf(player.getHealth()));
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

    @FXML
    private void handleDeckOnAction(ActionEvent event) {

        if ((gridPlayer.getChildren().size() == 5 && gridOpponent.getChildren().size() == 5) || (playerDeck.getDeck().isEmpty() && opponentDeck.getDeck().isEmpty())) {
            event.consume();
            return;
        }

        Button source = (Button) event.getSource();

        if (source instanceof Button && source.getId().contains("btnPlayerDeck")) {

            if (gridPlayer.getChildrenUnmodifiable().size() == 5) {
                resetLastIndex();
            }

            VBox vBox = NodeUtils.createCard(playerDeck.getCardForHand());
            gridPlayer.add(vBox, lastColIndexPlayer, lastRowIndexPlayer);

            System.out.println("Player deck size:" + " " + playerDeck.getDeck().size());

        }
        if (source instanceof Button && source.getId().contains("btnOpponentDeck")) {

            if (gridOpponent.getChildrenUnmodifiable().size() == 5) {
                resetLastIndex();
            }

            VBox vBox = NodeUtils.createCard(opponentDeck.getCardForHand());
            gridOpponent.add(vBox, lastColIndexOpponent, lastRowIndexOpponent);

            System.out.println("Opponent deck size:" + " " + opponentDeck.getDeck().size());

        }

    }

    private void resetLastIndex() {
        lastColIndexPlayer = null;
        lastRowIndexPlayer = null;
        lastColIndexOpponent = null;
        lastRowIndexOpponent = null;
    }

    @FXML
    private void handleIconOnDragOver(DragEvent event) {

        Dragboard dragboard = event.getDragboard();
        //List<Pane> panes = Arrays.asList(playerPosition1, playerPosition2, playerPosition3, opponentPosition1, opponentPosition2, opponentPosition3);
        
        boolean canAttack=HandleIconDragEvents.canAttack(event);
        
        if (dragboard.hasContent(NodeUtils.CARD)
                && HelperDragMethods.findParentFromNode("gridField", event, EventGesture.SOURCE) && canAttack ) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

    }

    @FXML
    private void handleIconOnDragDropped(DragEvent event) {

           
           
        
    }

   

}
