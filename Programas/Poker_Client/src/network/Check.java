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
    private static String phase = ""; // Phase the game is currently at. Will get updated by the thread.
    private static boolean turn = false, getChips = true; // Is this player turn?; Did this player won and already got the chips?
    
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
    
    /**
     * Checks if the player has the number of cards needed for the round.
     * If not, obtains and assigns them.
     * @param player Player to check.
     * @param cards AL of cards to check.
     * @param reference Reference of the game the player is in.
     * @param number Number of cards it should have in this phase.
     */
    private static void getTableCards(Player player, ArrayList<Card> cards, String reference, int number) {
        if(needsCards(cards, number)) {
            ArrayList<Card> obtained = Connection.getTableCards(reference);
            player.addTable(obtained);
            System.out.println("Table cards added.");
        }
    } 
    
    /**
     * Checks if the ID of the winner matches with this player.
     * @param id ID of the winner.
     * @param player ID of this player.
     * @return Bool. True if they do match.
     */
    private static boolean checkWinner(String id, Player player) {
        return player.getID().matches(id);
    }
    
    /**
     * Checks if the AL is not empty.
     * Checks if the player won and already added the chips.
     * If the player is the winner, adds him the amount of chips won.
     * @param winner AL with the info of the winner.
     * @param player Player to check if it's the winner.
     */
    private static void addChips(ArrayList winner, Player player) {
        if(winner.size() > 0) {
            String idWinner = (String) winner.get(0);
            if(checkWinner(idWinner, player) && getChips) {
                player.addChips((int) winner.get(2));
                getChips = false;
            }
        }
    }
    
    /**
     * Obtains the winner of the game. 
     * Obtains an AL from the server containing all the info needed.
     * Also resets the boolean of a player playing to true.
     * @param reference Reference of the game the player is playing at.
     * @return AL with winner's info. [0] = Str. ID of the player. [1] = Str. Name of the play achieved. [2] = int. Number of chips won; It equals the total pool. 
     */
    public static ArrayList getWinner(String reference, Player player) {
        ArrayList winner = Connection.getWinner(reference, player.getID());
        addChips(winner, player);
        player.setPlaying(true);
        boolean retired = retirePlayer(player);
        return winner;
    }
    
    private static boolean retirePlayer(Player player) {
        boolean retire = player.getChips() <= 0;
        boolean retired = Connection.retireFromGame(retire);
        return retired;
    }
    
    /**
     * Checks the phase of the game, depending which one is, tells which method has to be executed. Also how many cards there does need to be in it.
     * @param player Player we're checking.
     * @param reference Reference of the game the player is in.
     */
    private static void action(Player player, String reference) {
        switch(phase) {
            case "PreFlop":
                getPrivateCards(player, player.getOwnCards(), reference);
                getChips = true;
                break;
            case "Flop":
                getTableCards(player, player.getTableCards(), reference, 3);
                break;
            case "Turn":
                getTableCards(player, player.getTableCards(), reference, 4);
                break;
            case "River":
                getTableCards(player, player.getTableCards(), reference, 5);
                getWinner(reference, player); // todo: asign or return the AL from getWinner();
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
            
            action(player, reference);
        } else { System.out.println("The game #" +reference +" does not exist."); };
    }

    /**
     * @return the turn
     */
    public static boolean isTurn() {
        return turn;
    }

    /**
     * @return the phase
     */
    public static String getPhase() {
        return phase;
    }
}
