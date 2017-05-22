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
    public static boolean checkGameExists(String reference) {
        return GAMES.containsKey(reference);
    }
    
    /**
     * Checks if the game with the specified reference exists.
     * If it does, returns it.
     * @param reference Reference of the game to check.
     * @return Game if it does exist, null otherwise.
     */
    private static Game getGame(String reference) {
        if(checkGameExists(reference)) return (Game) GAMES.get(reference);
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
    
    /**
     * Erases the game from the common pool of games (HashLinkMap).
     * Checks if it exist before trying to delete it.
     * @param reference Reference of the game to delete.
     */
    public static void deleteGame(String reference) {
        if(GAMES.containsKey(reference)) {
            GAMES.remove(reference);
            System.out.println("Game with reference #" +reference +" has been deleted");
        }
    }
    
    /**
     * Adds a player to the selected game. Only if there's room left and the game's not started yet.
     * @param reference String. Reference of the game we want to join.
     * @return result of the operation. 1 player joined. -1 game does not exist. -2 game has started.
     */
    public static int joinGame(String reference, String id) {
        int result = 0;
        
        Game game = getGame(reference);
        if(game != null) result = game.joinNewPlayer(id) ? 1 : -2;
        else {
            System.out.println("Join rejected. There's no game #" +reference);
            result = -1;
        }
        
        return result;
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
    public static boolean checkMayPlayerBet(String reference, String id) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().checkMayPlayerBet(game, id);
        else return false;
    }
    
    /**
     * Check to see if there's a chosen winner in a game.
     * @param reference Reference of the game to check.
     * @return Bool. True if there's already a winner.
     */
    public static boolean hasGameAWinner(String reference) {
        Game game = getGame(reference);
        if(game != null) return game.hasGameAWinner();
        else return false;
    }
    
    /**
     * Check to see if there are more players playing the current round or this one is the only left.
     * @param reference Reference of the game to check.
     * @return True if there are more players left.
     */
    public static boolean checkMorePlayersLeft(String reference) {
        Game game = getGame(reference);
        if(game != null) return !game.isLastPlayerInRound();
        else return false;
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
     * Checks the game with x reference and retrieves the user with id i private cards.
     * @param reference Reference of the game the player is playing on.
     * @param id Unique ID to know from which player get its cards.
     * @return AL<Card>. Private player cards.
     */
    public static ArrayList<Card> getPrivateCards(String reference, String id) {
        Game game = getGame(reference);
        if(game != null) return game.getPrivateCards(id);
        else return null;
    }
    
    /**
     * Checks if the game exists. Gets the common cards for all the players.
     * @param reference Reference of the game where the player is playing in.
     * @return AL<Card>. Common player cards.
     */
    public static ArrayList<Card> getCommonCards(String reference) {
        Game game = getGame(reference);
        if(game != null) return game.getCommonCards();
        else return null;
    }
    
    /**
     * Does the action of a bet. Here it's already been checked if the player may.
     * Sets the player action to used.
     * @param reference Reference of the game the player is in.
     * @param id ID of the player who's betting.
     * @param amount Amount of chips to bet.
     * @return Int. Total amount of the pool until now (after the bet has been added). -1 if the game does not exist.
     */
    public static int doBet(String reference, String id, int amount) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().doBet(game, id, amount);
        else return -1;
    }
    
    /**
     * Retrieves information about the winner.
     * @param reference Reference of the game to get the winner from.
     * @return AL. [0] = String. Winner ID. [1] = String. Winner play. [2] = Int. winner's number of chips.
     */
    public static ArrayList getWinner(String reference) {
        Game game = getGame(reference);
        if(game != null && hasGameAWinner(reference)) return game.getWINNER();
        return null;
    }
    
    /**
     * Action to retire correctly a player from the current round.
     * @param reference Reference of the game to check.
     * @param ID personal ID of the player to retire.
     * @return Result of the operation. True if he was correctly retired and is no longer in this round.
     */
    public static boolean retirePlayerFromRound(String reference, String ID) {
        Game game = getGame(reference);
        if(game != null) return game.getPhase().retirePlayerFromRound(game, ID);
        else return false;
    }
    
    /**
     * Retires a player from the game entirely. To be done when he has no longer chips.
     * @param reference Reference of the game to get.
     * @param ID ID of the player to retire.
     * @return Result of the operation. Was this player retired?
     */
    public static boolean retirePlayerFromGame(String reference, String ID) {
        Game game = getGame(reference);
        if(game != null) return game.retirePlayerFromGame(ID);
        else return false;
    }
}
