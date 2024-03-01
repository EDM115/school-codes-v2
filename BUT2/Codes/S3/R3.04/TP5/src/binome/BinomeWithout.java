package binome;

import utils.WriterFacade;

/**
 * Binome object whithout roots
 */
class BinomeWithout extends Binome {
    /**
     * Initialization sub-contracted to Binome
     * @param cx2 The x^2 coefficient
     * @param cx The x coefficient
     * @param cons The constant
     * @param delta The discriminant
     */
    protected BinomeWithout(double cx2,double cx, double cons, Discriminant delta) {
        super(cx2, cx, cons, delta);
    }

    /**
     * Computes the roots
     */
    public void computeRoots() {
        // nothing to do here
    }

    /**
     * Displays the roots
     */
    public void displayRoots() {
        WriterFacade.getInstance().println("Pas de racine");
    }
}
