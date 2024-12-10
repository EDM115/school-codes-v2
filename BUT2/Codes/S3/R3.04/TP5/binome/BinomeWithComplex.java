package binome;

import complexe.Complexe;
import utils.WriterFacade;

/**
 * Binome object whithout roots
 */
class BinomeWithComplex extends Binome {
    /**
     * storage for the roots
     */
    private Complexe firstRoot, secondRoot;

    /**
     * Initialization sub-contracted to Binome
     * @param cx2 The x^2 coefficient
     * @param cx The x coefficient
     * @param cons The constant
     * @param delta The discriminant
     */
    protected BinomeWithComplex(double cx2, double cx, double cons, Discriminant delta) {
        super(cx2, cx, cons, delta);
    }

    /**
     * Computes the roots
     */
    public void computeRoots() {
        double imaginaryPart = Math.sqrt(-aDisc.value()) / (2.0 * a);
        firstRoot = new Complexe(-b / (2.0 * a), imaginaryPart);
        secondRoot = new Complexe(-b / (2.0 * a), -imaginaryPart);
    }

    /**
     * Displays the roots
     */
    public void displayRoots() {
        WriterFacade.getInstance().println("Deux racines complexes conjuguées : \n\tz1 = " + firstRoot);
        WriterFacade.getInstance().println("\tz2 = " + secondRoot);
    }
}
