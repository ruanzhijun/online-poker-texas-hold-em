package es.msanchez.poker.server.states;

import es.msanchez.poker.server.entities.Game;

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
        return game.mayPlayerBet(id);
    }

    /**
     * Checks if it was the last player to speak.
     * @param game Game we're operating with.
     * @param id ID of the player to check.
     * @return True if it was the last player to speak. Change Phase if so.
     */
    public static boolean isLastPlayerInOrder(Game game, String id) {
        return game.isLastPlayerInOrder(id);
    }

    public static boolean isOnlyPlayerLeft(Game game) {
        return game.isLastPlayerInRound();
    }

    /**
     * Does the action of a bet. Also manages the turns inside the game and calls the next phase when it's the moment to do so.
     * @param game Game to operate in.
     * @param id ID of the player to check.
     * @param amount Amount of chips to bet.
     * @return Int. Total chips of the common pool.
     */
    public static int bet(Game game, String id, int amount) {
        int pool = game.addBet(id, amount);
        return pool;
    }

    public static boolean retirePlayer(Game game, String id) {
        return game.retirePlayerFromRound(id);
    }

    public static void endRound(Game game) {
        Runnable t1 = () -> { // Setted in a new thread so the last user gets the pool and after i waiting seconds, the server starts a new round.
            try {
                if(isOnlyPlayerLeft(game)) game.setEnded(true);
                Thread.sleep(5000);
                new PreFlop().change(game);
            } catch(InterruptedException ex) { ex.printStackTrace(); }
        };
        new Thread(t1).start();
    }
}
