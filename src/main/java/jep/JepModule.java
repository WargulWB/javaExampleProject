package jep;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import com.google.inject.AbstractModule;

import jep.example.ExampleRegistry;
import jep.main.JavaExamplesProjectMain;

/**
 * This class implements a binding module to allow google guice injection.
 *
 */
public class JepModule extends AbstractModule {

    private final JavaExamplesProjectMain main;

    private final ResourceBundle bundle;

    private final ExampleRegistry exampleRegistry;

    /**
     * Constructs a new {@link JepModule}-instance. The given {@link JavaExamplesProjectMain}-class
     * is bound to the given instance.
     * 
     * @param main
     */
    public JepModule(JavaExamplesProjectMain main) {
        this.main = Objects.requireNonNull(main);
        this.bundle = assembleResourceBundle();
        this.exampleRegistry = new ExampleRegistry();
    }

    private ResourceBundle assembleResourceBundle() {
        return ResourceBundle.getBundle("texts", Locale.getDefault());
    }

    @Override
    protected void configure() {
        bind(JavaExamplesProjectMain.class).toInstance(main);
        bind(ResourceBundle.class).toInstance(bundle);
        bind(ExampleRegistry.class).toInstance(exampleRegistry);
    }

}
