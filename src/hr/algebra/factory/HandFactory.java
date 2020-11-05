/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.factory;

import hr.algebra.model.Hand;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 *
 * @author IgorKvakan
 */
public class HandFactory {
    
    public static Hand createHand(String type) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException{
        Objects.requireNonNull( type);
        return (Hand) Class.forName(type).getDeclaredConstructor().newInstance();
    }
}
