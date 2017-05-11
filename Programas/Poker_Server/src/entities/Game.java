package entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Encapsulates the logic of a game. Handles everything so it's playable.
 * @author Mario Codes
 * @version 0.0.2.1 Getting everything ready to start a game.
 */
public class Game {
    // private Phase phase = null; // State machine. To implement.
    private boolean started = false;
    private Deck deck = new Deck();
    
    private final String REFERENCE; // Own ID to handle multi-matches.
    private final LinkedHashMap<String, ArrayList> ALLPLAYERS = new LinkedHashMap<>(); /* A copy of every player in the game. A player will only get deleted from here when he has no more chips and cannot continue playing.
                                                                                            Also, the AL<Boolean> contains whether an user can use it's action or it has already done. (true it's not yet done. false when done.)*/
    private LinkedHashMap<String, ArrayList> ROUNDPLAYERS = new LinkedHashMap<>(); //<player id, AL of actions> [0] = Betted. Temporal copy of the players used in the current round. A player will get deleted from here when he retires. A new copy will be made on new round with all players in game.
    
    private int totalPlayers = 0, joinedPlayers = 1; // Number of players setted by user, number of players joined until now. The game will start when the second equals the first.
    private int playersTurn = 0; // Numeric index to access LinkedHashMap. The order to do it's action will be the order the players join in.
    
    private int chips_pool = 0; // Chips betted in the actual round by all players. The winner gets it all.

    /**
     * Default constructor. Assigns the ID to the game.
     * @param reference Unique ID so other players can join it.
     * @param totalPlayers Number of max players we want the game to have.
     */
    public Game(String reference, String id, int totalPlayers) { 
        this.REFERENCE = reference;
        this.totalPlayers = totalPlayers;
        addPlayerToList(id);
    }
    
    
    /**
     * Adds the player to the list of all players. It also sets it's actions all to true.
     * @param id ID of the player to be used as check condition.
     */
    private void addPlayerToList(String id) {
        ArrayList<Boolean> actions = new ArrayList<>();
        actions.add(true);
        ALLPLAYERS.put(id, actions);
    }
    
    
    /**
     * Starts the game when all the players have joined.
     * Makes a copy of all the players in the game to the local round Map.
     */
    private void start() {
        started = true;
        ROUNDPLAYERS.putAll(ALLPLAYERS);
        System.out.println("Game #" +REFERENCE +" has started.");
    }
    
    /**
     * Checks whether users can still join to this game.
     * @return Boolean. True if the game has not started and still room left.
     */
    private boolean joinable() {
        return !started && (joinedPlayers < totalPlayers);
    }
    
    /**
     * Checks if the game has room left and !started.
     * Sets +1 to the number of current players, starts the game if all the players did join. Adds the player to global Map.
     * Makes a copy of the Map with all the players to the local round Map.
     */
    boolean joinPlayer(String id) {
        if(joinable()) {
            System.out.println(id +" joined game #" +REFERENCE +"; " +(++joinedPlayers) +"/" +totalPlayers +" players.");
            if(joinedPlayers >= totalPlayers) { // Already do ++ in msg 1 line up.
                addPlayerToList(id);
                start();
            } else addPlayerToList(id);
            
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
