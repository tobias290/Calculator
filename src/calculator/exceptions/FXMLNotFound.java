package calculator.exceptions;

/**
 * Thrown if the application tries to load an FXML file that doesn't exist
 */
public class FXMLNotFound extends Exception {
    public FXMLNotFound(String message) {
        super(message);
    }
}


