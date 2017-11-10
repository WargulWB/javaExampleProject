package jep.model.optimizationProblem.ga;

import jep.model.optimizationProblem.OptimizationProblem;

/**
 * This class implements a utility implementation of a {@link CrossoverFunction} which skips the
 * crossover step. This class is to be used if a {@link GeneticAlgorithm}-implementation is used
 * which does not need crossover.
 * 
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class NoCrossoverFunction<T extends OptimizationProblem> implements CrossoverFunction<T> {

    /**
     * Constructs a new {@link NoCrossoverFunction}-instance.
     */
    public NoCrossoverFunction() {}

    /**
     * Returns the given <code>parentPopulation</code>.
     * 
     * @param parentPopulation given population which is returned
     * @return
     */
    @Override
    public Population<T> crossover(Population<T> parentPopulation) {
        return parentPopulation;
    }

}
