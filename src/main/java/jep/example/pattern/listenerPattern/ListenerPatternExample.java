package jep.example.pattern.listenerPattern;

import jep.example.AbstractExample;

/**
 * This class implements a simple example in which the listener pattern is applied. For this purpose
 * the {@link WorldModel}-class and the {@link WorldModel-Listener}-interface are used.
 *
 */
public class ListenerPatternExample extends AbstractExample implements WorldModelListener {

    @Override
    public String run(String... arguments) {
        logln("Instantiating a new world model with state 0.");
        logln("Adding this example class as listener for the world model.");
        logln("This is possible because this example extends the "
                + WorldModelListener.class.getSimpleName() + "-interface.");
        WorldModel model = new WorldModel(0);
        model.addListener(this);
        logln("Changing the world model, setting its state to: 5.");
        model.setState(5);

        return getLoggedText();
    }

    @Override
    public void update(WorldModel model) {
        logLineSeparator();
        logln(this.getClass().getSimpleName() + "#update(WorldModel)-method:");
        logln("The world models state changed to " + model.getState() + ".");
        logLineSeparator();
    }

    @Override
    public String getDescription() {
        return String.format(super.getDescription(), ListenerPatternExample.class.getSimpleName(),
                WorldModelListener.class.getSimpleName(), WorldModel.class.getSimpleName());
    }

    @Override
    public String[] getRelevantClassesInformation() {
        String[] relevantClasses = {ListenerPatternExample.class.getSimpleName(),
                WorldModel.class.getSimpleName(), WorldModelListener.class.getSimpleName()};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.listenerPattern";
    }

}
