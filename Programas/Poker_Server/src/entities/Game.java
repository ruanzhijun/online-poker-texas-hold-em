package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import states.Phase;
import states.PreFlop;

/**
 * Encapsulates the logic of a game. Handles and stores everything needed.
 * @author Mario Codes
 * @version 0.0.3 Lists and turns reseted correctly to start a new round.
 */
public class Game {
    private Phase phase = null; // State machine. Read interface's Phase code.
    private boolean started = false; // Used so other players can't join a game that's currently going on.
    private Deck deck; // Deck with all it's cards. A new one will be created for every round.
    
    private final String REFERENCE; // Own ID of this game to handle multi-matches.
    private final LinkedHashMap<String, ArrayList> ALLPLAYERS = new LinkedHashMap<>(); /* A copy of every player in the game. A player will only get deleted from here when he has no more chips and cannot continue playing. Also when he disconnects. */
    private LinkedHashMap<String, ArrayList> ROUNDPLAYERS = new LinkedHashMap<>(); /* Used to know players who is in this round and didn't retire. I copy the keys from the Linked Map 1 line above every new round. Don't make a copy because it can be a bit tricky btw shallow / deep copies.
                                                                                        It also stores personal information about the player: 
                                                                                        [0] - boolean, player turn to speak?
                                                                                        [1] - boolean, can this player bet?
                                                                                        [2] & [3] - Card, private cards #1 and #2
                                                                                        [4] & [5] - String, play with all the cards; Int, value of this play. */
    
    private final ArrayList WINNER = new ArrayList(); /* Used to save the data of the winner to share it with the clients.
                                                            [0] - String, ID of the player. 
                                                            [1] - String, Name of the play achieved. 
                                                            [2] - Integer, Score achieved. (score of the play + score of the cards to untie with similar plays). I don't send this one through the net. But need it here to compare scores and chose the winner.
                                                            [3] - Integer, number of chips won. */
    
    private int totalPlayers = 0, joinedPlayers = 1; // Number of players setted by user, number of players joined until now. The game will start when the second equals the first.   
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
     * Obtains the private cards from the deck for every player still in game.
     * It adds them in the player's HashMap as entries [2] and [3] inside the AL.
     */
    private void retrievePrivateCards() {
        for(Map.Entry<String, ArrayList> entry : ROUNDPLAYERS.entrySet()) {
            ArrayList list = entry.getValue();
            list.add(deck.getCard());
            list.add(deck.getCard());
        }
    }
    
    /**
     * Retrieves the specified cards and adds them into the table AL.
     * @param number Number of cards to retrieve. It changes depending the phase.
     */
    public void retrieveTableCards(int number) {
        deck.retrieveTableCards(number);
    }
    
    /**
     * Makes a 'snapshot' of ALLPLAYERS into ROUNDPLAYERS.
     * Iterates ALLPLAYERS to get the key, creates a completely new AL and adds into them the booleans to manage turns.
     * The first player is done apart because needs to speak first, the rest is automated.
     * DON'T try to change it and make a copy of one LinkedMap into another. Tried that and had a lot of troubles with shallow copies; They were linked and referencing entries.
     */
    private void resetPlayersList() {
        Iterator it = ALLPLAYERS.keySet().iterator();
        String key = "";
        ArrayList value = new ArrayList();
        
        if(it.hasNext()) key = (String) it.next(); // First player. It must have the 'turn' boolean to true.
        value.add(true);
        value.add(true);
        ROUNDPLAYERS.put(key, value);
        
        while(it.hasNext()) {
            key = (String) it.next();
            value = new ArrayList();
            value.add(false);
            value.add(true);
            ROUNDPLAYERS.put(key, value);
        }
    }
    
    /**
     * Resets everything that needs to be reseted for a new round.
     * That is: ROUNDPLAYER values; WINNER AL; new Deck; Pool of chips.
     */
    private void resetRoundValues() {
        ROUNDPLAYERS.clear();
        resetPlayersList();
        WINNER.clear();
        deck = new Deck();
        chips = 0;
    }
    
    /**
     * Method to be called by state machine -> PreFlop.
     * Gets everything ready to start a new fresh round.
     * Erases and 'copies' all the players to a new round Map.
     * Creates a new fresh deck.
     */
    public void startNewRound() {
        resetRoundValues();
        resetPhaseTurns();
        System.out.println("Game #" +REFERENCE +" has started a new round. " +ROUNDPLAYERS.size() +"/" +totalPlayers +" players left.");
        retrievePrivateCards();
    }
    
    
    /**
     * Adds the player to the list of all players. It also sets it's actions all to true.
     * @param id ID of the player to be used as check condition.
     */
    private void addPlayerToList(String id) {
        ArrayList actions = new ArrayList<>();
        actions.add(new Boolean(false));
        actions.add(new Boolean(true));
        ALLPLAYERS.put(id, actions);
    }
    
    
    /**
     * Starts the game when all the players have joined.
     * Makes a copy of all the players in the game to the local round Map.
     */
    private void startGame() {
        started = true;
        new PreFlop().change(this);
    }
    
    /**
     * Checks whether users can still join to this game.
     * @return Boolean. True if the game has not started and still room left.
     */
    private boolean isJoinable() {
        return !started && (joinedPlayers < totalPlayers);
    }
    
    /**
     * Sets the first turn to speak to the first player in the list of players.
     */
    private void setFirstTurn() {
        String id = ROUNDPLAYERS.keySet().iterator().next();
        ROUNDPLAYERS.get(id).set(0, true);
    }
    
    /**
     * Checks if the game has room left and !started.
     * Sets +1 to the number of current players, starts the game if all the players did join. Adds the player to global Map.
     * Makes a copy of the Map with all the players to the local round Map.
     */
    boolean joinNewPlayer(String id) {
        if(isJoinable()) {
            System.out.println(id +" joined game #" +REFERENCE +"; " +(++joinedPlayers) +"/" +totalPlayers +" players.");
            if(joinedPlayers >= totalPlayers) { // Already do ++ in msg 1 line up.
                addPlayerToList(id);
                startGame();
                setFirstTurn();
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
    boolean speaksPlayer(String id) {
        return (boolean) ROUNDPLAYERS.get(id).get(0);
    }
    
    
    /**
     * Gets the assigned player private cards. To be used from Games.
     * @param id ID of the player we want to retrieve it's cards.
     * @return AL<Card> with it's 2 cards.
     */
    ArrayList<Card> getPlayerCards(String id) {
        if(ROUNDPLAYERS.containsKey(id)) {
            ArrayList<Card> cards = new ArrayList<>();
            cards.add((Card) ROUNDPLAYERS.get(id).get(2));
            cards.add((Card) ROUNDPLAYERS.get(id).get(3));
            
            return cards;
        } else return null;
    }
    
    /**
     * Gets the common cards for all the players.
     * @return AL<Card> with all the common cards.
     */
    ArrayList<Card> getTableCards() {
        return deck.getCARDS_TABLE();
    }
    
    /**
     * Checks if the player may bet.
     * That means: its his turn, and didn't bet yet.
     * @param id ID of the player to check.
     * @return boolean. AND between bool his turn and bool did he already bet?
     */
    public boolean mayPlayerBet(String id) {
        System.out.println("Bets: " +id +", phase: " +phase +": " +ROUNDPLAYERS);
        return ((boolean) ROUNDPLAYERS.get(id).get(0) && (boolean) ROUNDPLAYERS.get(id).get(1));
    }
    
    /**
     * Phase ended. Everyone has spoken. Set booleans of 'player may speak' to true.
     */
    public void resetPhaseTurns() {
        for(ArrayList al : ROUNDPLAYERS.values()) al.set(1, true);
    }
    
    /**
     * Returns the first key of the players currently in the round.
     * @return First key of the list of players who didn't give up.
     */
    private String getFirstKey() {
        return ROUNDPLAYERS.keySet().iterator().next();
    }
    
    /**
     * Obtains the next following ID to the one tossed as parameter.
     * @param id String. ID of the player who has betted.
     * @return String. ID of the next player in line.
     */
    private String getNextID(String id) {
        Iterator it = ROUNDPLAYERS.keySet().iterator();
        while(it.hasNext()) {
            String tmp = (String) it.next();
            if(tmp.matches(id) && it.hasNext()) return (String) it.next();
        }
        
        // Here it will only reach when last player has betted.
        return getFirstKey(); // If it's the last, it will be overrided by the checking in the phase. Need a return that's a valid key tho (Can't be null).
    }
    
    /**
     * Manipulates the booleans of the HashLinkedMap after a player has betted. Sets the turn to speak of the next in line.
     * [0] = turn to speak.
     * [1] = can this player bet?
     * @param id String. ID of the player who has betted.
     */
    private void managesTurns(String id) {
        ROUNDPLAYERS.get(id).set(0, false);
        ROUNDPLAYERS.get(id).set(1, false);
        String next = getNextID(id);
        ROUNDPLAYERS.get(next).set(0, true);
    }
    
    /**
     * A player bets in this game, adds the amount of chips.
     * Advances 1 turn or resets them if was the last player.
     * @param id String. ID of the player who's betting. Used to advance the turn to the next one.
     * @param amount Int. Number of chips to bet.
     * @return Int. Total amount of the common pool after the bet was added.
     */
    public int addBet(String id, int amount) {
        chips += amount;
        managesTurns(id);
        return chips;
    }
    
    /**
     * Checks if it's the last entry in the hashmap. Executed after a bet. If so must advance to the next  phase.
     * @param id String. ID to check.
     * @return boolean. True if it's the last player to talk.
     */
    public boolean isLastPlayer(String id) {
        Iterator it = ROUNDPLAYERS.keySet().iterator();
        while(it.hasNext()) {
            String tmp = (String) it.next();
            if(tmp.matches(id)) return !it.hasNext();
        }
        
        return false;
    }
    
    /**
     * Retrieves the private cards of a player and puts them into an ArrayList. It gets them from the private AL of the player (tossed as parameter).
     * @param al Private player ArrayList where we can find their info.
     * @return AL <Card>. Contains the 2 private cards of the player.
     */
    private ArrayList<Card> getUserCards(ArrayList al) {
        ArrayList<Card> cards = new ArrayList<>();
        Card card1 = (Card) al.get(2);
        Card card2 = (Card) al.get(3);
        
        cards.add(card1);
        cards.add(card2);
        
        return cards;
    }
    
    /**
     * Iterates all the players that didn't retire in this round and adds them their play and score into their private AL with all their info.
     * It will be used to compare all the scores and get a winner.
     */
    private void checkAllPlays() {
        Iterator it = ROUNDPLAYERS.keySet().iterator();
        while(it.hasNext()) {
            String key = (String) it.next();
            ArrayList user = ROUNDPLAYERS.get(key);
            ArrayList<Card> privateCards = getUserCards(user);
            ArrayList result = deck.checkPlay(privateCards);
            
            user.add(4, result.get(0)); // String. Name of the play achieved.
            user.add(5, result.get(1)); // int. score of that play.
        }
    }
    
    /**
     * Compares the the plays of each player and gets the winner from there.
     * It adds them in a new ArrayList.
     * @return AL with the info of the winner. [0] = ID of the winner. [1] = Name of the play to show off. [2] = Score achieved (Score of the cards + value of the cards to untie).
     */
    private ArrayList comparePlays() {
        ArrayList winner = new ArrayList();
        Iterator it = ROUNDPLAYERS.keySet().iterator();
        
        String firstKey = (String) it.next(); // First player will always be first. Result to compare to.
        ArrayList tmp = ROUNDPLAYERS.get(firstKey); 
        
        winner.add(firstKey); // [0] = ID of the player.
        winner.add(tmp.get(4)); // [1] = Name of the play.
        winner.add(tmp.get(5)); // [2] = Score achieved. To compare vs this one. Higher wins.
        
        while(it.hasNext()) { // There will always be at least 1 player more.
            String key = (String) it.next();
            tmp = ROUNDPLAYERS.get(key);
            if(((int) tmp.get(5)) > ((int) winner.get(2))) { // If score of tmp is > than previous score.
                winner.clear();
                winner.add(key);
                winner.add(tmp.get(4));
                winner.add(tmp.get(5));
            }
        }
        
        winner.add(chips); // fixme: if this does work, delete the number of chips at the end or make so the winner cant get it twice.
        return winner;
    }
    
    /**
     * Main method. Gets the plays of every player, compares it's cards and core and gets the winner from there.
     */
    public void choseWinner() {
        checkAllPlays();
        WINNER.addAll(comparePlays());
    }
    
    /**
     * Check to see if there's already a winner of this round or not.
     * @return Bool. True if winner's results are not empty.
     */
    public boolean hasWinner() {
        return ((getWINNER() != null) && (WINNER.size() > 0));
    }
    
    boolean checkPlayerPlaying(String id) {
        return ROUNDPLAYERS.containsKey(id);
    }
    
    boolean retirePlayer(String id) {
        return ROUNDPLAYERS.remove(id, new ArrayList<>());
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

    /**
     * @return the phase
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * DO NOT USE THIS!!! Use the implementation of Phase.change(); instead.
     * I do need it to make the change internally inside the phase.
     * Apart of this, DO NOT USE IT - NEVER - DIRECTLY!!
     * @param phase the phase to set
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * @return the winner
     */
    public ArrayList getWINNER() {
        return WINNER;
    }
}
