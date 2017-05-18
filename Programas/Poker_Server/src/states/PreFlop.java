/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;
import entities.Games;

/**
 * PreFlop. Initial phase.
 * The players get their private cards. After this, players may bet.
 * @author Mario Codes
 */
public class PreFlop implements Phase {
    
    @Override
    public void change(Game game) {
        game.setPhase(this);
        if(!game.isEnded()) game.startNewRound();
        else {
            System.out.println("Game with reference #" +game.getREFERENCE() +" has ended");
            Games.deleteGame(game.getREFERENCE());
        }
    }
    
    @Override
    public boolean checkMayPlayerBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    @Override
    public int doBet(Game game, String id, int amount) {
        int pool = Actions.bet(game, id, amount);
        if(Actions.isLastPlayerInOrder(game, id)) new Flop().change(game);        
        return pool;
    }

    @Override
    public boolean retirePlayerFromRound(Game game, String id) {
        boolean retired = Actions.retirePlayer(game, id);
        if(game.isLastPlayerInRound()) new Flop().change(game);
        return retired;
    }
    
    @Override
    public String toString() {
        return "PreFlop";
    }
}
