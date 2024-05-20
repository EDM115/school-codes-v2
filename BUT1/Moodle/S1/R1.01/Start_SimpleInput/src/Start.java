import java.lang.reflect.Method;

public class Start {

		public static void msg (String msg) {
				System.err.println (msg);
				System.exit (0);
		}

		public static void main (String [] arg) {
				try {
						Class<?> c = Start.class.getClassLoader().loadClass (arg[0]);
						Method m = c.getDeclaredMethod ("principal");
						try {
								Object o = c.newInstance ();
								m.invoke (o);
						} catch (Exception e) {
								e.printStackTrace();
						}
				} catch (ArrayIndexOutOfBoundsException e) {
						msg ("Usage: java Start MonProg");
				} catch (ClassNotFoundException e) {
						msg ("Error: "+arg[0]+" class not found!");
				} catch (NoSuchMethodException e) {
						msg ("Error: method principal not found!");
				}
		}
}
