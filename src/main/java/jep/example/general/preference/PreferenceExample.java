package jep.example.general.preference;

import java.io.File;
import java.nio.file.Paths;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import jep.example.AbstractExample;

/**
 * This class implements an example which shows how to store parameters constantly using
 * {@link Preferences}.
 *
 */
public class PreferenceExample extends AbstractExample {

    @Override
    public void run(String... arguments) {
        /**
         * Initializing our preferences by setting the node as our example class
         * {@link PreferenceExample}.
         */
        Preferences preferences = Preferences.userNodeForPackage(PreferenceExample.class);
        try {
            // WARNING: You would only due this if you uninstall your software or reset the
            // preferences. However for this example to work it is mandatory to clear the
            // preferences before the example.
            preferences.clear();
        } catch (BackingStoreException e) {
        }

        String keyPath = "path";
        logln("In this example we want to save our work directory path as preference.");
        logln("First we define a preference key as '" + keyPath + "'.");
        logLineSeparator();

        logln("Attempting to fetch the preference stored as '" + keyPath + "'.");
        String pathString = preferences.get(keyPath, System.getProperty("user.home"));
        logln("Since we did not set the preference its default value is returned '" + pathString
                + "'.");
        logLineSeparator();


        logln("Attempting to set the preference for '" + keyPath + "' as '\\example\\path\\'.");
        preferences.put(keyPath, "\\example\\path\\"); // We would normally use a file chooser to
                                                       // pick a working directory and store its
                                                       // path as preference
        logLineSeparator();

        logln("Attempting to fetch the preference stored as '" + keyPath + "'.");
        pathString = preferences.get(keyPath, new File(".").getAbsolutePath());
        logln("Since we did set the preference its set value is returned '" + pathString + "'.");
        logLineSeparator();

        logln("We can use prefernces to store any basic data type."
                + "For example we could store user options, editable parameters of algorithms, etc.");
        String keyNumber = "number";
        logln("Attempting to set the preference for '" + keyNumber + "' as '5'.");
        preferences.putInt(keyNumber, 5);
        logln("Attempting to fetch the preference stored as '" + keyNumber + "'.");
        int number = preferences.getInt(keyNumber, 0);
        logln("The fetched number was the set number '" + number + "'.");
        logLineSeparator();

        logln("The above usage of preferences shows two issues of using preferences directly:");
        logln("1. If we use the same node and the same key in different programm section we might override our prefernce and get invalid values.");
        logln("2. We are restricted to only story basic data types.");
        logln("\nWe can prevent those issues for example by implementing a preference handler which encapsulates the preference access.");
        PreferenceHandler preferenceHandler = new PreferenceHandler(preferences);

        // WARNING: You would only due this if you uninstall your software or reset the
        // preferences. However for this example to work it is mandatory to clear the
        // preferences before the example.
        preferenceHandler.clearPreferences();

        logln("Attempting to set the workdir path preference as '\\example\\path\\' using the Path-class.");
        preferenceHandler.setWorkdirPathPreference(Paths.get("\\example\\path\\"));
        logln("Attempting to set the number preference as '5'.");
        preferenceHandler.setNumberPreference(5);
        logln("Fethcing the results:");
        log("Path: ");
        logln(preferenceHandler.getWorkdirPathPreference().toString());
        log("Number: ");
        logln(preferenceHandler.getNumberPreference());

    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses =
                {PreferenceExample.class, Preferences.class, PreferenceHandler.class};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.preferences";
    }

}
