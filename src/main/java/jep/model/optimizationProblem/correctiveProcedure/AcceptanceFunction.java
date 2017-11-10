package jep.model.optimizationProblem.correctiveProcedure;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This functional interface defines an acceptance function which checks if a neighbor is accepted.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface AcceptanceFunction<T extends OptimizationProblem> {

    /**
     * Returns <code>true</code> if the <code>neighbor</code> is accepted (comparing it to the
     * <code>currentSolution</code>) and <code>false</code> otherwise.
     * 
     * @param currentSolution which is compared to the neighbor
     * @param neighbor which is either going to be accepted or not
     * @param fitnessComparator comparator which can be used to compare the two solutions (some
     *        solutions might not require the comparator)
     * @return
     */
    boolean accept(Solution<T> currentSolution, Solution<T> neighbor,
            FitnessComparator<T> fitnessComparator);

}
