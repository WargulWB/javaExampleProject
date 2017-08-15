package jep.example;

/**
 * This class implements a new exception which is to be thrown by the {@link ExampleRegistry} in
 * cases were examples are registered whose types were not previously registered.
 *
 */
public class UnregisteredTypeException extends RuntimeException {

    private static final long serialVersionUID = 6543511265852434780L;

    /**
     * Constructs a new {@link UnregisteredTypeException}-instance with a default text which uses
     * the given <code>type</code> for which the exception occurred.
     */
    public UnregisteredTypeException(ExampleType type) {
        super("The type with key '" + type.getBundleKey()
                + "' or one of it's sup types was not registered.");
    }

}
