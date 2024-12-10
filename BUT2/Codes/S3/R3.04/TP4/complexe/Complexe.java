package complexe;

/**
 * Implementation of a complex number
 */
public class Complexe {
	/**
	 * The real part
	 */
    private double real;

	/**
	 * The imaginary part
	 */
    private double imaginary;

	/**
	 * Constructor of a complex number
	 * @param real The real part
	 * @param imaginary The imaginary part
	 */
    public Complexe(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

	/**
	 * Getter for the real part
	 * @return The real part
	 */
    public double getReal() {
        return real;
    }

	/**
	 * Setter for the real part
	 * @param real The real part
	 */
    public void setReal(double real) {
        this.real = real;
    }

	/**
	 * Getter for the imaginary part
	 * @return The imaginary part
	 */
    public double getImaginary() {
        return imaginary;
    }

	/**
	 * Setter for the imaginary part
	 * @param imaginary The imaginary part
	 */
    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

	/**
	 * Addition of two complex numbers
	 * @param other The other complex number
	 * @return The sum of the two complex numbers
	 */
    public Complexe add(Complexe other) {
        return new Complexe(this.real + other.real, this.imaginary + other.imaginary);
    }

	/**
	 * Subtraction of two complex numbers
	 * @param other The other complex number
	 * @return The difference of the two complex numbers
	 */
    public Complexe subtract(Complexe other) {
        return new Complexe(this.real - other.real, this.imaginary - other.imaginary);
    }

	/**
	 * Multiplication of two complex numbers
	 * @param other The other complex number
	 * @return The product of the two complex numbers
	 */
    public Complexe multiply(Complexe other) {
        double newReal = this.real * other.real - this.imaginary * other.imaginary;
        double newImaginary = this.real * other.imaginary + this.imaginary * other.real;
        return new Complexe(newReal, newImaginary);
    }

	/**
	 * Division of two complex numbers
	 * @param other The other complex number
	 * @return The quotient of the two complex numbers
	 */
    public Complexe divide(Complexe other) {
        Complexe conjugate = new Complexe(other.real, -other.imaginary);
        Complexe numerator = this.multiply(conjugate);
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        return new Complexe(numerator.real / denominator, numerator.imaginary / denominator);
    }

	/**
	 * String representation of a complex number
	 * @return The string representation of the complex number
	 */
    @Override
    public String toString() {
        if (imaginary >= 0) {
            return real + " + " + imaginary + "i";
        } else {
            return real + " - " + (-imaginary) + "i";
        }
    }
}
