package pipe_foundations;

public abstract class ThreadedRunner implements Runnable {
	private boolean isStarted = false;

    @Override
    abstract public void run();

	public void start(){
	    if(! isStarted){
	        isStarted = true;
	        Thread thread = new Thread(this);
	        thread.start();
	      }
	}

	public void stop(){
		isStarted = false;
	}

    /**
     * make your thread sleep so you can confirm that other threads still run in the meantime
     * for debugging purposes only
     */
     protected void delayForDebug(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
