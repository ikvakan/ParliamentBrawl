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
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable{

     private static final long serialVersionUID = 2L;
    
    private List<Card> deck;
    private List<Card> hand;

    public Deck() {

        deck = new ArrayList<>();
        hand = new ArrayList<>();

    }

    
    
//    public Deck(List<Card> cards) {
//        this.deck = cards;
//    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void clearDeck() {
        this.deck.clear();
    }

    public void clearHand() {
        this.hand.clear();
    }

    public void shuffleCards() {
        Collections.shuffle(deck);
    }

    public void createHand(List<Card> d) {

        for (int i = 0; i < 5; i++) {
            hand.add(d.get(i));

        }

        for (Card card : hand) {
            deck.remove(card);
        }

    }

    public Card getCardForHand() {

       // hand.clear();
        Card card=deck.get(deck.size() -1);
        deck.remove(card);
        
        return card;
        
    }

    public void removeCardFromHand(Card card) {

        hand.remove(card);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(deck);
        oos.writeObject(hand);

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        deck=(List<Card>)ois.readObject();
        hand=(List<Card>)ois.readObject();
        
    }
    
    
    
    

}
