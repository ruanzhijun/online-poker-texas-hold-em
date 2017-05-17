/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;

/**
 * PreFlop. Initial phase.
 * The players get their private cards. After this, players may bet.
 * @author Mario Codes
 */
public class PreFlop implements Phase {
    
    /**
     * To be called when the game starts, and after a round has ended.
     * @param game Game we're operating with.
     */
    @Override
    public void change(Game game) {
        System.out.println("");
        System.out.println("PreFlop");
        game.setPhase(this);
        game.startNewRound();
    }
    
    /**
     * Checks if the player may or may not bet right now.
     * @param game Game we're checking.
     * @param id ID of the player to check.
     * @return Boolean. May the player bet?
     */
    @Override
    public boolean mayBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    /**
     * Does the bet action. Here it's already been checked if the player may do it.
     * @param game Game we're checking.
     * @param id ID of the player who is betting.
     * @param amount Amount of chips to bet.
     * @return Int. Total amount of chips after the bet.
     */
    @Override
    public int bet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if(Actions.isLastPlayerInOrder(game, id)) new Flop().change(game);        
        return pool;
    }

    @Override
    public boolean retirePlayer(Game game, String id) {
        boolean retired = Actions.retirePlayer(game, id);
        if(game.isLastPlayerLeft()) new Flop().change(game);
        return retired;
    }
    
    @Override
    public String toString() {
        return "PreFlop";
    }
}
