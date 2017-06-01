package jep.example;

/**
 * This class implements the {@link Example}-interface and handles all logging functionality as well
 * as providing some general methods, which might be helpful for different examples.
 *
 */
public abstract class AbstractExample implements Example {

    private final StringBuilder logger = new StringBuilder();

    /**
     * Adds a general message to the log, which states that no arguments are required by this
     * example.
     */
    protected void logNoArgumentsRequired() {
        logger.append("This example requires no arguments.").append('\n');
    }

    @Override
    public void log(String text) {
        logger.append(text);
    }

    @Override
    public void logln(String line) {
        logger.append(line).append('\n');
    }

    @Override
    public void logln() {
        logger.append('\n');
    }

    @Override
    public void clearLog() {
        logger.setLength(0);
    }

    @Override
    public String getLoggedText() {
        return logger.toString();
    }

    /**
     * Returns the package name of the package containing the given class.
     * 
     * @param cls given class instance
     * @return
     */
    protected String getPackageNameOfClass(Class<?> cls) {
        String canoncialName = cls.getCanonicalName();
        int classNameLength = cls.getSimpleName().length();
        int canoncialNameLength = canoncialName.length();
        return canoncialName.substring(0, canoncialNameLength - (classNameLength + 1));
    }

}
