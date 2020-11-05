/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import hr.algebra.repo.dal.Repository;
import hr.algebra.repo.dal.RepositoryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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

    public void populateDeck(List<Card> newDeck) throws Exception {
        clearCards();
        deck.addAll(newDeck);
        //Collections.shuffle(cards);
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
