/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;
import java.util.ArrayList;

/**
 * River Phase. Last one.
 * @author Mario Codes
 */
public class River implements Phase {
    @Override
    public void change(Game game) {
//        juego.resetTurnos();
//        juego.setFase(this);
//        juego.extraerCartaComun();
//        System.out.println("Fase de River");
    }

    @Override
    public void bet(Game game) {
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
        return "River";
    }
}
