package poker_server;

import entities.Card;
import entities.Games;
import java.util.ArrayList;
import network.Connection;

/**
 * Static class to encapsulate menus and options selection.
 * It is also a monitor so threads do not collide with sensible methods.
 * @author Mario Codes
 * @version 0.0.3 Developed method to get the winner of the match.
 */
public class Menu {
    private static final int INFORMATION = 0; // First Menu, before the game starts.
    private static final int CREATE_GAME = 1; 
    private static final int JOIN_GAME = 2;
    
    private static final int BET = 6; // Second Menu, once the game's started.
    private static final int GET_CARDS_COMMON = 5;
    private static final int GET_CARDS_PRIVATE = 4;
    private static final int RETIRE = 7;
    private static final int GET_WINNER = 8;
    
    
    /**
     * Creates a whole new game.
     * Gets the reference to be used, checks it. If it doesn't exist, it creates the game with it.
     * Sends the result of the operation through the socket.
     */
    private synchronized static void createGame() {
        ArrayList parameters = Connection.getGameParemeters();
        boolean result = Games.createGame(parameters);
        Connection.sendActionResults(result);
    }
    
    
    /**
     * Join a previously created game.
     * Does check if the game exists, adds a player to it and sends the result of the operation.
     */
    private static void joinGame() {
        String reference = Connection.getGameReference();
        String id = Connection.getPlayerID();
        boolean result = Games.joinGame(reference, id);
        Connection.sendActionResults(result);
    }
    
    /**
     * Check which does a secondary thread.
     * With the games reference and players id, sends the games phase and if this player may speak or wait.
     */
    private synchronized static void information() {
        String reference = Connection.getGameReference();
        boolean exists = Games.checkExist(reference);
        Connection.sendActionResults(exists);
        if(exists) {
            String id = Connection.getPlayerID();
            String phase = Games.getPhase(reference);
            boolean speaks = Games.speaksPlayer(reference, id);
            Connection.sendThreadInformation(phase, speaks);
        }
    }
    
    
    /**
     * Retrieves and sends the private user cards through the socket.
     */
    private static void private_cards() {
        String reference = Connection.getGameReference();
        boolean exist = Games.checkExist(reference);
        Connection.sendActionResults(exist);
        if(exist) {
            String id = Connection.getPlayerID();
            ArrayList<Card> cards = Games.getPrivateCards(reference, id);
            Connection.sendCards(cards);
        }
    }
    
    /**
     * Gets the common cards of the game and sends it to the user who requested it.
     */
    private static void commonCards() {
        String reference = Connection.getGameReference();
        boolean exists = Games.checkExist(reference);
        Connection.sendActionResults(exists);
        if(exists) {
            ArrayList<Card> cards = Games.getCommonCards(reference);
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
        String reference = Connection.getGameReference();
        String id = Connection.getPlayerID();
        boolean exist = Games.checkExist(reference);
        Connection.sendActionResults(exist);
        if(exist) {
            boolean isInGame = Games.isPlayerInGame(reference, id);
            Connection.sendActionResults(isInGame);
            if(isInGame) {
                boolean mayBet = Games.mayBet(reference, id);
                Connection.sendActionResults(mayBet);
                if(mayBet) {
                    int amount = Connection.getBet();
                    int chips = Games.bet(reference, id, amount);
                    Connection.sendChips(chips);
                }
            }
        }
    }
    
    /**
     * Sends information about the winner of a game.
     * Checks if the games does exist and if this game has already chosen a winner.
     */
    private static void getWinner() {
        String reference = Connection.getGameReference();
        boolean exists = Games.checkExist(reference);
        Connection.sendActionResults(exists);
        if(exists) {
            boolean hasWinner = Games.hasWinner(reference);
            Connection.sendActionResults(hasWinner);
            if(hasWinner) {
                ArrayList winner = Games.getWinner(reference);
                Connection.sendWinner(winner);
            }
        }
    }
    
    /**
     * Sends information about the action of retiring a player from the game.
     */
    private static void retirePlayer() {
        String reference = Connection.getGameReference();
        String id = Connection.getPlayerID();
        boolean exists = Games.checkExist(reference);
        Connection.sendActionResults(exists);
        if(exists) {
            boolean isPlaying = Games.checkPlayerPlaying(reference, id);
            Connection.sendActionResults(isPlaying);
            if(isPlaying) {
                boolean playerRetired = Games.retirePlayer(reference, id);
                Connection.sendActionResults(playerRetired);
            }
        }
    }
    
    /**
     * Main Switch which derives everything where it needs to be.
     * Here the socket's connection has already been opened!.
     */
    static void selector() {
        int option = Connection.getMenuOption();
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
            case RETIRE:
                retirePlayer();
                break;
            case GET_WINNER:
                getWinner();
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
