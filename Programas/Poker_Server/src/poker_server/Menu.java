package poker_server;

import entities.Games;
import java.util.ArrayList;
import network.Connection;

/**
 * Static class to encapsulate menus and options selection.
 * It is also a monitor so threads do not collide.
 * @author Mario Codes
 * @version 0.0.1 Just created. Setting Basics.
 */
public class Menu {
    private static final int CREATE_GAME = 1; // First Menu, before the game starts.
    private static final int JOIN_GAME = 2;
    
    /* ATENTION! Before doing any of these, I should check the game's status. */
    private static final int BET = 4; // Second Menu, once the game's started.
    private static final int GET_CARDS_COMMON = 5;
    private static final int GET_CARDS_PRIVATE = 6;
    private static final int RETIRE = 7;
    
    /**
     * Creates a whole new game.
     * Gets the reference to be used, checks it. If it doesn't exist, it creates the game with it.
     * Sends the result of the operation through the socket.
     */
    private synchronized static void createGame() {
        ArrayList parameters = Connection.gameParemeters();
        boolean result = Games.create(parameters);
        Connection.sendResult(result);
    }
    
    /**
     * Join a previously created game.
     * Does check if the game exists, adds a player to it and sends the result of the operation.
     */
    private static void joinGame() {
        String reference = Connection.getReference();
        boolean result = Games.join(reference);
        Connection.sendResult(result);
    }
    
    /**
     * Main Switch which derives everything where it needs to be.
     * Here the socket's connection has already been opened!.
     */
    static void selector() {
        int option = Connection.menu();
        
        switch(option) {
            case CREATE_GAME:
                createGame();
                break;
            case JOIN_GAME:
                joinGame();
                break;
            case 4: case 5: case 6: case 7:
                break;
            case -1:
                System.out.println("Problem with the connection. Error output by main switch.");
                break;
            default:
                System.out.println("Undefined error. Default option by main switch.");
                break;
        }
    }
}
