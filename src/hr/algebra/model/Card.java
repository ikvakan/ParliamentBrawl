/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import hr.algebra.annotations.Getter;
import java.io.File;
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
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    //public static final DataFormat CARD = new DataFormat("Card");

    private String title;
    private int attack;
    private int defense;
    private transient Image image;
    private String picturePath;

    private int columnIndex;
    private int rowIndex;

    private final String SRC_DIR = "src";

    public Card() {

    }

    public Card(String title, int attack, int defense, String picturePath) throws FileNotFoundException {

        this.title = title;
        this.attack = attack;
        this.defense = defense;
        this.picturePath = picturePath;

        createImage(picturePath);
    }

    public void createImage(String path) throws FileNotFoundException {

        this.image = new Image(new FileInputStream(SRC_DIR + File.separator + path));
    }

    @Getter(comment = "gets column index for specific card")
    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Getter(comment = "gets row index for specific card")

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Getter(comment = "gets attack number for specific card")

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Getter(comment = "gets defens number for specific card")

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

    @Getter(comment = "gets card name for specific card")

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Getter(comment = "gets picture path for specific card")

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

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(title);
        oos.writeInt(attack);
        oos.writeInt(defense);
        oos.writeUTF(picturePath);
        oos.writeInt(columnIndex);
        oos.writeInt(rowIndex);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        title = ois.readUTF();
        attack = ois.readInt();
        defense = ois.readInt();
        picturePath = ois.readUTF();
        columnIndex = ois.readInt();
        rowIndex = ois.readInt();
        createImage(picturePath);
    }

}
