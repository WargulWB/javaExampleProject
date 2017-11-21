package jep.example.general.preference;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * This class implements a preference handler which is to be used as the exclusive way to handle
 * preference access.
 *
 */
public class PreferenceHandler {

    private final Preferences preferences;

    private final String keyPath = "path";
    private final String keyNumber = "number";

    private final String defaultPathString = System.getProperty("user.home");

    /**
     * Constructs a new {@link PreferenceHandler}-instance for the given <code>preferences</code>.
     * 
     * @param preferences {@link Preferences}-instances set for this handler
     */
    public PreferenceHandler(Preferences preferences) {
        this.preferences = Objects.requireNonNull(preferences);
    }

    /**
     * Return the path preference if it exits and is valid and otherwise return the default path.
     * 
     * @return
     */
    public Path getWorkdirPathPreference() {
        String pathString = preferences.get(keyPath, defaultPathString);
        try {
            return Paths.get(pathString);
        } catch (InvalidPathException exc) {
            return Paths.get(defaultPathString);
        }
    }

    /**
     * Sets the path preference as the given <code>path</code>.
     * 
     * @param path path set as path preference
     */
    public void setWorkdirPathPreference(Path path) {
        preferences.put(keyPath, path.toString());
    }

    /**
     * Returns the value of the number preference if it exists and <code>0</code> otherwise.
     * 
     * @return
     */
    public int getNumberPreference() {
        return preferences.getInt(keyNumber, 0);
    }

    /**
     * Sets the number preference as given <code>number</code>.
     * 
     * @param number value set for the preference
     */
    public void setNumberPreference(int number) {
        preferences.putInt(keyNumber, number);
    }

    public void clearPreferences() {
        try {
            preferences.clear();
        } catch (BackingStoreException exc) {
        }
    }

}
