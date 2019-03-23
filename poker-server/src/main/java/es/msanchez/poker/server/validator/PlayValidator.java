package es.msanchez.poker.server.validator;

import es.msanchez.poker.server.entities.Card;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

@Component
public class PlayValidator {

    public boolean isPair(final List<Card> cards) {
        final Predicate<String> isUnique = new HashSet<>()::add;
        return cards.stream()
                .map(Card::getValue)
                .anyMatch(isUnique.negate());
    }

}
