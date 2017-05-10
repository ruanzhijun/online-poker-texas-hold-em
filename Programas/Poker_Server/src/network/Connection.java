package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

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
    
    public static String reference() {
        try {
            return ois.readUTF();
        } catch(IOException ex) { ex.printStackTrace(); }
        
        return null;
    }
    
    public static void sendResult(boolean result) {
        try {
            oos.writeBoolean(result);
        } catch(IOException ex) { ex.printStackTrace(); }
    }
}
