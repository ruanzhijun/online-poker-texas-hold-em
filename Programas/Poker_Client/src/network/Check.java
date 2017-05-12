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
    private static String phase = "";
    private static boolean turn = false;
    
    /*
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
    */
    
    /**
     * Main method to be executed by the thread. It updates the static parameters in this class. (b. players turn and S. games phase).
     * Also calls the method to obtain cards depending on the phase and player cards.
     * @param player Player who calls this. Need its ID to know if its his / her turn.
     * @param reference Reference of the game the player is playing in.
     */
    public static void checks(Player player, String reference) {
        ArrayList data = Connection.information(reference, player.getID());
        if(data.size() > 0) {
            phase = (String) data.get(0);
            turn = (boolean) data.get(1);
            System.out.println("Data updated: phase " +phase +", turn " +turn);
        } else { System.out.println("The game #" +reference +" does not exist."); };
        
        // checkPhase(player, reference, phase);
    }

    /**
     * @return the turn
     */
    public static boolean isTurn() {
        return turn;
    }
}
