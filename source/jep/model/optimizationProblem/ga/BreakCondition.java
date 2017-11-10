package jep.model.optimizationProblem.ga;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This functional interface defines a condition used to state if a {@link GeneticAlgorithm} is to
 * be terminated or if it is to continue.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this condition is to be used for
 */
@FunctionalInterface
public interface BreakCondition<T extends OptimizationProblem> {

    /**
     * Returns <code>true</code> if the condition is fulfilled and <code>false</code> otherwise.
     * 
     * @param currentPopulation {@link Population} of the current genetic algorithm iteration/
     *        generation
     * @param iterationCount counter of genetic algorithm iterations
     * @param bestFoundSolution individual of all generations with the best fitness value
     * @return
     */
    boolean isFulfilled(Population<T> currentPopulation, long iterationCount,
            Solution<T> bestFoundSolution);
}
