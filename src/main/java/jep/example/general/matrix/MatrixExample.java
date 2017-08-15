package jep.example.general.matrix;

import jep.example.AbstractExample;

/**
 * This class implements an example which shows how a matrix can be implemented and used. For this
 * purpose the {@link DoubleMatrix} is used.
 *
 */
public class MatrixExample extends AbstractExample {

    @Override
    public String run(String... arguments) {
        logln("Initialize a 2 by 3 matrix A:");
        logln("[1.0, 2.0, 3.0]");
        logln("[4.0, 5.0, 6.0]");
        logln();
        DoubleMatrix a = new DoubleMatrix(2, 3);
        int m = a.getRowCount();
        int n = a.getColumnCount();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a.setField(i, j, i * m + (j + 1));
            }
        }
        logln(">> A:\n" + a.getStringRepresentation());
        logLineSeparator();

        int scalar = 2;
        logln("Multiply matrix A by scalar " + scalar + ".");
        a.multiplyByScalar(scalar);
        logln(">> A (new):\n" + a.getStringRepresentation());
        logLineSeparator();

        logln("Initialize a 3 by 2 matrix B (usingg an array as constructor argument):");
        logln("[1.0, 2.0]");
        logln("[3.0, 4.0]");
        logln("[5.0, 6.0]");
        logln();
        double[][] b_elements = {{1.0D, 2.0D}, {3.0D, 4.0D}, {5.0D, 6.0D}};
        DoubleMatrix b = new DoubleMatrix(b_elements);
        logln(">> B:\n" + b.getStringRepresentation());
        logLineSeparator();

        logln("Multiply matrix B to matrix A from the right.");
        a.multiplyByMatrix(b);
        logln(">> A (new):\n" + a.getStringRepresentation());
        logLineSeparator();
        
        logln("Transpose matrix B.");
        b.transpose();
        logln(">> B (new):\n" + b.getStringRepresentation());
        logLineSeparator();

        return getLoggedText();
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] classes = {MatrixExample.class, DoubleMatrix.class};
        return classes;
    }

    @Override
    public String getBundleKey() {
        return "example.matrix";
    }

}
