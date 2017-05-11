package entities;

import java.util.ArrayList;

/**
 * Player's hand. It's 2 personal cards and the 3 higher in the table.
 * @author Mario Codes
 */
public class Hand {
    private ArrayList<Card> own = new ArrayList<>();
    private ArrayList<Card> table = new ArrayList<>();
    
    /**
     * Adds a card to the private player's cards.
     * @param card Card to add.
     */
    public void addOwn(Card card) {
        getOwn().add(card);
    }
    
    /**
     * Empties the table and adds the cards into the AL.
     * @param card Cards which make the table.
     */
    public void addTable(ArrayList<Card> card) {
        table = new ArrayList<>();
        getTable().addAll(card);
    }
    
    /**
     * Removes every card from this player.
     * To do when starting a new round.
     */
    public void clean() {
        own = new ArrayList<>();
        table = new ArrayList<>();
    }

    /**
     * @return the own
     */
    public ArrayList<Card> getOwn() {
        return own;
    }

    /**
     * @return the table
     */
    public ArrayList<Card> getTable() {
        return table;
    }
}
