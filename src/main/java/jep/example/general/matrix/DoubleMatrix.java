package jep.example.general.matrix;

import java.util.Locale;
import java.util.Objects;

/**
 * This class implements matrix handling for the primitive data type <b>double</b>. It allows direct
 * operations on a matrix which result in a changed matrix, while offering static-methods as an
 * alternative approach to handle matrix operations by keeping the operands unchanged.
 *
 */
public class DoubleMatrix {

    private double[][] matrix;

    /**
     * Constructs a new {@link DoubleMatrix}-instance with <code>m</code>-rows and <code>n</code>
     * -columns. All fields of the matrix have the value <code>0</code>.
     * 
     * @param m number of rows
     * @param n number of columns
     */
    public DoubleMatrix(int m, int n) {
        if (m < 1) {
            throw new IllegalArgumentException(
                    "The number of rows 'm' has to be true positive (> 0).");
        }
        if (n < 1) {
            throw new IllegalArgumentException(
                    "The number of columns 'n' has to be true positive (> 0).");
        }
        matrix = new double[m][n];
    }

    /**
     * Constructs a new {@link DoubleMatrix}-instance which wraps the given matrix. The given matrix
     * is considered to be an <code>m</code> x <code>n</code> -Matrix (<code>m</code> rows,
     * <code>n</code> columns).
     * <p>
     * The first dimension of the given array (<code>matrix.length</code> ) is considered to
     * correspond to <code>m</code> while the second dimension ( <code>matrix[0].length</code>) is
     * considered to correspond to <code>n</code>.
     * <p>
     * Example 1 - simple matrix:
     * 
     * <pre>
     *     (1, 2)
     * M = (3, 4), would be created by using the following array as input:
     *     (5, 6)
     *     
     * double[][] a = {{1, 2}, {3, 4}, {5, 6}};
     * </pre>
     * <p>
     * (For the following two example it should be stated, that the use of an explicit vector class
     * would probably be a better approach.)
     * <p>
     * Example 2 - column vector:
     * 
     * <pre>
     *     (1)
     * v = (2), would be created by using the following array as input:
     *     (3)
     * double[][] a = {{1}, {2}, {3}};
     * </pre>
     * <p>
     * Example 3 - row vector:
     * 
     * <pre>
     * v = (1 2 3), would be created by using the following array as input:
     * double[][] a = {{1, 2, 3}};
     * </pre>
     * 
     * @param matrix given matrix as array which is wrapped by this constructed instance
     */
    public DoubleMatrix(double[][] matrix) {
        Objects.requireNonNull(matrix);
        if (matrix.length < 1) {
            throw new IllegalArgumentException(
                    "The first dimension of the matrix 'm' has to be true positive (> 0).");
        }
        if (matrix[0].length < 1) {
            throw new IllegalArgumentException(
                    "The second dimension of the matrix 'n' has to be true positive (> 0).");
        }
        this.matrix = matrix;
    }

    /**
     * Calculates the transpose of this matrix.
     * 
     * <pre>
     * Ex.
     *              [1 4]
     * [1 2 3]  ->  [2 5]
     * [4 5 6]      [3 6]
     * </pre>
     */
    public void transpose() {
        int m = getRowCount();
        int n = getColumnCount();
        double[][] transpose = new double[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        matrix = transpose;
    }

    /**
     * Multiplies each field of the matrix by the given <code>scalar</code>.
     * 
     * @param scalar value which is multiplied to each matrix field
     */
    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                matrix[i][j] *= scalar;
            }
        }
    }

    /**
     * Adds the given matrix (2. summand) to this matrix (1. summand) from the right. The sum
     * becomes the new value of this matrix.
     * <p>
     * <code>A = A + B</code> (where A is this matrix and B is the given matrix)
     * 
     * @param mat given matrix which is multiplied to this matrix
     * @param mat
     */
    public void addMatrix(DoubleMatrix mat) {
        Objects.requireNonNull(mat);
        int m = getRowCount();
        int n = getColumnCount();
        int mat_m = mat.getRowCount();
        int mat_n = mat.getColumnCount();
        if (m != mat_m) {
            throw new IllegalMatrixOperationException("The first dimension 'm' of this matrix was ["
                    + m + "] and the first dimension of the given matrix was [" + mat_m
                    + "], but they have to be equal.");
        }
        if (n != mat_n) {
            throw new IllegalMatrixOperationException(
                    "The second dimension 'n' of this matrix was [" + n
                            + "] and the second dimension of the given matrix was [" + mat_n
                            + "], but they have to be equal.");
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] += mat.matrix[i][j];
            }
        }
    }

    /**
     * Multiplies this matrix (as multiplier) with a given matrix (as multiplicand) from the right.
     * The result becomes the new value of this matrix.
     * <p>
     * <code>A = A * B</code> (where A is this matrix and B is the given matrix)
     * 
     * @param mat given matrix which is multiplied to this matrix
     */
    public void multiplyByMatrix(DoubleMatrix mat) {
        Objects.requireNonNull(mat);
        int m = getRowCount();
        int n = getColumnCount();
        int mat_m = mat.getRowCount();
        int mat_n = mat.getColumnCount();
        if (n != mat_m) {
            throw new IllegalMatrixOperationException("The second dimension 'n' of this matrix is ["
                    + n + "] and the first dimension 'm' of the given matrix is [" + mat_m
                    + "], but they have to be equal.");
        }
        double[][] result = new double[m][mat_n];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += (matrix[i][k] * mat.matrix[k][j]);
                }
            }
        }
        matrix = result;
    }

    /**
     * Calculates the transpose of this matrix.
     * 
     * <pre>
     * Ex.
     *              [1 4]
     * [1 2 3]  ->  [2 5]
     * [4 5 6]      [3 6]
     * </pre>
     */
    public static DoubleMatrix transposeMatrix(DoubleMatrix matrix) {
        Objects.requireNonNull(matrix);
        int m = matrix.getRowCount();
        int n = matrix.getColumnCount();
        double[][] transpose = new double[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transpose[j][i] = matrix.matrix[i][j];
            }
        }
        return new DoubleMatrix(transpose);
    }

    /**
     * Multiplies the given <code>matrix</code> by the given <code>scalar</code> and returns the
     * result as a new {@link DoubleMatrix}-instance.
     * 
     * @param matrix
     * @param scalar
     * @return
     */
    public static DoubleMatrix multiplyMatrixByScalar(DoubleMatrix matrix, double scalar) {
        Objects.requireNonNull(matrix);
        int m = matrix.getRowCount();
        int n = matrix.getColumnCount();
        double[][] result = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = matrix.matrix[i][j] * scalar;
            }
        }
        return new DoubleMatrix(result);
    }

    /**
     * Performs the operation <code>C = A + B</code> where the second matrix <code>b</code> is added
     * to the first matrix <code>a</code> from the right. The result is returned as a new
     * {@link DoubleMatrix}-instance.
     * 
     * @param a 1. summand matrix
     * @param b 2. summand matrix
     * @return sum <code>C</code> as the result
     */
    public static DoubleMatrix sumMatrices(DoubleMatrix a, DoubleMatrix b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        int a_m = a.getRowCount();
        int a_n = a.getColumnCount();
        int b_m = b.getRowCount();
        int b_n = b.getColumnCount();
        if (a_m != b_m) {
            throw new IllegalMatrixOperationException("The first dimension 'm' of matrix 'a' was ["
                    + a_m + "] and the first dimension of the matrix 'b' was [" + b_m
                    + "], but they have to be equal.");
        }
        if (a_n != b_n) {
            throw new IllegalMatrixOperationException("The second dimension 'n' of matrix 'a' was ["
                    + a_n + "] and the second dimension of the matrix 'b' was [" + b_n
                    + "], but they have to be equal.");
        }
        double[][] result = new double[a_m][a_n];
        for (int i = 0; i < a_m; i++) {
            for (int j = 0; j < a_n; j++) {
                result[i][j] = a.matrix[i][j] + b.matrix[i][j];
            }
        }
        return new DoubleMatrix(result);
    }

    /**
     * Performs the operation <code>C = A * B</code> where the first given matrix <code>a</code> is
     * multiplied by the second given matrix <code>b</code> from the right. The result is returned
     * as a new {@link DoubleMatrix}-instance.
     * 
     * @param a multiplier matrix
     * @param b multiplicand matrix
     * @return product <code>C</code> of the multiplication.
     */
    public static DoubleMatrix multiplyMatrices(DoubleMatrix a, DoubleMatrix b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        int a_m = a.getRowCount();
        int a_n = a.getColumnCount();
        int b_m = b.getRowCount();
        int b_n = b.getColumnCount();
        if (a_n != b_m) {
            throw new IllegalMatrixOperationException(
                    "The second dimension 'n' of the matrix 'a' is [" + a_n
                            + "] and the first dimension 'm' of the matrix 'b' is [" + b_m
                            + "], but they have to be equal.");
        }
        double[][] result = new double[a_m][b_n];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < a_n; k++) {
                    result[i][j] += (a.matrix[i][k] * b.matrix[k][j]);
                }
            }
        }
        return new DoubleMatrix(result);
    }

    /**
     * Sets the value of the field <code>[i, j]</code>.
     * 
     * @param i row index
     * @param j column index
     * @param value value which si set for the field
     * @return
     * @throws ArrayOutOfBoundException
     */
    public void setField(int i, int j, double value) {
        matrix[i][j] = value;
    }

    /**
     * Return the value of the field <code>[i, j]</code>.
     * 
     * @param i row index
     * @param j column index
     * @return
     * @throws ArrayOutOfBoundException
     */
    public double getField(int i, int j) {
        return matrix[i][j];
    }

    /**
     * Returns the underlying matrix. The first dimension of the array (<code>matrix.length</code> )
     * corresponds to <code>m</code> while the second dimension (<code>matrix[0].length</code>)
     * corresponds to <code>n</code> for the <code>m</code> x <code>n</code> -matrix (with
     * <code>m</code> rows and <code>n</code> columns).
     * 
     * @return
     */
    public double[][] getMatrrixAsArray() {
        return matrix;
    }

    /**
     * Returns the number of rows of the matrix/ the first dimension <code>m</code> of the matrix.
     * 
     * @return
     */
    public int getRowCount() {
        return matrix.length;
    }

    /**
     * Returns the number of columns of the matrix/ the second dimension <code>n</code> of the
     * matrix.
     * 
     * @return
     */
    public int getColumnCount() {
        return matrix[0].length;
    }

    /**
     * Returns a string representation of this matrix, which shows the value of all fields and is
     * designed as a text visualization of the matrix.
     * 
     * <pre>
     * Ex. - (ij) defines index combinations.
     *          n = 2
     * m = 3 [(00), (01)]
     *       [(10), (11)]
     *       [(20), (21)]
     * </pre>
     * 
     * @return
     */
    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        int m = getRowCount();
        int n = getColumnCount();
        int[] maxTextLengthPerColumn = new int[n];
        String[][] valuesAsText = new String[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                valuesAsText[i][j] = String.format(Locale.US, "%.3f", matrix[i][j]);
                int length = valuesAsText[i][j].length();
                if (length > maxTextLengthPerColumn[j]) {
                    maxTextLengthPerColumn[j] = length;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%" + maxTextLengthPerColumn[j] + "s", valuesAsText[i][j]));
                if (j < n - 1) {
                    sb.append(", ");
                }
            }
            sb.append(']');
            if (i < m - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    /**
     * Return <tt>true</tt> if matrix is either a row or a column vector, <tt>false</tt> otherwise.
     * 
     * @return
     */
    public boolean isVector() {
        return getRowCount() == 1 || getColumnCount() == 1;
    }

    /**
     * Return <tt>true</tt> if matrix is a row vector and <tt>false</tt> otherwise.
     * 
     * @return
     */
    public boolean isRowVector() {
        return getRowCount() == 1;
    }

    /**
     * Return <tt>true</tt> if matrix is a row vector and <tt>false</tt> otherwise.
     * 
     * @return
     */
    public boolean isColumnVector() {
        return getColumnCount() == 1;
    }

    /**
     * Return <tt>true</tt> if matrix the matrix only holds a single value and <tt>false</tt>
     * otherwise.
     * 
     * @return
     */
    public boolean isScalar() {
        return getRowCount() == 1 && getColumnCount() == 1;
    }

    /**
     * Returns the single value hold by a 1 x 1 matrix.
     * 
     * @return
     * @throws IllegalMatrixOperationException if matrix is not of dimension 1 x 1
     */
    public double convertToScalar() {
        if (!isScalar()) {
            throw new IllegalMatrixOperationException("Matrix holds more than one value"
                    + " (is not of dimension 1x1) and can not be converted into a scalar.");
        }
        return matrix[0][0];
    }
}
