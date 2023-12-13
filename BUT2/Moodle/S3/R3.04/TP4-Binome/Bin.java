import binome.Binome;

/**
 * Testing class for the Binome package
 */
public class Bin {

    /**
     * The test launcher
     */
    public static void main(String args[]) {
     
        Binome abin;
        
        abin = Binome.create (1.0, 0.0, 1.0);
        abin.computeRoots();
        abin.displayRoots();
        abin = Binome.create (1.0, 0.0, -1.0);
        abin.computeRoots();
        abin.displayRoots();
        abin = Binome.create (1.0, 2.0, 1.0);
        abin.computeRoots();
        abin.displayRoots();
    } // end main

} // end Bin

