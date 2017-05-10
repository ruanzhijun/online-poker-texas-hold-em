package entities;

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
    
    private int totalPlayers = 0, joinedPlayers = 0; // Number of players setted by user, number of players joined until now. The game will start when the second equals the first.
    
    private int chips_pool = 0; // Chips betted in the actual round by all players. The winner gets it all.

    /**
     * Default constructor. Assigns the ID to the game.
     * @param reference Unique ID so other players can join it.
     */
    public Game(String reference, int totalPlayers) { 
        this.REFERENCE = reference;
        this.totalPlayers = totalPlayers;
    }
    
    boolean joinPlayer() {
        if(!started && (joinedPlayers < totalPlayers)) {
            joinedPlayers++;
            return true;
        }else return false;
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
