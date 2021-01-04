/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IgorKvakan
 */
public class GameStateModel implements Serializable{
    
    private static final long serialVersionUID = 5L; 

    public GameStateModel() {
    }

    private List<Card> playerDeck = new ArrayList<>();
    private List<Card> opponentDeck = new ArrayList<>();
    private List<Card> playerHand = new ArrayList<>();
    private List<Card> opponentHand = new ArrayList<>();

    private List<Card> fieldCards = new ArrayList<>();
    
    private List<Player> players= new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    

    
    public List<Card> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(List<Card> playerDeck) {
        this.playerDeck = playerDeck;
    }

    public List<Card> getOpponentDeck() {
        return opponentDeck;
    }

    public void setOpponentDeck(List<Card> opponentDeck) {
        this.opponentDeck = opponentDeck;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public List<Card> getOpponentHand() {
        return opponentHand;
    }

    public void setOpponentHand(List<Card> opponentHand) {
        this.opponentHand = opponentHand;
    }

    public List<Card> getFieldCards() {
        return fieldCards;
    }

    public void setFieldCards(List<Card> fieldCards) {
        this.fieldCards = fieldCards;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(playerDeck);
        oos.writeObject(playerHand);
        oos.writeObject(opponentDeck);
        oos.writeObject(opponentHand);
        oos.writeObject(fieldCards);
        oos.writeObject(players);

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        
        playerDeck = (List<Card>) ois.readObject();
        playerHand = (List<Card>) ois.readObject();
        opponentDeck = (List<Card>) ois.readObject();
        opponentHand = (List<Card>) ois.readObject();
        fieldCards = (List<Card>) ois.readObject();
        players=(List<Player>) ois.readObject();
    
    }
}
