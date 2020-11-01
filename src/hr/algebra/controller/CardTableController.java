/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.dal.sql.CardRepository;
import hr.algebra.model.Card;
import hr.algebra.model.Deck;
import hr.algebra.model.Hand;
import hr.algebra.repo.dal.Repository;
import hr.algebra.repo.dal.RepositoryFactory;
import java.io.File;
import java.io.FileNotFoundException;
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

    private List<Card> listDeck;
    private List<Card> cardsInHand;

    private Deck deck;
    private Hand hand;

    @FXML
    private Pane pnOpponent, pnPlayer;
    @FXML
    private Button btnPlayerDeck;
    @FXML
    private GridPane gridPlayer, gridField;

    private Repository repository;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Optional<ButtonType> result =MessageUtils.ConfirmMessage("Start", "Star the game ?").showAndWait();
        try {
            initCardRepository();
            initObjects();
            //createCard();
            populateDeck();
            populateStartHand();
            createCards();

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
        deck=new Deck(repository.selectCards());
        deck.shuffleCards();

    }

    private void populateStartHand() {
        hand.setStartHand(deck);
        deck.setDeckAfterPopulatingHand(hand);
        
    }

    private void initCardRepository() {
        repository = RepositoryFactory.getRepository();

    }

    private void initObjects() {
        deck = new Deck();
        hand = new Hand();
    }

    private void createCards()  {

        int column = 0;
        int row = 0;

        for (Card card : hand.getHand()) {
            VBox vBox = createVBox();
            Label title = createTitle(card.getTitle());
            ImageView imageView = createImageView(card.getImage());
            Label attackDefense = createAttDef(card.getAttack(), card.getDefense());
            vBox.getChildren().setAll(title, imageView, attackDefense);
            gridPlayer.add(vBox, column++, row);

        }

    }

    private Label createTitle(String title) {
        Label lbl = new Label(title);
        return lbl;
    }

    private VBox createVBox() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(100);
        vBox.setPrefHeight(150);
        vBox.setMaxWidth(vBox.USE_PREF_SIZE);
        vBox.setMaxHeight(vBox.USE_PREF_SIZE);
        vBox.setStyle("-fx-background-color:#DFFF; -fx-border-color: #0284d0; -fx-border-width: 2;");
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(90);
        return imageView;
    }

    private Label createAttDef(int attack, int defense) {

        Label lbl = new Label(String.valueOf(attack) + File.separator + String.valueOf(defense));
        return lbl;
    }

}
