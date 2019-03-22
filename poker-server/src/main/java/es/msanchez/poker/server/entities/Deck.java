package es.msanchez.poker.server.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Deck {

    private List<Card> cards = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Card card : cards) {
            sb.append('#');
            sb.append(i++);
            sb.append(" ");
            sb.append(card);
            sb.append("\n");
        }
        return sb.toString();
    }

}
