package jep.model.optimizationProblem.ga.mutation;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.GeneticAlgorithm;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements a utility implementation of a {@link MutationFunction} which skips the
 * mutation step. This class is to be used if a {@link GeneticAlgorithm}-implementation is used
 * which does not need mutation.
 * <p>
 * <b>Warning:</b> A genetic algorithm without mutation will likely never examine the whole solution
 * space since crossover can only lead to so many different solutions depending on the initial
 * population.
 * 
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class NoMutationFunction<T extends OptimizationProblem> implements MutationFunction<T> {

    /**
     * Constructs a new {@link NoMutationFunction}-instance.
     */
    public NoMutationFunction() {}

    /**
     * Returns the given <code>population</code>.
     * 
     * @param population given population which is returned
     * @return
     */
    @Override
    public Population<T> mutate(Population<T> population) {
        return population;
    }

}
