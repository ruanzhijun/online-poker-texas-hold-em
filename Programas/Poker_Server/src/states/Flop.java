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
        // game.resetList();
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
        return "Flop";
    }
}
