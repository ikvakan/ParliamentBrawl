/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.nodes;

import hr.algebra.model.Card;
import hr.algebra.repo.dal.Repository;
import hr.algebra.repo.dal.RepositoryFactory;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.Bindings;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class NodeUtils {

    
   public static final DataFormat CARD = new DataFormat("Card");
   

   
    private static final String DELIMITER = "/";

    public static VBox createVBox() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(100);
        vBox.setPrefHeight(150);
        vBox.setMaxWidth(vBox.USE_PREF_SIZE);
        vBox.setMaxHeight(vBox.USE_PREF_SIZE);
        vBox.setStyle("-fx-background-color:#DFFF; -fx-border-color: #0284d0; -fx-border-width: 2;");
        vBox.setAlignment(Pos.CENTER);
        vBox.setOnDragDetected((event) -> {
            dragDetected(event, vBox);
        });

        ///mouse drag event!!!
        return vBox;

    }

    private static void dragDetected(MouseEvent event, VBox source) {

        Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);
        ObservableList<Node> nodes = source.getChildren();
        Card card = new Card();
        ClipboardContent content = new ClipboardContent();


        if (nodes.size() == 0) {
            event.consume();
            return;
        }

        for (Node node : nodes) {
            if (node instanceof Label && node.getId().contentEquals("Title")) {
                Label lbl = (Label) node;
                card.setTitle(lbl.getText());

            } else if (node instanceof ImageView && node.getId().contentEquals("Image")) {
                ImageView iv = (ImageView) node;
                System.out.println(iv.getUserData());
                card.setPicturePath((String) iv.getUserData());
            } else if (node instanceof Label && node.getId().contentEquals("AttDef")) {
                Label lbl = (Label) node;
                String txt = lbl.getText();
                System.out.println(lbl.getText());
                String[] value = txt.split(DELIMITER);
                card.setAttack(Integer.valueOf(value[0]));
                card.setDefense(Integer.valueOf(value[1]));
            }
        }

        content.put(CARD, card);
        dragboard.setContent(content);
        
        System.out.println(dragboard.getContent(CARD));
        event.consume();

    }

    public static ImageView createImageView(Image image,String picPath) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(95);
        imageView.setFitHeight(90);
        imageView.setId("Image");
        imageView.setUserData(picPath);
        return imageView;
    }

    public static Label createAttDef(int attack, int defense) {

        Label lbl = new Label(String.valueOf(attack) + DELIMITER + String.valueOf(defense));
        lbl.setId("AttDef");
        return lbl;
    }

    public static Label createTitle(String title) {
        Label lbl = new Label(title);
        lbl.setId("Title");
        lbl.setTooltip(new Tooltip(title));
        return lbl;
    }

}
