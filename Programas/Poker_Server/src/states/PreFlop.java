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
        game.setPhase(this);
        game.newRound();
    }
    
    @Override
    public boolean mayBet(Game game, String id) {
        return Actions.mayBet(game, id);
    }
    
    @Override
    public int bet(Game game, String id, int amount) {
        return Actions.bet(game, id, amount);
        
//        if(juego.getHABLADO().contains(false)) Conexion.sendBooleano(false); //Si todos no han hablado no se puede apostar.
//        else {
//            Conexion.sendBooleano(true);
//            int id = Conexion.getID();
//            if(juego.getAPOSTADO().get(id)) Conexion.sendBooleano(false);
//            else {
//                Conexion.sendBooleano(true);
//                Conexion.getApuesta(juego);
//                juego.getAPOSTADO().set(id, true);
//            }
//        }
    }

    @Override
    public String toString() {
        return "PreFlop";
    }
}
