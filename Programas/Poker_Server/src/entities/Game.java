package entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import states.Phase;
import states.PreFlop;

/**
 * Encapsulates the logic of a game. Handles everything so it's playable.
 * @author Mario Codes
 * @version 0.0.2.3 Doing so the game flows and does its actions. Cards for every player are obtained from deck.
 */
public class Game {
    private Phase phase = null; // State machine. Read interface's code.
    private boolean started = false;
    private Deck deck;
    
    private final String REFERENCE; // Own ID to handle multi-matches.
    private final LinkedHashMap<String, ArrayList> ALLPLAYERS = new LinkedHashMap<>(); /* A copy of every player in the game. A player will only get deleted from here when he has no more chips and cannot continue playing. */
    private LinkedHashMap<String, ArrayList> ROUNDPLAYERS = new LinkedHashMap<>(); /* Used to know players who are in this round and didn't retire. Will copy the LHM 1 line above every new round.
                                                                                        It also stores personal information about the player: 
                                                                                        [0] - boolean, player turn to speak?
                                                                                        [1] - boolean, can this player bet?
                                                                                        [2][3] - private cards #1 and #2 */
    
    private int totalPlayers = 0, joinedPlayers = 1; // Number of players setted by user, number of players joined until now. The game will start when the second equals the first.
    private int playersTurn = 0; // Numeric index to access LinkedHashMap. The order to do it's action will be the order the players join in.
    
    private int chips = 0; // Chips betted in the actual round by all players. The winner gets it all.

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
     * Draws the private cards from the deck for every player still in game.
     * It adds them in the player's HashMap as entries [2] and [3] inside the AL.
     */
    private void drawCards() {
        for(Map.Entry<String, ArrayList> entry : ALLPLAYERS.entrySet()) {
            ArrayList list = entry.getValue();
            list.add(deck.getCard());
            list.add(deck.getCard());
        }
    }
    
    /**
     * To be called by state machine -> PreFlop.
     * Gets everything ready to start a new fresh round.
     * Erases and copies all the players to a new round Map.
     * Creates a new fresh deck.
     */
    public void newRound() {
        ROUNDPLAYERS = new LinkedHashMap<>();
        ROUNDPLAYERS.putAll(ALLPLAYERS);
        deck = new Deck();
        System.out.println("Game #" +REFERENCE +" has started a new round. " +ROUNDPLAYERS.size() +"/" +totalPlayers +" players left.");
        // todo: reset player action AL. Chips to 0. Erase players jugada.
        drawCards();
    }
    
    
    /**
     * Adds the player to the list of all players. It also sets it's actions all to true.
     * @param id ID of the player to be used as check condition.
     */
    private void addPlayerToList(String id) {
        ArrayList actions = new ArrayList<>();
        actions.add(false);
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
        System.out.println("All players joined; Game #" +REFERENCE +" starting!.");
        new PreFlop().change(this);
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
     * Check if it's this user's turn to speak.
     * @param id ID of the user to check the turn.
     * @return Boolean. True if the player may speak.
     */
    boolean speaks(String id) {
        return (boolean) ROUNDPLAYERS.get(id).get(0);
    }
    
    
    /**
     * Gets the assigned player private cards. To be used from Games.
     * @param id ID of the player we want to retrieve it's cards.
     * @return AL<Card> with it's 2 cards.
     
    ArrayList<Card> getPlayerCards(String id) {
        if(ROUNDPLAYERS.containsKey(id)) {
            ArrayList<Card> cards = new ArrayList<>();
            cards.add((Card) ROUNDPLAYERS.get(id).get(2));
            cards.add((Card) ROUNDPLAYERS.get(id).get(3));
            
            return cards;
        } else return null;
    } */
    
    
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

    /**
     * @return the phase
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * DO NOT USE THIS!!! Do use Phase.change(); instead.
     * I do need it to make the change internally inside the phase.
     * Apart of this, DO NOT USE IT - NEVER - DIRECTLY!!
     * @param phase the phase to set
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
