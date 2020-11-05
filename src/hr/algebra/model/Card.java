/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;




/**
 *
 * @author IgorKvakan
 */
public  class Card {
    
    private final StringProperty title;
    private final  IntegerProperty attack;
    private final  IntegerProperty defense;
    private  String picturePath;
    private  ObjectProperty<Image> image;

    private final String SRC_DIR="src";


    public Card(String title, int attack, int defense, String picturePath) throws FileNotFoundException {
        this.title = new SimpleStringProperty(title);
        this.attack = new SimpleIntegerProperty(attack);
        this.defense = new SimpleIntegerProperty(defense);
        this.picturePath = picturePath;
        createImage(picturePath);
    }

    private void createImage(String path) throws FileNotFoundException{
       
        this.image=new SimpleObjectProperty<>(new Image(new FileInputStream(SRC_DIR + File.separator + path)));
    }

    public Image getImage() {
        return image.get();
    }

    
   
    
    
    public  String getTitle() {
        return title.get();
    }

    public  void setTitle(String title) {
        this.title.set(title);
    }

    public  int getAttack() {
        return attack.get();
    }

    public  void setAttack(int attack) {
        this.attack.set(attack);
    }

    public  int getDefense() {
        return defense.get();
    }

    public  void setDefense(int defense) {
        this.defense.set(defense);
    }

    public  String getPicturePath() {
        return picturePath;
    }

    public  void setPicturePath(String picturePath) {
        this.picturePath=picturePath;
    }

    @Override
    public String toString() {
        return title + " (" + attack + "/" + defense+")" ;
    }

    

   

   
   
    
    
    
    
}
