package poly.ig4.maven;

import java.util.logging.Logger;
import java.util.logging.Level;

public class HelloWorld {
    private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());

    public static void main(String... args) {
        logger.log(Level.INFO, "Hello World !!!");
    }
}
