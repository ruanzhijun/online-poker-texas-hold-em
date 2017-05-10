package poker_client.graphic;

/**
 * Representation of a player.
 * @author Mario Codes
 * @version 0.1
 */
public class Player {
    // private Hand hand = new Hand();
    private int chips = 1000;
    private final String ID;
    
    /**
     * Default Constructor.
     * It assigns the ID of the player. 'Guest' if none.
     * @param ID Own players ID.
     */
    public Player(String ID) { this.ID = ID; }
}
