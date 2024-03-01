package binome;

/**
 * Binome object whit two roots
 */
class BinomeWithTwo extends Binome {
    /**
     * storage for the roots
     */
    private double firstRoot, secondRoot;

    /**
     * Initialization sub-contracted to Binome
     * @param cx2 The x^2 coefficient
     * @param cx The x coefficient
     * @param cons The constant
     * @param delta The discriminant
     */
    protected BinomeWithTwo(double cx2,double cx, double cons, Discriminant delta) {
        super(cx2, cx, cons, delta);
    }

    /**
     * Computes the roots
     */
    public void computeRoots() {
        firstRoot = (-b + Math.sqrt(aDisc.value()) / (2.0 * a));
        secondRoot = (-b - Math.sqrt(aDisc.value()) / (2.0 * a));
    }

    /**
     * Displays the roots
     */
    public void displayRoots() {
        System.out.println("Deux racines distinctes : \n\tx1 = " + firstRoot);
        System.out.println("\tx2 = " + secondRoot);
    }
}
