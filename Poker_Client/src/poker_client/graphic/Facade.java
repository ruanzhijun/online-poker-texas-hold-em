package poker_client.graphic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import network.Connection;

/**
 * Facade design pattern. Used to separate and code from MVC and encapsulate it better.
 * @author Mario Codes
 */
public class Facade {
    private final static String REGEX_REFERENCE = "^[a-zA-Z0-9_]+$"; // Only alphanumerics, underscore and cannot be empty.
    private final static String REGEX_NUMERIC_PLAYERS = "[2-9]"; // Only numbers from 2 to 9. Inclusives.
    
    /**
     * Compares a string against a regex pattern.
     * @param regex Pattern the String is compared against.
     * @param check String to check.
     * @return True if the String does match against the pattern. Correct String.
     */
    private static boolean checkRegex(String regex, String check) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(check);
        return m.matches();
    }
    
    /**
     * Operations to create a game.
     * Checks the reference vs a regex pattern. Checks the number of players.
     * Sends the info to the server. Creates the game.
     * Returns result of the operation.
     * @param playerID ID of the player who is creating the game.
     * @param reference Unique reference of the game.
     * @param players Number of players the game will have.
     * @return Result of the operation. 1 correctly created. 0 internal error, shouldn't be achievable. -1 Connection troubles, server no reachable. -2 Reference does not match regex. -3 Number of players does not match regex (2-9). -4 Reference currently in use, try another one.
     */
    public static int createGame(String playerID, String reference, String players) {
        int result = 0;
        
        if(checkRegex(REGEX_REFERENCE, reference)) {
            if(checkRegex(REGEX_NUMERIC_PLAYERS, players)) {
                int numberPlayers = Integer.parseInt(players);
                int created = Connection.createGame(reference, playerID, numberPlayers);
                
                if(created == -1) result = -4;
                else {
                    if(created == 0) result = -1;
                    else if(created == 1) result = 1;
                }
            } else result = -3;
        } else result = -2;
        
        System.out.println(result);
        return result;
    }
    
    public static int joinGame(String playerID, String reference) {
        int result = 0;
        
        if(checkRegex(REGEX_REFERENCE, reference)) {
            int created = Connection.joinGame(reference, playerID);
            if(created == 1) result = 1;
            else {
                if(created == 0) result = -1;
                else if(created == -1) result = -3;
                else if(created == -2) result = -4;
            }
        } else result = -2;
        
        return result;
    }
}
