
/**
 * The IllegalRFidTagNumberException class is a custom exception that is thrown when
 * an invalid RFID tag number format is encountered. This exception is used in the
 * context of the DepartmentStore program to handle cases where the provided RFID
 * tag number does not match the required format.
 * 
 * This class extends the Exception class and in the DepartmentStore program,
 * this exception is thrown in situations where the user inputs an incorrect
 * RFID tag number format.
 * 
 * @author Shiv Kanani
 * SBU ID: 115171965
 * Homework #2 for CSE 214, Summer 2023
 */
public class IllegalRFidTagNumberException extends Exception {
    
    /**
     * Constructs a new IllegalRFidTagNumberException with the specified error message.
     * 
     * @param message the error message describing the reason for the exception
     */
    public IllegalRFidTagNumberException(String message) {
        super(message);
    }
}
