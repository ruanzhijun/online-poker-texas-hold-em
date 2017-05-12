/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.Game;
import java.util.ArrayList;

/**
 * PreFlop. Initial phase.
 * The players get their private cards. After this, players bet's.
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
    public void sendPlayerCards(Game game) {
//        int id = Conexion.getID();
//        if(!juego.getHABLADO().get(id)) {
//            Conexion.sendBooleano(true);
//            ArrayList<Carta> cartas = juego.getCartasJugador();
//            Conexion.repartoCartas(cartas);
//            juego.getHABLADO().set(id, true);
//        } else Conexion.sendBooleano(false);
//        
//        if(!juego.getHABLADO().contains(false)) System.out.println("Comenzada ronda de Apuestas.");
    }

    @Override
    public void sendTableCards(Game game) {
//        Conexion.getID();
//        Conexion.sendBooleano(false);
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
    public void retire(Game game) {
//        String id = Integer.toString(Conexion.getID()+1);
//        System.out.println("ID a retirar: " +id);
//        System.out.println("IDs en Juego antes de retirar: ");
//        for(String s: juego.getJugando()) System.out.println("ID: " +s);
//        
//        if(!juego.getJugando().contains(id)) { //El Jugador ya esta retirado.
//            Conexion.sendBooleano(false);
//            System.out.println("Un Jugador que ya estaba retirado se ha intentado retirar de nuevo.");
//        }
//        else {
//            Conexion.sendBooleano(true);
//            juego.getJugando().remove(id);
//            juego.setActualPlayers(juego.getActualPlayers()-1);
//            juego.getHABLADO().set(Integer.parseInt(id)-1, Boolean.TRUE);
//            juego.getAPOSTADO().set(Integer.parseInt(id)-1, Boolean.TRUE);
//            
//            //Output para testing.
//            System.out.println("Se ha retirado al jugador con ID: " +id +".\nResto de Jugadores en Juego: ");
//            for (int i = 0; i < juego.getJugando().size(); i++) {
//                System.out.println("(ID: " +juego.getJugando().get(i) +").\n");
//            }
//        }
    }

    @Override
    public String toString() {
        return "PreFlop";
    }
}
