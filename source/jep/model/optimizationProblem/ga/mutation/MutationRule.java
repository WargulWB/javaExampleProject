package jep.model.optimizationProblem.ga.mutation;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This functional interface defines a mutation rule for a {@link GeneticAlgorithm}-implementation.
 * This rule shall be used to apply mutation to a given single individual. Such rule would be used
 * in a {@link MutationFunction}-implementation where the function picks individuals of a population
 * in some way and uses the rule to mutate those.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface MutationRule<T extends OptimizationProblem> {

    /**
     * Mutates the given <code>individual</code> and returns the generated mutation.
     * 
     * @param individual individual which is mutated
     * @return
     */
    Solution<T> mutate(Solution<T> individual);

}
