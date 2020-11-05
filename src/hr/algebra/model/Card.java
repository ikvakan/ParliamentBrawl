/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 *
 * @author IgorKvakan
 */
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    //public static final DataFormat CARD = new DataFormat("Card");

    //private  StringProperty title;
      //  private  ObjectProperty<Image> image;
//    private   IntegerProperty attack;
//    private   IntegerProperty defense;
    private String title;
    private int attack;
    private int defense;
    private String picturePath;
    private Image image;

    private final String SRC_DIR = "src";

    public Card() {

    }

    public Card(String title, int attack, int defense, String picturePath) throws FileNotFoundException {
        // this.title = new SimpleStringProperty(title);
//        this.attack = new SimpleIntegerProperty(attack);
//        this.defense = new SimpleIntegerProperty(defense);
//        this.picturePath = picturePath;
        this.title = title;
        this.attack = attack;
        this.defense = defense;
        this.picturePath = picturePath;
        createImage(picturePath);
    }

    public void createImage(String path) throws FileNotFoundException {

//        this.image=new SimpleObjectProperty<>(new Image(new FileInputStream(SRC_DIR + File.separator + path)));
        this.image = new Image(new FileInputStream(SRC_DIR + File.separator + path));
    }

//    public Image getImage() {
//        return image.get();
//    }
//
//    public void setImage(Image image) {
//        this.image=new SimpleObjectProperty<>();
//        this.image.set(image);
//    }
    
    //    public  String getTitle() {
//        return title.get();
//    }
//
//    public  void setTitle(String title) {
//        this.title.set(title);
//    }
    
    
    //    public  int getAttack() {
//        return attack.get();
//    }
//
//    public  void setAttack(int attack) {
//        this.attack.set(attack);
//    }
//
//    public  int getDefense() {
//        return defense.get();
//    }
//
//    public  void setDefense(int defense) {
//        this.defense.set(defense);
//    }
    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return title + " (" + attack + "/" + defense + ")";
    }
    
    
    

}
