package pipe_foundations;

public abstract class SimpleSink<T> extends Sink<T> {
    public SimpleSink(Pipe<T> input) {
        super(input);
    }

    @Override
    public void takeFrom(Pipe<T> pipe) {
        try {
            // any required setup can be done in the constructor, so not providing a hook
            T in;
            while ((in = pipe.nextOrNullIfEmptied()) != null) {
                handle(in);
            }
        } catch (InterruptedException e) {
            // TODO handle properly, using advice in http://www.ibm.com/developerworks/java/library/j-jtp05236/
            System.err.println("interrupted");
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected abstract void handle(T in);

    /**
     * override this if you need to do anything, like closing an opened file
     */
    protected void cleanup() {
    }
}
