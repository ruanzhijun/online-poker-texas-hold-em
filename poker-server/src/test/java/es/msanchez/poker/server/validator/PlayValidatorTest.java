package es.msanchez.poker.server.validator;

import es.msanchez.poker.server.abstraction.PokerTest;
import es.msanchez.poker.server.entities.Card;
import org.apache.commons.collections4.ListUtils;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.util.Lists;
import org.mockito.InjectMocks;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author msanchez
 * @since 22.03.2019
 */
public class PlayValidatorTest extends PokerTest {

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
        cards.add(new Card("4", "diamonds"));
        cards.add(new Card("8", "diamonds"));
        cards.add(new Card("A", "hearts"));
        return cards;
    }

    @Test(dataProvider = "dataProviderPairCaseNegative")
    public void testIsPairCaseNegative(final List<Card> joinedCards) {
        // Given

        // When
        final boolean result = validator.isPair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @DataProvider
    private Object[][] dataProviderPairCaseNegative() {
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> highCard = Lists.newArrayList(new Card("J", "diamonds"),
                new Card("K", "diamonds"));

        return new Object[][]{
                {ListUtils.union(tableCards, highCard)},
        };
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

    @Test(dataProvider = "dataProviderDoublePairCaseNegative")
    public void testIsDoublePairCaseNegative(final List<Card> joinedCards) {
        // Given

        // When
        final boolean result = validator.isDoublePair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @DataProvider
    private Object[][] dataProviderDoublePairCaseNegative() {
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> highCard = Lists.newArrayList(new Card("J", "diamonds"),
                new Card("4", "diamonds"));
        final List<Card> pair = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("10", "diamonds"));
        final List<Card> threeOfAKind = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("A", "jacks"));

        return new Object[][]{
                {ListUtils.union(tableCards, highCard)},
                {ListUtils.union(tableCards, pair)},
                {ListUtils.union(tableCards, threeOfAKind)}
        };
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

    @Test(dataProvider = "dataProviderThreeOfAKindCaseNegative")
    public void testIsThreeOfAKindCaseNegative(final List<Card> joinedCards) {
        // Given

        // When
        final boolean result = validator.isThreeOfAKind(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @DataProvider
    private Object[][] dataProviderThreeOfAKindCaseNegative() {
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> highCard = Lists.newArrayList(new Card("J", "diamonds"),
                new Card("4", "diamonds"));
        final List<Card> pair = Lists.newArrayList(new Card("A", "diamonds"),
                new Card("10", "diamonds"));
        final List<Card> doublePair = Lists.newArrayList(new Card("2", "diamonds"),
                new Card("3", "jacks"));

        return new Object[][]{
                {ListUtils.union(tableCards, highCard)},
                {ListUtils.union(tableCards, pair)},
                {ListUtils.union(tableCards, doublePair)}
        };
    }

    @Test
    public void testIsStraight() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("5", "diamonds"),
                new Card("6", "jacks"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isStraight(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isTrue();
    }

    @Test
    public void testIsStraightNegativeCaseAlmost() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("6", "diamonds"),
                new Card("7", "jacks"));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isStraight(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

}