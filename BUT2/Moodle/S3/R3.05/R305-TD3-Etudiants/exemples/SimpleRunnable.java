// (c) F. Merciol 
// Plus d'informations sur http://m3101.merciol.fr
package thread;
public class SimpleRunnable implements Runnable {

    public void run () {
	System.err.println ("Here is "+Thread.currentThread ().getName ());
    }

    static public void main (String[] arg) {
	System.err.println ("new");
	Runnable runnable = new SimpleRunnable ();
	Thread thread = new Thread (runnable);

	System.err.println ("start");
	thread.start ();
    }
}

