// (c) F. Merciol 
// Plus d'informations sur http://m3101.merciol.fr
package thread;
public class Semaphore {

    private int value;

    public Semaphore (int value) {
	if (value < 0)
	    throw new  IllegalArgumentException ("Negative value !");
	this.value = value;
    }

    public synchronized void p () {
	if (value <= 0)
	    try {
		wait ();
	    } catch (InterruptedException e) {
	    }
	value--;
    }

    public synchronized void v () {
	value++;
	notify ();
    }
}
