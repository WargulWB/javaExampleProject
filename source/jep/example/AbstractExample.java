package jep.example;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.inject.Inject;

/**
 * This class implements the {@link Example}-interface and handles all logging functionality as well
 * as providing some general methods, which might be helpful for different examples.
 *
 */
public abstract class AbstractExample implements Example {

    @Inject
    protected ResourceBundle bundle;

    private final StringBuilder logger = new StringBuilder();

    /**
     * Adds a general message to the log, which states that no arguments are required by this
     * example.
     */
    protected void logNoArgumentsRequired() {
        logger.append("This example requires no arguments.").append('\n');
    }

    /**
     * Logs the following string '- - - - - - - -\n' which is used as a separator between to lines.
     */
    public void logLineSeparator() {
        logger.append("- - - - - - - -").append('\n');
    }

    @Override
    public void log(String text) {
        logger.append(text);
    }

    @Override
    public void log(Object object) {
        if (object == null) {
            logger.append("null");
        } else {
            logger.append(object.toString());
        }
    }

    @Override
    public void logln(String line) {
        logger.append(line).append('\n');
    }

    @Override
    public void logln(Object object) {
        if (object == null) {
            logger.append("null");
        } else {
            logger.append(object.toString());
        }
        logger.append('\n');
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

    @Override
    public String[] getRelevantPackagesInformation() {
        Set<String> relevantPackagesSet = new HashSet<>();
        for (Class<?> c : getRelevantClasses()) {
            relevantPackagesSet.add(getPackageNameOfClass(c));
        }
        String[] relevantPackages = new String[relevantPackagesSet.size()];
        relevantPackagesSet.stream().sorted().collect(Collectors.toList())
                .toArray(relevantPackages);
        return relevantPackages;
    }

    @Override
    public String[] getRelevantClassesInformation() {
        Set<String> relevantClassesSet = new HashSet<>();
        for (Class<?> c : getRelevantClasses()) {
            relevantClassesSet.add(c.getSimpleName());
        }
        String[] relevantClasses = new String[relevantClassesSet.size()];
        relevantClassesSet.stream().sorted().collect(Collectors.toList())
                .toArray(relevantClasses);
        return relevantClasses;
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses = {this.getClass()};
        return relevantClasses;
    }

    @Override
    public String getDescription() {
        return bundle.getString(this.getBundleKey() + ".description");
    }

}
