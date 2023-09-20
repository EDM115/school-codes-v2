// (c) F. Merciol 
// Plus d'informations sur http://m3101.merciol.fr
package thread;
public class AnonymousThread {
    static public void main (String[] arg) {
	System.err.println ("new & start");

	(new Thread () {
		public void run () {
		    System.err.println ("Here is "+getName ());
		}
	    }).start ();
    }
}
