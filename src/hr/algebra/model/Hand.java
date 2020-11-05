/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author IgorKvakan
 */
public  class Hand  {

    private List<Card> hand;

    public Hand() {
        hand=new ArrayList<>();
    }

    public Hand(List<Card> startHand) {
        this.hand = startHand;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void clearHand(){
        hand.clear();
    }
    
    
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void removeCardFromHand(Card card) {
        if (hand.isEmpty() || hand == null) {
            return;
        }
        hand.remove(card);
        
    }

    public void setStartHand(Deck deck){
        
        List<Card> cards= deck.getCards();
        
        for (int i = 0; i < 5; i++) {
            hand.add(cards.get(i));
        }
        
    }
    
}
