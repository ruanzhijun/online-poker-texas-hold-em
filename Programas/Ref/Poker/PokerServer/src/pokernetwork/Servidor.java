/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokernetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import entidades.Juego;
import fases.*;

/*
    Ideas Por Hacer:
        //@todo: Al finalizar cualquier accion en el Server, hacer que lo ultimo que envie sea la Fase en la que se encuentra el Juego y adecuar la GUI del cliente en funcion.
        //@todo: Mas adelante podria implementar que solo se acabe la ronda de apuestas si ninguno sube o todos pasan.
*/

/**
 * todo: implementar comparacion de cartas, palos y demas para saber quien gana o que tiene cada jugador.
 * todo: implementar forma de repartir las fichas y que se sumen al jugador que gana.
 * fixme: Bug - juego comenzado, cierro cliente, abro nuevo e intento unirme -> Salta selector switch primera opcion. Arreglarlo.
 * Proyecto Online juego Oscar -> Poker Texas Hold'em!. Parte Servidor.
 * La forma de uso de llamado de los metodos es -> Servidor -> Juego -> Instancia Fase -> Conexion.
 * @author Mario Codes Sánchez
 * @since 19/02/2017
 * @see 'https://es.wikipedia.org/wiki/Texas_hold_'em'
 */
public class Servidor {
    private static final int JOIN_JUGADOR = 1; //Opciones del menu1.
    
    private static final int SEND_CARTAS_PROPIAS = 2;
    private static final int SEND_CARTAS_COMUNES = 3;
    private static final int APOSTAR = 4;
    private static final int GET_JUGADA_FINAL = 6;
    private static final int GET_GANADOR = 7;    
    private static final int RETIRARSE = 8;
    
    private static Juego juego = new Juego();
    
    private static final int PUERTO = 8143;
    private static Socket socket = null;
    
    /**
     * Pasamos a la siguiente fase correcta al terminar la actual de apuestas.
     * @param f Fase de Apuestas actual.
     * @return Fase siguiente a la cual cambiar el estado del juego.
     */
    private static Fase getFaseCorrecta(Fase f) {
        switch(f.toString()) {
                case "PreFlop":
                    return new FaseFlop();
                case "Flop":
                    return new FaseTurn();
                case "Turn":
                    return new FaseRiver();
                case "River":
                    return new FasePreFlop();
                default:
                    System.out.println("Switch cambio fase apostar default().");
                    return null;
        }
    }
    
    private static String getGanador() {
        String s = juego.getGanador();
        System.out.println("El ganador es: " +s);
        return s;
    }
    
    private static void getJugada() {
        juego.getJugada();
        System.out.println("Chequeando input de jugada.");
    }
   
    /**
     * Cambio a la siguiente fase correspondiente del juego.
     * @param juego Juego sobre el que operamos.
     */
    private static void cambioFase(Juego juego) {
        Fase fase = getFaseCorrecta(juego.getFase());
        if(fase != null) {
            fase.cambioFase(juego);
        }
    }
    
    /**
     * Ronda de apuestas simple.
     * Cuando terminan de apostar todos, se pasa a la siguiente fasa.
     */
    private static void apostar() {
        juego.getFase().apostar(juego);
        if(juego.turnosTerminados() && !juego.getFase().toString().matches("River")) { //Que todos hayan hablado y apostado y NO sea River (esta actuara diferente).
            cambioFase(juego);
        }
    }
    
    private static void sendGanador() {
        String idJugador = Integer.toString(Conexion.getID()+1);
        System.out.println("ID Jugador: " +idJugador);
        System.out.println("Jugador retirado, IDs aun en juego: ");
        for(String s: juego.getJugando()) {
            System.out.println(s);
        }
        
        if((juego.getRECIBIDO().contains(false) && juego.getJugando().contains(idJugador)) || juego.getIdGanador() != null) {
            Conexion.sendBooleano(true);
            String id = getGanador();
            int pool = juego.getPoolApuestas();
            Conexion.sendGanador(id, pool);
            int idRecibida = Conexion.getID();
            boolean recibido = Conexion.getBooleano();
            juego.getRECIBIDO().set(idRecibida, recibido);            
            //Parte para quitar un jugador si fichas <= 0.
            idRecibida += 1; //Por el desfase.
            boolean sigueJugando = Conexion.getBooleano();
            if(!sigueJugando) {
                juego.getConFichas().remove(Integer.toString(idRecibida));
                juego.getEliminados().add(Integer.toString(idRecibida-1));
                System.out.println("El jugador con ID: " +idRecibida +" se ha quedado sin fichas y se le ha eliminado de la partida.");
                System.out.println("Jugadores en juego despues de borrar: ");
                for(String s: juego.getConFichas()) System.out.println("ID: " +s);
            }
        } else { 
            Conexion.sendBooleano(false);
        }
        if(!juego.getRECIBIDO().contains(false)) cambioFase(juego); //fixme: el problema esta aqui, se esta pasando de fase y reiniciando antes de que todos tengan los resultados.
    }
    
    private static void retirarse() {
        juego.getFase().retirarse(juego);
    }
    
    /**
     * Gestor de acciones una vez ya se ha comenzado el juego.
     */
    private static void accionesJuego() {
        try {
            Conexion.aperturaConexion(socket);

            int opcion = Conexion.getInt();
            switch(opcion) {
                case SEND_CARTAS_PROPIAS:
                    juego.getFase().repartoCartasJugador(juego);
                    break;
                case SEND_CARTAS_COMUNES:
                    juego.getFase().repartoCartasComunes(juego);
                    break;
                case APOSTAR: //Apuestas.
                    apostar();
                    break;
                case GET_JUGADA_FINAL:
                    getJugada();
                    break;
                case GET_GANADOR:
                    sendGanador();
                    break;
                case RETIRARSE:
                    retirarse();
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version juego).");
                    break;
            }
            Conexion.cerradoConexion();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Gestor de acciones para el menu principal antes de que se comience el juego.
     * Una vez se haya comenzado se usara el otro gestor.
     */
    private static void accionesMenu() {
        try {
            Conexion.aperturaConexion(socket);
            int opcion = Conexion.getInt();
            
            switch(opcion) {
                case JOIN_JUGADOR:
                    Conexion.addPlayer(juego);
                    break;
                default:
                    System.out.println("Comprobar selector de Acciones (version menu principal).");
                    break;
            }
            Conexion.cerradoConexion();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Ejecucion de la accion del Server.
     * Orden de las cabeceras:
     *      Server Input int menu. Server Output booleano posible. -> Resto de info.
     */
    public static void ejecucionServidor() {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Espera y escucha la llegada de los clientes. Una vez establecida, devuelve el Socket.; 
        
            while(!juego.isFinalizado()) {
                socket = serverSocket.accept(); /* El ServerSocket me da el Socket.
                                                        Bloquea el programa en esta linea y solo avanza cuando un cliente se conecta.*/
                
                if(!juego.isComenzado()) accionesMenu();
                else accionesJuego();
            }
            
            System.out.println("¡Se Acabo el Juego!");
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ejecucionServidor();
    }
}
