package entities;

import java.io.Serializable;

/**
 * Representation of a card in a deck.
 * @author Mario Codes
 * @version 0.2 Copied and matched the previous project.
 */
public class Card implements Serializable {
    private final String VALUE, SUIT;
    
    /**
     * Default constructor.
     * @param value Own value in a deck (2 - A).
     * @param suit Suit of the card.
     */
    public Card(String value, String suit) {
        this.VALUE = value;
        this.SUIT = suit;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(VALUE);
        sb.append(",");
        sb.append(SUIT);
        
        return sb.toString();
    }

    /**
     * @return the VALUE
     */
    public String getVALUE() {
        return VALUE;
    }
}
