/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;
import java.util.ArrayList;

/**
 * Flop Phase.
 * @author Mario Codes
 */
public class Flop implements Phase {
    @Override
    public void change(Game game) {
        game.setPhase(this);
        game.resetTurns();
        game.retrieveTableCards(3);
    }

    @Override
    public boolean mayBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    @Override
    public int bet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if(Actions.isLastPlayer(game, id)) new Turn().change(game);        
        return pool;
    }
    
    @Override
    public String toString() {
        return "Flop";
    }
}
