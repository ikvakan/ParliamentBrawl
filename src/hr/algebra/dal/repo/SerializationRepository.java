/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.repo;

import hr.algebra.model.Card;
import hr.algebra.model.Player;
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
public class SerializationRepository implements Serializable {

    private static final long serialVersionUID = 4L;

    public SerializationRepository() {
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
    

    private static final SerializationRepository INSTANCE = new SerializationRepository();

    public static SerializationRepository getInstance() {
        return INSTANCE;
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

        INSTANCE.playerDeck.clear();
        INSTANCE.playerHand.clear();
        INSTANCE.opponentDeck.clear();
        INSTANCE.opponentHand.clear();
        INSTANCE.fieldCards.clear();
        INSTANCE.players.clear();
        
        INSTANCE.playerDeck.addAll(playerDeck);
        INSTANCE.playerHand.addAll(playerHand);
        INSTANCE.opponentDeck.addAll(opponentDeck);
        INSTANCE.opponentHand.addAll(opponentHand);
        INSTANCE.fieldCards.addAll(fieldCards);
        INSTANCE.players.addAll(players);
        
    }

    private Object readResolve() {

        return INSTANCE;
    }
    
    public void swapPlayerDeck(List<Card> deck){
        this.playerDeck.clear();
        this.playerDeck.addAll(deck);
    }
    
    public void swapOpponentDeck(List<Card> deck){
        this.opponentDeck.clear();
        this.opponentDeck.addAll(deck);
    }
    
    public void swapPlayerHand(List<Card> deck){
        this.playerHand.clear();
        this.playerHand.addAll(deck);
    }
    
    public void swapOpponentHand(List<Card> deck){
        this.opponentHand.clear();
        this.opponentHand.addAll(deck);
    }
    
    public void swapFieldCards(List<Card> cards){
        this.fieldCards.clear();
        this.fieldCards.addAll(cards);
               
    }
    
    public void swapPlayers(List<Player> players){
        this.players.clear();
        this.players.addAll(players);
    }

    
    

}
