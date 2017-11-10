package jep.model.optimizationProblem.ga.mutation;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements {@link SuccessFunction} which calculates the mean over the fitness of the
 * individuals within the given populations per population and compares those means to determine if
 * the child population was "better" (had a better fitness) than the parent population).
 * 
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class PopulationMeanSuccessFunction<T extends OptimizationProblem>
        implements SuccessFunction<T> {

    private final FitnessComparator<T> fitnessComparator = new FitnessComparator<>();

    /**
     * Constructs a new {@link PopulationMeanSuccessFunction}-instance.
     */
    public PopulationMeanSuccessFunction() {}

    /**
     * Calculates the mean over the fitness values of the solutions in the parent population as well
     * as the mean fort the child population solutions fitness values. Returns <code>true</code> if
     * the "child-mean"
     * 
     * @see SuccessFunction#wasSuccessfulImprovement(Population,Population)
     */
    @Override
    public boolean wasSuccessfulImprovement(Population<T> parentPopulation,
            Population<T> childPopulation) {
        double parentPopFitnessMean = 0;
        for (Solution<T> solution : parentPopulation.getIndividualsAsUnmodifiableList()) {
            parentPopFitnessMean += solution.getFitness();
        }
        parentPopFitnessMean /= parentPopulation.size();
        double childPopFitnessMean = 0;
        for (Solution<T> solution : childPopulation.getIndividualsAsUnmodifiableList()) {
            childPopFitnessMean += solution.getFitness();
        }
        childPopFitnessMean /= childPopulation.size();
        return fitnessComparator.checkIfFirstIsBetterOrEqual(childPopFitnessMean,
                parentPopFitnessMean);
    }

}
