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
    public void testIsPairCaseNegative() {
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
}