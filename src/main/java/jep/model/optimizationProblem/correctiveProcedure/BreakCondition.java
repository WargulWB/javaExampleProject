package jep.model.optimizationProblem.correctiveProcedure;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This functional interface defines a condition which defines at which point a corrective procedure
 * is to be terminated.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this condition is defined for
 */
@FunctionalInterface
public interface BreakCondition<T extends OptimizationProblem> {

    /**
     * Returns <code>true</code> if this break condition is fulfilled and <code>false</code>
     * otherwise.
     * 
     * @param currentSolution currently stored {@link Solution}-instance (last accepted solution)
     * @param iterationCount count of iteration of the corrective procedure
     * @param iterationWithAcceptanceCount count of iterations whose accepted a new solution of the
     *        corrective procedure
     * @return
     */
    boolean isFulfilled(Solution<T> currentSolution, long iterationCount,
            long iterationWithAcceptanceCount);

}
