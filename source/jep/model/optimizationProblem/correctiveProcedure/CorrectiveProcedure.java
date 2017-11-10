package jep.model.optimizationProblem.correctiveProcedure;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This interface defines a general corrective procedure. Actual corrective procedure
 * implementations are to implement this interface.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this procedure is to be used for
 */
public interface CorrectiveProcedure<T extends OptimizationProblem> {

    /**
     * Enum which defines the result modes of an corrective procedure
     * {@link AbstractCorrectiveProcedure#run(ResultMode)}-method.
     * <ul>
     * <li>{@link #TOTAL_BEST}: Every iteration the neighbor is compared to the current best result,
     * if the neighbor has a better fitness than the current best solution, it is set as the current
     * best result. When the run-method finishes it returns the current best result.</li>
     * <li>{@link #LAST}: When the run-method finishes it returns the last result.</li>
     * </ul>
     *
     */
    public enum ResultMode {

        /**
         * Indicates that the total best result (best of initial solution and all found neighbors)
         * is to be returned.
         */
        TOTAL_BEST,

        /**
         * Indicates that the last result is to be returned.
         */
        LAST;
    }

    /**
     * Runs this corrective procedure for the given <code>resultMode</code>.
     * 
     * @param resultMode defines if the total best or the last result is to be returned
     * @return
     */
    Solution<T> run(ResultMode resultMode);

    /**
     * This procedure is called at the end of the run method and allows to reset procedure specific
     * variables.
     */
    void reset();

    /**
     * Returns the number of iterations of the last run.
     * 
     * @return
     */
    long getNumberOfIterations();
}
