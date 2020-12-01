/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.events.drag;

import enums.EventGesture;
import hr.algebra.game.logic.GameLogic;
import hr.algebra.model.Card;
import hr.algebra.model.Player;
import hr.algebra.utils.MessageUtils;
import hr.algebra.utils.node.card.CardUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 *
 */
public class HandleIconDragEvents {

    private static final String OPPONENT_HEALTH = "lbOpponentHealth";
    private static final String PLAYER_HEALTH = "lbPlayerHealth";

    public static void iconDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

        boolean canAttack = GameLogic.canAttack(event);

        if (dragboard.hasContent(CardUtils.CARD)
                && helperDragMethods.findParentFromNode("gridField", event, EventGesture.SOURCE) && canAttack) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    public static void iconDragDropped(DragEvent event, VBox playerIcon, VBox opponentIcon, Player player, Player opponent) {
        boolean dragCompleted = false;

        Dragboard db = event.getDragboard();

        VBox target = (VBox) event.getGestureTarget();
        VBox source = (VBox) event.getGestureSource();

        if (db.hasContent(CardUtils.CARD) && target == opponentIcon) {

            Card card = CardUtils.getCardFromNode(source.getChildren());

            for (Node node : opponentIcon.getChildren()) {
                if (node instanceof Label && node.getId().contentEquals(OPPONENT_HEALTH)) {
                    Label lbl = (Label) node;
                    String health = lbl.getText();
                    player.setHealth(Integer.valueOf(health) - card.getAttack());
                    if (player.isDead()) {
                        lbl.setText("DEAD");
                        MessageUtils.ShowMessage("Player dead", "You have lost the game !").showAndWait();
                    } else {
                        lbl.setText(String.valueOf(player.getHealth()));

                    }
                }
            }

            dragCompleted = true;
            event.setDropCompleted(dragCompleted);

        } else if (db.hasContent(CardUtils.CARD) && target == playerIcon) {

            Card card = CardUtils.getCardFromNode(source.getChildren());

            for (Node node : playerIcon.getChildren()) {
                if (node instanceof Label && node.getId().contentEquals(PLAYER_HEALTH)) {
                    Label lbl = (Label) node;
                    String health = lbl.getText();
                    opponent.setHealth(Integer.valueOf(health) - card.getAttack());
                    if (opponent.isDead()) {
                        lbl.setText("DEAD");
                        MessageUtils.ShowMessage("Player dead", "You have lost the game !").showAndWait();
                    } else {
                        lbl.setText(String.valueOf(opponent.getHealth()));

                    }
                }
            }

            dragCompleted = true;
            event.setDropCompleted(dragCompleted);
        }

        event.consume();

    }

}
