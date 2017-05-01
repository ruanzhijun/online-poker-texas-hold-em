package poker_server;

import entities.Game;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Multithreading Server. It's able to serve multiple hosts simultaneously.
 * @author Mario Codes
 * @version 0.0.1
 */
public class Poker_Server {
    private static final int PUERTO = 8143;
    private static Socket socket = null;
    private static final HashMap GAMES = new <String, Game>HashMap();
    
    private static final int CREATE_GAME = 1; // First Menu, before the game starts.
    private static final int JOIN_GAME = 2;
    
    private static final int BET = 1; // Second Menu, once the game´s started.
    private static final int GET_CARDS_COMMON = 2;
    private static final int GET_CARDS_PRIVATE = 3;
    private static final int RETIRE = 4;
    
    // private static Juego juego = new Juego();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
        
            // Primero pillar comprobar si crea juego o se une.
            
            // Si lo crea, crearlo.
            
            // Si se une, comprobar si existe.
            /*
            while(!Game.isFinalizado()) {
                socket = serverSocket.accept(); 
                
                if(!juego.isComenzado()) accionesMenu();
                else accionesJuego();
            }
            */
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
