/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.scene.image.Image;

/**
 *
 * @author IgorKvakan
 */
public class Player implements Serializable{
    
    private final String PICTURE_PATH="src/assets/no_image.jpeg";
     private static final long serialVersionUID = 3L;


    private String name;
    private int health;
    private transient Image image;

    public Player() {
    }
    
    
    public Player(String name) throws FileNotFoundException {
        createDefaultImage();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    
    public void createDefaultImage() throws FileNotFoundException {
       this.image=new Image(new FileInputStream(PICTURE_PATH));
    }
    
    
    public boolean isDead( ){
        boolean result=false;
        
        if (health <1) {
            result =true;
        }
        
        return result;
    }
    
    private void writeObject(ObjectOutputStream oos) throws IOException  {
        oos.writeUTF(name);
        oos.writeInt(health);
    }
   
    private void readObject(ObjectInputStream ois) throws IOException{
        name=ois.readUTF();
        health=ois.readInt();
        createDefaultImage();
    }
    
    
    
}
