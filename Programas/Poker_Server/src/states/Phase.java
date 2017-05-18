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
    public void change(Game game);
    
    public boolean checkMayPlayerBet(Game game, String id);
    
    public int doBet(Game game, String id, int amount);
    
    public boolean retirePlayer(Game game, String id);
}
