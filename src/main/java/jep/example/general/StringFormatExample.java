package jep.example.general;

import java.util.Formatter;
import java.util.Locale;

import jep.example.AbstractExample;

public class StringFormatExample extends AbstractExample {

    @Override
    public String run(String... arguments) {
        String fullName = "Bob Andrews";
        int age = 25;
        double perMilleValue = 1.3D;
        String text = "%s of age %d was caught driving with a per mille value of %f.";
        logln("Parameters:");
        logln("[1] String: " + fullName);
        logln("[2] int: " + age);
        logln("[3] double: " + perMilleValue);
        logln(">> Unformatted text: " + text);
        logln(">>   Formatted text: " + String.format(text, fullName, age, perMilleValue));
        logln("The following text is formatted using the same parameters and a slightly different text.");
        logln("To force a defined floating value format and usage of the US locale (in the above example the format is default locale dependend).");
        logln("(That means the output looks for example like '1,300000' in the German locale while it looks like '1.300000' in the US locale.)");
        text = "%s of age %d was caught driving with a per mille value of %.2f.";
        logln(">>   Formatted text: " + String.format(Locale.US, text, fullName, age, perMilleValue));
        logLineSeparator();
        
        logln("The following example shows how to write a table of formated text.");
        double[][] values = {{1D, 2.534D, 2.5D}, {3D, 30D, 2.54067D}, {2000.17D, 5D, 100D}};
        logln("The table consists of the original values:");
        logArray(values);
        String[][] valuesAsText = new String[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                valuesAsText[i][j] = String.format(Locale.US, "%.3f", values[i][j]);
            }
        }
        int[] maxTextLengthPerColumn = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            int max = valuesAsText[i][0].length();
            for (int j = 1; j < values[0].length; j++) {
                int value = valuesAsText[i][j].length();
                if (value > max) {
                    max = value;
                }
            }
            maxTextLengthPerColumn[i] = max;
        }
        logln("The formatted table is the following:");
        for (int j = 0; j < values[0].length; j++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                sb.append(String.format("%" + maxTextLengthPerColumn[i] + "s", valuesAsText[i][j]))
                        .append(' ');
            }
            logln(sb.toString());
        }
        logLineSeparator();
        
        int x = 1, y = 2, z = 0;
        float v = 2.5F;
        double w = 0.4D;
        logln("In some situations you may want to use multiple arguments of the same type or multiple the same arguments at multiple text positions.");
        logln("The following example shows how to achieve this - this example also shows how you can use 'Formatter' to achieve the result.");
        logln("Parameters:");
        logln("[1] int: " + x);
        logln("[2] int: " + y);
        logln("[3] int: " + z);
        logln("[4] float: " + v);
        logln("[5] double: " + w);
        text = "x:=%1$d is smaller than y:=%2$d, but x:=%1$d is greater than z:=%3$d. x is also greater than w:=%5$.1f, but x is smaller than v:=%4$.1f.";
        logln(">> Unformatted Text: " + text);
        try (Formatter formatter = new Formatter(Locale.US)) {
            formatter.format(text, x, y, z, v, w);
            logln(">>   Formatted Text: " + formatter.toString());
        }

        return getLoggedText();
    }

    private void logArray(double[][] values) {
        assert (values != null && values.length > 0 && values[0].length > 0);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < values.length; i++) {
            sb.append('{');
            for (int j = 0; j < values[0].length; j++) {
                sb.append(values[i][j]);
                if (j < values[0].length - 1) {
                    sb.append(',');
                }
            }
            sb.append('}');
            if (i < values.length - 1) {
                sb.append(',');
            }
        }
        sb.append('}');
        logln(sb.toString());
    }

    @Override
    public String getBundleKey() {
        return "example.stringFormat";
    }

}
