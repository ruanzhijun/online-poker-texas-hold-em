package es.msanchez.poker.server.validator;

import es.msanchez.poker.server.entities.Card;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlayValidator {

    public boolean isPair(final List<Card> tableCards,
                          final List<Card> handCards) {
        boolean isPair = false;
        final Map<String, Card> cardValues = new HashMap<>();
        final List<Card> joinedCards = ListUtils.union(tableCards, handCards);
        for (final Card card : joinedCards) {
            final Card valueExists = cardValues.putIfAbsent(card.getValue(), card);
            if (valueExists != null) {
                isPair = true;
                break;
            }
        }
        return isPair;
    }

}
