/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils.net;

import enums.Grid;
import enums.PlayersIcon;
import hr.algebra.controller.CardTableController;
import hr.algebra.model.Card;
import hr.algebra.model.GameStateModel;
import hr.algebra.model.Player;
import hr.algebra.utils.node.card.CardUtils;
import hr.algebra.utils.node.icon.IconUtils;
import java.io.FileNotFoundException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class ProccesResponseData {
    
    public ProccesResponseData() {
    }
    
    private static class SingletonHelper{
        private static final ProccesResponseData INSTANCE=new ProccesResponseData();
    }
    
    
    public static ProccesResponseData getInstance(){
        return SingletonHelper.INSTANCE;
    }
    

    public void refreshGameState(GameStateModel gameStateModel, CardTableController tableController) throws FileNotFoundException  {
        

        clearFields(tableController);
        populateDeck(gameStateModel,tableController);
        
        refreshGrid(gameStateModel.getPlayerHand(), Grid.PLAYER,tableController);
        refreshGrid(gameStateModel.getOpponentHand(), Grid.OPPONENT,tableController);
        refreshGrid(gameStateModel.getFieldCards(), Grid.FIELD,tableController);
        
        setPlayers(gameStateModel.getPlayers(), tableController);
        
    }

   private  void populateDeck(GameStateModel gameStateModel, CardTableController tableController) {
        
        tableController.playerDeck.clearDeck();
        tableController.opponentDeck.clearDeck();
        tableController.playerDeck.setDeck(gameStateModel.getPlayerDeck());
        tableController.opponentDeck.setDeck(gameStateModel.getOpponentDeck());
    }
    
    private  void clearFields(CardTableController tableController) {
        
       
        tableController.gridPlayer.getChildren().removeAll(tableController.gridPlayer.getChildren());
        tableController.gridOpponent.getChildren().removeAll(tableController.gridOpponent.getChildren());

        ObservableList<Node> list = FXCollections.observableArrayList();

        for (Node node : tableController.gridField.getChildrenUnmodifiable()) {
            if (node instanceof VBox) {
                list.add(node);
            }
        }
        tableController.gridField.getChildren().removeAll(list);
       
    }
    
   private  void refreshGrid(List<Card> cards, Grid grid,CardTableController controller) throws FileNotFoundException  {

        switch (grid) {
            case PLAYER:
                fillGrid(cards, controller.gridPlayer);
                break;
            case OPPONENT:
                fillGrid(cards, controller.gridOpponent);
                break;
            case FIELD:
                fillGrid(cards, controller.gridField);
                break;

        }

    }
   
   private  void fillGrid(List<Card> cards, GridPane grid) throws FileNotFoundException {
        for (Card card : cards) {
            card.createImage(card.getPicturePath());
            VBox createCard = CardUtils.createCard(card);
            grid.add(createCard, card.getColumnIndex(), card.getRowIndex());

        }
    }
    
   private  void setPlayers(List<Player> players,CardTableController controller) throws FileNotFoundException {

        for (Player player : players) {
            if (player.getName().contentEquals(controller.PLAYER_NAME)) {
                player.createDefaultImage();
                IconUtils.modifyPlayers(player, PlayersIcon.PLAYER_ICON, controller.playerIcon);
            }
            if (player.getName().contentEquals(controller.OPPONENT_NAME)) {
                player.createDefaultImage();
                IconUtils.modifyPlayers(player, PlayersIcon.OPPONENT_ICON, controller.opponentIcon);
            }

        }

    }
   
}
