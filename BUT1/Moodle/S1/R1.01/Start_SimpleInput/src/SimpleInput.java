/**
 * Class SimpleInput - input class for input of simple input types
 * eg. int, char, String,float or boolean.
 * 
 * @author: Bruce Quig
 * @author: Michael Kolling
 * @author: Michel Adam
 *
 * @version: 2.0
 * Date:     26.12.2020
 */
import java.util.Scanner;
public class SimpleInput {
    // instance variables
    static final String STRING_TITLE = "Enter a String";
    static final String CHAR_TITLE = "Enter a char";
    static final String INT_TITLE = "Enter an int";
    static final String BOOLEAN_TITLE = "Select True or False";
    static final String FLOAT_TITLE = "Enter a float";
    static final String TRUE = "True";
    static final String FALSE = "False";
    static final String EMPTY_STRING = "";
    static Scanner sc = new Scanner(System.in);
    
    /**
     ** String input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog    
     ** @return String input from the user.
     **/
    public static String getString(String prompt) {
        String result;
        System.out.print (prompt);
        result = sc.nextLine();
        while (result.length() == 0){
            result = sc.nextLine();
        }
        return result;
    }


    /**
     ** char input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog  
     ** @return char input from the user.
     **/
    public static char getChar(String prompt) {
        System.out.print (prompt);
        String str = sc.nextLine();
        if (str.length() != 1){
            str = sc.nextLine();
        }
        while (str.length() != 1) {
			System.out.print (prompt);
			str = sc.nextLine();
		}
        return str.charAt(0);
    }

    /**
     ** boolean selection from the user via a simple dialog.
     ** @param  prompt message to appear in dialog
     ** @return boolean selection from the user
     **/
    public static boolean getBoolean(String prompt) {
		boolean result = false;
        boolean validResponse = false;
        while (!validResponse) {
			try {
				validResponse = true;
				System.out.print (prompt);
				result = sc.nextBoolean();
			} catch (java.util.InputMismatchException exception) {
				validResponse = false;
				sc.nextLine();
			}
		}
        return result;
    }


   /**
    ** returns integer input from the user via a simple dialog.
    ** @param prompt the message string to be displayed inside dialog
    ** @return the input integer
    **/
    public static int getInt(String prompt) {
        int result = 0;
        boolean validResponse = false;
        while (!validResponse) {
			try {
				validResponse = true;
				System.out.print (prompt);
				result = sc.nextInt();
			} catch (java.util.InputMismatchException exception) {
				validResponse = false;
                System.out.println ("java.util.InputMismatchException exception");
				sc.nextLine();
			}
		}
        return result;
    }


   /**
    ** returns a float input from the user via a simple dialog.
    ** @param prompt the message string to be displayed inside dialog
    ** @return the input float
    **/
    public static float getFloat(String prompt) {
		float result = 0;
        boolean validResponse = false;
        while (!validResponse) {
			try {
				validResponse = true;
				System.out.print (prompt);
				result = sc.nextFloat();
			} catch (java.util.InputMismatchException exception) {
				validResponse = false;
				sc.nextLine();
			}
		}
        return result;
    }
}
