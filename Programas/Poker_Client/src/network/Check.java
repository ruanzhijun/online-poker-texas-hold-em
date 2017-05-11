package network;

import entities.Card;
import entities.Player;
import java.util.ArrayList;

/**
 * Static class. Contains the periodic checks to be done by the user to retrieve information from the server.
 * It serves the client to know when to do which automatic actions.
 * @author Mario Codes
 */
public class Check {
    private static String turn = "";
    
    private static void ownCards(Player player, String reference) {
        ArrayList<Card> own = player.getOwnCards();
        ArrayList<Card> serverCards;
        
        if(own.size() > 2) {
            serverCards = Connection.getOwnCards(player, reference);
            player.getHand().addOwn(serverCards);
        }
    }
    
    private static void checkPhase(Player player, String reference, String phase) {
        switch(phase) {
            case "PreFlop":
                ownCards(player, reference);
                break;
            case "Flop":
                break;
            case "Turn":
                break;
            case "River":
                break;
        }
    }
    
    public static void check(Player player, String reference) {
        ArrayList<String> data = Connection.information(reference);
        String phase = data.get(0);
        turn = data.get(1);
        
        checkPhase(player, reference, phase);
    }
}
