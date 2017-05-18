package poker_server;

import entities.Card;
import entities.Games;
import java.util.ArrayList;
import network.Connection;

/**
 * Static class to encapsulate menus and options selection.
 * It is also a monitor so threads do not collide with sensible methods.
 * @author Mario Codes
 * @version 0.0.3.1 Added new checks and error exits to bet.
 */
public class Menu {
    private static final int INFORMATION = 0; // First Group. Before the game starts.
    private static final int CREATE_GAME = 1; 
    private static final int JOIN_GAME = 2;
    
    private static final int GET_CARDS_PRIVATE = 4; // Second Group. Game options.
    private static final int GET_CARDS_COMMON = 5;
    private static final int BET = 6;
    private static final int RETIRE = 7;
    private static final int GET_WINNER = 8;
    
    /**
     * Check which does the secondary thread launched by the clients.
     * Gets the game reference and player id, sends the game phase and if this player may speak or wait until it's his turn.
     */
    private synchronized static void sendInformation() {
        String reference = Connection.getReference();
        boolean exists = Games.gameExists(reference);
        Connection.sendResult(exists);
        if(exists) {
            String id = Connection.getID();
            String phase = Games.getPhase(reference);
            boolean speaks = Games.isPlayersTurn(reference, id);
            Connection.sendThreadInformation(phase, speaks);
        }
    }
    
    /**
     * Creates a whole new game.
     * Gets the reference to be used, checks it. If it doesn't exist, it creates the game with it.
     * Sends the result of the operation through the socket.
     */
    private synchronized static void createGame() {
        ArrayList parameters = Connection.getGameParemeters();
        boolean result = Games.createGame(parameters);
        Connection.sendResult(result);
    }
    
    
    /**
     * Join a previously created game.
     * Does check if the game exists, adds a player to it and sends the result of the operation.
     */
    private synchronized static void joinGame() {
        String reference = Connection.getReference();
        String id = Connection.getID();
        boolean result = Games.joinGame(reference, id);
        Connection.sendResult(result);
    }
    
    
    /**
     * Retrieves and sends the private user cards through the socket.
     */
    private static void sendPrivateCards() {
        String reference = Connection.getReference();
        boolean exist = Games.gameExists(reference);
        Connection.sendResult(exist);
        if(exist) {
            String id = Connection.getID();
            ArrayList<Card> cards = Games.getPrivateCards(reference, id);
            Connection.sendCards(cards);
        }
    }
    
    /**
     * Gets the common cards of the game and sends it to the user who requested it.
     */
    private static void sendCommonCards() {
        String reference = Connection.getReference();
        boolean exists = Games.gameExists(reference);
        Connection.sendResult(exists);
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
    private static void doBet() {
        String reference = Connection.getReference();
        String id = Connection.getID();
        boolean exist = Games.gameExists(reference);
        Connection.sendResult(exist);
        if(exist) {
            boolean isInGame = Games.isPlayerInRound(reference, id);
            Connection.sendResult(isInGame);
            if(isInGame) {
                boolean morePlayersLeft = Games.isMorePlayersLeft(reference);
                Connection.sendResult(morePlayersLeft);
                if(morePlayersLeft) {
                    boolean mayBet = Games.mayBet(reference, id);
                    Connection.sendResult(mayBet);
                    if(mayBet) {
                        int amount = Connection.getBet();
                        int chips = Games.bet(reference, id, amount);
                        Connection.sendChips(chips);
                    }
                }
            }
        }
    }
    
    /**
     * Sends information about the winner of a game.
     * Checks if the games does exist and if this game has already chosen a winner.
     */
    private static void sendWinner() {
        String reference = Connection.getReference();
        boolean exists = Games.gameExists(reference);
        Connection.sendResult(exists);
        if(exists) {
            boolean hasWinner = Games.hasWinner(reference);
            Connection.sendResult(hasWinner);
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
        String reference = Connection.getReference();
        String id = Connection.getID();
        boolean exists = Games.gameExists(reference);
        Connection.sendResult(exists);
        if(exists) {
            boolean isPlaying = Games.checkPlayerPlaying(reference, id);
            Connection.sendResult(isPlaying);
            if(isPlaying) {
                boolean playerRetired = Games.retirePlayer(reference, id);
                Connection.sendResult(playerRetired);
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
                sendInformation();
                break;
            case CREATE_GAME:
                createGame();
                break;
            case JOIN_GAME:
                joinGame();
                break;
            case GET_CARDS_PRIVATE: 
                sendPrivateCards();
                break;
            case GET_CARDS_COMMON:
                sendCommonCards();
                break;
            case BET:
                doBet();
                break;
            case RETIRE:
                retirePlayer();
                break;
            case GET_WINNER:
                sendWinner();
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
