package utils;

/**
 * Facade for writing to the console
 */
public class WriterFacade {
	/**
	 * The unique instance of the facade
	 */
    private static WriterFacade instance;

	/**
	 * Private constructor
	 */
    private WriterFacade() {}

	/**
	 * Getter for the unique instance of the facade
	 * @return The unique instance of the facade
	 */
    public static WriterFacade getInstance() {
        if (instance == null) {
            instance = new WriterFacade();
        }
        return instance;
    }

	/**
	 * Prints a message to the console
	 * @param message The message to print
	 */
    public void println(String message) {
        System.out.println(message);
    }
}
