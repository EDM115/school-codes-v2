package binome;

/**
 * Factory for binomial equations
 */
public class BinomeFactory {
    /**
     * Creates a binomial equation based on the coefficients a, b, and c.
     * @param a The coefficient of x^2
     * @param b The coefficient of x
     * @param c The constant term
     * @return a Binome object representing the binomial equation
     */
    public Binome create(double a, double b, double c) {
        Discriminant d = new Discriminant(a, b, c);
        double delta = d.value();

        if (delta == 0.0) {
            return new BinomeWithOne(a, b, c, d);
        } else if (delta > 0.0) {
            return new BinomeWithTwo(a, b, c, d);
        } else {
            return new BinomeWithComplex(a, b, c, d);
        }
    }

    /**
     * Useless constructor, for javadoc
     */
    public BinomeFactory() {}
}
