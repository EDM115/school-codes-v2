package binome;

/**
 *  Handle the computation of the discriminant
 */
public class Discriminant {

    /** the evaluated discrimenant */
    private double delta;

    /**
     *  Dicriminant constructor: computes his value
     */
    public Discriminant(double a, double b, double c) {
        delta = (b * b) - (4.0 * a * c);
    }

    /**
     *  Return the discriminant value
     */
    public double value() {
        return delta;
    }

} // end Discriminant

