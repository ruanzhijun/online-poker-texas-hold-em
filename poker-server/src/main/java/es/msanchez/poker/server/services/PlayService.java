package es.msanchez.poker.server.services;

import es.msanchez.poker.server.entities.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Private, inner static class to check which play does a player have and assign it a value. Comparing those values we get the winner.
 * Checks it from high to low, when detects a play, returns it.
 * Done as inner class because it has no sense for it to exist without a cards.
 * It's a big class, I know. I've tried to re-use all the code possible but could not find any fast solution. It works perfectly tho so I'm not going to change anything.
 */
public class PlayService {

    public static int value;
    public static String play;

    /**
     * Gets the numeric value of a card. A = 14, K = 13...
     *
     * @param card Card from which we want to know the value.
     * @return Int. Numeric value of the card.
     */
    private static int getValue(Card card) {
        int value = -1;
        String v = card.toString().substring(0, 1);
        if (v.matches("1")) v = "10";
        try {
            value = Integer.parseInt(v);
        } catch (ClassCastException | NumberFormatException ex) {
            switch (v) {
                case "A":
                    value = 14;
                    break;
                case "K":
                    value = 13;
                    break;
                case "Q":
                    value = 12;
                    break;
                case "J":
                    value = 11;
                    break;
                default:
                    value = -1;
                    System.out.println("Default Switch in PlayService.getValue(). Check it.");
                    break;
            }
        }

        return value;
    }

    /**
     * Gets the suit of a card.
     * Needed to check for straight.
     *
     * @param card Card from which we want to know its suit.
     * @return String. Suit of the card.
     */
    private static String getSuit(Card card) {
        return card.toString().substring(card.toString().indexOf(',') + 1);
    }

    /**
     * Checks both the suit and the numeric value. Needed for royal flush.
     *
     * @param cards Cards to check.
     * @param suit  Suit which has the card I'm checking now.
     * @param value Value which has the card I need to find.
     * @return Bool. True if the card with value x is of the suit I'm looking for.
     */
    private static boolean checkSuitAndValue(List<Card> cards, String suit, int value) {
        boolean match = false;
        for (Card c : cards) if (getSuit(c).matches(suit) && getValue(c) == value) match = true;
        return match;
    }

    /**
     * Checks for royal flush. That's A - K - Q - J - 10; All of the same suit.
     *
     * @param own    Own private cards of the player.
     * @param common Common cards to all the players.
     * @return Bool. True if there's a royal flush.
     */
    private static boolean checkRoyalFlush(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);
        List<Card> cards = new ArrayList<>();
        cards.addAll(own);
        cards.addAll(common);

        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            String suit = getSuit(cards.get(i));
            if (value == 10) { // It has to start always with 10.
                if (values.contains((Integer) value + 1) && checkSuitAndValue(cards, suit, value + 1)) {
                    if (values.contains((Integer) value + 2) && checkSuitAndValue(cards, suit, value + 2)) {
                        if (values.contains((Integer) value + 3) && checkSuitAndValue(cards, suit, value + 3)) {
                            // From here on royal flush found. There's no need for the other checks as a Royal flush are max. 5 cards and the highest ones.
                            if (values.contains((Integer) value + 4) && checkSuitAndValue(cards, suit, value + 4)) {
                                PlayService.value = value + (value + 1) + (value + 2) + (value + 3) + (value + 4);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check to see if there's a straight flush. Flush all of the same suit.
     *
     * @param own    Own cards of the player.
     * @param common Common cards to all the players.
     * @return Bool. True if there's a straight flush.
     */
    private static boolean checkStraightFlush(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);
        List<Card> cards = new ArrayList<>();
        cards.addAll(own);
        cards.addAll(common);

        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            String suit = getSuit(cards.get(i));
            if (values.contains((Integer) value + 1) && checkSuitAndValue(cards, suit, value + 1)) {
                if (values.contains((Integer) value + 2) && checkSuitAndValue(cards, suit, value + 2)) {
                    if (values.contains((Integer) value + 3) && checkSuitAndValue(cards, suit, value + 3)) {
                        // From here on there's a straight flush. The rest it's to check if there's a +5 cards one, to take the higher one.
                        if (values.contains((Integer) value + 4) && checkSuitAndValue(cards, suit, value + 4)) {
                            PlayService.value = value + (value + 1) + (value + 2) + (value + 3) + (value + 4);
                            if (values.contains((Integer) value + 5) && checkSuitAndValue(cards, suit, value + 5)) {
                                PlayService.value -= value;
                                PlayService.value += (value + 5);
                                // There will never be more than 7 Cards in a play.
                                if (values.contains((Integer) value + 6) && checkSuitAndValue(cards, suit, value + 6)) {
                                    PlayService.value -= value + 1;
                                    PlayService.value += (value + 6);
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check to see if there're 4 of a kind.
     *
     * @param own    Own private player's cards.
     * @param common Common cards to all the players.
     * @return Bool. True if there's a 4 of a kind.
     */
    private static boolean checkPoker(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);

        for (int i = 0; i < values.size(); i++) { // Checking for a pair.
            int valor1 = values.get(i);
            for (int j = 0; j < values.size(); j++) {
                int valor2 = values.get(j);
                if (j != i) {
                    for (int q = 0; q < values.size(); q++) {
                        int valor3 = values.get(q);
                        if (q != j && q != i) {
                            if (valor3 == valor2 && valor3 == valor1) { //Three of a kind found.
                                for (int k = 0; k < values.size(); k++) {
                                    int valor4 = values.get(k);
                                    if (k != q && k != j && k != i) { // Four of a kind found.
                                        if (valor4 == valor3) {
                                            PlayService.value = valor4 + valor3 + valor2 + valor1;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check to see if there's a full.
     *
     * @param own    Private player cards.
     * @param common Common cards to all the players.
     * @return Bool. True if there's a full.
     */
    private static boolean checkFull(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);
        boolean pairFound = false;
        boolean threeOfAKindFound = false;
        int threeOfAKindValue = 0;

        for (int i = 0; i < values.size() && !pairFound; i++) { // Looking for three of a kind.
            int value1 = values.get(i);
            for (int j = 0; j < values.size() && !pairFound; j++) {
                int value2 = values.get(j);
                if (j != i) {
                    for (int q = 0; q < values.size() && !pairFound; q++) {
                        int value3 = values.get(q);
                        if (q != j && q != i) {
                            if (value3 == value2 && value3 == value1) {
                                PlayService.value = value3 + value2 + value1;
                                threeOfAKindFound = true;
                                threeOfAKindValue = value1;
                            }
                        }
                    }
                }
            }
        }

        if (threeOfAKindFound) {
            for (int i = 0; i < values.size(); i++) { // Looking for a pair.
                int value1 = values.get(i);
                for (int j = 0; j < values.size(); j++) {
                    int value2 = values.get(j);
                    if (j != i) {
                        if (value1 == value2 && value1 != threeOfAKindValue) { //Full found.
                            PlayService.value += value1 + value2;

                            for (int k = 0; k < values.size(); k++) { // Looking for another pair of higher value.
                                int valueSecond1 = values.get(k);
                                for (int l = 0; l < values.size(); l++) {
                                    int valueSecond2 = values.get(l);
                                    if (l != k && l != j && l != i) {
                                        if (valueSecond1 == valueSecond2 && valueSecond1 != value1 && valueSecond1 != threeOfAKindValue) {
                                            PlayService.value -= value1 + value2;
                                            PlayService.value += valueSecond1 * 2;
                                            value1 = valueSecond1;
                                        }
                                    }
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check to see if there's color.
     *
     * @param own    Own player cards.
     * @param common Common cards to all the players.
     * @return Bool. True if there's color.
     */
    private static boolean checkColor(List<Card> own, List<Card> common) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(own);
        cards.addAll(common);

        List<String> suits = getSuits(own, common);
        boolean exists = false;
        String colorsSuit = "";
        int equals = 1;

        for (int i = 0; i < suits.size(); i++) {
            String suit = suits.get(i);
            for (int j = 0; j < suits.size(); j++) {
                if (j != i) {
                    if (suits.get(j).matches(suit)) equals++;
                    if (equals == 5) {
                        exists = true;
                        colorsSuit = suit;
                    }
                }
            }
            equals = 1;
        }

        if (exists) {
            int value = 0;
            for (int i = 0; i < cards.size(); i++) {
                if (getSuit(cards.get(i)).matches(colorsSuit) && getValue(cards.get(i)) > value)
                    value = getValue(cards.get(i));
            }
            PlayService.value = value;
        }

        return exists;
    }

    /**
     * Check to see if there's an straight.
     *
     * @param own    Own player cards.
     * @param common Common cards to all the players.
     * @return Bool. True if there's straight.
     */
    private static boolean checkStraight(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);

        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            if (values.contains((Integer) value + 1)) {
                if (values.contains((Integer) value + 2)) {
                    if (values.contains((Integer) value + 3)) {
                        if (values.contains((Integer) value + 4)) { // From here on straight found. Checking to see if there's a higher one.
                            PlayService.value = value + (value + 1) + (value + 2) + (value + 3) + (value + 4);
                            if (values.contains((Integer) value + 5)) {
                                PlayService.value -= value;
                                PlayService.value += (value + 5);
                                if (values.contains((Integer) value + 6)) { // There will never be more than 7 cards in a play.
                                    PlayService.value -= value + 1;
                                    PlayService.value += (value + 6);
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check for three of a kind.
     *
     * @param own    Own private player cards
     * @param common Common cards to all the players.
     * @return Bool. True if there's three of a kind.
     */
    private static boolean checkThreeOfAKind(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);
        boolean pairFound = false;

        for (int i = 0; i < values.size() && !pairFound; i++) { // Looking for a pair.
            int value1 = values.get(i);
            for (int j = 0; j < values.size() && !pairFound; j++) {
                int value2 = values.get(j);
                if (j != i) {
                    for (int q = 0; q < values.size() && !pairFound; q++) {
                        int value3 = values.get(q);
                        if (q != j && q != i) {
                            if (value3 == value2 && value3 == value1) {
                                PlayService.value = value3 + value2 + value1;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checking for two pairs.
     *
     * @param own    Own private player cards.
     * @param common Common cards to all the players.
     * @return Bool. True if there're two pairs.
     */
    private static boolean checkDoublePair(List<Card> own, List<Card> common) {
        List<Integer> values = getValues(own, common);
        boolean pairFound = false;
        int pairOne = 0;

        for (int i = 0; i < values.size() && !pairFound; i++) { // Looking for a first pair.
            int value = values.get(i);
            for (int j = 0; j < values.size() && !pairFound; j++) {
                int value2 = values.get(j);
                if (j != i) {
                    if (value == value2) {
                        pairOne += value + value2;
                        pairFound = true;
                    }
                }
            }
        }

        if (pairOne == 0) return false; // No need to keep searching.

        for (int i = 0; i < values.size(); i++) { // Second pair that it's not the first.
            int value = values.get(i);
            for (int j = 0; j < values.size(); j++) {
                int value2 = values.get(j);
                if (j != i) {
                    if (value == value2 && ((value + value2) != pairOne)) {
                        PlayService.value += (value + value2 + pairOne);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checking to see if the player has a simple pair.
     *
     * @param own    Own player private cards.
     * @param common Common cards to everyone.
     * @return Bool. True if there's a pair.
     */
    private static boolean checkPair(List<Card> own, List<Card> common) {
        List<Integer> valores = getValues(own, common);

        for (int i = 0; i < valores.size(); i++) {
            int valor = valores.get(i);
            for (int j = 0; j < valores.size(); j++) {
                int valor2 = valores.get(j);
                if (j != i) {
                    if (valor == valor2) {
                        PlayService.value = valor + valor2;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check for a high card. When there's not anything more in game.
     *
     * @param own    Own private player cards.
     * @param common Common cards to all the players.
     * @return Bool. True always. Lowest play possible.
     */
    private static boolean checkHighCard(List<Card> own, List<Card> common) {
        int maxValue = getValue(own.get(0));
        if (getValue(own.get(1)) > maxValue) maxValue = getValue(own.get(1));
        else {
            for (Card c : common) {
                if (getValue(c) > maxValue) maxValue = getValue(c);
            }
        }

        PlayService.value = maxValue;
        return true;
    }

    /**
     * Mix of all the cards to check for suits in just one List.
     *
     * @param own    Own private player cards.
     * @param common Common cards to all the players.
     * @return List. Mix of all suits.
     */
    private static List<String> getSuits(List<Card> own, List<Card> common) {
        List<String> suits = new ArrayList<>();
        for (Card c : own) suits.add(getSuit(c));
        for (Card c : common) suits.add(getSuit(c));
        suits.sort(Comparator.naturalOrder());
        return suits;
    }

    /**
     * Mix of all the cards to check for every combination possible in an List.
     *
     * @param own    Own private player cards.
     * @param common Common cards to all the players.
     * @return List. Mix of an List with all the cards.
     */
    private static List<Integer> getValues(List<Card> own, List<Card> common) {
        List<Integer> values = new ArrayList<>();
        for (Card c : own) values.add(getValue(c));
        for (Card c : common) values.add(getValue(c));
        return values;
    }

    /**
     * Main method to use, calls, checks and uses every other in here.
     * It sets the best play which can be done with this cards.
     * It sets the value of the play.
     *
     * @param own    Own private player cards.
     * @param common Common cards to all the players.
     */
    public static int checkPlay(List<Card> own, List<Card> common) {
        if (checkRoyalFlush(own, common)) PlayService.play = "Royal Flush";
        else {
            if (checkStraightFlush(own, common)) PlayService.play = "Color Straight";
            else {
                if (checkPoker(own, common)) PlayService.play = "Four of a kind";
                else {
                    if (checkFull(own, common)) PlayService.play = "Full House";
                    else {
                        if (checkColor(own, common)) PlayService.play = "Color";
                        else {
                            if (checkStraight(own, common)) PlayService.play = "Straight";
                            else {
                                if (checkThreeOfAKind(own, common)) PlayService.play = "Three of a Kind";
                                else {
                                    if (checkDoublePair(own, common)) PlayService.play = "Doble Pair";
                                    else {
                                        if (checkPair(own, common)) PlayService.play = "Pair";
                                        else {
                                            if (checkHighCard(own, common)) PlayService.play = "High Card";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        value += getScore(play);
        return value;
    }

    /**
     * Calculates the score of a play. I add it to the score of the cards and compare it against the one of other players to check who is the winner.
     *
     * @param play PlayService to check.
     *             Be sure the Strings match the ones in method checkPlay.
     * @return Int. Numeric value of the play.
     */
    private static int getScore(String play) {
        switch (play) {
            case "Royal Flush":
                return 2000;
            case "Color Straight":
                return 1800;
            case "Four of a kind":
                return 1600;
            case "Full House":
                return 1400;
            case "Color":
                return 1200;
            case "Straight":
                return 1000;
            case "Three of a Kind":
                return 800;
            case "Doble Pair":
                return 600;
            case "Pair":
                return 400;
            case "High Card":
                return 200;
            default:
                System.out.println("Default Switch in DeckService.getScore().");
                return 0;
        }
    }
}