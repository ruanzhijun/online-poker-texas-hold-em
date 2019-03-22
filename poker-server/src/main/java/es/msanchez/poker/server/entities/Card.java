package es.msanchez.poker.server.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Representation of a card in a cards.
 *
 * @author msanchez
 * @since 21.03.2019
 */
@Getter
@AllArgsConstructor
public class Card implements Serializable {

    private final String value;
    private final String suit;

    // FIXME: Convert this into an enum and do there the conversions. Same for Suit.
    public String getValue() {
        switch (this.value) {
            case "J":
                return "11";
            case "Q":
                return "12";
            case "K":
                return "13";
            case "A":
                return "14";
            default:
                return this.value;
        }
    }

}
