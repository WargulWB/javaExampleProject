package jep.example.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import jep.example.AbstractExample;

public class WriteAndReadTextFilesPreJava7Example extends AbstractExample {

    private static final String TEXT_TO_WRITE =
            "This is the text which is supposed to be written to the given file.";
    private static final int COUNT = 10;
    private static final int APPEND_COUNT = 5;
    private static final int TOTAL_COUNT = COUNT + APPEND_COUNT;

    @Inject
    private ResourceBundle bundle;

    @Override
    public String run(String... arguments) {
        if (validateArguments(arguments)) {
            logln("Argument '" + arguments[0] + "' seems to be valid. Going to run the example.");

            String path = arguments[0];
            File destination = new File(path);
            if (!destination.exists()) {
                // Generates the super directories of the given file, defined in the file path
                destination.getParentFile().mkdirs();
            }

            logln("Writing '" + TEXT_TO_WRITE + "\\n' " + COUNT
                    + " times to the given file. If the file allready exists it is going to be overwritten.");
            try (BufferedWriter bw =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destination),
                            StandardCharsets.UTF_8))) {
                for (int i = 0; i < COUNT; i++) {
                    bw.write(TEXT_TO_WRITE + '\n');
                }
            } catch (IOException exc) {
                logln("Caught exception on attempt to write the file:\n" + exc.toString());
                return getLoggedText();
            }

            logln("Writing '" + TEXT_TO_WRITE + "\\n' " + APPEND_COUNT
                    + " times to the given file. The file is going to be extended.");
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(destination, true), StandardCharsets.UTF_8))) {
                for (int i = 0; i < APPEND_COUNT; i++) {
                    bw.write(TEXT_TO_WRITE + '\n');
                }
            } catch (IOException exc) {
                logln("Caught exception on attempt to write the file:\n" + exc.toString());
                return getLoggedText();
            }

            logln("Reading " + TOTAL_COUNT
                    + " lines of written file back into an array. (Adding a enumeration to the start of every line.)");
            File source = new File(path);
            String[] lines = new String[TOTAL_COUNT];
            try (BufferedReader br = new BufferedReader(new FileReader(source))) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    lines[i] = i + ". " + line;
                    i++;
                }
            } catch (IOException exc) {
                logln("Caught exception on attempt to read the file:\n" + exc.toString());
                return getLoggedText();
            }

            logln("Going to log the read in lines:");
            for (int i = 0; i < lines.length; i++) {
                logln(lines[i]);
            }

        }
        return getLoggedText();
    }

    private boolean validateArguments(String[] arguments) {
        if (arguments.length == 0 || arguments.length > 1) {
            logln("This example requires one argument - the path which leads to a file, which is going to be written and then read back into the application.");
            if (arguments.length == 0) {
                logln("No argument was given.");
            } else {
                logln("More than one argument was given.");
            }
            return false;
        }
        File file = new File(arguments[0]);
        if (file.exists() && !file.isFile()) {
            logln("Given path exists but does not lead to a valid file.");
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return String.format(bundle.getString("example.writeAndReadTextFilesPostJava7.description"),
                TEXT_TO_WRITE, COUNT, APPEND_COUNT);
    }

    @Override
    public String getBundleKey() {
        return "example.writeAndReadTextFilesPreJava7";
    }

}
