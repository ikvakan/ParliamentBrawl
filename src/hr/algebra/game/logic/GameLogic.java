/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.game.logic;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class GameLogic {
    
    
     public static boolean canAttack(DragEvent event) {
        boolean result = true;

        VBox source = (VBox) event.getGestureSource();

        Integer sourceColIndex = GridPane.getColumnIndex(source);
        Integer sourceRowIndex = GridPane.getRowIndex(source);
        

        boolean isPlayerField = (sourceRowIndex == 1) ? true : false;
        boolean isOpponentField = (sourceRowIndex == 0) ? true : false;

        Parent parent = source.getParent();

        if (isPlayerField) {
            for (Node node : parent.getChildrenUnmodifiable()) {
                if (node instanceof VBox) {
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (rowIndex == 0 && columnIndex == sourceColIndex) {
                        result=false;
                    }
                }
            }
        }
        
        else if (isOpponentField) {
            for (Node node : parent.getChildrenUnmodifiable()) {
                if (node instanceof VBox) {
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (rowIndex == 1 && columnIndex == sourceColIndex) {
                        result=false;
                    }
                }
            }
        }

        return result;

    }

   
     
     
}
