package binome;

/**
 * Represents a binomial equation of the form ax^2 + bx + c = 0.
 * The Binome class provides methods to create different types of binomial equations
 * based on the discriminant value.
 */
public abstract class Binome {
    /**
     * x^2 coefficient
     */
    protected double a;

    /**
     * x coefficient
     */
	protected double b;

    /**
     * Constant of the binome
     */
    protected double c;

    /**
     * The binome discriminant
     */
	protected Discriminant aDisc;

    /**
     * A standard constructor inherited by subclasses
     * @param cx2 The x^2 coefficient
     * @param cx The x coefficient
     * @param cons The constant
     * @param delta The discriminant
     */
    protected Binome(double cx2, double cx, double cons, Discriminant delta) {
        a = cx2;
        b = cx;
        c = cons;
        aDisc = delta;
    }

    /**
     * Creates a binomial equation based on the coefficients a, b, and c.
     * 
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @return a Binome object representing the binomial equation
     */
    public static Binome create(double a, double b, double c) {
        Discriminant d;
        double delta;
        Binome aBin;

        d = new Discriminant(a, b, c);
        delta = d.value();
        if (delta == 0.0) {
            aBin = new BinomeWithOne(a, b, c, d);
        } else if (delta > 0.0) {
            aBin = new BinomeWithTwo(a, b, c, d);
        } else {
            //aBin = new BinomeWithout(a, b, c, d);
            aBin = new BinomeWithComplex(a, b, c, d);
        }

        return aBin;
    }

    /**
     * Abstract operation that computes the roots
     */
    public abstract void computeRoots();

    /**
     * Abstract operation that displays the roots
     */
    public abstract void displayRoots();
}
