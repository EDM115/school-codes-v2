import java.util.*;

public class SonarQubeIssuesExample {

    // 1. Hardcoded credentials (Security Vulnerability)
    private String dbUsername = "admin";
    private String dbPassword = "password123"; // Sensitive data in code

    // 2. Unused variable (Code Smell)
    private int unusedVariable;

    // 3. Dead store (Code Smell) - 'temp' value is never used
    public void deadStoreExample() {
	int temp = 5;
	temp = 10; // This assignment is never used
    }

    // 4. Complexity Issue (Code Smell) - High Cyclomatic Complexity
    public String highComplexityMethod(int x) {
	if (x > 0) {
	    if (x % 2 == 0) {
		if (x > 10) {
		    return "Even and greater than 10";
		} else {
		    return "Even and less than 10";
		}
	    } else {
		if (x > 10) {
		    return "Odd and greater than 10";
		} else {
		    return "Odd and less than 10";
		}
	    }
	} else {
	    return "Non-positive number";
	}
    }

    // 5. Empty Catch Block (Code Smell)
    public void emptyCatchBlock() {
	try {
	    int result = 10 / 0;
	} catch (Exception e) {
	    // Empty catch block is a bad practice
	}
    }

    // 6. Ignoring InterruptedException (Bug)
    public void ignoringInterruptedException() {
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    // Ignoring InterruptedException is risky
	}
    }

    // 7. Public static mutable field (Code Smell)
    public static List<String> mutableList = new ArrayList<>();

    // 8. Deprecated API usage (Code Smell)
    @SuppressWarnings("deprecation")
    public void useDeprecatedMethod() {
	Date now = new Date();
	now.getYear(); // getYear() is deprecated
    }

    // 9. Lack of parameter validation (Bug)
    public void noParameterValidation(String input) {
	System.out.println(input.toUpperCase()); // Could throw NullPointerException if input is null
    }

    // 10. Performance issue - Inefficient List search
    public boolean inefficientListSearch(List<String> list, String target) {
	for (int i = 0; i < list.size(); i++) {  // Linear search is inefficient for large lists
	    if (list.get(i).equals(target)) {
		return true;
	    }
	}
	return false;
    }

    // 11. Using raw types (Code Smell)
    public List getRawList() {  // No generics specified, leads to type safety issues
	return new ArrayList();
    }

    // 12. Poor naming convention (Code Smell)
    public void bAdNaMeDMethod() {
	// Method name does not follow standard naming conventions
    }

    // 13. Unnecessary object creation (Performance Issue)
    public void unnecessaryObjectCreation() {
	String s = new String("Hello"); // Creates a new String object unnecessarily
    }

    // 14. Code duplication (Code Smell)
    public void duplicatedCode() {
	System.out.println("Duplicated code example"); // Duplicated code block
	System.out.println("Duplicated code example"); // Duplicated code block
    }

    // 15. Commented out code (Code Smell)
    public void commentedOutCode() {
	// int x = 100; // Commented out code is a bad practice
    }
}
