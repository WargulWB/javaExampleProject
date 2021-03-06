package jep.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import jep.example.Example;
import jep.example.ExampleContent;
import jep.example.ExampleRegistry;
import jep.example.ExampleType;

/**
 * This class implements the controller of the <tt>MainFrameView.fxml</tt>.
 *
 */
public class MainFrameController {

    @Inject
    private ResourceBundle bundle;

    @Inject
    private ExampleRegistry exampleRegistry;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabOverview;

    @FXML
    private Tab tabDescription;

    @FXML
    private Tab tabOutput;

    @FXML
    private Tab tabSourceCode;

    @FXML
    private Button buttonRun;

    @FXML
    private TextArea textAreaArguments;

    @FXML
    private TreeView<ExampleContent> treeViewExamples;

    @FXML
    private TreeView<Class<?>> treeViewClasses;

    @FXML
    private TextArea textAreaRelevantPackages;

    @FXML
    private TextArea textAreaRelevantClasses;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private TextFlow textFlowOutput;

    @FXML
    private TextFlow textFlowSourceCode;

    private SourceCodeProvider sourceCodeProvider;

    @FXML
    private void initialize() {
        initializeTreeViewExamples();
        initializeTreeViewClasses();
    }

    private void initializeTreeViewClasses() {
        TreeItem<Class<?>> root = new TreeItem<>(null);
        root.setExpanded(true);

        treeViewClasses.setRoot(root);
        treeViewClasses.setShowRoot(false);
        treeViewClasses.setCellFactory(c -> new ClassTreeCellFactory());
        treeViewClasses.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<TreeItem<Class<?>>>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends TreeItem<Class<?>>> observableValue,
                            TreeItem<Class<?>> oldValue, TreeItem<Class<?>> newValue) {
                        if (newValue == null) {
                            textFlowSourceCode.getChildren().clear();
                            textFlowSourceCode.getChildren()
                                    .add(getSourceCodeUnselectedClassText());
                        } else {
                            textFlowSourceCode.getChildren().clear();
                            textFlowSourceCode.getChildren()
                                    .addAll(sourceCodeProvider.getTextListFor(newValue.getValue()));
                        }
                    }
                });
    }

    protected Text getSourceCodeUnselectedClassText() {
        Text text = new Text(bundle.getString("sourceCode.unselectedMsg"));
        text.setFill(Paint.valueOf("#e3e3e3"));
        return text;
    }

    private void updateTreeViewClasses() {
        TreeItem<Class<?>> root = treeViewClasses.getRoot();
        root.getChildren().clear();

        Example selectedExample =
                (Example) treeViewExamples.getSelectionModel().getSelectedItem().getValue();
        for (Class<?> cls : selectedExample.getRelevantClasses()) {
            root.getChildren().add(new TreeItem<>(cls));
        }
        treeViewClasses.getSelectionModel().clearSelection();
    }

    private void initializeTreeViewExamples() {
        TreeItem<ExampleContent> contentRoot = new TreeItem<>(new ExampleTreeHeader("root"));
        contentRoot.setExpanded(true);

        treeViewExamples.setRoot(contentRoot);
        treeViewExamples.setShowRoot(false);
        treeViewExamples.setCellFactory(c -> new ExampleTreeCellFactory());
        exampleRegistry.getRootTypes().stream().sorted((t1, t2) -> compareExampleTypes(t1, t2))
                .forEach(type -> {
                    TreeItem<ExampleContent> rootTypeHeader =
                            new TreeItem<>(new ExampleTreeHeader(type));
                    contentRoot.getChildren().add(rootTypeHeader);
                    addSubTypes(rootTypeHeader, type);
                    exampleRegistry.getRegisteredExamplesOfType(type).stream()
                            .sorted((e1, e2) -> bundle.getString(e1.getBundleKey())
                                    .compareTo(bundle.getString(e2.getBundleKey())))
                            .forEach(example -> rootTypeHeader.getChildren()
                                    .add(new TreeItem<>(example)));
                });
        treeViewExamples.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<TreeItem<ExampleContent>>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends TreeItem<ExampleContent>> observableValue,
                            TreeItem<ExampleContent> oldValue, TreeItem<ExampleContent> newValue) {
                        if (newValue == null) {
                            buttonRun.setDisable(true);
                            tabDescription.setDisable(true);
                            tabSourceCode.setDisable(true);
                        } else {
                            if (Example.class.isAssignableFrom(newValue.getValue().getClass())) {
                                buttonRun.setDisable(false);
                                tabDescription.setDisable(false);
                                tabSourceCode.setDisable(false);
                                updateTabDescription();
                                updateTabSourceCode();
                            } else {
                                buttonRun.setDisable(true);
                                tabDescription.setDisable(true);
                                tabSourceCode.setDisable(true);
                            }
                        }
                    }
                });
    }

    protected void updateTabSourceCode() {
        updateTreeViewClasses();
        Example selectedExample =
                (Example) treeViewExamples.getSelectionModel().getSelectedItem().getValue();
        try {
            sourceCodeProvider = new SourceCodeProvider(selectedExample.getRelevantClasses(),
                    bundle.getString("sourceCode.defaultErrorMsg"));
        } catch (IOException exc) {
            Text text = new Text(bundle.getString("sourceCode.IOErrorMsg"));
            text.setFill(Color.FIREBRICK);
            textFlowSourceCode.getChildren().clear();
            textFlowSourceCode.getChildren().add(text);
            exc.printStackTrace();
        }
    }

    protected void updateTabDescription() {
        Example selectedExample =
                (Example) treeViewExamples.getSelectionModel().getSelectedItem().getValue();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectedExample.getRelevantPackagesInformation().length; i++) {
            sb.append(selectedExample.getRelevantPackagesInformation()[i]);
            if (i < selectedExample.getRelevantPackagesInformation().length - 1) {
                sb.append(',').append('\n');
            }
        }
        textAreaRelevantPackages.setText(sb.toString());

        sb.setLength(0);
        for (int i = 0; i < selectedExample.getRelevantClassesInformation().length; i++) {
            sb.append(selectedExample.getRelevantClassesInformation()[i]);
            if (i < selectedExample.getRelevantClassesInformation().length - 1) {
                sb.append(',').append('\n');
            }
        }
        textAreaRelevantClasses.setText(sb.toString());

        updateDescriptionTextArea(selectedExample.getDescription());
    }

    private void updateDescriptionTextArea(String description) {
        textAreaDescription.setText(description);
    }

    private void addSubTypes(TreeItem<ExampleContent> typeHeader, ExampleType type) {
        List<ExampleType> children = type.getSubTypesAsUnmodifiableSet().stream()
                .sorted((t1, t2) -> compareExampleTypes(t1, t2)).collect(Collectors.toList());
        for (ExampleType subType : children) {
            TreeItem<ExampleContent> subTypeHeader = new TreeItem<>(new ExampleTreeHeader(subType));
            typeHeader.getChildren().add(subTypeHeader);
            addSubTypes(subTypeHeader, subType);
            for (Example example : exampleRegistry.getRegisteredExamplesOfType(type).stream()
                    .sorted((ex1, ex2) -> compareExamples(ex1, ex2)).collect(Collectors.toList())) {
                typeHeader.getChildren().add(new TreeItem<>(example));
            }
        }
    }

    private int compareExampleTypes(ExampleType t1, ExampleType t2) {
        return bundle.getString(t1.getBundleKey()).compareTo(bundle.getString(t2.getBundleKey()));
    }

    private int compareExamples(Example ex1, Example ex2) {
        return bundle.getString(ex1.getBundleKey()).compareTo(bundle.getString(ex2.getBundleKey()));
    }

    /**
     * Called up on hitting the 'Run'-button. Tries to execute the selected {@link Example}
     * -instances run method.
     */
    @FXML
    private void runSelectedExample() {
        Example selectedExample =
                (Example) treeViewExamples.getSelectionModel().getSelectedItem().getValue();
        selectedExample.run(packArguments());
        String log = selectedExample.getLoggedText();
        printLog(log);
        tabOutput.setDisable(false);
        tabPane.getSelectionModel().select(tabOutput);
    }

    private void printLog(String log) {
        textFlowOutput.getChildren().clear();
        Text logText = new Text(log);
        logText.setFill(Color.rgb(0xe3, 0xe3, 0xe3));
        textFlowOutput.getChildren().add(new Text("\n"));
        textFlowOutput.getChildren().add(logText);
    }

    private String[] packArguments() {
        String argumentsText = textAreaArguments.getText();
        argumentsText = argumentsText.trim();
        argumentsText = argumentsText.replaceAll("\\s+", " ");
        String[] arguments = argumentsText.split(" ");
        // avoid empty fields
        List<String> actualArguments = new ArrayList<>();
        for (String arg : arguments) {
            if (!arg.equals("")) {
                actualArguments.add(arg);
            }
        }
        arguments = new String[actualArguments.size()];
        actualArguments.toArray(arguments);
        return arguments;
    }

    private class ExampleTreeHeader implements ExampleContent {

        private final Optional<ExampleType> type;

        private final String key;

        public ExampleTreeHeader(String key) {
            this.type = Optional.empty();
            this.key = Objects.requireNonNull(key);
        }

        public ExampleTreeHeader(ExampleType type) {
            this.type = Optional.of(Objects.requireNonNull(type));
            this.key = type.getBundleKey();
        }

        @Override
        public String getBundleKey() {
            return key;
        }

        public Optional<ExampleType> getType() {
            return type;
        }
    }

    private class ExampleTreeCellFactory extends TreeCell<ExampleContent> {
        @Override
        public void updateItem(ExampleContent item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
            } else {
                String key = item.getBundleKey();
                if (key != null) {
                    setText(bundle.getString(key));
                } else {
                    setText("WARNING: No key provided.");
                }
            }
        }
    }

    private class ClassTreeCellFactory extends TreeCell<Class<?>> {
        @Override
        public void updateItem(Class<?> item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
            } else {
                setText(item.getCanonicalName());
            }
        }
    }
}
