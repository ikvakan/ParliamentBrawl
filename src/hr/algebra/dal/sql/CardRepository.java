/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.model.Card;
import hr.algebra.dal.repo.Repository;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author IgorKvakan
 */
public class CardRepository implements Repository {

    private static final String TITLE = "Title";
    private static final String ATTACK = "Attack";
    private static final String DEFENSE = "Defense";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String SELECT_CARDS = " {CALL selectCards}";

    @Override
    public List<Card> selectCards() throws Exception {
        List<Card> cards = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_CARDS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {                
                cards.add(new Card(
                        rs.getString(TITLE),
                        rs.getInt(ATTACK),
                        rs.getInt(DEFENSE),
                        rs.getString(PICTURE_PATH)));
            }
        }

        return cards;
    }

}
