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
    
    /**
     * Check to see if the player does need its cards or he has them already.
     * @param cards Cards to be checked.
     * @param number Number of cards needed for this specific phase.
     * @return boolean. True if the player does need cards.
     */
    private static boolean needsCards(ArrayList<Card> cards, int number) {
        return (cards.size() < number);
    }
    
    /**
     * Checks if the player has its 2 private cards, if not. Retrieves and adds them.
     * @param player Player playing.
     * @param cards Cards the player currently has.
     * @param reference Reference of the game the player is in.
     */
    private static void getPrivateCards(Player player, ArrayList<Card> cards, String reference) {
        if(needsCards(cards, 2)) {
            ArrayList<Card> obtained = Connection.getOwnCards(player, reference);
            player.addOwn(obtained);
            System.out.println("Private player's cards added.");
        }
    }
    
    private static void getTableCards(Player player, ArrayList<Card> cards, String reference, int number) {
        if(needsCards(cards, number)) {
            ArrayList<Card> obtained = Connection.getTableCards(reference);
            player.addTable(obtained);
            System.out.println("Table cards added.");
        }
    } 
    
    /**
     * Checks the phase of the game, depending which one is, tells which method has to be executed and how many cards there does need to be in it.
     * @param player Player we're checking.
     * @param reference Reference of the game the player is in.
     */
    private static void cards(Player player, String reference) {
        switch(phase) {
            case "PreFlop":
                getPrivateCards(player, player.getOwnCards(), reference);
                break;
            case "Flop":
                getTableCards(player, player.getTableCards(), reference, 3);
                break;
            case "Turn":
                break;
            case "River":
                break;
        }
    }
    
    /**
     * Main method to be executed by the thread. It updates the static parameters in this class. (bool. players turn and str. games phase).
     * Also calls the method to obtain cards depending on the phase and player cards.
     * @param player Player who calls this. Need its ID to know if its his / her turn.
     * @param reference Reference of the game the player is playing in.
     */
    public static void checks(Player player, String reference) {
        ArrayList data = Connection.information(reference, player.getID());
        if(data.size() > 0) {
            phase = (String) data.get(0);
            turn = (boolean) data.get(1);
            System.out.println("Data updated: phase " +phase +", turn " +turn); // todo: delete when not needed.
            
            cards(player, reference);
        } else { System.out.println("The game #" +reference +" does not exist."); };
    }

    /**
     * @return the turn
     */
    public static boolean isTurn() {
        return turn;
    }
}
