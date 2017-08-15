package jep.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SourceCodeProvider {

    /**
     * This variable holds the information about the src-folder (if it exists)
     */
    private static final File SOURCE_DIRECTORY;

    private static final String[] KEY_WORDS = {"abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const", "continue", "default", "do", "double",
            "else", "extends", "false", "final", "finally", "float", "for", "goto", "if",
            "implements", "import", "instanceof", "int", "interface", "long", "native", "new",
            "null", "package", "private", "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
            "true", "try", "void", "volatile", "while"};

    private static final int MINIMAL_KEYWORD_SIZE;

    static {
        int size = KEY_WORDS[0].length();
        for (int i = 1; i < KEY_WORDS.length; i++) {
            int wordSize = KEY_WORDS[i].length();
            if (wordSize < size) {
                size = wordSize;
            }
        }
        MINIMAL_KEYWORD_SIZE = size;
    }

    private final Map<Class<?>, List<Text>> classTextMap = new HashMap<>();


    static {
        /**
         * This file object defines the root directory in which the project is placed. This means
         * either the directory in which the bin-folder is placed (in case the project is not build
         * and in its raw form) or the directory in which the projects jar is placed.
         */
        File rootDirectory = new File(SourceCodeProvider.class.getResource("").getPath())
                .getParentFile().getParentFile().getParentFile();

        SOURCE_DIRECTORY = new File(rootDirectory.getAbsolutePath() + File.separator + "source");
    }

    /**
     * Constructs a new instance of {@link SourceCodeProvider}.
     * 
     * @throws IOException
     */
    public SourceCodeProvider(Class<?>[] classes, String unavailableMessage) throws IOException {
        Objects.requireNonNull(classes);
        Objects.requireNonNull(unavailableMessage);
        for (Class<?> cls : classes) {
            if (sourceDirectoryIsValid()) {
                String sourcePath = SOURCE_DIRECTORY.getAbsolutePath() + File.separator
                        + cls.getCanonicalName().replace('.', File.separatorChar) + ".java";
                File classSourceFile = new File(sourcePath);
                if (classSourceFile.isFile()) {
                    classTextMap.put(cls, getTextsFrom(classSourceFile));
                } else {
                    classTextMap.put(cls, getDefaultTextList(unavailableMessage));
                }
            } else {
                classTextMap.put(cls, getDefaultTextList(unavailableMessage));
            }
        }
    }

    private List<Text> getTextsFrom(File classSourceFile) throws IOException {
        List<String> lines = Files.readAllLines(classSourceFile.toPath(), StandardCharsets.UTF_8);
        List<Text> texts = new ArrayList<>();
        boolean isMultilineComment = false;
        for (String line : lines) {
            boolean isSingleComment = false;
            String trimmedLine = line.trim();
            if (trimmedLine.length() >= 2) {
                String firstSymbols = trimmedLine.substring(0, 2);
                if (firstSymbols.equals("//")) {
                    isSingleComment = true;
                }
                if (firstSymbols.equals("/*")) {
                    isMultilineComment = true;
                }
            }

            if (isMultilineComment || isSingleComment) {
                Text text = new Text(line + "\n");
                text.setFill(Paint.valueOf("#9b9b9b"));
                if (isMultilineComment && trimmedLine.length() >= 2) {
                    String endSymbols =
                            trimmedLine.substring(trimmedLine.length() - 2, trimmedLine.length());
                    if (endSymbols.equals("*/")) {
                        isMultilineComment = false;
                    }
                }
                texts.add(text);
            } else {
                addNoCommentText(line, texts);
            }
        }
        return texts;
    }


    private void addNoCommentText(String line, List<Text> texts) {
        String[] parts = line.split("\"");
        if (parts.length == 1) {
            addTextByCheckingForKEyWords(line, texts, true);
        } else {
            for (int i = 0; i < parts.length; i++) {
                if (i % 2 == 0) {
                    addTextByCheckingForKEyWords(parts[i], texts, i == parts.length - 1);
                } else {
                    Text text;
                    if (i != parts.length - 1) {
                        text = new Text("\"" + parts[i] + "\"");
                    } else {
                        text = new Text("\"" + parts[i] + "\"\n");
                    }
                    text.setFill(Paint.valueOf("#00d20f"));
                    texts.add(text);
                }
            }
        }
    }

    private void addTextByCheckingForKEyWords(String linePart, List<Text> texts,
            boolean containsLineEnd) {
        String[] parts = linePart.split("\\s");
        int positionInText = 0;
        int i = 0;
        int endOfLastKeyWord = 0;
        for (String part : parts) {
            int endOfPartIndex = positionInText + part.length();
            if (part.length() >= MINIMAL_KEYWORD_SIZE && isJavaKeyword(part)) {
                String previousText = linePart.substring(endOfLastKeyWord, positionInText);
                String keyWord = part;
                Text text = new Text(previousText);
                text.setFill(Paint.valueOf("#e3e3e3"));
                texts.add(text);
                if (i == parts.length - 1 && containsLineEnd) {
                    text = new Text(keyWord + "\n");
                } else {
                    text = new Text(keyWord);
                }
                text.setFill(Paint.valueOf("#b121d0"));
                texts.add(text);
                endOfLastKeyWord = endOfPartIndex;
            } else if (i == parts.length - 1) {
                Text text;
                if (containsLineEnd) {
                    text = new Text(linePart.substring(endOfLastKeyWord, linePart.length()) + "\n");
                } else {
                    text = new Text(linePart.substring(endOfLastKeyWord, linePart.length()));
                }
                text.setFill(Paint.valueOf("#e3e3e3"));
                texts.add(text);
            }
            positionInText = endOfPartIndex + 1;
            i++;
        }
    }

    public List<Text> getTextListFor(Class<?> cls) {
        return classTextMap.get(cls);
    }

    private List<Text> getDefaultTextList(String unavailableMessage) {
        List<Text> defaultTextList = new ArrayList<>();
        Text defaultText = new Text(unavailableMessage);
        defaultText.setFill(Color.FIREBRICK);
        defaultTextList.add(defaultText);
        return defaultTextList;
    }

    private static boolean isJavaKeyword(String word) {
        return (Arrays.binarySearch(KEY_WORDS, word) >= 0);
    }

    /**
     * Returns <code>true</code> if the source directory exists and is actually an directory,
     * returns <code>false</code> otherwise.
     * 
     * @return
     * @see File#isDirectory()
     */
    public boolean sourceDirectoryIsValid() {
        return SOURCE_DIRECTORY.isDirectory();
    }
}
