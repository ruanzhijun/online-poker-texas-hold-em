package network;

import entities.Card;
import entities.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Static class to encapsulate everything related to Client's connection management.
 * @author Mario Codes
 * @version 0.0.2 Connection achieved. Working on create_game.
 */
public class Connection {
    private static final int PORT = 8143;
    private static final String SERVER_IP = "127.0.0.1";
    
    // Check them! They need to be the same as in the server.
    private static final int INFORMATION = 0;
    private static final int CREATE_GAME = 1; // First Menu, before the game starts.
    private static final int JOIN_GAME = 2;
    
    private static final int BET = 4; // Second Menu, once the game's started.
    private static final int GET_OWN_CARDS = 5;
    private static final int GET_TABLE_CARDS = 6;
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
     * The client is the one who should end the communication.
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
     * Creates a new game with the reference and number of players specified.
     * The server will check if the game already exists.
     * Sends menu option.
     * Sends reference of the game.
     * Sends total number of Players. Flush all.
     * Receives result of the operation.
     * @param reference String. Reference to ID the game.
     * @param totalPlayers int. Total number of players there will be in our game.
     * @return Boolean. Status of the operation, true if everything went well. False if ID in use.
     */
    public static boolean createGame(String reference, String id, int totalPlayers) {
        boolean status = false;
        
        try {
            open();
            oos.writeInt(CREATE_GAME);
            oos.writeUTF(reference);
            oos.writeUTF(id);
            oos.writeInt(totalPlayers);
            oos.flush();
            
            status = ois.readBoolean();
            close();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return status;
    }
    
    /**
     * Joins this player to a previously created game.
     * Sends menu option.
     * Sends game reference. Flush them.
     * Receives status of the operation.
     * @param reference
     * @return 
     */
    public static boolean joinGame(String reference, String id) {
        boolean status = false;
        
        try {
            open();
            oos.writeInt(JOIN_GAME);
            oos.writeUTF(reference);
            oos.writeUTF(id);
            oos.flush();
            
            status = ois.readBoolean();
            close();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return status;
    }
    
    /**
     * Retrieving information on the current game. Will be executed periodically in a separated thread.
     * @param reference String. Games ID we want to obtain information on.
     * @return AL. [0] = Current phase of the game. [1] = Players turn to talk (ID).
     */
    public static ArrayList information(String reference, String id) {
        ArrayList inf = new ArrayList<>();
        
        try {
            open();
            oos.writeInt(INFORMATION);
            oos.writeUTF(reference);
            oos.flush();
            
            boolean exists = ois.readBoolean();
            if(exists) {
                oos.writeUTF(id);
                oos.flush();
                
                inf.add(ois.readUTF()); //Current Phase.
                inf.add(ois.readBoolean()); // Players turn (ID).
                
                return inf;
            }
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    /**
     * Gets this player's private cards retrieved by the server. The confirmation that he needs those cards is done by the standalone thread.
     * @param player Player from which we want to retrieve it's cards.
     * @param reference Reference of the game the player is currently playing.
     * @return AL<Card> with the user's private cards.
     */
    public static ArrayList<Card> getOwnCards(Player player, String reference) {
        ArrayList<Card> cards = new ArrayList<>();
        
        try {
            open();
            oos.writeInt(GET_OWN_CARDS);
            oos.writeUTF(reference);
            oos.flush();
            
            boolean exists = ois.readBoolean();
            if(exists) {
                oos.writeUTF(player.getID());
                oos.flush();
                
                Card card1 = (Card) ois.readObject();
                Card card2 = (Card) ois.readObject();

                cards.add(card1);
                cards.add(card2);

                return cards;
            }
        } catch(IOException|ClassNotFoundException ex) { ex.printStackTrace(); }
        
        return null;
    }
}
