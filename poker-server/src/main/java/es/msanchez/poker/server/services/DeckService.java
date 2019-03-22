package es.msanchez.poker.server.services;

import es.msanchez.poker.server.entities.Card;
import es.msanchez.poker.server.entities.Deck;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author msanchez
 * @since 21.03.2019
 */
public class DeckService {

    private final ArrayList<Card> tableCards = new ArrayList<>();

    @Getter
    private final ArrayList<Card> deck = new ArrayList<>(52);

    /**
     * Getting a full new cards ready.
     * Initialization and random shuffle.
     */
    public Deck prepareNewDeck() {
        final Deck deck = new Deck();
        deck.setCards(prepareShuffledCards());
        return deck;
    }

    /**
     * Creates all the cards for every suit and randomizes them.
     *
     * @return -
     */
    private List<Card> prepareShuffledCards() {
        final List<Card> cards = new ArrayList<>();
        initCardSuit(cards, "hearts");
        initCardSuit(cards, "picas");
        initCardSuit(cards, "trebol");
        initCardSuit(cards, "diamonds");
        shuffle(cards);
        return cards;
    }

    /**
     * Fisher-Yates algorithm. Shuffles a list without repeating any index.
     *
     * @param deck -
     */
    public void shuffle(List<Card> deck) {
        Random rand = ThreadLocalRandom.current();
        for (int i = deck.size() - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Card randomCard = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, randomCard);
        }
    }

    private void initCardSuit(final List<Card> cards, String suit) {
        for (int value = 2; value < 11; value++) {
            cards.add(new Card(Integer.toString(value), suit));
        }

        cards.add(new Card("J", suit));
        cards.add(new Card("Q", suit));
        cards.add(new Card("K", suit));
        cards.add(new Card("A", suit));
    }

    public void burnCard(final Deck deck) {
        final List<Card> cards = deck.getCards();
        cards.remove(0);
    }

    /**
     * Obtains one card from the cards.
     *
     * @return Card removed from the cards.
     */
    public Card withdrawCard(final Deck deck) {
        final List<Card> cards = deck.getCards();
        return cards.remove(1);
    }

    /**
     * Checks the existent play and it's value between the private cards and the common ones found in this cards.
     *
     * @param privateCards Player own private cards.
     * @return ArrayList. [0] = String. Name of the play. [1] = Int. Value of the play.
     */
    public List checkPlay(List<Card> privateCards) {
        // FIXME: Kill the usage of generics lists. with fire.
        List results = new ArrayList();
        PlayService.checkPlay(privateCards, tableCards);
        results.add(PlayService.play);
        results.add(PlayService.value);
        System.out.println("Value: " + PlayService.value);
        return results;
    }

}
