/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils.node.card;

import hr.algebra.events.drag.HandleCardDragEvents;
import hr.algebra.model.Card;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class CardUtils {

    public static final DataFormat CARD = new DataFormat("Card");

    private static final String TITLE = "Title";
    private static final String IMAGE = "Image";
    private static final String ATT_DEF = "AttDef";

   
    private static class SingletonHelper{
        private static final CardUtils INSTANCE= new CardUtils();
    }
    
    public static CardUtils getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public static final String DELIMITER = "/";

    public static VBox createVBox() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(100);
        vBox.setPrefHeight(150);
        vBox.setMaxWidth(vBox.USE_PREF_SIZE);
        vBox.setMaxHeight(vBox.USE_PREF_SIZE);
        vBox.setStyle("-fx-background-color:#DFFF; -fx-border-color: #0284d0; -fx-border-width: 2;");
        vBox.setAlignment(Pos.CENTER);
        vBox.setOnDragDetected((event) -> {

            HandleCardDragEvents.getInstance().dragDetected(event);
            
            //HandleCardDragEvents.dragDetected(event);

        });

        vBox.setOnDragOver((event) -> {
            //HandleCardDragEvents.dragOver(event);
            HandleCardDragEvents.getInstance().dragOver(event);

        });

        vBox.setOnDragDropped(event -> {
            try {
                //HandleCardDragEvents.dragDropped(event);
                HandleCardDragEvents.getInstance().dragDropped(event);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CardUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CardUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        vBox.setOnDragDone(event->{
            HandleCardDragEvents.getInstance().dragDone(event);
        
        });
        
        
        
        return vBox;

    }

    public static ImageView createImageView(Image image, String picPath) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(90);
        imageView.setId(IMAGE);
        imageView.setUserData(picPath);
        return imageView;
    }

    public static Label createAttDef(int attack, int defense) {

        Label lbl = new Label(String.valueOf(attack) + DELIMITER + String.valueOf(defense));
        lbl.setId(ATT_DEF);
        return lbl;
    }

    public static Label createTitle(String title) {
        Label lbl = new Label(title);
        lbl.setId(TITLE);
        lbl.setTooltip(new Tooltip(title));
        return lbl;
    }

    public static Card getCardFromNode(ObservableList<Node> nodes) {
        Card card = new Card();

        for (Node node : nodes) {
            if (node instanceof Label && node.getId().contentEquals(TITLE)) {
                Label lbl = (Label) node;
                card.setTitle(lbl.getText());

            } else if (node instanceof ImageView && node.getId().contentEquals(IMAGE)) {
                ImageView iv = (ImageView) node;

                card.setPicturePath((String) iv.getUserData());

            } else if (node instanceof Label && node.getId().contentEquals(ATT_DEF)) {
                Label lbl = (Label) node;
                String txt = lbl.getText();
                String[] value = txt.split(DELIMITER);
                card.setAttack(Integer.valueOf(value[0]));
                card.setDefense(Integer.valueOf(value[1]));

            }
        }

        return card;
    }

    public static VBox createCard(Card card) {
        VBox vBox = createVBox();
        Label title = createTitle(card.getTitle());
        ImageView imageView = createImageView(card.getImage(), card.getPicturePath());
        Label attackDefense = createAttDef(card.getAttack(), card.getDefense());
        vBox.getChildren().setAll(title, imageView, attackDefense);
        return vBox;
    }

    

    
    

}
