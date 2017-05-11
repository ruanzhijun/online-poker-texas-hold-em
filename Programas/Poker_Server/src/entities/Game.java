package entities;

import java.util.ArrayList;

/**
 * Encapsulates the logic of a game. Handles everything so it's playable.
 * @author Mario Codes
 * @version 0.1 Just created. Setting basics.
 */
public class Game {
    // private Phase phase = null; // State machine.
    private boolean started = false;
    private Deck deck = new Deck();
    
    private final String REFERENCE; // Own ID to handle multi-matches.
    
    private int totalPlayers = 0, joinedPlayers = 1; // Number of players setted by user, number of players joined until now. The game will start when the second equals the first.
    
    
    private int chips_pool = 0; // Chips betted in the actual round by all players. The winner gets it all.

    /**
     * Default constructor. Assigns the ID to the game.
     * @param reference Unique ID so other players can join it.
     */
    public Game(String reference, int totalPlayers) { 
        this.REFERENCE = reference;
        this.totalPlayers = totalPlayers;
    }
    
    /**
     * Sets +1 to the number of current players and starts the game if all the players did join.
     */
    boolean joinPlayer() {
        if(!started && (joinedPlayers < totalPlayers)) {
            System.out.println("Player Joined. Game #" +REFERENCE +"; " +(++joinedPlayers) +"/" +totalPlayers +" players.");
            if(joinedPlayers >= totalPlayers) { // Already do ++ in msg 1 line up.
                started = true;
                System.out.println("Game #" +REFERENCE +" has started.");
            }
            return true;
        }else {
            System.out.println("Player rejected. Game #" +REFERENCE +" has already started or is full.");
            return false;
        }
    }
    
    /**
     * @return the isStarted
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @param started the isStarted to set
     */
    public void setStarted(boolean started) {
        this.started = started;
    }
}
