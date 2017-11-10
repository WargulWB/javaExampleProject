package jep.model.optimizationProblem.correctiveProcedure;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This functional interface defines a method which constructs a new solution (a neighbor) by using
 * the current solution.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface NeighborFunction<T extends OptimizationProblem> {

    /**
     * Constructs a new {@link Solution}-instance by using the given <code>currentSolution</code>.
     * 
     * @param currentSolution which is used to construct the new solution (neighbor)
     * @return
     */
    Solution<T> constructNeighbor(Solution<T> currentSolution);

}
