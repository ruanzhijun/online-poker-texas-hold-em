package es.msanchez.poker.server.validator;

import es.msanchez.poker.server.entities.Card;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * To use this class to check a {@code List<Card>} it has to be checked
 * from top play to lowest and find the first where it returns {@code true}.
 * <p>
 * This has to be done like this, as per definition a {@code Double Pair} will also contain
 * a {@code Pair}. Or a {@code Full House} will also contain a {@code Pair} and
 * {@code Three of a Kind} but they're not the strongest play.
 *
 * @author msanchez
 * @since 22.03.2019
 */
@Component
public class PlayValidator {

    /**
     * @param cards -
     * @return true, if the {@code cards} contain, at least a double pair.
     */
    public boolean isDoublePair(final List<Card> cards) {
        final Predicate<String> isUnique = new HashSet<>()::add;
        final long pairs = cards.stream()
                .map(Card::getValue)
                .filter(isUnique.negate())
                .distinct()
                .count();
        return pairs == 2L;
    }

    /**
     * @param cards -
     * @return true, if the {@code cards} contain, at least a pair.
     */
    public boolean isPair(final List<Card> cards) {
        final Predicate<String> isUnique = new HashSet<>()::add;
        return cards.stream()
                .map(Card::getValue)
                .anyMatch(isUnique.negate());
    }

}
