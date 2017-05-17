/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;

/**
 * Flop Phase.
 * @author Mario Codes
 */
public class Flop implements Phase {
    @Override
    public void change(Game game) {
        System.out.println("Flop");
        game.setPhase(this);
        if(game.isLastPlayerLeft()) new Turn().change(game);
        else {
            game.resetPhaseTurns();
            game.retrieveTableCards(3);
        }
    }

    @Override
    public boolean mayBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    @Override
    public int bet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if(Actions.isLastPlayerInOrder(game, id)) new Turn().change(game);        
        return pool;
    }
    
    @Override
    public boolean retirePlayer(Game game, String id) {
        boolean retired = Actions.retirePlayer(game, id);
        if(game.isLastPlayerLeft()) new Turn().change(game);
        return retired;
    }
    
    @Override
    public String toString() {
        return "Flop";
    }
}
