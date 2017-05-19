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
    
    private static final int GET_OWN_CARDS = 4; // Second Menu, once the game's started.
    private static final int GET_TABLE_CARDS = 5;
    private static final int BET = 6;
    private static final int RETIRE = 7;
    private static final int GET_WINNER = 8;
    
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
            
//            Runtime.getRuntime().addShutdownHook(new NetShutdownHook());
        } catch(IOException ex) { ex.printStackTrace(); }
    }
    
    /**
     * Closes the oppened (and only oppened) data streams and socket.
     * The client is the one who should end the communication.
     */
    static void close() {
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
     * @return AL. If length == 0 the game does not exist. Otherwise [0] = Current phase of the game. [1] = Players turn to talk (ID).
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
            }

            close();
            return inf;
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    /**
     * Gets n number of cards from the server. Parses them, adds them in an AL<Card> and returns this AL.
     * As I use this method in others which already do capture exceptions, I throw them instead of capture them again here.
     * @param number Number of cards to be received.
     * @return AL<Card>. Contains all the cards received. May be 3, 4 or 5.
     * @throws IOException Network exception.
     * @throws ClassNotFoundException Class exception throws when can't parse (Card) ois.readObject.
     */
    private static ArrayList<Card> getCards(int number) throws IOException, ClassNotFoundException {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < number; i++) cards.add((Card) ois.readObject());
        return cards;
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
                
                int number = ois.readInt();
                cards = getCards(number);
            }
            
            close();
            return cards;
        } catch(IOException|ClassNotFoundException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    /**
     * Gets the common cards which are the same for all the players.
     * @param reference Reference of the game the player is playing at.
     * @return AL<Card>. Contains n number of cards. It may be 3, 4 or 5. Depends on the phase the game is at.
     */
    public static ArrayList<Card> getTableCards(String reference) {
        ArrayList<Card> cards = new ArrayList<>();
        
        try {
            open();
            oos.writeInt(GET_TABLE_CARDS);
            oos.writeUTF(reference);
            oos.flush();
            
            boolean exists = ois.readBoolean();
            if(exists) {
                int number = ois.readInt();
                cards = getCards(number);
            }
            
            close();
            return cards;
        } catch(IOException|ClassNotFoundException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    /**
     * Does a bet. Checks if the game exists. Checks if it's this player's turn to speak and may bet.
     * @param player Player to bet.
     * @param reference Reference of the game the player is in.
     * @param amount Amount of chips to bet.
     * @return Amount of chips in common pool. Error return see doc.
     */
    public static int bet(Player player, String reference, int amount) {
        try {
            int result = 0;
            
            open();
            oos.writeInt(BET);
            oos.writeUTF(reference);
            oos.writeUTF(player.getID());
            oos.flush();
            
            boolean exists = ois.readBoolean();
            if(exists) {
                boolean isInGame = ois.readBoolean();
                if(isInGame) {
                    boolean morePlayersLeft = ois.readBoolean();
                    if(morePlayersLeft) {
                        boolean mayBet = ois.readBoolean();
                        if(mayBet) {
                            oos.writeInt(amount);
                            oos.flush();

                            int chips = ois.readInt();
                            result = chips;
                        } else result = -3;
                    } else result = -5;
                } else result = -4;
            } else result = -2;
            
            close();
            return result;
        } catch(IOException ex) { ex.printStackTrace(); }
        
        close();
        return -1;
    }
    
    /**
     * Gets the winner of a match.
     * It checks if the game exists and if the game has chosen a winner or there are plays to do yet.
     * @param reference Reference of the game the player is playing in.
     * @return AL. Contains the info of the winner. Null if game !exist or exception. Empty if the game has not a winner yet. Else [0] = ID of the winner. [1] = Name of the play. [2] = number of chips won.
     */
    public static ArrayList getWinner(String reference, String id) {
        try {
            ArrayList winner = new ArrayList();
            
            open();
            oos.writeInt(GET_WINNER);
            oos.writeUTF(reference);
            oos.writeUTF(id);
            oos.flush();
            
            boolean exists = ois.readBoolean();
            if(exists) {
                boolean hasWinner = ois.readBoolean();
                if(hasWinner) {
                    winner.add(ois.readUTF()); // ID of the winner.
                    winner.add(ois.readUTF()); // Name of the play.
                    winner.add(ois.readInt()); // Amount of chips to add if this player has won.
                    
                    return winner;
                } else return winner;
            } else return null;
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    public static boolean retire(String reference, String id) {
        try {
            open();
            oos.writeInt(RETIRE);
            oos.writeUTF(reference);
            oos.writeUTF(id);
            oos.flush();
            
            boolean exists = ois.readBoolean();
            if(exists) {
                boolean playing = ois.readBoolean();
                if(playing) {
                    boolean retired = ois.readBoolean();
                    return retired;
                }
            }
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return false;
    }
    
    public static boolean retireFromGame(boolean retire) {
        try {
            boolean retired = false;
            
            oos.writeBoolean(retire);
            oos.flush();
            if(retire) retired = ois.readBoolean();
            
            return retired;
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return false;
    }
}
