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

import static es.msanchez.poker.server.enums.Suit.*;

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
        final List<Card> own = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("K", DIAMOND));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        final boolean result = validator.isPair(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isTrue();
    }

    private List<Card> prepareTableCards() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card("2", HEARTS));
        cards.add(new Card("3", HEARTS));
        cards.add(new Card("4", DIAMOND));
        cards.add(new Card("8", DIAMOND));
        cards.add(new Card("A", HEARTS));
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
        final List<Card> highCard = Lists.newArrayList(new Card("J", DIAMOND),
                new Card("K", DIAMOND));

        return new Object[][]{
                {ListUtils.union(tableCards, highCard)},
        };
    }

    @Test
    public void testIsDoublePair() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("2", DIAMOND));

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
        final List<Card> highCard = Lists.newArrayList(new Card("J", DIAMOND),
                new Card("4", DIAMOND));
        final List<Card> pair = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("10", DIAMOND));
        final List<Card> threeOfAKind = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("A", JACK));

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
        final List<Card> own = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("A", JACK));

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
        final List<Card> highCard = Lists.newArrayList(new Card("J", DIAMOND),
                new Card("4", DIAMOND));
        final List<Card> pair = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("10", DIAMOND));
        final List<Card> doublePair = Lists.newArrayList(new Card("2", DIAMOND),
                new Card("3", JACK));

        return new Object[][]{
                {ListUtils.union(tableCards, highCard)},
                {ListUtils.union(tableCards, pair)},
                {ListUtils.union(tableCards, doublePair)}
        };
    }

    @Test(dataProvider = "dataProviderStraight")
    public void testIsStraight(final List<Card> joinedCards) {
        // Given

        // When
        final boolean result = validator.isStraight(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isTrue();
    }

    @DataProvider
    private Object[][] dataProviderStraight() {
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> lowStraight = Lists.newArrayList(new Card("5", DIAMOND),
                new Card("6", JACK));

        final List<Card> tableCards2 = this.prepareTableCardsHigh();
        final List<Card> highStraight = Lists.newArrayList(new Card("5", DIAMOND),
                new Card("10", JACK));

        return new Object[][]{
                {ListUtils.union(tableCards, lowStraight)},
                {ListUtils.union(tableCards2, highStraight)},
        };
    }

    private List<Card> prepareTableCardsHigh() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card("5", HEARTS));
        cards.add(new Card("J", HEARTS));
        cards.add(new Card("Q", DIAMOND));
        cards.add(new Card("K", DIAMOND));
        cards.add(new Card("A", HEARTS));
        return cards;
    }

    @Test(dataProvider = "dataProviderStraightCaseNegative")
    public void testIsStraightCaseNegative(final List<Card> joinedCards) {
        // Given

        // When
        final boolean result = validator.isStraight(joinedCards);

        // Then
        BDDAssertions.assertThat(result).isFalse();
    }

    @DataProvider
    private Object[][] dataProviderStraightCaseNegative() {
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> highCard = Lists.newArrayList(new Card("J", DIAMOND),
                new Card("4", DIAMOND));
        final List<Card> pair = Lists.newArrayList(new Card("A", DIAMOND),
                new Card("10", DIAMOND));
        final List<Card> doublePair = Lists.newArrayList(new Card("2", DIAMOND),
                new Card("3", JACK));
        final List<Card> threeOfAKind = Lists.newArrayList(new Card("2", DIAMOND),
                new Card("2", JACK));
        final List<Card> brokenStraight = Lists.newArrayList(new Card("6", DIAMOND),
                new Card("7", JACK));

        return new Object[][]{
                {ListUtils.union(tableCards, highCard)},
                {ListUtils.union(tableCards, pair)},
                {ListUtils.union(tableCards, doublePair)},
                {ListUtils.union(tableCards, threeOfAKind)},
                {ListUtils.union(tableCards, brokenStraight)}
        };
    }

    @Test
    public void testIsColor() {
        // Given
        final List<Card> tableCards = this.prepareTableCards();
        final List<Card> own = Lists.newArrayList(new Card("A", HEARTS),
                new Card("A", HEARTS));

        final List<Card> joinedCards = ListUtils.union(tableCards, own);

        // When
        // final boolean result = validator.isColor(joinedCards);

        // Then
        // BDDAssertions.assertThat(result).isTrue();
    }

}