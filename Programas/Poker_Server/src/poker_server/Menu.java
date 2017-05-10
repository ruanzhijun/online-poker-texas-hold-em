package poker_server;

/**
 * Static class to encapsulate menus and options selection.
 * @author Mario Codes
 * @version 0.0.1 Just created. Setting Basics.
 */
public class Menu {
    private static final int CREATE_GAME = 1; // First Menu, before the game starts.
    private static final int JOIN_GAME = 2;
    
    private static final int BET = 4; // Second Menu, once the game´s started.
    private static final int GET_CARDS_COMMON = 5;
    private static final int GET_CARDS_PRIVATE = 6;
    private static final int RETIRE = 7;
    
    static void selector(int option) {
        switch(option) {
            case 1:
                break;
            case 2:
                break;
            case 4: case 5: case 6: case 7:
                break;
        }
    }
}
