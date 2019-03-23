package es.msanchez.poker.server.validator;

import es.msanchez.poker.server.entities.Card;
import org.apache.commons.collections4.ListUtils;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author msanchez
 * @since 22.03.2019
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayValidatorTest {

    @InjectMocks
    private PlayValidator validator;

    @Test
    public void testIsPair() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("K", "diamonds"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isPair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isTrue();
    }

    private List<Card> prepareTableCards() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card("2", "hearts"));
        cards.add(new Card("3", "hearts"));
        cards.add(new Card("A", "hearts"));
        return cards;
    }

    @Test
    public void testIsPairNegativeCaseHighCard() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("J", "diamonds"),
                new Card("K", "diamonds"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isPair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsDoublePair() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("2", "diamonds"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isDoublePair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isTrue();
    }

    @Test
    public void testIsDoublePairNegativeCaseHighCard() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("J", "diamonds"),
                new Card("4", "diamonds"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isDoublePair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsDoublePairNegativeCasePair() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("4", "diamonds"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isDoublePair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsDoublePairNegativeCaseThreeOfAKind() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("A", "jacks"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isDoublePair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsThreeOfAKind() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("A", "jacks"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isThreeOfAKind(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isTrue();
    }

    @Test
    public void testIsThreeOfAKindNegativeCaseHighCard() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("K", "diamonds"),
                new Card("J", "jacks"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isThreeOfAKind(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsThreeOfAKindNegativeCaseDoublePair() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("2", "diamonds"),
                new Card("3", "jacks"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isThreeOfAKind(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

}