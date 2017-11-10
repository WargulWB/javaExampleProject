package jep.model.optimizationProblem.ga.mutation;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.Population;

/**
 * This functional interface defines a success function for a {@link GeneticAlgorithm}
 * -implementation. Such function determines if a child population is "better" than a parent
 * population. This information is needed for example needed for mutation functions using a adaptive
 * mutation rate (like {@link AdaptiveRateMutationFunction}.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
@FunctionalInterface
public interface SuccessFunction<T extends OptimizationProblem> {

    /**
     * Returns <code>true</code> if the <code>childPopulation</code> is "better" than the
     * <code>parentPopulation</code>.
     * <p>
     * One individual is better than another individual if its fitness is better (greater). The
     * "goodness" of a population normally is some kind of statistical measure over the "goodness"
     * of its individuals fitness, like mean of fitness or fitness of worst individual.
     * 
     * @param parentPopulation population which is compared to the <code>childPopulation</code>, if
     *        this population is better the new generation was a failure (no success)
     * @param childPopulation population which is compared to the <code>parentPopulation</code>, if
     *        this population is better the new generation (this generation) was a success
     * @return
     */
    public boolean wasSuccessfulImprovement(Population<T> parentPopulation,
            Population<T> childPopulation);

}
