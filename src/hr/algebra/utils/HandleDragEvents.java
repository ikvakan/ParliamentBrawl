/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import hr.algebra.model.Card;
import static hr.algebra.utils.NodeUtils.CARD;
import java.io.FileNotFoundException;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Parent;
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
public class HandleDragEvents {

    public static void dragOver(DragEvent event) {

        Dragboard db = event.getDragboard();

        VBox source =(VBox) event.getGestureSource();
        VBox target = (VBox) event.getGestureTarget();
        
        Parent parent = source.getParent();
        
        
        
        

        
         if (db.hasContent(CARD) && !(parent.getId().contains("gridPlayer") || parent.getId().contains("gridOpponent")  ) ) {
            event.acceptTransferModes(TransferMode.COPY);
            
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

        Card card = NodeUtils.getCardFromNode(nodes);

        content.put(CARD, card);

        dragboard.setContent(content);

        System.out.println(dragboard.getContent(CARD));
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

            Card defender = NodeUtils.getCardFromNode(target.getChildren());
            defender.createImage(defender.getPicturePath());
          
            GridPane parent = (GridPane) source.getParent();

            int attackerHealth = attacker.getDefense() - defender.getAttack();
            int defenderHealth = defender.getDefense() - attacker.getAttack();

            attacker.setDefense(attackerHealth);
            defender.setDefense(defenderHealth);

            System.out.println("Attacker:" + attacker.toString());
            System.out.println("Defender:" + defender.toString());

            if (attackerHealth < 1) {

                parent.getChildren().remove(source);

                Integer columnIndex = getColumnIndex(target);
                Integer rowIndex = getRowIndex(target);
                
                
                parent.getChildren().remove(target);
                
                parent.add(NodeUtils.createCard(defender), columnIndex, rowIndex);

                setDragOverToAny(parent);

                

                System.out.println("Dead:" + attacker.toString());
            }
            
            if (defenderHealth < 1) {

                
                setDragOverToAny(parent);

                System.out.println("Dead:" + defender.toString());
            } 
            
//           else if (attackerHealth < 1 && defenderHealth <1 ) {
//                
//                parent.getChildren().remove(source);
//                parent.getChildren().remove(target);
//                
//            }
            else {

                
                System.out.println("Both alive:" + attacker.toString() + " " + defender.toString());
            }

            dragCompleted=true;
        }

        event.setDropCompleted(dragCompleted);
        event.consume();

    }

    private static Integer getRowIndex(Node target) {
        Integer rowIndex = GridPane.getRowIndex(target);
        return rowIndex;
    }

    private static Integer getColumnIndex(Node target) {
        Integer columnIndex = GridPane.getColumnIndex(target);
        return columnIndex;
    }

    public static void setDragOverToNone(DragEvent event) {
        Pane target = (Pane) event.getGestureTarget();
        target.setOnDragOver((e) -> {
            e.acceptTransferModes(TransferMode.NONE);
        });
    }

    public static void setDragOverToAny(GridPane parent) {

        parent.setOnDragOver((e) -> {
            e.acceptTransferModes(TransferMode.ANY);
        });

    }

}
