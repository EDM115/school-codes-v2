package pipe_foundations;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

// replaceable with BlockingQueue<T>? That doesn't support closing, though
public class PipeImpl<T> implements Pipe<T> {
    private Queue<T> buffer = new LinkedList<T>();
    private boolean isOpenForWriting = true;
    private boolean hasReadLastObject = false;

    @Override
    public synchronized boolean put(T obj) {
        if (!isOpenForWriting) {
            throw new RuntimeException(new IOException("pipe is closed; cannot write to it"));
        } else if (obj == null) {
            throw new IllegalArgumentException("cannot put null in pipe; null is reserved for pipe-empty sentinel value");
        }

        boolean wasAdded = buffer.add(obj);
        notify();
        //System.out.println("added to pipe: " + (obj==null?"<null>":obj.toString()));
        return wasAdded;
    }

    @Override
    // not using next() and willHaveNext() because a currently-empty pipe might be
    //  closed after the willHaveNext() check, causing next() to wait forever
    // not using an exception because would require consumers to write unidiomatic `while(true)`
    // not using an Option because there is no standard Option and reimplementing it is too annoying
    public synchronized T nextOrNullIfEmptied() throws InterruptedException {
        if (hasReadLastObject) {
            throw new NoSuchElementException("pipe is closed and empty; will never contain any further values");
        }

        while (buffer.isEmpty()) {
            wait(); // pipe empty - wait
        }

        T obj = buffer.remove();
        if (obj == null) { // will be null if it's the last element
            hasReadLastObject = true;
        }
        return obj;
    }

    @Override
    public synchronized void closeForWriting() {
        isOpenForWriting = false;
        buffer.add(null);
        notify();
    }
}

