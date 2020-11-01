/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.repo.dal;

import hr.algebra.dal.sql.CardRepository;

/**
 *
 * @author IgorKvakan
 */
public class RepositoryFactory {
    
    public static Repository getRepository(){
        return new CardRepository();
    }
    
}
