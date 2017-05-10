/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fases;

import entidades.Carta;
import entidades.Juego;
import java.util.ArrayList;
import pokernetwork.Conexion;

/**
 * Fase de River, seguida de la Fase de Turn; Es la ultima fase del juego antes de una de apuestas.
 * Se destapa otra carta y se pasa de nuevo a apuestas; con esa ya terminara el juego.
 * @author Mario Codes Sánchez
 * @since 15/02/2017
 */
public class FaseRiver implements Fase {
    @Override
    public void cambioFase(Juego juego) {
        juego.resetTurnos();
        juego.setFase(this);
        juego.extraerCartaComun();
        System.out.println("Fase de River");
    }

    @Override
    public void repartoCartasJugador(Juego juego) {
        Conexion.getID();
        Conexion.sendBooleano(false);
    }

    @Override
    public void repartoCartasComunes(Juego juego) {
        int id = Conexion.getID();
        if(!juego.getHABLADO().get(id)) {
            Conexion.sendBooleano(true);
            ArrayList<Carta> cartas = juego.getCartasComunes();
            Conexion.repartoCartas(cartas);
            juego.getHABLADO().set(id, true);
        } else Conexion.sendBooleano(false);
        
        if(!juego.getHABLADO().contains(false)) System.out.println("Comenzada ronda de Apuestas.");
    }

    @Override
    public void apostar(Juego juego) {
        if(juego.getHABLADO().contains(false)) Conexion.sendBooleano(false); //Si todos no han hablado no se puede apostar.
        else {
            Conexion.sendBooleano(true);
            int id = Conexion.getID();
            if(juego.getAPOSTADO().get(id)) Conexion.sendBooleano(false);
            else {
                Conexion.sendBooleano(true);
                Conexion.getApuesta(juego);
                juego.getAPOSTADO().set(id, true);
            }
        }
    }

    @Override
    public void retirarse(Juego juego) {
        String id = Integer.toString(Conexion.getID()+1);
        if(!juego.getJugando().contains(id)) { //El Jugador ya esta retirado.
            Conexion.sendBooleano(false);
            System.out.println("Un Jugador que ya estaba retirado se ha intentado retirar de nuevo.");
        }
        else {
            Conexion.sendBooleano(true);
            juego.getJugando().remove(id);
            juego.setActualPlayers(juego.getActualPlayers()-1);
            juego.getHABLADO().set(Integer.parseInt(id)-1, Boolean.TRUE);
            juego.getAPOSTADO().set(Integer.parseInt(id)-1, Boolean.TRUE);
            
            //Output para testing.
            System.out.println("Se ha retirado al jugador con ID: " +id +".\nResto de Jugadores en Juego: ");
            for (int i = 0; i < juego.getJugando().size(); i++) {
                System.out.println("(ID: " +juego.getJugando().get(i) +").\n");
            }
        }
    }
    
    @Override
    public String toString() {
        return "River";
    }
}
