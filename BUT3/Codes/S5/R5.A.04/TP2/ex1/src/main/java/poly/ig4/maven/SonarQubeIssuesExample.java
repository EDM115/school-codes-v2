package poly.ig4.maven;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SonarQubeIssuesExample {
    private static final Logger logger = Logger.getLogger(SonarQubeIssuesExample.class.getName());

    public String setDbCredentials(String username, String password) {
        String dbUsername = username;
        String dbPassword = password;

        return dbUsername + " - " +dbPassword;
    }

    public String highComplexityMethod(int x) {
        if (x <= 0) {
            return "Non-positive number";
        }
        
        String parity = (x % 2 == 0) ? "Even" : "Odd";
        String size = (x > 10) ? "greater than 10" : "less than 10";
        
        return parity + " and " + size;
    }

    public int emptyCatchBlock() {
        try {
            return 10 / 1;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
            return -1;
        }
    }

    public void ignoringInterruptedException() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "InterruptedException occurred", e);
            Thread.currentThread().interrupt();
        }
    }

    private static final List<String> mutableList = Collections.unmodifiableList(new ArrayList<>());

    public static List<String> getMutableList() {
        return mutableList;
    }

    public int useDeprecatedMethod() {
        Calendar now = Calendar.getInstance();

        return now.get(Calendar.YEAR);
    }

    public void noParameterValidation(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
    
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, input.toUpperCase());
        }
    }

    public boolean inefficientListSearch(List<String> list, String target) {
        Set<String> set = new HashSet<>(list);
        return set.contains(target);
    }

    public List<String> getRawList() {
        return new ArrayList<>();
    }

    public void duplicatedCode() {
        logger.log(Level.INFO, "Duplicated code example");
    }
}
