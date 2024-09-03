package pipe_foundations.example;

import pipe_foundations.Pipe;
import pipe_foundations.Sink;

public class ExampleSink extends Sink<String> {
    public ExampleSink(Pipe<String> input) {
        super(input);
    }

    @Override
    public void takeFrom(Pipe<String> pipe) {
        try {
            String in;
            while ((in = pipe.nextOrNullIfEmptied()) != null) {
                System.out.println(in);
                delayForDebug(300);
            }
            System.out.println("sink finished");
        } catch (InterruptedException e) {
            System.err.println("interrupted");
            e.printStackTrace();
        } finally {
            System.out.close();
        }
    }
}
