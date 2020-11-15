/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dragEvents;

import hr.algebra.controller.CardTableController;
import hr.algebra.model.Card;
import hr.algebra.utils.NodeUtils;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class HandleFieldDragEvents {
    
    public static void dragDropped(DragEvent event,int columnIndex,int rowIndex ) {
        boolean dragCompleted = false;

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(NodeUtils.CARD)) {

            Card copy = (Card) dragboard.getContent(NodeUtils.CARD);

            try {
                Card card = new Card(copy.getTitle(), copy.getAttack(), copy.getDefense(), copy.getPicturePath());
                VBox vbox = NodeUtils.createCard(card);

                Node node  =  (Node) event.getGestureTarget();
                
                Parent targetGrid = node.getParent();
                
                if (targetGrid instanceof GridPane && targetGrid.getId().contains("gridField")) {
                    GridPane gridField=(GridPane) targetGrid;
                    gridField.add(vbox, columnIndex, rowIndex);
                }
                
               

                HandleCardDragEvents.setDragOverToNone(event);

                VBox source = (VBox) event.getGestureSource();
                //System.out.println(source);
                Parent root = source.getParent();
                //System.out.println(root);

                if (root instanceof GridPane && root.getId().contentEquals("gridPlayer")) {

                    GridPane grid=(GridPane)root;
                    
                    grid.getChildren().remove(source); //get column and row
                  

                } else if (root instanceof GridPane && root.getId().contentEquals("gridOpponent")) {
                    GridPane grid=(GridPane)root;

                    grid.getChildren().remove(source); //get column and row
                   
                }

                dragCompleted = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        event.setDropCompleted(dragCompleted);

        event.consume();
    }
    
    
        public static void dragOver(DragEvent event) {

        Dragboard dragboard = event.getDragboard();
        
        Pane source = (Pane) event.getGestureSource();
        Pane target = (Pane) event.getGestureTarget();
        
        

        if (dragboard.hasContent(NodeUtils.CARD) && source!=target) {
            event.acceptTransferModes(TransferMode.ANY);
        }

        event.consume();
    }


}
