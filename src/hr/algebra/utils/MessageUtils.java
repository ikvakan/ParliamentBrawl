/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author IgorKvakan
 */
public class MessageUtils {

    public static Alert ConfirmMessage(String title, String content ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        
        return alert;
    }
    public static Alert ShowMessage(String title, String content ) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        
        return alert;
    }

}
