package jep.main;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jep.JepModule;
import jep.example.ExampleRegistry;
import jep.example.ExampleType;
import jep.example.general.StreamApiExample;
import jep.example.general.StringFormatExample;
import jep.example.general.lambda.LambdaExpressionExample;
import jep.example.io.WriteAndReadTextFilesPostJava7Example;
import jep.example.io.WriteAndReadTextFilesPreJava7Example;
import jep.example.pattern.builderPattern.BuilderPatternExample;

/**
 * This is the main class of the java examples project which provides the {@link #main(String...)}
 * -method. This class extends {@link Application} and runs a new JavaFX graphical user interface up
 * on start.
 *
 */
public class JavaExamplesProjectMain extends Application {

    private final Injector injector;

    private final ResourceBundle bundle;

    private final ExampleRegistry exampleRegistry;

    private Stage primaryStage;

    private MainFrameController mainFrameController;

    /**
     * Constructs a new {@link JavaExamplesProjectMain}-instance. Initializes the {@link JepModule}
     * and fetches certain instances via an injector.
     */
    public JavaExamplesProjectMain() {
        this.injector = Guice.createInjector(new JepModule(this));
        this.bundle = injector.getInstance(ResourceBundle.class);
        this.exampleRegistry = injector.getInstance(ExampleRegistry.class);
        registerExamples();
    }

    private void registerExamples() {
        ExampleType typeGeneral = new ExampleType("exampleType.general");
        ExampleType typePattern = new ExampleType("exampleType.patterns");
        ExampleType typeIo = new ExampleType("exampleType.io");
        exampleRegistry.registerType(typeGeneral);
        exampleRegistry.registerType(typePattern);
        exampleRegistry.registerType(typeIo);
        // exampleRegistry.register(new SingletonPatternExample(), typePattern);
        // exampleRegistry.register(new FactoryPatternExample(), typePattern);
        exampleRegistry.register(injector.getInstance(StreamApiExample.class),
                typeGeneral);
        exampleRegistry.register(injector.getInstance(LambdaExpressionExample.class),
                typeGeneral);
        exampleRegistry.register(injector.getInstance(StringFormatExample.class),
                typeGeneral);
        exampleRegistry.register(injector.getInstance(BuilderPatternExample.class), typePattern);
        exampleRegistry.register(injector.getInstance(WriteAndReadTextFilesPreJava7Example.class), typeIo);
        exampleRegistry.register(injector.getInstance(WriteAndReadTextFilesPostJava7Example.class),
                typeIo);
    }

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(bundle.getString("title"));
        initRootLayout();
    }


    private void initRootLayout() {
        FXMLLoader loader = createLoader();
        loader.setLocation(getResourceURL("MainFrameView.fxml"));
        try {
            BorderPane main = (BorderPane) loader.load();
            Scene scene = new Scene(main);
            primaryStage.setScene(scene);
            primaryStage.show();
            mainFrameController = loader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private URL getResourceURL(String relativeFilePath) {
        return JavaExamplesProjectMain.class.getResource(Objects.requireNonNull(relativeFilePath));
    }

    private FXMLLoader createLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(injector::getInstance);
        loader.setResources(bundle);
        return loader;
    }

}
