package jep.example.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import jep.example.AbstractExample;

public class WriteAndReadTextFilesPostJava7Example extends AbstractExample {

    private static final String TEXT_TO_WRITE =
            "This is the text which is supposed to be written to the given file.";
    private static final int COUNT = 10;
    private static final int APPEND_COUNT = 5;
    private static final int TOTAL_COUNT = COUNT + APPEND_COUNT;

    @Override
    public String run(String... arguments) {
        if (validateArguments(arguments)) {
            logln("Argument '" + arguments[0] + "' seems to be valid. Going to run the example.");

            String pathText = arguments[0];
            Path path = Paths.get(pathText);

            logln("Writing '" + TEXT_TO_WRITE + "\\n' " + COUNT
                    + " times to the given file . If the file allready exists it is going to be overwritten.");
            try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
                for (int i = 0; i < COUNT; i++) {
                    bw.write(TEXT_TO_WRITE + '\n');
                }
            } catch (IOException | UnsupportedOperationException | SecurityException exc) {
                logln("Caught exception on attempt to write the file:\n" + exc.toString());
                return getLoggedText();
            }

            logln("Writing '" + TEXT_TO_WRITE + "\\n' " + APPEND_COUNT
                    + " times to the given file. The file is going to be extended.");
            try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND)) {
                for (int i = 0; i < APPEND_COUNT; i++) {
                    bw.write(TEXT_TO_WRITE + '\n');
                }
            } catch (IOException | UnsupportedOperationException | SecurityException exc) {
                logln("Caught exception on attempt to write the file:\n" + exc.toString());
                return getLoggedText();
            }

            logln("Reading " + TOTAL_COUNT
                    + " lines of written file back into an array.");

            List<String> lines;
            try {
                lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException | SecurityException exc) {
                logln("Caught exception on attempt to write the file:\n" + exc.toString());
                return getLoggedText();
            }

            logln("Going to log the read in lines (adding a enumeration to the start of every line):");
            int i = 0;
            for (String line : lines) {
                logln(i + ". " + line);
                i++;
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
        return String.format(super.getDescription(), TEXT_TO_WRITE, COUNT, APPEND_COUNT);
    }

    @Override
    public String getBundleKey() {
        return "example.writeAndReadTextFilesPostJava7";
    }

}
