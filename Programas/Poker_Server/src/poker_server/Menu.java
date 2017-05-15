package poker_server;

import entities.Card;
import entities.Games;
import java.util.ArrayList;
import network.Connection;

/**
 * Static class to encapsulate menus and options selection.
 * It is also a monitor so threads do not collide.
 * @author Mario Codes
 * @version 0.0.2 Setting side thread information.
 */
public class Menu {
    private static final int INFORMATION = 0;
    private static final int CREATE_GAME = 1; // First Menu, before the game starts.
    private static final int JOIN_GAME = 2;
    
    /* ATENTION! Before doing any of these, I should check the game's status. */
    private static final int BET = 6; // Second Menu, once the game's started.
    private static final int GET_CARDS_COMMON = 5;
    private static final int GET_CARDS_PRIVATE = 4;
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
        String id = Connection.getID();
        boolean result = Games.join(reference, id);
        Connection.sendResult(result);
    }
    
    /**
     * Check which does a secondary thread.
     * With the games reference and players id, sends the games phase and if this player may speak or wait.
     */
    private synchronized static void information() {
        String reference = Connection.getReference();
        boolean exists = Games.check(reference);
        Connection.sendResult(exists);
        if(exists) {
            String id = Connection.getID();
            String phase = Games.getPhase(reference);
            boolean speaks = Games.speaks(reference, id);
            Connection.sendInformation(phase, speaks);
        }
    }
    
    
    /**
     * Retrieves and sends the private user cards through the socket.
     */
    private static void private_cards() {
        String reference = Connection.getReference();
        boolean exist = Games.check(reference);
        Connection.sendResult(exist);
        if(exist) {
            String id = Connection.getID();
            ArrayList<Card> cards = Games.privateCards(reference, id);
            Connection.sendCards(cards);
        }
    }
    
    private static void commonCards() {
        String reference = Connection.getReference();
        String id = Connection.getID();
        boolean exists = Games.check(reference);
        Connection.sendResult(exists);
        if(exists) {
            ArrayList<Card> cards = Games.commonCards(reference);
            Connection.sendCards(cards);
        }
    }
    
    /**
     * Checks if it's the player's turn to bet.
     * If it is, it does bet.
     * It does a special 'flow'. Goes through the state machine to manipulate turn changes.
     * The order of execution is Menu -> Games -> Game Phase -> Game. The phase it's a filter.
     */
    private static void bet() {
        String reference = Connection.getReference();
        String id = Connection.getID();
        boolean exist = Games.check(reference);
        Connection.sendResult(exist);
        if(exist) {
            boolean mayBet = Games.mayBet(reference, id);
            Connection.sendResult(mayBet);
            if(mayBet) {
                int amount = Connection.getBet();
                int chips = Games.bet(reference, id, amount);
                Connection.sendChips(chips);
            }
        }
    }
    
    /**
     * Main Switch which derives everything where it needs to be.
     * Here the socket's connection has already been opened!.
     */
    static void selector() {
        int option = Connection.menu();
        System.out.println("New connection. Option " +option);
        
        switch(option) {
            case INFORMATION:
                information();
                break;
            case CREATE_GAME:
                createGame();
                break;
            case JOIN_GAME:
                joinGame();
                break;
            case GET_CARDS_PRIVATE: 
                private_cards();
                break;
            case GET_CARDS_COMMON:
                commonCards();
                break;
            case BET:
                bet();
                break;
            case 7:
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
