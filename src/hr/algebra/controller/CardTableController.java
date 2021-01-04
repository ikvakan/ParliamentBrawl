/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import enums.Grid;
import enums.PlayersIcon;
import hr.algebra.factory.DeckFactory;
import hr.algebra.model.Card;
import hr.algebra.model.Deck;
import hr.algebra.model.Player;
import hr.algebra.utils.node.card.CardUtils;
import hr.algebra.dal.repo.Repository;
import hr.algebra.dal.repo.RepositoryFactory;
import hr.algebra.dal.repo.SerializationRepository;
import hr.algebra.events.drag.HandleFieldDragEvents;
import hr.algebra.events.drag.HandleIconDragEvents;
import hr.algebra.model.GameStateModel;
import hr.algebra.net.GameClient;

import hr.algebra.reflection.HandleReflection;
import hr.algebra.utils.FileUtils;
import hr.algebra.utils.SerializationUtils;
import hr.algebra.utils.net.ProccesResponseData;
import hr.algebra.utils.node.icon.IconUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
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

    GameClient gameClient;

    private boolean isHost = false;

    private Player player;
    private Player opponent;

    public Deck playerDeck;
    public Deck opponentDeck;

    Integer columnIndex = 0;
    Integer rowIndex = 0;

    Integer lastColIndexPlayer;
    Integer lastRowIndexPlayer;

    Integer lastColIndexOpponent;
    Integer lastRowIndexOpponent;

    public final String PLAYER_NAME = "Player 1";
    public final String OPPONENT_NAME = "Player 2";

    @FXML
    private Pane pnOpponent, pnPlayer;
    @FXML
    private Button btnPlayerDeck, btnOpponentDeck;
    @FXML
    public GridPane gridPlayer;
    @FXML
    public GridPane gridField;
    @FXML
    public GridPane gridOpponent;

    @FXML
    private Label lbOpponentHealth, lbPlayerName, lbPlayerHealth, lbOpponentName;
    @FXML
    private ImageView imageOpponent, imagePlayer;

    @FXML
    private Pane playerPosition1, playerPosition2, playerPosition3, opponentPosition1, opponentPosition2, opponentPosition3;

    @FXML
    public VBox playerIcon;
    @FXML
    public VBox opponentIcon;

    @FXML
    private MenuItem miSaveData, miLoadData;
    @FXML
    private MenuItem miDocumentation;
    @FXML
    private Label lblTest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            initClient();
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

    private void initObjects() throws FileNotFoundException, IOException {
        try {

            playerDeck = DeckFactory.createDeck(Deck.class.getName());
            opponentDeck = DeckFactory.createDeck(Deck.class.getName());

            player = new Player(PLAYER_NAME);
            player.setHealth(20);
            opponent = new Player(OPPONENT_NAME);
            opponent.setHealth(20);

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

    private void populateDeck() throws FileNotFoundException, Exception {

        playerDeck.setDeck(repository.selectCards());
        // playerDeck.shuffleCards();

        opponentDeck.setDeck(repository.selectCards());
        //opponentDeck.shuffleCards();

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
            VBox vBox = CardUtils.createCard(card);

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

            VBox vBox = CardUtils.createCard(playerDeck.getCardForHand());
            gridPlayer.add(vBox, lastColIndexPlayer, lastRowIndexPlayer);

            System.out.println("Player deck size:" + " " + playerDeck.getDeck().size());

        }
        if (source instanceof Button && source.getId().contains("btnOpponentDeck")) {

            if (gridOpponent.getChildrenUnmodifiable().size() == 5) {
                resetLastIndex();
            }

            VBox vBox = CardUtils.createCard(opponentDeck.getCardForHand());
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
        HandleIconDragEvents.iconDragOver(event);

    }

    @FXML
    private void handleIconOnDragDropped(DragEvent event) {

        HandleIconDragEvents.iconDragDropped(event, playerIcon, opponentIcon, player, opponent);

    }

    @FXML
    private void handleSaveData(ActionEvent event) {

        try {
            File file = FileUtils.saveFileDialog(btnPlayerDeck.getScene().getWindow(), "ser");
            if (file != null) {

                SerializationRepository.getInstance().setPlayerDeck((List<Card>) playerDeck.getDeck());
                SerializationRepository.getInstance().setOpponentDeck((List<Card>) opponentDeck.getDeck());

                SerializationRepository.getInstance().setPlayerHand(ChooseGrid(Grid.PLAYER));
                SerializationRepository.getInstance().setOpponentHand(ChooseGrid(Grid.OPPONENT));
                SerializationRepository.getInstance().setFieldCards(ChooseGrid(Grid.FIELD));

                SerializationRepository.getInstance().addPlayer(IconUtils.getPlayerFromPane(PlayersIcon.PLAYER_ICON, playerIcon));
                SerializationRepository.getInstance().addPlayer(IconUtils.getPlayerFromPane(PlayersIcon.OPPONENT_ICON, opponentIcon));

                SerializationUtils.write(SerializationRepository.getInstance(), file.getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Card> ChooseGrid(Grid grid) {

        List<Card> cards = new ArrayList<>();

        switch (grid) {
            case PLAYER:
                cards = getCardsFromGrid(gridPlayer);
                break;
            case OPPONENT:
                cards = getCardsFromGrid(gridOpponent);
                break;
            case FIELD:
                cards = getCardsFromGrid(gridField);
                break;
        }

        return cards;
    }

    private List<Card> getCardsFromGrid(GridPane grid) {
        List<Card> list = new ArrayList<>();

        for (Node node : grid.getChildren()) {

            if (node instanceof VBox) {

                int columnIndex = GridPane.getColumnIndex(node);
                int rowIndex = GridPane.getRowIndex(node);
                Card card = CardUtils.getCardFromNode(((VBox) node).getChildren());
                card.setColumnIndex(columnIndex);
                card.setRowIndex(rowIndex);
                list.add(card);
            }
        }

        return list;
    }

    @FXML
    private void handleLoadData(ActionEvent event) {
        File file = FileUtils.uploadFileDialog(btnPlayerDeck.getScene().getWindow(), "ser");
        if (file != null) {
            try {
                clearFields();

                SerializationUtils.read(file.getAbsolutePath());

                populateDeckAfterLoad();

                fillGridAfterLoad(SerializationRepository.getInstance().getPlayerHand(), Grid.PLAYER);
                fillGridAfterLoad(SerializationRepository.getInstance().getOpponentHand(), Grid.OPPONENT);
                fillGridAfterLoad(SerializationRepository.getInstance().getFieldCards(), Grid.FIELD);

                setPlayersAfterLoad(SerializationRepository.getInstance().getPlayers());

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void clearFields() {

        gridPlayer.getChildren().removeAll(gridPlayer.getChildren());
        gridOpponent.getChildren().removeAll(gridOpponent.getChildren());

        ObservableList<Node> list = FXCollections.observableArrayList();

        for (Node node : gridField.getChildrenUnmodifiable()) {
            if (node instanceof VBox) {
                list.add(node);
            }
        }
        gridField.getChildren().removeAll(list);

    }

    private void fillGridAfterLoad(List<Card> cards, Grid grid) throws FileNotFoundException {

        switch (grid) {
            case PLAYER:
                fillGrid(cards, gridPlayer);
                break;
            case OPPONENT:
                fillGrid(cards, gridOpponent);
                break;
            case FIELD:
                fillGrid(cards, gridField);
                break;

        }

    }

    private void fillGrid(List<Card> cards, GridPane grid) throws FileNotFoundException {
        for (Card card : cards) {
            card.createImage(card.getPicturePath());
            VBox createCard = CardUtils.createCard(card);
            grid.add(createCard, card.getColumnIndex(), card.getRowIndex());

        }
    }

    public void populateDeckAfterLoad() {
        playerDeck.clearDeck();
        opponentDeck.clearDeck();
        playerDeck.setDeck(SerializationRepository.getInstance().getPlayerDeck());
        opponentDeck.setDeck(SerializationRepository.getInstance().getOpponentDeck());
    }

    private void setPlayersAfterLoad(List<Player> players) throws FileNotFoundException {

        for (Player player : players) {
            if (player.getName().contentEquals(PLAYER_NAME)) {
                player.createDefaultImage();
                IconUtils.modifyPlayers(player, PlayersIcon.PLAYER_ICON, playerIcon);
            }
            if (player.getName().contentEquals(OPPONENT_NAME)) {
                player.createDefaultImage();
                IconUtils.modifyPlayers(player, PlayersIcon.OPPONENT_ICON, opponentIcon);
            }

        }

    }

    @FXML
    private void handleDocumentationOnAction(ActionEvent event) {

        HandleReflection.createDocumentation();

    }

    private void initClient() {
        gameClient = new GameClient(this);

        gameClient.setDaemon(true);
        gameClient.start();
    }

    public void testClient(String msg) {
        lblTest.setText(msg);
    }

    public void testClientMessageHandler(String msg) {

        Platform.runLater(() -> {
            lblTest.setText(msg);

        });
    }


    public void refreshGameState(GameStateModel gameStateModel, CardTableController tableController) { //param controller?
        Platform.runLater(() -> {
            try {
                ProccesResponseData.refreshGameState(gameStateModel, tableController);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    

    
//    public void refreshGameState(GameStateModel gameStateModel) { //param controller?
//        Platform.runLater(() -> {
//            try {
//                clearFieldsClientThread();
//                refreshDeck(gameStateModel);
//
//                refreshGrid(gameStateModel.getPlayerHand(), Grid.PLAYER );
//                refreshGrid(gameStateModel.getOpponentHand(), Grid.OPPONENT);
//                refreshGrid(gameStateModel.getFieldCards(), Grid.FIELD);
//
//                setPlayersClientThread(gameStateModel.getPlayers());
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });
//
//    }
//    
//     private  void refreshDeck(GameStateModel gameStateModel ) {
//        
//        playerDeck.clearDeck();
//        opponentDeck.clearDeck();
//        playerDeck.setDeck(gameStateModel.getPlayerDeck());
//        opponentDeck.setDeck(gameStateModel.getOpponentDeck());
//    }
//    
//    private  void clearFieldsClientThread() {
//        
//       
//        gridPlayer.getChildren().removeAll(gridPlayer.getChildren());
//        gridOpponent.getChildren().removeAll(gridOpponent.getChildren());
//
//        ObservableList<Node> list = FXCollections.observableArrayList();
//
//        for (Node node : gridField.getChildrenUnmodifiable()) {
//            if (node instanceof VBox) {
//                list.add(node);
//            }
//        }
//        gridField.getChildren().removeAll(list);
//       
//    }
//    
//   private  void refreshGrid(List<Card> cards, Grid grid) throws FileNotFoundException  {
//
//        switch (grid) {
//            case PLAYER:
//                fillGridClientThread(cards, gridPlayer);
//                break;
//            case OPPONENT:
//                fillGridClientThread(cards, gridOpponent);
//                break;
//            case FIELD:
//                fillGridClientThread(cards, gridField);
//                break;
//
//        }
//
//    }
//   
//   private  void fillGridClientThread(List<Card> cards, GridPane grid) throws FileNotFoundException {
//        for (Card card : cards) {
//            card.createImage(card.getPicturePath());
//            VBox createCard = CardUtils.createCard(card);
//            grid.add(createCard, card.getColumnIndex(), card.getRowIndex());
//
//        }
//    }
//    
//   private  void setPlayersClientThread(List<Player> players) throws FileNotFoundException {
//
//        for (Player player : players) {
//            if (player.getName().contentEquals(PLAYER_NAME)) {
//                player.createDefaultImage();
//                IconUtils.modifyPlayers(player, PlayersIcon.PLAYER_ICON, playerIcon);
//            }
//            if (player.getName().contentEquals(OPPONENT_NAME)) {
//                player.createDefaultImage();
//                IconUtils.modifyPlayers(player, PlayersIcon.OPPONENT_ICON, opponentIcon);
//            }
//
//        }
//
//    }
    }
}
