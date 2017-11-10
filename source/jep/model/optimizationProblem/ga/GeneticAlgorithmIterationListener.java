package jep.model.optimizationProblem.ga;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This functional interface defines a listener which can be added to a {@link GeneticAlgorithm}
 * -instance. This algorithm should than call the {@link #update(Population)}-method of this
 * listener at each iteration (after the selection-step is done) informing this listener about he
 * current generation.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this listener is to be used for
 */
@FunctionalInterface
public interface GeneticAlgorithmIterationListener<T extends OptimizationProblem> {

    /**
     * This method is called after each iteration of the {@link GeneticAlgorithm}-instance this
     * listener was added to informing this listener about the current algorithm state.
     * <p>
     * (The method is also called once (before the {@link GeneticAlgorithm} starts its loop) for the
     * initialPopulation, its best individual and itCount being -1.)
     * 
     * @param currentPopulation population of the current genetic algorithm iteration/generation
     * @param currentBestIndividual best individual of the current and all previous generations
     * @param iterationCount count of the current generation/iteration
     */
    void update(Population<T> currentPopulation, Solution<T> currentBestIndividual,
            long iterationCount);

}
