package es.msanchez.poker.server.services;

import es.msanchez.poker.server.entities.Deck;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DeckServiceTest {

    @InjectMocks
    private DeckService deckService;

    @Test
    public void testPrepareNewDeck() {
        // Given
        final int expectedSize = 52;

        final List<String> expectedValues = this.prepareExpectedValues();

        // When
        final Deck deck = deckService.prepareNewDeck();

        // Then
        BDDAssertions.assertThat(deck.getCards()).isNotNull()
                .isNotEmpty()
                .hasSize(expectedSize);

        BDDAssertions.assertThat(deck.getCards())
                .extracting("value")
                .containsExactlyInAnyOrderElementsOf(expectedValues);

    }

    private List<String> prepareExpectedValues() {
        final List<String> values = new ArrayList<>();
        for (int suit = 0; suit < 4; suit++) {
            values.add("2");
            values.add("3");
            values.add("4");
            values.add("5");
            values.add("6");
            values.add("7");
            values.add("8");
            values.add("9");
            values.add("10");
            values.add("11");
            values.add("12");
            values.add("13");
            values.add("14");
        }
        return values;
    }
}