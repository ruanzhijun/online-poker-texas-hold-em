/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;

/**
 * Representacion de una carta de Juego.
 * @author Mario Codes Sánchez
 * @sice 06/02/2017
 */
public class Carta implements Serializable {
    private final String VALOR, PALO;
    
    /**
     * Constructor por defecto de la carta.
     * @param valor Valor propio de la carta.
     * @param palo Palo al que pertenece la carta.
     */
    public Carta(String valor, String palo) {
        this.VALOR = valor;
        this.PALO = palo;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(VALOR);
        sb.append(",");
        sb.append(PALO);
        
        return sb.toString();
    }

    /**
     * @return the PALO
     */
    public String getPALO() {
        return PALO;
    }
}
