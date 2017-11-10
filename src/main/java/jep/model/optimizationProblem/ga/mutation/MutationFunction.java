package jep.model.optimizationProblem.ga.mutation;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.GeneticAlgorithm;
import jep.model.optimizationProblem.ga.Population;

/**
 * This functional interface defines a mutation function for a {@link GeneticAlgorithm}
 * -implementation. Such function shall pick individuals of a given population and mutate those.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface MutationFunction<T extends OptimizationProblem> {

    /**
     * Returns a new population which consists of individuals of the given population with some of
     * them/or all of them being mutated.
     * 
     * @param population {@link Population}-instance whose individuals are picked and mutated to
     *        generate a new population
     * @return
     */
    Population<T> mutate(Population<T> population);

}
