package entities;

import java.io.Serializable;

/**
 * A card inside the game.
 * @author Mario Codes
 */
public class Card implements Serializable {
    private final String VALUE, SUIT;
    
    /**
     * Default constructor.
     * @param value Card's value (2-A).
     * @param suit Suit to which the card belongs.
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
     * @return the PALO
     */
    public String getSUIT() {
        return SUIT;
    }
}
