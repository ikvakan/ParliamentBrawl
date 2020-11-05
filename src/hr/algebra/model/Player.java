/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
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
public class Player {
    
    private final String PICTURE_PATH="src/assets/no_image.jpeg";
    
    private  StringProperty name;
    private IntegerProperty health=new SimpleIntegerProperty(20);
    private ObjectProperty<Image>  image;
    private ObjectProperty<List<Hand>> hand;

    public Player() {
    }
    
    
    public Player(String name) throws FileNotFoundException {
        createDefaultImage();
        this.name = new SimpleStringProperty(name);
    }

    public Player(String name, List<Hand> hand) {
        this.name = new SimpleStringProperty(name);
        this.hand = new SimpleObjectProperty<>(hand);
    }

    public int getHealth() {
        return health.get();
    }

    public void setHealth(int health) {
        this.health.set(health);
    }

    public List<Hand> getHand() {
        return hand.get();
    }

    public void setHand(List<Hand> hand) {
        this.hand.set(hand);
    }

    public String getName() {
        return name.get();
    }

    public Image getImage() {
        return image.get();
    }
    
    
    
    

    private void createDefaultImage() throws FileNotFoundException {
       this.image=new SimpleObjectProperty<>(new Image(new FileInputStream(PICTURE_PATH)));
    }
}
