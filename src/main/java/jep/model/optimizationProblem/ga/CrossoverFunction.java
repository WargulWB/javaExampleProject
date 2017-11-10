package jep.model.optimizationProblem.ga;

import jep.model.optimizationProblem.OptimizationProblem;

/**
 * This functional interface defines a crossover function of a {@link GeneticAlgorithm}. A crossover
 * function defines how a new population (child generation) is to be generated via a given previous
 * population (parent population).
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface CrossoverFunction<T extends OptimizationProblem> {

    /**
     * Returns a new child population using the given parent population.
     * 
     * @param parentPopulation {@link Population}-instance from which individuals (parents) are
     *        picked to be paired to generate a child
     * @return
     */
    Population<T> crossover(Population<T> parentPopulation);

}
