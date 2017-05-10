/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fases;

import entidades.Carta;
import entidades.Juego;
import java.util.ArrayList;

/**
 * Implementacion de una maquina de estados para saber en cada momento en que fase se encuentra el Juego.
 * De esta forma tengo control total sobre lo que se puede y lo que no se puede hacer en cada fase del juego.
 * El orden de las fases en el Juego es -> PreFlop -> Apuestas -> Flop -> Apuestas -> Turn -> Apuestas -> River -> Apuestas.
 *      Como a la hora de programarlas, Turn y River son Iguales, solo implemento una de las 2 y la reuso para evitar duplicidad de codigo.
 *      'Apuestas' en si no es una fase, pero me hace mas sencillo la programacion si la implemento como tal.
 * @author Mario Codes Sánchez
 * @since 08/02/2017
 * @see https://en.wikipedia.org/wiki/State_pattern
 * @see https://www.tutorialspoint.com/design_pattern/state_pattern.htm
 */
public interface Fase {
    public void cambioFase(Juego juego);
    
    public void repartoCartasJugador(Juego juego);
    
    /**
     * Le voy a pasar juego como parametro, porque en funcion de la fase en la que se encuentre, debere pasar 3, 4 o 5 cartas como comunes.
     * @param juego Juego actual.
     */
    public void repartoCartasComunes(Juego juego);
    
    public void apostar(Juego juego);
    
    public void retirarse(Juego juego);
}
