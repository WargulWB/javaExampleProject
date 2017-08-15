package jep.example.general.matrix;

/**
 * This class implements the exception which is to be thrown in cases of illegal matrix operations.
 *
 */
public class IllegalMatrixOperationException extends RuntimeException {

    private static final long serialVersionUID = 8361658992260609580L;

    /**
     * Constructs a new {@link IllegalMatrixOperationException}-instance for the given
     * <code>message</code>.
     * 
     * @param message which is set for this exception
     */
    public IllegalMatrixOperationException(String message) {
        super(message);
    }

}
