package binome;

/**
 * Binome object whit one root
 */
class BinomeWithOne extends Binome {
    /**
     * storage for the root
     */
    private double theRoot;

    /**
     * Initialization sub-contracted to Binome
     * @param cx2 The x^2 coefficient
     * @param cx The x coefficient
     * @param cons The constant
     * @param delta The discriminant
     */
    protected BinomeWithOne(double cx2,double cx, double cons, Discriminant delta) {
        super(cx2, cx, cons, delta);
	}

    /**
     * Computes the roots
     */
    public void computeRoots() {
        theRoot = (-b / (2 * a));
    }

    /**
     * Displays the roots
     */
    public void displayRoots() {
        System.out.println("Une racine double : " + theRoot);
    }
}
