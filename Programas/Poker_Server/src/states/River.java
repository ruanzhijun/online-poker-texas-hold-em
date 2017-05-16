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
        game.resetTurns();
        game.retrieveTableCards(1);
    }

    @Override
    public boolean mayBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    @Override
    public int bet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if(Actions.isLastPlayer(game, id)) {
            game.choseWinner();
            
            Runnable t1 = () -> { // Setted in a new thread so the last user gets the pool and after i waiting seconds, the server starts a new round.
                try { 
                    Thread.sleep(6000); // todo: set it as a variable asked on startup maybe.
                    new PreFlop().change(game); 
                } catch(InterruptedException ex) { ex.printStackTrace(); }
            };
            new Thread(t1).start();
            
        }        
        return pool;
    }
    
    
    @Override
    public String toString() {
        return "River";
    }
}
