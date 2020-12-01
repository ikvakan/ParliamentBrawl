/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.events.drag;

import enums.EventGesture;
import hr.algebra.model.Card;
import hr.algebra.utils.node.card.CardUtils;
import static hr.algebra.utils.node.card.CardUtils.CARD;
import java.io.FileNotFoundException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class HandleCardDragEvents {

    public static void dragOver(DragEvent event) {

        Dragboard db = event.getDragboard();

        VBox source = (VBox) event.getGestureSource();
        VBox target = (VBox) event.getGestureTarget();

        if (db.hasContent(CARD) 
                && source != target 
                && !(helperDragMethods.findParentFromNode("gridPlayer", event, EventGesture.SOURCE)
                || helperDragMethods.findParentFromNode("gridOpponent", event, EventGesture.SOURCE))) 
        {
            
            event.acceptTransferModes(TransferMode.ANY);

        }

        event.consume();

    }

    public static void dragDetected(MouseEvent event) {

        VBox source = (VBox) event.getSource();

        Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);

        ObservableList<Node> nodes = source.getChildren();

        ClipboardContent content = new ClipboardContent();

        if (nodes.size() == 0) {
            event.consume();
            return;
        }

        Card card = CardUtils.getCardFromNode(nodes);

        content.put(CARD, card);

        dragboard.setContent(content);

        //System.out.println(dragboard.getContent(CARD));
        event.consume();

    }

    public static void dragDropped(DragEvent event) throws FileNotFoundException {

        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(CARD)) {

            Card attacker = (Card) dragboard.getContent(CARD);

            //source - attacker
            VBox source = (VBox) event.getGestureSource();

            //target - defender
            VBox target = (VBox) event.getGestureTarget();

            Card defender = CardUtils.getCardFromNode(target.getChildren());
            defender.createImage(defender.getPicturePath());

            GridPane parent = (GridPane) source.getParent();

            int attackerHealth = attacker.getDefense() - defender.getAttack();
            int defenderHealth = defender.getDefense() - attacker.getAttack();

            boolean isAttackerDead = attackerHealth < 1;
            boolean isDefenderDead = defenderHealth < 1;

            attacker.setDefense(attackerHealth);
            defender.setDefense(defenderHealth);

            System.out.println("Attacker:" + attacker.toString());
            System.out.println("Defender:" + defender.toString());

            if (isAttackerDead && !isDefenderDead) {

                removeAttacker(parent, source, target, defender);

                System.out.println("Dead:" + attacker.toString());

            }

            if (isDefenderDead && !isAttackerDead) {

                removeDefender(parent, source, target, attacker);

                System.out.println("Dead:" + defender.toString());
            }
            if (isAttackerDead && isDefenderDead) {

                removeBothCard(parent, source, target);

            }

            if (!isAttackerDead && !isDefenderDead) {

                modifyCards(parent, source, target, attacker, defender);

            }

            dragCompleted = true;
        }

        event.setDropCompleted(dragCompleted);
        event.consume();

    }

    private static void removeAttacker(GridPane parent, VBox source, VBox target, Card defender) {

        parent.getChildren().remove(source);

        Integer columnIndex = getColumnIndex(target);
        Integer rowIndex = getRowIndex(target);

        parent.getChildren().remove(target);

        parent.add(CardUtils.createCard(defender), columnIndex, rowIndex);

        setDragOverToAny(parent);

    }

    private static void removeDefender(GridPane parent, VBox source, VBox target, Card attacker) {

        parent.getChildren().remove(target);

        Integer columnIndex = getColumnIndex(source);
        Integer rowIndex = getRowIndex(source);

        parent.getChildren().remove(source);

        parent.add(CardUtils.createCard(attacker), columnIndex, rowIndex);

        setDragOverToAny(parent);

    }

    private static void removeBothCard(GridPane parent, VBox source, VBox target) {

        parent.getChildren().remove(source);
        parent.getChildren().remove(target);

        setDragOverToAny(parent);
    }

    private static void modifyCards(GridPane parent, VBox source, VBox target, Card attacker, Card defender) {

        Integer attackerColIndex = getColumnIndex(source);
        Integer AttackerRowIndex = getRowIndex(source);

        parent.getChildren().remove(source);
        parent.add(CardUtils.createCard(attacker), attackerColIndex, AttackerRowIndex);

        Integer defenderColIndex = getColumnIndex(target);
        Integer defenderRowIndex = getRowIndex(target);

        parent.getChildren().remove(target);
        parent.add(CardUtils.createCard(defender), defenderColIndex, defenderRowIndex);

        setDragOverToAny(parent);

    }

    private static Integer getRowIndex(Node node) {
        Integer rowIndex = GridPane.getRowIndex(node);
        return rowIndex;
    }

    private static Integer getColumnIndex(Node node) {
        Integer columnIndex = GridPane.getColumnIndex(node);
        return columnIndex;
    }

    public static void setDragOverToNone(DragEvent event) {
        Pane target = (Pane) event.getGestureTarget();
        target.setOnDragOver((e) -> {
            e.acceptTransferModes(TransferMode.NONE);
        });
    }

    public static void setDragOverToAny(GridPane parent) {

        ObservableList<Node> children = parent.getChildren();

        for (Node node : children) {
            if (node instanceof Pane) {
                node.setOnDragOver((e) -> {
                    e.acceptTransferModes(TransferMode.ANY);
                });
            }
        }

    }

}
