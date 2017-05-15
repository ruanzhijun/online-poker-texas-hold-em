package entities;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Card's deck composition and usage.
 * @author Mario Codes
 * @version 0.0.3 Implemented method to retrieve table cards.
 */
public class Deck {
    private ArrayList<Card> cards_table = new ArrayList<Card>();
    private ArrayList<Card> deck = new ArrayList<Card>(52);
    
    /**
     * Default constructor. Gets everything ready.
     */
    public Deck() {
        prepareDeck();
    }
    
    /**
     * Ini of the 13 cards of every suit. Must be repeated 4 times, once per suit.
     * @param suit Suit being initialized in the current run. 
     */
    private void iniSuits(String suit) {
        for (int i = 0; i < 9; i++)
            deck.add(new Card(Integer.toString(i+1), suit));
        
        deck.add(new Card("J", suit));
        deck.add(new Card("Q", suit));
        deck.add(new Card("K", suit));
        deck.add(new Card("A", suit));
    }
    
    /**
     * Initializes the deck with every card.
     */
    private void iniDeck() {
        iniSuits("hearts");
        iniSuits("picas");
        iniSuits("trebol");
        iniSuits("diamonds");
    }
    
    /**
     * Random shuffle of a deck.
     * Done through Fisher-Yates algorithm. I've adapted it to an AL instead of int[].
     * @param deck Deck to shuffle.
     */
    private void shuffle(ArrayList<Card> deck) {
        Random rand = ThreadLocalRandom.current();
        for (int i = deck.size() - 1; i > 0; i--) { // Shuffles a list random without repeating an index.
            int index = rand.nextInt(i+1);
            Card cartaRandom = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, cartaRandom);
        }
    }
    
    /**
     * Getting a full new deck ready.
     * Initialization and random shuffle.
     */
    private void prepareDeck() {
        iniDeck();
        shuffle(deck);
    }
    
    
    /**
     * As stated by the rules, burns the required number of cards.
     * @param cards Int. Number of cards to burn from the deck.
     * todo: check if used, if not delete.
     */
    private void burn(int cards) {
        for (int i = 0; i < cards; i++) {
            deck.remove(0);
        }
    }
    
    
    /**
     * Obtains one card from the deck.
     * @return Card removed from the deck.
     */
    public Card getCard() {
        return deck.remove(0);
    }
    
    
    /**
     * Obtains i cards from the deck.
     * @param number Number of cards we want to obtain.
     * @return AL<Card>. Contains the cards removed from the deck.
     */
    public ArrayList<Card> getCards(int number) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < number; i++) cards.add(deck.remove(0));
        
        return cards;
    }
    
    /**
     * Retrieves cards from the deck and adds them into the AL.
     * @param number Number of cards to retrieve.
     */
    public void retrieveTableCards(int number) {
        for (int i = 0; i < number; i++) cards_table.add(getCard());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        int i = 1;
        for(Card carta: deck) {
            sb.append('#');
            sb.append(i++);
            sb.append(" ");
            sb.append(carta);
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
