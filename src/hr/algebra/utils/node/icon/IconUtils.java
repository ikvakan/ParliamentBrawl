/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils.node.icon;

import enums.PlayersIcon;
import hr.algebra.model.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author IgorKvakan
 */
public class IconUtils {

    private static final String LBL_PLAYER_NAME = "lbPlayerName";
    private static final String LBL_OPPONENT_NAME = "lbOpponentName";
    private static final String LBL_PLAYER_HEALTH = "lbPlayerHealth";
    private static final String LBL_OPPONENT_HEALTH = "lbOpponentHealth";
    private static final String LBL_PLAYER_IMAGE = "imagePlayer";
    private static final String LBL_OPPONENT_IMAGE = "imageOpponent";
    

    public static Player getPlayerFromPane(PlayersIcon icon, VBox iconPlacerHolder) {
        Player player = new Player();

        switch (icon) {
            case PLAYER_ICON:
                player = getPlayerFromVBox(iconPlacerHolder, LBL_PLAYER_NAME, LBL_PLAYER_HEALTH);
                break;
            case OPPONENT_ICON:
                player = getPlayerFromVBox(iconPlacerHolder, LBL_OPPONENT_NAME, LBL_OPPONENT_HEALTH);
                break;
        }

        return player;
    }

    public static Player getPlayerFromVBox(VBox iconPlacerHolder, String lblName, String lbHealth) {

        Player player = new Player();

        for (Node node : iconPlacerHolder.getChildrenUnmodifiable()) {
            if (node instanceof Label && node.getId().contentEquals(lblName)) {
                Label lbl = (Label) node;
                String name = lbl.getText();
                player.setName(name);
            }
            if (node instanceof Label && node.getId().contentEquals(lbHealth)) {
                Label lbl = (Label) node;
                String health = lbl.getText();
                player.setHealth(Integer.valueOf(health));
            }

        }
        return player;

    }

   public static void modifyPlayers(Player player, PlayersIcon icon,VBox iconPlaceHolder) {

        switch (icon) {
            case PLAYER_ICON:
                modifyIconCard(player, iconPlaceHolder,LBL_OPPONENT_NAME,LBL_PLAYER_HEALTH);
                break;
            case OPPONENT_ICON:
                modifyIconCard(player, iconPlaceHolder,LBL_OPPONENT_NAME,LBL_OPPONENT_HEALTH);
                break;
        }
    }

    public static void modifyIconCard(Player player, VBox icon,String lblName,String lblHealth)  {

        for (Node node : icon.getChildren()) {
            if (node instanceof Label && node.getId().contentEquals(lblName)) {
                Label lbl = (Label) node;
                lbl.setText(player.getName());
            }
            if (node instanceof Label && node.getId().contentEquals(lblHealth)) {
                Label lbl = (Label) node;
                lbl.setText(String.valueOf(player.getHealth()));
                
            }
            

        }
    
    }

}
