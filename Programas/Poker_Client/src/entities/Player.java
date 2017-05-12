package entities;

import java.util.ArrayList;

/**
 * Representation of a player.
 * @author Mario Codes
 * @version 0.0.1 Just created. Setting the basics.
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
     * Deletes the previous cards in the hand (if any) and adds the new ones.
     * @param cards AL<Card> to add as private cards.
     */
    public void addOwn(ArrayList<Card> cards) {
        this.hand.addOwn(cards);
    }
    
    public ArrayList<Card> getOwnCards() {
        return getHand().getOwn();
    }
    
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
}
