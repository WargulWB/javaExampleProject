package jep.model.optimizationProblem;

/**
 * This class implements an exception which is to be thrown in cases were an illegal solution is
 * constructed,
 *
 */
public class IllegalSolutionException extends RuntimeException {

    private static final long serialVersionUID = 453406143286456137L;

    /**
     * Constructs a new {@link IllegalSolutionException}-instance.
     * 
     * @param text text set for this exception
     */
    public IllegalSolutionException(String text) {
        super(text);
    }

}
