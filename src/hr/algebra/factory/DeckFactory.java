/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.factory;

import hr.algebra.model.Deck;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 *
 * @author IgorKvakan
 */
public class DeckFactory {
    
    public static Deck createDeck(String type) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException{
        Objects.requireNonNull(type);
        return (Deck) Class.forName(type).getDeclaredConstructor().newInstance();
    }
    
}
