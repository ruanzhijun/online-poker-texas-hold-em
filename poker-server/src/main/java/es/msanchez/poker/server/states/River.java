package es.msanchez.poker.server.states;

import es.msanchez.poker.server.entities.Game;

/**
 * River Phase. Last one.
 *
 * @author Mario Codes
 */
public class River implements Phase {
    @Override
    public void change(Game game) {
        game.setPhase(this);
        if (game.isLastPlayerInRound()) Actions.endRound(game);
        else {
            game.resetPhaseTurns();
            game.retrieveTableCards(1);
        }
    }

    @Override
    public boolean checkMayPlayerBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }

    @Override
    public int doBet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if (Actions.isLastPlayerInOrder(game, id)) {
            game.choseWinner();
            Actions.endRound(game);
        }
        return pool;
    }

    @Override
    public boolean retirePlayerFromRound(Game game, String id) {
        boolean retired = Actions.retirePlayer(game, id);
        if (game.isLastPlayerInRound()) {
            game.choseWinner();
            Actions.endRound(game);
        }
        return retired;
    }

    @Override
    public String toString() {
        return "River";
    }
}
