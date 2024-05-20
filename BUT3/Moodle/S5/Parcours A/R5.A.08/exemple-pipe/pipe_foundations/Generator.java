package pipe_foundations;

public abstract class Generator<T> extends ThreadedRunner {
    protected Pipe<T> output;

    public Generator(Pipe<T> output) {
        this.output = output;
    }

    @Override
    public void run() {
        generateInto(output);
    }

    public abstract void generateInto(Pipe<T> pipe);
}
