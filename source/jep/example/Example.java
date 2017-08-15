package jep.example;

/**
 * This interface is to implemented by all actual example implementations, it defines some generally
 * required like {@link #run(String...)}.
 *
 */
public interface Example extends ExampleContent {

    /**
     * Runs the example for the given <code>arguments</code> and returns the logged text.
     * 
     * @param arguments
     * @return text logged during run operation as one {@link String}-object.
     */
    String run(String... arguments);

    /**
     * Appends the log by the given <code>text</code>.
     * 
     * @param text
     */
    void log(String text);

    /**
     * Appends the log by the given <code>object</code> (writing null if it's values is null or
     * writing {@link Object#toString()} otherwise).
     * 
     * @param object
     */
    void log(Object object);

    /**
     * Appends the log by a new line symbol.
     */
    void logln();

    /**
     * Appends the log by the given <code>line</code> and afterwards appends the log by a new line
     * symbol.
     * 
     * @param line
     */
    void logln(String line);

    /**
     * Appends the log by the given <code>object</code> (writing null if it's values is null or
     * writing {@link Object#toString()} otherwise) and afterwards appends the log by a new line
     * symbol.
     * 
     * @param object
     */
    void logln(Object object);

    /**
     * Clears the log.
     */
    void clearLog();

    /**
     * Returns the logged text (every text logged via {@link #log(String)}, {@link #logln()} or
     * {@link #logln(String)}).
     * 
     * @return
     */
    String getLoggedText();

    /**
     * Returns the description text of this example.
     * 
     * @return
     */
    String getDescription();

    /**
     * Returns the names of the packages which are relevant for this example.
     * 
     * @return
     */
    String[] getRelevantPackagesInformation();

    /**
     * Returns the names of the classes which are relevant for this example.
     * 
     * @return
     */
    String[] getRelevantClassesInformation();

    /**
     * Returns the classes which are relevant for this example.
     * 
     * @return
     */
    Class<?>[] getRelevantClasses();

}
