package jep.example.pattern.listenerPattern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class implements a simple world model which consists of a single state defined by a number
 * which can changed from the outside.
 *
 */
public class WorldModel {

    private final Set<WorldModelListener> listeners = new HashSet<>();

    private int state;

    /**
     * Constructs a new instance of {@link WorldModel}.
     * 
     * @param initialState initial value of this models state field
     */
    public WorldModel(int initialState) {
        this.state = initialState;
    }

    /**
     * Adds the given listener to this models listener set.
     * 
     * @param listener which is added
     */
    public void addListener(WorldModelListener listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    /**
     * Removes the given listen from this models listener set.
     * 
     * @param listener which is removed
     */
    public void removeListener(WorldModelListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns this world models state value.
     * 
     * @return state value
     */
    public int getState() {
        return state;
    }

    /**
     * Sets this world models state as the given value. (Also informs this models listeners about
     * the change.)
     * 
     * @param state new state value
     */
    public void setState(int state) {
        this.state = state;
        listeners.forEach(listener -> listener.update(this));
    }
}
