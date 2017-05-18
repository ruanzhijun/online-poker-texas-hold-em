/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;


/**
 * State machine to differentiate between the several phases in a game. The behaviour of the server
 *  changes depending on those phases.
 * Phase order: PreFlop -> Flop -> Turn -> River. Every phase has a bet action.
 * @author Mario Codes
 */
public interface Phase {
    
    /**
     * To be called when the game starts, and after a round has ended.
     * @param game Game we're operating with.
     */
    public void change(Game game);
    
    /**
     * Checks if the player may or may not bet right now.
     * @param game Game we're checking.
     * @param id ID of the player to check.
     * @return Boolean. May the player bet?
     */
    public boolean checkMayPlayerBet(Game game, String id);
    
    /**
     * Does the bet action. Here it's already been checked if the player may do it.
     * @param game Game we're checking.
     * @param id ID of the player who is betting.
     * @param amount Amount of chips to bet.
     * @return Total amount of chips after the bet.
     */
    public int doBet(Game game, String id, int amount);
    
    /**
     * Retires a player from the current round.
     * The rest of the players will keep playing the round normally until the end. Where all with chips left will resume.
     * @param game Game to retire the player from.
     * @param id ID of the player to retire.
     * @return Result of the operation.
     */
    public boolean retirePlayerFromRound(Game game, String id);
}
