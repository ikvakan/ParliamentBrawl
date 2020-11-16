/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dragEvents;

import enums.EventGesture;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class helperDragMethods {

    public static boolean  findParentFromNode(String parentName, DragEvent event, EventGesture eventOrigin) {

        boolean result=false;
       

        switch (eventOrigin) {
            case SOURCE:
                result = getCardSourceParent(parentName, event);
                break;
            case TARGET:
                result = getCardTargetParent(parentName, event);
                break;
        }

        return result;
    }

    private static boolean getCardSourceParent(String parentName, DragEvent event) {

        boolean result=false;
        
        Node source = (Node) event.getGestureSource();
        
        if (source instanceof VBox) {
            VBox card=(VBox)source;
            Parent parent = card.getParent();
            result=parent.getId().contains(parentName);
            
        }
            
        return result;

    }

    private static boolean getCardTargetParent(String parentName, DragEvent event) {
        boolean result=false;
        
        Node source = (Node) event.getGestureTarget();
        
        if (source instanceof VBox) {
            VBox card=(VBox)source;
            Parent parent = card.getParent();
            result=parent.getId().contains(parentName);
            
        }
            
        return result;
    }

}
