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
    
    public void sendPlayerCards(Game game);
    
    public void sendTableCards(Game game);
    
    public void bet(Game game);
    
    public void retire(Game game);
}
