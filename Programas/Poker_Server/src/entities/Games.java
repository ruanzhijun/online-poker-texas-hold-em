package entities;

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
     * @return Status of the operation. True if created correctly.
     */
    public static boolean create(String reference) {
        boolean result = false;
        
        if(!check(reference)) {
            GAMES.put(reference, new Game(reference));
            result = true;
        }
        
        return result;
    }
    
    // Send a signal to the clients so they disconnect themselves. Then stop and delete the game from HM.
    public static void delete(String reference) {
        throw new UnsupportedOperationException("To be done. Will need checks and a correct stop.");
    }
}
