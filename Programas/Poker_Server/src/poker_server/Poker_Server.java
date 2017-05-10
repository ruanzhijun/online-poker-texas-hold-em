package poker_server;

import entities.Games;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import network.Connection;

/**
 * Multithread Server. It's able to serve multiple hosts simultaneously.
 * @author Mario Codes
 * @version 0.0.1 Just created and doing basics.
 */
public class Poker_Server {
    private static final int PORT = 8143; // todo: It should ask it when first run.
    private static Socket socket = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
        
            while(true) {
                // Primero pillar comprobar si crea juego o se une.
            
                // Si lo crea, crearlo.
            
                // Si se une, comprobar si existe.
            
                System.out.println(Games.create("SU"));
                System.out.println("Game exists: " +Games.check("SU"));
                
                socket = serverSocket.accept(); 
                System.out.println("Connection Accepted");
                
                Connection.open(socket);
                System.out.println("Connection Opened");
                
                Connection.close();
                System.out.println("Connection Closed");
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
