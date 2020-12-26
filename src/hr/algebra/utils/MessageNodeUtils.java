/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import javafx.scene.control.Label;

/**
 *
 * @author IgorKvakan
 */
public class MessageNodeUtils {
    
    public static Label createMessageLabel(String message){
        Label lbl =new Label();
        lbl.setText(message);
        
        return lbl;
    };
    
}
