package es.msanchez.poker.server.entities;

import es.msanchez.poker.server.services.DeckService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Round {

    private final DeckService deckService;

    private final Deck deck;

    @Getter
    private final List<Card> tableCards;

    @Autowired
    public Round(final DeckService deckService) {
        this.deckService = deckService;
        this.deck = deckService.prepareNewDeck();
        tableCards = new ArrayList<>();
    }

    public Card withdrawCard() {
        this.deckService.burnCard(deck);
        return this.deckService.withdrawCard(deck);
    }

    public void withdrawTableCards(int number) {
        for (int i = 0; i < number; i++) {
            tableCards.add(withdrawCard());
        }
    }

}
