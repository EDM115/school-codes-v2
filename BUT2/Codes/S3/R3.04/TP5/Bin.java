import binome.Binome;
import binome.BinomeFactory;

/**
 * Testing class for the Binome package
 */
public class Bin {
    /**
     * The test launcher
     * @param args The command line arguments
     */
    public static void main(String args[]) {
        BinomeFactory factory = new BinomeFactory();
        Binome abin;

        abin = factory.create(1.0, 0.0, 1.0);
        abin.computeRoots();
        abin.displayRoots();
        abin = factory.create(1.0, 0.0, -1.0);
        abin.computeRoots();
        abin.displayRoots();
        abin = factory.create(1.0, 2.0, 1.0);
        abin.computeRoots();
        abin.displayRoots();
    }

    /**
     * Useless constructor, for javadoc
     */
    public Bin() {}
}
