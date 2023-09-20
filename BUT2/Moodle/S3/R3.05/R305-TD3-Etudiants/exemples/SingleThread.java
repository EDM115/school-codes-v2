// (c) F. Merciol 
// Plus d'informations sur http://m3101.merciol.fr
package thread;
public class SingleThread {
		
    private Thread lastThread;
    public void stopSingle () {
	lastThread = null;
    }
    public void startSingle () {
	(new Thread () {
		public void run () {
		    for (lastThread = currentThread ();
			 lastThread == currentThread ();
			 ) {
			System.err.println ("Here is "+getName ());
			pause (1);
		    }
		}
	    }).start ();
    }

    static public void pause (int seconds) {
	try {
	    Thread.sleep (seconds*1000);
	} catch (InterruptedException e) {
	}
    }
    static public void main (String[] arg) {
	SingleThread activeObjet = new SingleThread ();
	for (int i = 0; i < 4 ; i++) {
	    activeObjet.startSingle ();
	    pause (1);
	}
	activeObjet.stopSingle ();
    }
}
