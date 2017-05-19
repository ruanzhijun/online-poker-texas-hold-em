package network;

/**
 * Hook to be called when the client closes to shut all the possible connections opened.
 * @author Mario Codes
 */
public class NetShutdownHook extends Thread {
    /**
     * Action to run.
     * To call it -> Runtime.getRuntime().addShutdownHook(new ShutdownHook());
     */
    @Override
    public void run() {
        System.out.println("SU! Close!");
        Connection.close();
    }
}
