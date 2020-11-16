/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.factory;

import hr.algebra.model.Player;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 *
 * @author IgorKvakan
 */
public class PlayerFactory {
    public static Player createPlayer(String type) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException{
        Objects.requireNonNull(type);
        return (Player) Class.forName(type).getDeclaredConstructor().newInstance();
    }
}
