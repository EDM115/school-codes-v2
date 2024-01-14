/**
 * This class is used to launch the game.
 * To play this adventure game, create an instance of this class and call the "play" method.
 */
public class GameLauncher {

    /**
     * This is the main method of the game.
     * It creates an instance of the Game class and calls the "play" method.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Game jeu = new Game();

        jeu.play();
    }

    /**
     * useless constructor made for the sole purpose of the javadoc.
     */
    private GameLauncher() {}
}
