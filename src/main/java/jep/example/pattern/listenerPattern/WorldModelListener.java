package jep.example.pattern.listenerPattern;

/**
 * This functional interface defines a listener for {@link WorldModel}-instances.
 * <p>
 * Can be added to the model via {@link WorldModel#addListener(WorldModelListener)} and removed via
 * {@link WorldModel#removeListener(WorldModelListener)}.
 * 
 * @see WorldModel
 */
@FunctionalInterface
public interface WorldModelListener {

    /**
     * Update function defined by this {@link WorldModelListener}-instance. Should be called by the
     * {@link WorldModel} if its state changes.
     * 
     * @param model {@link WorldModel}-instance which called this function
     */
    public void update(WorldModel model);

}
