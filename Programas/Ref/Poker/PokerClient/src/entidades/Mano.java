/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;

/**
 * Mano del Jugador, se compone de sus 2 cartas personales y las 3 mas altas de las 5 de la mesa.
 * @author Mario Codes Sánchez
 * @since 15/02/2017
 */
public class Mano {
    private ArrayList<Carta> cartas_propias = new ArrayList<>();
    private ArrayList<Carta> cartas_mesa = new ArrayList<>();
    
    /**
     * Aniadido de una unica carta a la mano personal.
     * @param carta Carta a anaidir.
     */
    public void aniadirCartaPropias(Carta carta) {
        cartas_propias.add(carta);
    }
    
    /**
     * Aniadido de varias cartas a la mano personal.
     * @param carta Cartas a aniadir.
     */
    public void aniadirCartaPropias(ArrayList<Carta> carta) {
        cartas_propias.addAll(carta);
    }
    
    /**
     * Aniadido de una unica carta nueva a las comunes de la mesa.
     * @param carta Carta a aniadir.
     */
    public void aniadirCartaMesa(Carta carta) {
        cartas_mesa.add(carta);
    }
    
    /**
     * Aniadido de varias cartas nuevas a las comunes de la mesa.
     * @param carta Cartas a aniadir.
     */
    public void aniadirCartaMesa(ArrayList<Carta> carta) {
        cartas_mesa.addAll(carta);
    }
    
    /**
     * Limpiamos las cartas de la mano y la mesa para comenzar una nueva ronda.
     */
    public void limpiarCartas() {
        cartas_propias = new ArrayList<>();
        cartas_mesa = new ArrayList<>();
    }

    /**
     * @return the mano
     */
    public ArrayList<Carta> getCartasPropias() {
        return cartas_propias;
    }

    /**
     * @return the cartas_mesa
     */
    public ArrayList<Carta> getCartas_mesa() {
        return cartas_mesa;
    }

    /**
     * @param cartas_mesa the cartas_mesa to set
     */
    public void setCartas_mesa(ArrayList<Carta> cartas_mesa) {
        this.cartas_mesa = cartas_mesa;
    }
}
