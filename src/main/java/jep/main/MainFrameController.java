package jep.main;

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
    private Button buttonRun;

    @FXML
    private TextArea textAreaArguments;

    @FXML
    private TreeView<ExampleContent> treeViewExamples;

    @FXML
    private TextArea textAreaRelevantPackages;

    @FXML
    private TextArea textAreaRelevantClasses;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private TextFlow textFlowOutput;

    @FXML
    private void initialize() {
        initializeTreeViewExamples();
    }

    private void initializeTreeViewExamples() {
        TreeItem<ExampleContent> contentRoot = new TreeItem<>(new ExampleTreeHeader("root"));
        contentRoot.setExpanded(true);

        treeViewExamples.setRoot(contentRoot);
        treeViewExamples.setShowRoot(false);
        treeViewExamples.setCellFactory(c -> new TreeCellFactory());
        exampleRegistry.getRootTypes().stream().sorted((t1, t2) -> compareExampleTypes(t1, t2))
                .forEach(type -> {
                    TreeItem<ExampleContent> rootTypeHeader =
                            new TreeItem<>(new ExampleTreeHeader(type));
                    contentRoot.getChildren().add(rootTypeHeader);
                    addSubTypes(rootTypeHeader, type);
                    for (Example example : exampleRegistry.getRegisteredExamplesOfType(type)) {
                        rootTypeHeader.getChildren().add(new TreeItem<>(example));
                    }
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
                        } else {
                            if (Example.class.isAssignableFrom(newValue.getValue().getClass())) {
                                buttonRun.setDisable(false);
                                tabDescription.setDisable(false);
                                updateTabDescription();
                            } else {
                                buttonRun.setDisable(true);
                                tabDescription.setDisable(true);
                            }
                        }
                    }
                });
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
        String log = selectedExample.run(packArguments());
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

    private class TreeCellFactory extends TreeCell<ExampleContent> {

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
}
