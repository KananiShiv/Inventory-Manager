
/**
 * The IllegalLocationException class is a custom exception that is thrown when
 * an invalid location format is encountered. This exception is used in the
 * context of the DepartmentStore program to handle cases where the provided
 * location does not match the required format.
 * 
 * This class extends the Exception class and in the DepartmentStore program,
 * this exception is thrown in situations where the user inputs an incorrect
 * location format, such as an invalid cart number or an improperly formatted
 * location string.
 * 
 * @author Shiv Kanani
 * SBU ID: 115171965
 * Homework #2 for CSE 214, Summer 2023
 */
public class IllegalLocationException extends Exception {

    /**
     * Constructs a new IllegalLocationException with the specified error message.
     * 
     * @param message the error message describing the reason for the exception
     */
    public IllegalLocationException(String message) {
        super(message);
    }
}
