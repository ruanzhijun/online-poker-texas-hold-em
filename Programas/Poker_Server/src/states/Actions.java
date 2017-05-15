package states;

import entities.Game;

/**
 * Static Class. Gathers the common actions to implement in the phases.
 * @author Mario Codes
 */
public class Actions {
    public static boolean mayBet(Game game, String id) { 
        return game.mayBet(id);
    }
    
    public static int bet(Game game, String id, int amount) {
        return game.bet(id, amount);
    }
}
