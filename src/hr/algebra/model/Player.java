/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

/**
 *
 * @author IgorKvakan
 */
public class Player  {
    
    private final String PICTURE_PATH="src/assets/no_image.jpeg";
    


    private String name;
    private int health;
    private Image image;

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

    
    private void createDefaultImage() throws FileNotFoundException {
       this.image=new Image(new FileInputStream(PICTURE_PATH));
    }
    
    
    public boolean isDead( ){
        boolean result=false;
        
        if (health <1) {
            result =true;
        }
        
        return result;
    }
}
