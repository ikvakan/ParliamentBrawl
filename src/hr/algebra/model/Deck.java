/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {


    private  List<Card> deck;

    public Deck() {

        deck = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        this.deck = cards;
    }

    
    
    public List<Card> getCards() {
        return deck;
    }

    public  void clearCards() {
        this.deck.clear();
    }

    public void populateDeck(List<Card> newDeck)  {
        clearCards();
        deck.addAll(newDeck);
        
    }

    public void shuffleCards() {
        Collections.shuffle(deck);
    }
    
    public void setDeckAfterPopulatingHand(Hand hand){
        List<Card> cards= new ArrayList<>();
        cards.addAll(hand.getHand());
        
        for (Card card : cards) {
            deck.remove(card);
        }
        
        
    }
    
    
   
}
