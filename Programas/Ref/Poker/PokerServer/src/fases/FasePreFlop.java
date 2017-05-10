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
 * Fase de Pre-Flop. 
 * Es la fase en la cual se reparte dos cartas individuales a cada Jugador.
 * Despues de repartir se hace una fase para introducir las Ciegas, y despues uan ronda de Apuestas.
 * @author Mario Codes Sánchez
 * @since 18/02/2017
 */
public class FasePreFlop implements Fase{
    @Override
    public void cambioFase(Juego juego) {
        juego.newRound();
        juego.setFase(this);
        System.out.println("Nueva Ronda. Fase de PreFlop.");
        
        System.out.println("Jugadores en juego despues de la limpieza: ");
        for(String s: juego.getConFichas()) System.out.println("ID: " +s);
    }

    @Override
    public void repartoCartasJugador(Juego juego) {
        int id = Conexion.getID();
        if(!juego.getHABLADO().get(id)) {
            Conexion.sendBooleano(true);
            ArrayList<Carta> cartas = juego.getCartasJugador();
            Conexion.repartoCartas(cartas);
            juego.getHABLADO().set(id, true);
        } else Conexion.sendBooleano(false);
        
        if(!juego.getHABLADO().contains(false)) System.out.println("Comenzada ronda de Apuestas.");
    }

    @Override
    public void repartoCartasComunes(Juego juego) {
        Conexion.getID();
        Conexion.sendBooleano(false);
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
        System.out.println("ID a retirar: " +id);
        System.out.println("IDs en Juego antes de retirar: ");
        for(String s: juego.getJugando()) System.out.println("ID: " +s);
        
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
        return "PreFlop";
    }
}
