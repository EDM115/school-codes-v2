// (c) F. Merciol 
// Plus d'informations sur http://m3101.merciol.fr
//package thread;
public class SimpleThread extends Thread {

    public void run () {
	for (;;) {
	    System.err.println ("Here is "+getName ());
	    try {
		sleep (1000);
	    } catch (InterruptedException e) {
	    }
	}
    }

    static public void main (String[] arg) {
	System.err.println ("new");
	Thread thread = new SimpleThread ();

	System.err.println ("start");
	thread.run ();

	for (;;) {
	    try {
		sleep (1000);
	    } catch (InterruptedException e) {
	    }
	    System.err.println ("ici main");
	}
    }
}
