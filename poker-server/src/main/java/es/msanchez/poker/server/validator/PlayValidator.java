package es.msanchez.poker.server.validator;

import es.msanchez.poker.server.entities.Card;
import es.msanchez.poker.server.enums.Suit;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
     * @return true, if the {@code cards} contain, at least, five cards of the same {@link Suit}
     */
    public boolean isColor(final List<Card> cards) {
        for (final Suit suit : Suit.values()) {
            final long match = cards.stream()
                    .map(Card::getSuit)
                    .filter(suit::equals)
                    .count();
            if (match >= 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param cards -
     * @return true, if the {@code cards} contain five cards in order.
     */
    public boolean isStraight(final List<Card> cards) {
        final List<Integer> values = obtainOrderedValues(cards);
        final int deadCards = 4;
        return searchPlay(values, deadCards, this::straightFound);
    }

    private boolean straightFound(final List<Integer> values,
                                  final int index) {
        final Integer actualValue = values.get(index);
        return values.get(index + 1).equals(actualValue + 1)
                && values.get(index + 2).equals(actualValue + 2)
                && values.get(index + 3).equals(actualValue + 3)
                && values.get(index + 4).equals(actualValue + 4);
    }

    /**
     * @param cards -
     * @return true, if the {@code cards} contain, at least three cards with the same value.
     */
    public boolean isThreeOfAKind(final List<Card> cards) {
        final List<Integer> values = obtainOrderedValues(cards);
        final int deadCards = 2;
        return this.searchPlay(values, deadCards, this::threeOfAKindFound);
    }

    private List<Integer> obtainOrderedValues(final List<Card> cards) {
        return cards.stream()
                .map(Card::getValue)
                .map(Integer::parseInt)
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
    }

    /**
     * @param values       -
     * @param deadCards    number of cards left, with which the play is impossible.
     *                     <p>
     *                     For example, if I didn't find Three of a Kind checking all the possible
     *                     combinations for the first 5 cards, it's impossible I have it, missing
     *                     only 2 cards to check. This also avoids a {@link ArrayIndexOutOfBoundsException}
     * @param playToSearch condition which will be true if we found the play we're looking for.
     * @return -
     */
    private boolean searchPlay(final List<Integer> values,
                               final int deadCards,
                               final BiPredicate<List<Integer>, Integer> playToSearch) {
        boolean matchFound = false;
        for (int index = 0; index < values.size() - deadCards && !matchFound; index++) {
            if (playToSearch.test(values, index)) {
                matchFound = true;
            }
        }
        return matchFound;
    }

    private boolean threeOfAKindFound(final List<Integer> values,
                                      final int index) {
        return values.get(index).equals(values.get(index + 1))
                && values.get(index).equals(values.get(index + 2));
    }

    /**
     * @param cards -
     * @return true, if the {@code cards} contain, at least two single pairs.
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
     * @return true, if the {@code cards} contain, at least two equal cards.
     */
    public boolean isPair(final List<Card> cards) {
        // TODO: Measure which solution is faster for a Pair. This or the one at Three of a Kind.
        final Predicate<String> isUnique = new HashSet<>()::add;
        return cards.stream()
                .map(Card::getValue)
                .anyMatch(isUnique.negate());
    }

}
