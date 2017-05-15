package states;

import entities.Game;

/**
 * Static Class. Gathers the common actions to implement in the phases.
 * @author Mario Codes
 */
public class Actions {
    /**
     * Checks whether the player may bet right now
     * @param game Game to operate in.
     * @param id ID of the player to check.
     * @return Boolean. True if the player may bet.
     */
    public static boolean mayBet(Game game, String id) { 
        return game.mayBet(id);
    }
    
    /**
     * Checks if it was the last player to speak.
     * @param game Game we're operating with.
     * @param id ID of the player to check.
     * @return True if it was the last player to speak. Change Phase if so.
     */
    public static boolean isLastPlayer(Game game, String id) {
        return game.isLastPlayer(id);
    }
    
    /**
     * Does the action of a bet. Also manages the turns inside the game and calls the next phase when it's the moment to do so.
     * @param game Game to operate in.
     * @param id ID of the player to check.
     * @param amount Amount of chips to bet.
     * @return Int. Total chips of the common pool.
     */
    public static int bet(Game game, String id, int amount) {
        int pool = game.bet(id, amount);
        
        return pool;
    }
}
