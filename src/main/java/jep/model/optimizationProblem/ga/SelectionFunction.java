package jep.model.optimizationProblem.ga;

import jep.model.optimizationProblem.OptimizationProblem;

/**
 * This functional interface defines a selection function for a {@link GeneticAlgorithm}
 * -implementation. A selection function defines which individuals of the current
 * iterations/generations child and parent population should be selected as population of the new
 * generation/next iteration.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface SelectionFunction<T extends OptimizationProblem> {

    /**
     * Returns a new population which consists of individuals selected from the given
     * <code>childPopulation</code> (or from the given <code>childPopulation</code> and
     * <code>parentPopulation</code>).
     * 
     * @param parentPopulation population of the last generation (individuals of this generation can
     *        but do not have to be selected)
     * @param childPopulation child population generated via crossover and mutation (using the
     *        parentPopulation as parents) from which individuals should be picked (at least with
     *        some probability)
     * @return
     */
    Population<T> select(Population<T> parentPopulation, Population<T> childPopulation);

}
