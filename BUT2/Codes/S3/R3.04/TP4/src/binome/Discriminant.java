package binome;

/**
 * Handle the computation of the discriminant
 */
public class Discriminant {
    /**
     * the evaluated discrimenant
     */
    private double delta;

    /**
     * Dicriminant constructor: computes his value
     * @param a The x^2 coefficient
     * @param b The x coefficient
     * @param c The constant
     */
    public Discriminant(double a, double b, double c) {
        delta = (b * b) - (4.0 * a * c);
    }

    /**
     * Return the discriminant value
     * @return The discriminant value
     */
    public double value() {
        return delta;
    }
}
