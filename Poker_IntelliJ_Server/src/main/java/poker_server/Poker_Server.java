package poker_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import network.Connection;

/**
 * Multi-thread Server. It's able to serve multiple hosts simultaneously.
 * @author Mario Codes
 * @version 0.0.2 Did it multi-thread.
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
            System.out.println("Server started and ready.");
            while(true) {
                socket = serverSocket.accept(); 
                
                Connection.open(socket);
                Runnable menu = () -> { Menu.selector(); }; // New Thread where it does its operations.
                new Thread(menu).start();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
