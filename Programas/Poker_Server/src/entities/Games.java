package entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manipulate the multiple games which can be executed simultaneously.
 * @author Mario Codes
 * @version 0.0.2 Added first methods. Check if a game exists and create, it checks first if a game already exist.
 */
public class Games {
    private static final HashMap GAMES = new <String, Game>HashMap();
    
    /**
     * Checks if a game with the reference already exists.
     * @param reference Reference to check.
     * @return Boolean. True if a game with this reference already exists.
     */
    public static boolean check(String reference) {
        return GAMES.containsKey(reference);
    }
    
    /**
     * First it checks if a game with this reference already exists, if it does not then it creates it and
     * saves it into the local HashMap.
     * @param reference String. ID to locate the game. Unique.
     * @param totalPlayers int. Number of fixed players to know when to start the game.
     * @return Status of the operation. True if created correctly.
     */
    public static boolean create(ArrayList parameters) {
        boolean result = false;
        
        String reference = (String) parameters.get(0);
        String id = (String) parameters.get(1);
        int totalPlayers = (int) parameters.get(2);
        
        if(!check(reference)) {
            GAMES.put(reference, new Game(reference, id, totalPlayers));
            System.out.println(id +" has created the game #" +reference +". 1/" +totalPlayers +" player(s). " +GAMES.size() +" simultaneous games.");
            result = true;
        } else System.out.println("Game rejected. This # already exists.");
        
        return result;
    }
    
    // Send a signal to the clients so they disconnect themselves. Then stop and delete the game from HM.
    public static void delete(String reference) {
        throw new UnsupportedOperationException("To be done. Will need checks and a correct stop.");
    }
    
    /**
     * Adds a player to the selected game. Only if there's room left and the game's not started yet.
     * @param reference String. Reference of the game we want to join.
     * @return Boolean. Result of the operation. False if game does not exist.
     */
    public static boolean join(String reference, String id) {
        boolean result = false;
        
        if(check(reference)) {
            Game game = (Game) GAMES.get(reference);
            result = game.joinPlayer(id);
        } else System.out.println("Join rejected. There's no game #" +reference);
        
        return result;
    }
    
    
    /**
     * Gets the phase from the game with the specified reference.
     * @param reference Games reference we want to obtain it's phase from.
     * @return String. Phase this game is currently at.
     */
    public static String getPhase(String reference) {
        if(GAMES.containsKey(reference)) {
            Game game = (Game) GAMES.get(reference);
            return game.getPhase().toString();
        }
        
        return null;
    }
    
    /**
     * Checks inside the game specified, the user ID to see if it's this user's turn to speak.
     * @param reference Reference of the game we want to check.
     * @param id ID of the player to check.
     * @return Boolean. True if he speaks. False if not such game or does not speak.
     */
    public static boolean speaks(String reference, String id) {
        if(GAMES.containsKey(reference)) {
            Game game = (Game) GAMES.get(reference);
            return game.speaks(id);
        }
        
        return false;
    }
    
    /**
     * Checks the game with x reference and retrieves the user with id i private cards.
     * @param reference Reference of the game the player is playing on.
     * @param id Unique ID to know from which player get its cards.
     * @return AL<Card>. Private player cards.
     */
    public static ArrayList<Card> privateCards(String reference, String id) {
        if(GAMES.containsKey(reference)) {
            Game game = (Game) GAMES.get(reference);
            return game.getPlayerCards(id);
        }
        
        return null;
    }
    
    
    public static boolean mayBet(String reference, String id) {
        if(GAMES.containsKey(reference)) {
            Game game = (Game) GAMES.get(reference);
            return game.mayBet(id);
        } 
        
        return false;
    }
    
    public static int bet(String reference, String id, int amount) {
        if(mayBet(reference, id)) {
            Game game = (Game)GAMES.get(reference);
            int pool = game.bet(amount);
            return pool;
        }
        
        return -1;
    }
}
