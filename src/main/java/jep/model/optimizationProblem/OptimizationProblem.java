package jep.model.optimizationProblem;

/**
 * This interface defines a general optimization problem. Actual optimization problem
 * implementations are to implement this interface.
 *
 */
public interface OptimizationProblem {

    /**
     * Enum which defines the type of an optimization problem, which can either be a
     * {@link #MAXIMIZATION} or a {@link #MINIMIZATION} problem.
     *
     */
    public enum ProblemType {
        /**
         * States: smaller fitness is better.
         */
        MINIMIZATION,

        /**
         * States: higher fitness is better.
         */
        MAXIMIZATION;
    }

    /**
     * Returns the {@link ProblemType} of this {@link OptimizationProblem}.
     * 
     * @return
     */
    ProblemType getType();

}
