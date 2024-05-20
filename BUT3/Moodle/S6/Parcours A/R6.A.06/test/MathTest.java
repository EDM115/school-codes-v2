public class MathTest extends TestCase {
	
	protected double fValue1;
	protected double fValue2;
	
	public MathTest(String name){
		super(name);
	}
	
	protected void setUp() {
		fValue1= 2.0;
		fValue2= 3.0;
	}
	public void runTest() {
		double result= fValue1 + fValue2;
		assert(result == 5.0);
	}
}
