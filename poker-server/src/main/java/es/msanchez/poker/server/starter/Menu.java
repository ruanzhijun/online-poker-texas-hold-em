package es.msanchez.poker.server.starter;

import es.msanchez.poker.server.entities.Card;
import es.msanchez.poker.server.entities.Games;
import es.msanchez.poker.server.network.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Class to encapsulate menus and options selection.
 * It is also a monitor so threads do not collide with sensible methods.
 *
 * @author msanchez
 */
@Component
public class Menu {

    private final Connection conn;
    private final Games games;

    @Autowired
    public Menu(final Connection conn,
                final Games games) {
        this.conn = conn;
        this.games = games;
    }


    /**
     * Check which does the secondary thread launched by the clients.
     * Gets the game reference and player id, sends the game phase and if this player may speak or wait until it's his turn.
     */
    public synchronized void sendInformation() {
        String reference = conn.getReference();
        boolean exists = games.checkGameExists(reference);
        conn.sendResult(exists);
        if (exists) {
            boolean started = games.checkGameStarted(reference);
            conn.sendResult(started);
            if (started) {
                String id = conn.getID();
                String phase = games.getPhase(reference);
                boolean speaks = games.isPlayersTurn(reference, id);
                int pool = games.getPool(reference);
                conn.sendThreadInformation(phase, speaks, pool);
            }
        }
    }

    /**
     * Creates a whole new game.
     * Gets the reference to be used, checks it. If it doesn't exist, it creates the game with it.
     * Sends the result of the operation through the socket.
     */
    private synchronized void createGame() {
        ArrayList parameters = conn.getGameParemeters();
        boolean result = games.createGame(parameters);
        conn.sendResult(result);
    }


    /**
     * Join a previously created game.
     * Does check if the game exists, adds a player to it and sends the result of the operation.
     */
    private synchronized void joinGame() {
        String reference = conn.getReference();
        String id = conn.getID();
        int result = games.joinGame(reference, id);
        conn.sendResult(result);
    }


    /**
     * Retrieves and sends the private user cards through the socket.
     */
    private void sendPrivateCards() {
        String reference = conn.getReference();
        boolean exist = games.checkGameExists(reference);
        conn.sendResult(exist);
        if (exist) {
            String id = conn.getID();
            ArrayList<Card> cards = games.getPrivateCards(reference, id);
            conn.sendCards(cards);
        }
    }

    /**
     * Gets the common cards of the game and sends it to the user who requested it.
     */
    private void sendCommonCards() {
        String reference = conn.getReference();
        boolean exists = games.checkGameExists(reference);
        conn.sendResult(exists);
        if (exists) {
            ArrayList<Card> cards = games.getCommonCards(reference);
            conn.sendCards(cards);
        }
    }

    /**
     * Checks if it's the player's turn to bet.
     * If it is, it does bet.
     * It does a special 'flow'. Goes through the state machine to manipulate turn changes.
     * The order of execution is Menu -> games -> Game Phase -> Game. The phase it's a filter.
     */
    private void doBet() {
        String reference = conn.getReference();
        String id = conn.getID();
        boolean exist = games.checkGameExists(reference);
        conn.sendResult(exist);
        if (exist) {
            boolean isInGame = games.isPlayerInRound(reference, id);
            conn.sendResult(isInGame);
            if (isInGame) {
                boolean morePlayersLeft = games.checkMorePlayersLeft(reference);
                conn.sendResult(morePlayersLeft);
                if (morePlayersLeft) {
                    boolean mayBet = games.checkMayPlayerBet(reference, id);
                    conn.sendResult(mayBet);
                    if (mayBet) {
                        int amount = conn.getBet();
                        int chips = games.doBet(reference, id, amount);
                        conn.sendChips(chips);
                    }
                }
            }
        }
    }

    /**
     * Sends information about the winner of a game.
     * Checks if the games does exist and if this game has already chosen a winner.
     */
    private void sendWinner() {
        String reference = conn.getReference();
        String id = conn.getID();
        boolean exists = games.checkGameExists(reference);
        conn.sendResult(exists);
        if (exists) {
            boolean hasWinner = games.hasGameAWinner(reference);
            conn.sendResult(hasWinner);
            if (hasWinner) {
                ArrayList winner = games.getWinner(reference);
                conn.sendWinner(winner);
                boolean retirePlayer = conn.getRetire();
                boolean retired = false;
                if (retirePlayer) {
                    retired = games.retirePlayerFromGame(reference, id);
                    conn.sendResult(retired);
                }
            }
        }
    }

    /**
     * Sends information about the action of retiring a player from the game.
     */
    private void retirePlayer() {
        String reference = conn.getReference();
        String id = conn.getID();
        boolean exists = games.checkGameExists(reference);
        conn.sendResult(exists);
        if (exists) {
            boolean isPlaying = games.isPlayerInRound(reference, id);
            conn.sendResult(isPlaying);
            if (isPlaying) {
                boolean playerRetired = games.retirePlayerFromRound(reference, id);
                conn.sendResult(playerRetired);
            }
        }
    }

    /**
     * Main Switch which derives everything where it needs to be.
     * Here the socket's conn has already been opened!.
     */
    void selector() {
        int option = conn.getMenuOption();
        switch (option) {
            case MenuOptions.INFORMATION:
                sendInformation();
                break;
            case MenuOptions.CREATE_GAME:
                createGame();
                break;
            case MenuOptions.JOIN_GAME:
                joinGame();
                break;
            case GameOptions.GET_CARDS_PRIVATE:
                sendPrivateCards();
                break;
            case GameOptions.GET_CARDS_COMMON:
                sendCommonCards();
                break;
            case GameOptions.BET:
                doBet();
                break;
            case GameOptions.RETIRE:
                retirePlayer();
                break;
            case GameOptions.GET_WINNER:
                sendWinner();
                break;
            case -1:
                System.out.println("Problem with the conn. Error output by main switch.");
                break;
            default:
                System.out.println("Undefined error. Default option by main switch.");
                break;
        }
    }

    /**
     * Possible options for the menu to be selected.
     */
    private static final class MenuOptions {

        // Before the game starts
        private static final int INFORMATION = 0;
        private static final int CREATE_GAME = 1;
        private static final int JOIN_GAME = 2;

    }

    /**
     * Possible options once the game has started.
     */
    private static final class GameOptions {

        // Game options
        private static final int GET_CARDS_PRIVATE = 4;
        private static final int GET_CARDS_COMMON = 5;
        private static final int BET = 6;
        private static final int RETIRE = 7;
        private static final int GET_WINNER = 8;

    }
}