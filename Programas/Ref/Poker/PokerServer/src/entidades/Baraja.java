/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Conjunto de Cartas que forman la baraja de Juego.
 * @author Mario Codes Sánchez
 * @since 06/02/2017
 * @see https://es.wikipedia.org/wiki/Algoritmo_Fisher-Yates#Tabla_paso_a_paso_.28implementaci.C3.B3n_Fisher-Yates.29
 */
public class Baraja {
    private ArrayList<Carta> CARTAS_MESA = new ArrayList<>();
    private ArrayList<Carta> BARAJA = new ArrayList<>(52);
    
    /**
     * Constructor por defecto. Se encarga de inicializar la baraja.
     */
    public Baraja() {
        preparacionBaraja();
    }
    
    public void rebarajar() {
        CARTAS_MESA = new ArrayList<>();
        BARAJA = new ArrayList<>(52);
        preparacionBaraja();
    }
    
        /**
     * Calculo de la puntuacion de cada jugada.
     * @param s String con la jugada.
     * @return Valor numerico de esa jugada.
     */
    public int getPuntuacion(String s) {
        switch(s) {
            case "Escalera Real":
                return 2000;
            case "Escalera de Color":
                return 1800;
            case "Poker":
                return 1600;
            case "Full":
                return 1400;
            case "Color":
                return 1200;
            case "Escalera":
                return 1000;
            case "Trio":
                return 800;
            case "Doble Pareja":
                return 600;
            case "Pareja":
                return 400;
            case "Carta Alta":
                return 200;
            default:
                System.out.println("Default Switch en puntuacionJugada().");
                return 0;
        }
    }
    
    /**
     * Preparacion completa de una baraja. Inicializacion y mareado de las cartas.
     */
    private void preparacionBaraja() {
        iniBaraja();
        randomShuffleFY(BARAJA);
    }
    
    /**
     * Quemamos (descartamos) el numero de cartas indicadas.
     * @param numeroCartas 
     */
    public void quemarCartas(int numeroCartas) {
        for (int i = 0; i < numeroCartas; i++) {
            BARAJA.remove(0);
        }
    }
    
    /**
     * Inicializacion de las 13 cartas de cada palo. Se repetira 4 veces.
     * @param palo Nombre del palo a asignar.
     */
    private void iniPalos(String palo) {
        for (int i = 0; i < 9; i++) {
            BARAJA.add(new Carta(Integer.toString(i+1), palo));
        }
        
        BARAJA.add(new Carta("J", palo));
        BARAJA.add(new Carta("Q", palo));
        BARAJA.add(new Carta("K", palo));
        BARAJA.add(new Carta("A", palo));
    }
    
    /**
     * Shuffle aleatorio de la baraja, mediante el algoritmo Fisher-Yates (adaptado a ArrayList<Carta> en vez de int[]).
     * @param baraja Baraja con las cartas a marear.
     */
    private void randomShuffleFY(ArrayList<Carta> baraja) {
        Random rand = ThreadLocalRandom.current();
        for (int i = baraja.size() - 1; i > 0; i--) { //Ordena una lista de manera aleatoria sin repeticiones.
            int index = rand.nextInt(i+1);
            Carta cartaRandom = baraja.get(index);
            baraja.set(index, baraja.get(i));
            baraja.set(i, cartaRandom);
        }
    }
    
    /**
     * Inicializacion de la Baraja con todas sus cartas.
     */
    private void iniBaraja() {
        iniPalos("hearts");
        iniPalos("picas");
        iniPalos("trebol");
        iniPalos("diamonds");
    }

    /**
     * Obtener x cartas de la baraja y quitarlas de esta.
     * @param numero Numero de cartas a obtener.
     * @return Cartas extraidas.
     */
    public ArrayList<Carta> extraerCartas(int numero) {
        ArrayList<Carta> cartas = new ArrayList<>(numero);
        
        for (int i = 0; i < numero; i++) {
            cartas.add(BARAJA.get(0));
            BARAJA.remove(0);
        }
        
        return cartas;
    }
    
    /**
     * Sacar una sola carta y quitarla de la baraja.
     * @return Carta siguiente.
     */
    public Carta extraerCarta() {
        Carta carta = BARAJA.get(0);
        BARAJA.remove(0);
        return carta;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        int c = 1;
        for(Carta carta: BARAJA) {
            sb.append('#');
            sb.append(c++);
            sb.append(" ");
            sb.append(carta);
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * @return the BARAJA
     */
    public ArrayList<Carta> getBARAJA() {
        return BARAJA;
    }

    /**
     * @return the CARTAS_MESA
     */
    public ArrayList<Carta> getCARTAS_MESA() {
        return CARTAS_MESA;
    }
}
