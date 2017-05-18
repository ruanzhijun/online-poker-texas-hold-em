/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;

/**
 * River Phase. Last one.
 * @author Mario Codes
 */
public class River implements Phase {
    @Override
    public void change(Game game) {
        game.setPhase(this);
        if(game.isLastPlayerLeft()) Actions.endRound(game);
        else {
            game.resetPhaseTurns();
            game.retrieveTableCards(1); 
        }
    }

    @Override
    public boolean mayBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    @Override
    public int bet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if(Actions.isLastPlayerInOrder(game, id)) {
            game.choseWinner();
            Actions.endRound(game);
        }        
        return pool;
    }
    
    @Override
    public boolean retirePlayer(Game game, String id) {
        boolean retired = Actions.retirePlayer(game, id);
        if(game.isLastPlayerLeft()) {
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
