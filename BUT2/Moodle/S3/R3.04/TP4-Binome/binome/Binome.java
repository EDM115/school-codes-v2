package binome;

/**
 *Binome is responsible to create binome suxh as : ax2 + bx + c
 */
public abstract class Binome {

   
    protected double a; // X2 coefficient 
	protected double b; // X coefficient/
    protected double c; // constant of the binome

	protected Discriminant aDisc; // The binome discriminant

    /**
     *  A standard constructor inherited by subclasses
     */
    protected Binome (double cx2, double cx, double cons, Discriminant delta) {
        a = cx2 ; b = cx ; c = cons ; aDisc = delta ;
    }

	public static Binome create(double a, double b, double c) {
        Discriminant d;         // a discriminant instance
        double delta;           // the discriminant value
        Binome aBin;            // the instancied Binome

        d = new Discriminant (a, b, c);
        delta = d.value();
        if ( delta == 0.0 ) {
            aBin = new BinomeWithOne (a, b, c, d);
        } else if ( delta > 0.0 ) {
            aBin = new BinomeWithTwo (a, b, c, d);
        } else {
            aBin = new BinomeWithout (a, b, c, d);
        }
        return aBin;
    } // end create


    /**
     *  Abstract operation that computes the roots
     */
    public abstract void computeRoots();

    /**
     *  Abstract operation that displays the roots
     */
    public abstract void displayRoots();

} // end Binome



