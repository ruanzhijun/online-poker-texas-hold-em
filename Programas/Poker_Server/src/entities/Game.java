package entities;

/**
 * Encapsulates the logic of a game. Handles the transitions between phases.
 * @author Mario Codes
 * @version 0.1
 */
public class Game {
    // private Phase phase = null; // State machine.
    private Deck deck = new Deck();
    private final String ID; // Own ID to handle multi-matches.
    
    private int numberTotalPlayers = 0, actualPlayers = 0;
    private int chips_pool = 0; // Chips betted in the actual round by all players.
    
    private boolean isStarted = false;

    /**
     * Default constructor. Assigns the ID to the game.
     * @param ID Unique ID so other players can join it.
     */
    public Game(String ID) { this.ID = ID; }
    
    /**
     * @return the isStarted
     */
    public boolean isIsStarted() {
        return isStarted;
    }

    /**
     * @param isStarted the isStarted to set
     */
    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
}
