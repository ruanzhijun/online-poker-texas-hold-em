package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Static class to encapsulate everything related to Client's connection management.
 * @author Mario Codes
 * @version 0.1 Created. Doing the basics.
 */
public class Connection {
    private static final int PORT = 8143;
    private static final String SERVER_IP = "127.0.0.1";
    
    // Check them! They need to be the same as the server.
    private static final int CREATE_GAME = 1; // First Menu, before the game starts.
    private static final int JOIN_GAME = 2;
    private static final int BET = 4; // Second Menu, once the game's started.
    private static final int GET_CARDS_COMMON = 5;
    private static final int GET_CARDS_PRIVATE = 6;
    private static final int RETIRE = 7;
    
    private static Socket socket = null;
    
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    
    /**
     * Opens the connection to send and receive data.
     */
    private static void open() {
        try {
            socket = new Socket(SERVER_IP, PORT);
            
            in = socket.getInputStream();
            out = socket.getOutputStream();
            ois = new ObjectInputStream(in);
            oos = new ObjectOutputStream(out);
        } catch(IOException ex) { ex.printStackTrace(); }
    }
    
    /**
     * Closes the oppened (and only oppened) data streams and socket.
     * todo: think about setting this in a shutdown hook.
     */
    private static void close() {
        try {
            if(ois != null) ois.close();
            if(oos != null) oos.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) { ex.printStackTrace(); }
    }
    
    /**
     * Sends the desired option so the server knows what to do.
     * @param option int. Number of the option we want to execute.
     */
    public static void menu(int option) {
        try {
            oos.writeInt(option);
            oos.flush();
        }catch(IOException ex) { ex.printStackTrace(); }
    }
    
    /**
     * Creates a new game with the reference.
     * @param reference String. Reference to ID the game.
     * @return Boolean. Status of the operation, true if everything went well. False if ID in use.
     */
    public static boolean createGame(String reference) {
        boolean status = false;
        
        try {
            open();
            oos.writeInt(CREATE_GAME);
            oos.writeUTF(reference);
            oos.flush();
            
            status = ois.readBoolean();
            close();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return status;
    }
}
