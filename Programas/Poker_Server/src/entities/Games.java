package entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to store and manipulate the multiple games which can be executed simultaneously.
 * @author Mario Codes
 * @version 0.0.3 Added methods to bet and get winner.
 */
public class Games {
    private static final HashMap GAMES = new <String, Game>HashMap(); /* Place to store the multiple games. I must access here to retrieve them with their specific key. */
    
    /**
     * Checks if a game with the reference already exists.
     * @param reference Reference to check.
     * @return Boolean. True if a game with this reference already exists.
     */
    public static boolean gameExists(String reference) {
        return GAMES.containsKey(reference);
    }
    
    /**
     * Checks if the game with the specified reference exists.
     * If it does, returns it.
     * @param reference Reference of the game to check.
     * @return Game if it does exist, null otherwise.
     */
    private static Game getGame(String reference) {
        if(gameExists(reference)) return (Game) GAMES.get(reference);
        else return null;
    }
    
    /**
     * First it checks if a game with this reference already exists, if it does not then it creates it and
     * saves it into the local HashMap.
     * @param reference String. ID to locate the game. Unique.
     * @param totalPlayers int. Number of fixed players to know when to start the game.
     * @return Status of the operation. True if created correctly.
     */
    public static boolean createGame(ArrayList parameters) {
        boolean result = false;
        
        String reference = (String) parameters.get(0);
        String id = (String) parameters.get(1);
        int totalPlayers = (int) parameters.get(2);
        
        if(getGame(reference) == null) {
            GAMES.put(reference, new Game(reference, id, totalPlayers));
            System.out.println(id +" has created the game #" +reference +". 1/" +totalPlayers +" player(s). " +GAMES.size() +" simultaneous games.");
            result = true;
        } else System.out.println("Game rejected. This # already exists.");
        
        return result;
    }
    
    // todo: develop it. Send a signal to the clients so they disconnect themselves. Then stop and delete the game from HM.
    public static void deleteGame(String reference) {
        throw new UnsupportedOperationException("To be done. Will need checks and a correct stop.");
    }
    
    /**
     * Adds a player to the selected game. Only if there's room left and the game's not started yet.
     * @param reference String. Reference of the game we want to join.
     * @return Boolean. Result of the operation. False if game does not exist.
     */
    public static boolean joinGame(String reference, String id) {
        boolean result = false;
        
        Game game = getGame(reference);
        if(game != null) result = game.joinNewPlayer(id);
        else System.out.println("Join rejected. There's no game #" +reference);
        
        return result;
    }
    
    
    /**
     * Gets the phase from the game with the specified reference.
     * Used by the secondary thread.
     * @param reference Games reference we want to obtain it's phase from.
     * @return String. Phase this game is currently at.
     */
    public static String getPhase(String reference) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().toString();
        else return null;
    }
    
    /**
     * Checks inside the game specified, the user ID to see if it's this user's turn to speak.
     * @param reference Reference of the game we want to check.
     * @param id ID of the player to check.
     * @return Boolean. True if he speaks. False if not such game or does not speak.
     */
    public static boolean isPlayersTurn(String reference, String id) {
        Game game = getGame(reference);
        if(game != null) return game.isPlayersTurn(id);
        else return false;
    }
    
    /**
     * Checks the game with x reference and retrieves the user with id i private cards.
     * @param reference Reference of the game the player is playing on.
     * @param id Unique ID to know from which player get its cards.
     * @return AL<Card>. Private player cards.
     */
    public static ArrayList<Card> getPrivateCards(String reference, String id) {
        Game game = getGame(reference);
        if(game != null) return game.getPlayerCards(id);
        else return null;
    }
    
    /**
     * Checks if the game exists. Gets the common cards for all the players.
     * @param reference Reference of the game where the player is playing in.
     * @return AL<Card>. Common player cards.
     */
    public static ArrayList<Card> getCommonCards(String reference) {
        Game game = getGame(reference);
        if(game != null) return game.getTableCards();
        else return null;
    }
    
    /**
     * Checks if this player is still playing in this round.
     * @param reference Reference of the game to check.
     * @param id ID of the player to check.
     * @return Boolean. True if this player is still playing this current round.
     */
    public static boolean isPlayerInRound(String reference, String id) {
        Game game = getGame(reference);
        if(game != null) return game.isPlayerInRound(id);
        else return false;
    }
    
    /**
     * Check if this player may bet right now. This means it's his turn and he didn't speak yet.
     * @param reference String. Reference of the game to check.
     * @param id String. ID of the player who tries to speak.
     * @return Boolean. States if the player may bet or not.
     */
    public static boolean mayBet(String reference, String id) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().mayBet(game, id);
        else return false;
    }
    
    /**
     * Does the action of a bet. Here it's already been checked if the player may.
     * Sets the player action to used.
     * @param reference Reference of the game the player is in.
     * @param id ID of the player who's betting.
     * @param amount Amount of chips to bet.
     * @return Int. Total amount of the pool until now (after the bet has been added). -1 if the game does not exist.
     */
    public static int bet(String reference, String id, int amount) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().bet(game, id, amount);
        else return -1;
    }
    
    /**
     * Check to see if there's a chosen winner in a game.
     * @param reference Reference of the game to check.
     * @return Bool. True if there's already a winner.
     */
    public static boolean hasWinner(String reference) {
        Game game = getGame(reference);
        if(game != null) return game.hasWinner();
        else return false;
    }
    
    /**
     * Retrieves information about the winner.
     * @param reference Reference of the game to get the winner from.
     * @return AL. [0] = String. Winner ID. [1] = String. Winner play. [2] = Int. winner's number of chips.
     */
    public static ArrayList getWinner(String reference) {
        Game game = getGame(reference);
        if(game != null && hasWinner(reference)) return game.getWINNER();
        return null;
    }
    
    public static boolean checkPlayerPlaying(String reference, String ID) {
        Game game = getGame(reference);
        if(game != null) return game.checkPlayerPlaying(ID);
        else return false;
    }
    
    public static boolean retirePlayer(String reference, String ID) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().retirePlayer(game, ID);
        else return false;
    }
    
    public static boolean isMorePlayersLeft(String reference) {
        Game game = getGame(reference);
        if(game != null) return !game.isLastPlayerLeft();
        else return false;
    }
}
