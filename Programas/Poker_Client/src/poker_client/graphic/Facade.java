package poker_client.graphic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import network.Connection;

/**
 * Facade design pattern. Used to separate and code from MVC and encapsulate it better.
 * @author Mario Codes
 */
public class Facade {
    private final static String REGEX_ALPHANUMERIC = "^[a-zA-Z0-9_]+$"; // Only alphanumerics, underscore and cannot be empty.
    private final static String REGEX_NUMERIC_PLAYERS = "[2-9]"; // Only numbers from 2 to 9. Inclusives.
    
    
    private static boolean checkRegex(String regex, String check) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(check);
        return m.matches();
    }
    
    public static int createGame(String playerID, String reference, String players) {
        int result = 0;
        
        if(checkRegex(REGEX_ALPHANUMERIC, reference)) {
            if(checkRegex(REGEX_NUMERIC_PLAYERS, players)) {
                int numberPlayers = Integer.parseInt(players);
                if(Connection.createGame(reference, playerID, numberPlayers)) result = 1;
                else result = -3;
            } else result = -2;
        } else result = -1;
        
        return result;
    }
}
