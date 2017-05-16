package network;

import entities.Card;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Everything related to the Server's connection and network.
 * @author Mario Codes
 * @version 0.0.2 First methods. Open and close the connection.
 */
public class Connection {
    private static Socket socket = null;
    
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    /**
     * Opens the connection and sets the static channels to be used.
     * @param socket Socket opened by main.
     */
    public static void open(Socket socket) {
        try {
            Connection.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Closes a connection if is opened.
     * ATENTION! Shouldn't really use it until close the server. The Client should disconnect.
     */
    public static void close() {
        try {
            if(ois != null) ois.close();
            if(oos != null) oos.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Gets the menu option desired by the client.
     * @return Int. Option to be used in the menu. -1 error.
     */
    public static int menu() {
        try {
            return ois.readInt();
        } catch (IOException ex) { ex.printStackTrace(); }
        
        return -1;
    }
    
    /**
     * Gets from the client the parameters needed to start a new game. (ID and number of players).
     * @return AL. Contains [0] Ref of the new game; [1] Total number of players in the game.
     */
    public static ArrayList gameParemeters() {
        ArrayList parameters = new ArrayList();
        
        try {
            parameters.add(ois.readUTF());
            parameters.add(ois.readUTF());
            parameters.add(ois.readInt());
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return parameters;
    }
    
    
    /**
     * Gets the ID of a player.
     * @return String. ID of the player.
     */
    public static String getID() {
        try {
            return ois.readUTF();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    
    /**
     * Gets the reference of a game.
     * @return String. Reference of the game.
     */
    public static String getReference() {
        try {
            return ois.readUTF();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    
    /**
     * Sends the result of an operation through the socket. Generic method.
     * @param result boolean. Result of the operation we want to inform the user.
     */
    public static void sendResult(boolean result) {
        try {
            oos.writeBoolean(result);
            oos.flush();
        } catch(IOException ex) { ex.printStackTrace(); }
    }
    
    
    public static void sendInformation(String phase, boolean speaks) {
        try {
            oos.writeUTF(phase);
            oos.writeBoolean(speaks);
            oos.flush();
        } catch(IOException ex) { ex.printStackTrace(); }
    }
    
    
    /**
     * Sends the cards through the socket. Dynamic number of cards.
     * Integer. Number of cards to be sent.
     * Card. Card #i.
     * @param cards Number of cards to be sent.
     */
    public static void sendCards(ArrayList<Card> cards) {
        try {
            oos.writeInt(cards.size());
            for (int i = 0; i < cards.size(); i++) oos.writeObject(cards.get(i));
            oos.flush();
        }catch(IOException ex) { ex.printStackTrace(); }
    }
    
    /**
     * Gets the amount of chips to bet.
     * @return Amount of chips to bet.
     */
    public static int getBet() {
        try {
            return ois.readInt();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return -1;
    }
    
    /**
     * Sends the total amount of chips in the common pool.
     * @param amount Total amount of chips in common pool.
     */
    public static void sendChips(int amount) {
        try {
            oos.writeInt(amount);
            oos.flush();
        } catch(IOException ex) { ex.printStackTrace(); }
    }
    
    public static void sendWinner(ArrayList winner) {
        try {
            oos.writeUTF((String) winner.get(0)); // ID of the winner.
            oos.writeUTF((String) winner.get(1)); // Name of the play achieved.
            oos.writeInt((int) winner.get(3)); // Chips won.
            oos.flush();
        } catch(IOException ex) { ex.printStackTrace(); }
    }
}
