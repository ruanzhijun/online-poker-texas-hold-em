package es.msanchez.poker.server.network;

import es.msanchez.poker.server.entities.Card;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * FIXME: Fuck this whole class. Use Rest architecture.
 * <p>
 * Everything related to the Server's connection and network.
 * Before touching anything here, read the doc and then read it again. Inside the documentation it's stated the order in which the packages need be so Client and Server are coordinated.
 *
 * @author msanchez
 */
@Component
public class Connection {

    private Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    /**
     * Opens the connection and sets the  channels to be used.
     *
     * @param socket Socket opened by main.
     */
    public void open(Socket socket) {
        try {
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes a connection if is opened.
     * ATENTION! Shouldn't really use it until close the whole server. The Client is the one who should disconnect.
     */
    public void close() {
        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the menu option desired by the client.
     *
     * @return Option to be used in the menu. -1 error.
     */
    public int getMenuOption() {
        try {
            return ois.readInt();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /**
     * Gets from the client the parameters needed to start a new game. (ID and number of players).
     *
     * @return Contains [0] Ref of the new game; [1] Total number of players in the game.
     */
    public ArrayList getGameParemeters() {
        ArrayList parameters = new ArrayList();

        try {
            parameters.add(ois.readUTF());
            parameters.add(ois.readUTF());
            parameters.add(ois.readInt());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return parameters;
    }

    /**
     * Gets the ID of a player.
     *
     * @return String. ID of the player.
     */
    public String getID() {
        try {
            return ois.readUTF();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the reference of a game.
     *
     * @return String. Reference of the game.
     */
    public String getReference() {
        try {
            return ois.readUTF();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the amount of chips to bet.
     *
     * @return Amount of chips to bet.
     */
    public int getBet() {
        try {
            return ois.readInt();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /**
     * Sends the result of an operation. Integer version.
     *
     * @param result Result to be sent.
     */
    public void sendResult(int result) {
        try {
            oos.writeInt(result);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends the result of an operation through the socket. Generic method.
     *
     * @param result boolean. Result of the operation we want to inform the user.
     */
    public void sendResult(boolean result) {
        try {
            oos.writeBoolean(result);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends the info the secondary thread needs to update the client status regularly.
     * That is the phase the game is currently at and a bool. which states if the player may or may not bet now.
     *
     * @param phase  Phase the game is currently at.
     * @param speaks Does this player speak now?
     */
    public void sendThreadInformation(String phase, boolean speaks, int pool) {
        try {
            oos.writeUTF(phase);
            oos.writeBoolean(speaks);
            oos.writeInt(pool);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends the cards through the socket. Dynamic number of cards.
     * Integer. Number of cards to be sent.
     *
     * @param cards Number of cards to be sent.
     */
    public void sendCards(List<Card> cards) {
        try {
            oos.writeInt(cards.size());
            for (int i = 0; i < cards.size(); i++) oos.writeObject(cards.get(i));
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends the total amount of chips in the common pool.
     *
     * @param amount Total amount of chips in common pool.
     */
    public void sendChips(int amount) {
        try {
            oos.writeInt(amount);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends the information about the winner to the client.
     *
     * @param winner AL containing all the info to be sent.
     */
    public void sendWinner(ArrayList winner) {
        try {
            oos.writeUTF((String) winner.get(0)); // ID of the winner.
            oos.writeUTF((String) winner.get(1)); // Name of the play achieved.
            oos.writeInt((int) winner.get(3)); // Chips won.
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the boolean to know if a player has to be retired.
     *
     * @return Retire this player? (If he has no chips left)
     */
    public boolean getRetire() {
        try {
            return ois.readBoolean();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}

