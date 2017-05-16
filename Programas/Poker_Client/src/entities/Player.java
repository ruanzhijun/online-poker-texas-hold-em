package entities;

import java.util.ArrayList;
import network.Connection;

/**
 * Representation of a player.
 * @author Mario Codes
 * @version 0.0.3 Added methods to check for the winner of a game.
 */
public class Player {
    private Hand hand = new Hand();
    private int chips = 1000;
    private final String ID;
    
    /**
     * Default Constructor.
     * It assigns the ID of the player. 'Guest' if none.
     * @param ID Own players ID.
     */
    public Player(String ID) { 
        this.ID = ID;
    }
    
    /**
     * Bets an amount of chips and adds them to the common pool.
     * @param reference Reference of the game the player is playing.
     * @param amount Number of chips to bet.
     * @return Int. Number of chips in the common pool after the bet has been added.
     */
    public int bet(String reference, int amount) {
        int pool = Connection.bet(this, reference, amount);
        if(pool > 0) chips -= amount;
        return pool;
    }
    
    /**
     * Adds the amount of chips to the ones owned by the player.
     * @param amount Number of chips to add.
     */
    public void addChips(int amount) {
        chips += amount;
    }
    
    /**
     * Deletes the previous cards in the hand (if any) and adds the new ones.
     * @param cards AL<Card> to add as private cards.
     */
    public void addOwn(ArrayList<Card> cards) {
        this.hand.addOwn(cards);
    }
    
    /**
     * Adds the tossed cards to the table cards AL.
     * @param cards Cards to be added.
     */
    public void addTable(ArrayList<Card> cards) {
        this.hand.addTable(cards);
    }
    
    /**
     * Get the private player cards.
     * @return AL<Card>. Private player cards.
     */
    public ArrayList<Card> getOwnCards() {
        return getHand().getOwn();
    }
    
    /**
     * Gets the saved common cards.
     * @return AL<Card>. Player common cards.
     */
    public ArrayList<Card> getTableCards() {
        return getHand().getTable();
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @return the hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * @return the chips
     */
    public int getChips() {
        return chips;
    }
}
