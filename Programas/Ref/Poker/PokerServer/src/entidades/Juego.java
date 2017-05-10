/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import fases.Fase;
import fases.FasePreFlop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import pokernetwork.Conexion;

/**
 * Gestion de la logica del juego.
 * @author Mario Codes Sánchez
 * @since 04/03/2017
 */
public class Juego {
    private Fase fase = null; //Implementacion de la maquina de estados.
    private Baraja baraja = new Baraja();
    
    private int numberPlayers = 0, actualPlayers = 0; //Usado para saber el numero de players maximo puesto por el priemro y cuando comenzar el juego.
    private boolean comenzado = false; //Para no volver a pasar por el primer menu.
    private boolean finalizado = false;
    
    private ArrayList<String> eliminados = new ArrayList<>();
    private ArrayList<String> conFichas = new ArrayList<>();
    private ArrayList<String> jugando = new ArrayList<>(); //Para retirar un jugador y no tenerlo en cuenta.
    private ArrayList<ArrayList> jugadas = new ArrayList<>(); //Almaceno aqui las jugadas de todos para compararlas y saber quien ha ganado la ronda. [0] ID, [1] Jugada, [2] Valor propio de la jugada.
    private ArrayList<Boolean> HABLADO = new ArrayList<>(); //Si un jugador ha quemado su accion del turno. Debera haberlo hecho para poder apostar.
    private ArrayList<Boolean> APOSTADO = new ArrayList<>(); //Si un jugador ha apostado, para no dejarle repetir.
    private ArrayList<Boolean> RECIBIDO = new ArrayList<>();
    
    private int pool_fichas = 0;
    private String idGanador = null;

    /**
     * Constructor por defecto.
     */
    public Juego() {
        System.out.println("Juego nuevo Comenzado.");
    }
    
    /**
     * Reseteo necesario. Se debera llamar en el cambio de cada fase.
     **/
    public void resetTurnos() {
        for (int i = 0; i < HABLADO.size(); i++) { //Pongo todos a true, como que han gastado su turno y solo quito los que siguen en Juego.
            HABLADO.set(i, true); 
            APOSTADO.set(i, true);
        }
        
        for (int i = 0; i < jugando.size(); i++) {
            int id = Integer.parseInt(jugando.get(i))-1;
            HABLADO.set(id, false);
            APOSTADO.set(id, false);
        }
    }
    
    /**
     * Reseteado de los checks. Solo se utiliza al empezar la ronda nueva en PreFlop.
     * @param lista AL a resetear.
     */
    private void resetAL() {
        jugando = new ArrayList<>();
        for(String s: conFichas) jugando.add(s);
        
        HABLADO = new ArrayList<>(Arrays.asList(new Boolean[actualPlayers]));
        Collections.fill(HABLADO, false);
        
        APOSTADO = new ArrayList<>(Arrays.asList(new Boolean[actualPlayers]));
        Collections.fill(APOSTADO, false);
        
        RECIBIDO = new ArrayList<>(Arrays.asList(new Boolean[actualPlayers]));
        Collections.fill(RECIBIDO, false);
        
        for(String s: eliminados) { //Para quitar las opciones de los que ya han sido eliminados y pase de estos.
            int idAcceso = Integer.parseInt(s);
            HABLADO.set(idAcceso, true);
            APOSTADO.set(idAcceso, true);
            RECIBIDO.set(idAcceso, true);
        }
                
//        System.out.println("Valores despues de resetear las listas de comprobantes: "); //Muchos quebraderos hasta localizar que fallaba por esto. Lo dejo para Testing.
//        for(boolean b: HABLADO) System.out.println("Hablado: " +b);
//        for(boolean b: APOSTADO) System.out.println("Apostado: " +b);
//        for(boolean b: RECIBIDO) System.out.println("Recibido: " +b);
    }
    
    public void retirarse(String id) {
        jugando.remove(id);
    }
    
    /**
     * Reseteos necesarios para comenzar una nueva ronda dentro del mismo Juego.
     * Se llama cada vez que se pone el Juego en 'FasePreFlop'.
     */
    public void newRound() {
        actualPlayers = numberPlayers;
        idGanador = null;
        baraja.rebarajar();
        pool_fichas = 0;
        jugadas = new ArrayList<>();
        resetAL();
        
        if(conFichas.size() <= 1 && !(eliminados.size() <= 0)) this.finalizado = true;
    }
    
    /**
     * Sumamos uno al numero de Jugadores que se han unido.
     * Si se han unido todos los que deben, iniciamos el juego.
     */
    public void addPlayer() {
        HABLADO.add(false);
        APOSTADO.add(false);
        RECIBIDO.add(false);
        jugando.add(Integer.toString(++actualPlayers));
        conFichas.add(Integer.toString(actualPlayers));
        if(actualPlayers >= numberPlayers) {
            comenzado = true;
            new FasePreFlop().cambioFase(this);
        }
    }
    
    /**
     * Obtencion de la Jugada de un Jugador y aniadido a la AL de Jugadas para mas adelante obtener de ahi el ganador.
     **/
    public void getJugada() {
        jugadas.add(Conexion.getJugada());
    }
    
    /**
     * Obtiene el ID del ganador, pasandole como parametro una String[] con las jugadas de cada uno y una int[] con el valor de la jugada propio (desempates).
     * @return ID del ganador.
     */
    public String getGanador() {
        if(idGanador == null) {
            ArrayList<ArrayList> lista = jugadas; //lista de las jugadas [0] = id; [1]= jugada; [2] puntuacion sin tener en cuenta la jugada.
            String[] id = new String[lista.size()];
            String[] jugadas = new String[id.length];
            int[] puntuacionJugador = new int[jugadas.length];

            for (int i = 0; i < lista.size(); i++) {
                id[i] = lista.get(i).get(0).toString();
                jugadas[i] = lista.get(i).get(1).toString();
                puntuacionJugador[i] = (int) lista.get(i).get(2);
            }
            
            int[] puntuacion = new int[jugadas.length];

            for (int i = 0; i < jugadas.length; i++) {
                puntuacion[i] = baraja.getPuntuacion(jugadas[i]);
                puntuacion[i] += puntuacionJugador[i];
            }

            String idGanador = "-1";
            int puntuacionGanador = puntuacion[0];
            for (int i = 0; i < puntuacion.length; i++) {
                if(puntuacion[i] >= puntuacionGanador) {
                    idGanador = id[i];
                    puntuacionGanador = puntuacion[i];
                }
            }
            this.idGanador = idGanador;
        }
        if(idGanador.matches("-1")) System.out.println("Problemas en el get del Ganador: -1");
        return this.idGanador;
    }
    
    /**
     * Gestion de los turnos. Si un jugador se ha retirado se le salta el turno y pasa al siguiente.
     * @return True si todos han terminado.
     */
    public boolean turnosTerminados() {
        return !HABLADO.contains(false) && !APOSTADO.contains(false);
    }
    
    /**
     * Accion de apostar, sumamos las fichas y devolvemos el total de la pool.
     * @param fichas Fichas que sumamos a la pool.
     * @return Pool total hasta ahora.
     */
    public int apostar(int fichas) {
        pool_fichas += fichas;
        return pool_fichas;
    }
    
    public void extraerCartaComun() {
        baraja.getCARTAS_MESA().add(baraja.extraerCarta());
    }
    
    /**
     * Extraccion de las 3 cartas de la baraja comunes para la mesa en la fase de Flop..
     * @return ArrayList de Carta con las Cartas Comunes.
     */
    public ArrayList<Carta> getCartasComunes() {
        if(baraja.getCARTAS_MESA().isEmpty()) baraja.getCARTAS_MESA().addAll(baraja.extraerCartas(3));
        return baraja.getCARTAS_MESA();
    }

    /**
     * Repartimos las 2 cartas necesarias propias para el jugador.
     * @return ArrayList con las 2 cartas extraidas de la baraja.
     */
    public ArrayList<Carta> getCartasJugador() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.addAll(baraja.extraerCartas(2));
        return cartas;
    }

    /**
     * @return the poolApuestas
     */
    public int getPoolApuestas() {
        return pool_fichas;
    }

    /**
     * @return the juegoComenzado
     */
    public boolean isComenzado() {
        return comenzado;
    }

    /**
     * @param comenzado the juegoComenzado to set
     */
    public void setComenzado(boolean comenzado) {
        this.comenzado = comenzado;
    }

    /**
     * @return the fase
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * NO UTILIZAR. HACER EL CAMBIO MEDIANTE Fase.cambioFase().
     * Me hace falta para hacer el cambio de forma interna pero no utilizarlo fuera de esto.
     * @param fase the fase to set
     */
    public void setFase(Fase fase) {
        this.fase = fase;
    }
    
    /**
     * @return the jugadas
     */
    public ArrayList<ArrayList> getJugadas() {
        return jugadas;
    }

    /**
     * @return the numberPlayers
     */
    public int getNumberPlayers() {
        return numberPlayers;
    }

    /**
     * @param numberPlayers the numberPlayers to set
     */
    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    /**
     * @return the HABLADO
     */
    public ArrayList<Boolean> getHABLADO() {
        return HABLADO;
    }

    /**
     * @return the APOSTADO
     */
    public ArrayList<Boolean> getAPOSTADO() {
        return APOSTADO;
    }

    /**
     * @return the actualPlayers
     */
    public int getActualPlayers() {
        return actualPlayers;
    }

    /**
     * @return the RECIBIDO
     */
    public ArrayList<Boolean> getRECIBIDO() {
        return RECIBIDO;
    }

    /**
     * @return the jugando
     */
    public ArrayList<String> getJugando() {
        return jugando;
    }

    /**
     * @param actualPlayers the actualPlayers to set
     */
    public void setActualPlayers(int actualPlayers) {
        this.actualPlayers = actualPlayers;
    }

    /**
     * @return the idGanador
     */
    public String getIdGanador() {
        return idGanador;
    }

    /**
     * @return the conFichas
     */
    public ArrayList<String> getConFichas() {
        return conFichas;
    }

    /**
     * @return the eliminados
     */
    public ArrayList<String> getEliminados() {
        return eliminados;
    }

    /**
     * @return the finalizado
     */
    public boolean isFinalizado() {
        return finalizado;
    }
}
