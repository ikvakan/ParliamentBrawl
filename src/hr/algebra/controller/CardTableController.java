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
import hr.algebra.nodes.NodeUtils;
import hr.algebra.repo.dal.Repository;
import hr.algebra.repo.dal.RepositoryFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author IgorKvakan
 */
public class CardTableController implements Initializable {

    private Player player;
    private Player opponent;

    private Deck playerDeck;
    private Hand playerHand;

    private Deck opponentDeck;
    private Hand opponentHand;

    @FXML
    private Pane pnOpponent, pnPlayer;
    @FXML
    private Button btnPlayerDeck, btnOpponentDeck1;
    @FXML
    private GridPane gridPlayer, gridField, gridOpponent;

    private Repository repository;
    @FXML
    private Label lbOpponentName, lbOpponentHealth, lbPlayerName, lbPlayerHealtj;
    @FXML
    private ImageView imageOpponent, imagePlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Optional<ButtonType> result =MessageUtils.ConfirmMessage("Start", "Star the game ?").showAndWait();
        try {
            initCardRepository();
            initObjects();
            //createCard();
            populateDeck();
            populateStartHand();
            createPlayers();
            createHand();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void drawCardFromDeck(ActionEvent event) {

    }

    private void populateDeck() throws FileNotFoundException, Exception {

        //deck.populateDeck(repository.selectCards());
        playerDeck = new Deck(repository.selectCards());
        playerDeck.shuffleCards();
        opponentDeck = new Deck(repository.selectCards());
        opponentDeck.shuffleCards();

    }

    private void populateStartHand() {
        playerHand.setStartHand(playerDeck);
        playerDeck.setDeckAfterPopulatingHand(playerHand);
        opponentHand.setStartHand(opponentDeck);
        opponentDeck.setDeckAfterPopulatingHand(opponentHand);

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
            
        } catch (ClassNotFoundException | NoSuchMethodException |SecurityException | InstantiationException 
                | IllegalAccessException | InvocationTargetException | IllegalArgumentException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    private void createHand() {

        createPlayerHand();
        createOpponentHand();

    }

    private void createPlayerHand() {
        int column = 0;
        int row = 0;

        for (Card card : playerHand.getHand()) {
            VBox vBox = NodeUtils.createVBox();
            Label title = NodeUtils.createTitle(card.getTitle());
            ImageView imageView = NodeUtils.createImageView(card.getImage());
            Label attackDefense = NodeUtils.createAttDef(card.getAttack(), card.getDefense());
            vBox.getChildren().setAll(title, imageView, attackDefense);

            gridPlayer.add(vBox, column++, row);

        }
    }

    private void createOpponentHand() {
        int column = 0;
        int row = 0;

        for (Card card : opponentHand.getHand()) {
            VBox vBox = NodeUtils.createVBox();
            Label title = NodeUtils.createTitle(card.getTitle());
            ImageView imageView = NodeUtils.createImageView(card.getImage());
            Label attackDefense = NodeUtils.createAttDef(card.getAttack(), card.getDefense());
            vBox.getChildren().setAll(title, imageView, attackDefense);

            gridOpponent.add(vBox, column++, row);

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
        lbPlayerHealtj.setText(String.valueOf(player.getHealth()));
    }

    private void createOpponent(Player opponent) {
        lbOpponentName.setText(opponent.getName());
        lbOpponentName.setStyle("-fx-background-color:#DFFF;");
        imageOpponent.setImage(opponent.getImage());
        lbOpponentHealth.setText(String.valueOf(opponent.getHealth()));

    }

}
