/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.repo.dal;

import hr.algebra.model.Card;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author IgorKvakan
 */
public interface Repository {
    
    List<Card> selectCards() throws Exception;
    
}
