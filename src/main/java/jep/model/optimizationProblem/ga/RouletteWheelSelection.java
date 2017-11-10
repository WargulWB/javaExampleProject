package jep.model.optimizationProblem.ga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This class provides a specific implementation of the {@link SelectionFunction}-interface called
 * "RouletteWheelSelection". Each individual/candidate is assigned a probability to be picked. This
 * probability is proportional to the fitness of the candidate in comparison to the fitness of the
 * other candidates. This can be thought of as a pie-chart-diagram displaying the probabilities. We
 * iterate as often as we want members in the next generation each time picking a candidate with its
 * given probability. We can think of this as picking a random point on the diameter of the pie
 * chart and than choosing the candidate whose "pie" contains this point.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class RouletteWheelSelection<T extends OptimizationProblem> implements SelectionFunction<T> {

    private final Random random = new Random();
    private final boolean allowParentSelection;
    private final int targetPopulationSize;

    /**
     * Constructs a new {@link RouletteWheelSelection}-instance.
     * 
     * @param allowParentSelection if <code>true</code> the parent and child population are "merged"
     *        and the selection picks from this set of candidates, if <code>false</code> only
     *        candidates of the child population are selected
     * @param targetPopulationSize size of the generated population, number of
     *        individuals/candidates to be selected. Note the size has to be >= 1.
     */
    public RouletteWheelSelection(boolean allowParentSelection, int targetPopulationSize) {
        if (targetPopulationSize < 1) {
            throw new IllegalArgumentException(
                    "Target population size has to be greaterthan or equal to 1.");
        }
        this.allowParentSelection = allowParentSelection;
        this.targetPopulationSize = targetPopulationSize;
    }

    @Override
    public Population<T> select(Population<T> parentPopulation, Population<T> childPopulation) {
        assert parentPopulation.size() >= 1 && childPopulation.size() >= 1;
        Population<T> selectableIndividuals;
        if (!allowParentSelection) {
            selectableIndividuals = childPopulation;
        } else {
            selectableIndividuals = Population.merge(parentPopulation, childPopulation);
        }

        int range = selectableIndividuals.size();
        double[] fitnessValues = new double[range];
        Iterator<Solution<T>> itr =
                selectableIndividuals.getIndividualsAsUnmodifiableList().iterator();
        double fitnessSum = 0;
        for (int i = 0; i < range; i++) {
            fitnessValues[i] = itr.next().getFitness();
            fitnessSum += fitnessValues[i];
        }

        fitnessValues[0] /= fitnessSum;
        fitnessValues[range - 1] = 1; // to avoid numerical issues
        for (int i = 1; i < range - 1; i++) {
            // normalize fitness values and construct probability intervals by storing
            // there upper inclusive border
            fitnessValues[i] = fitnessValues[i] / fitnessSum + fitnessValues[i - 1];
        }

        List<Solution<T>> selectedIndividuals = new ArrayList<>(targetPopulationSize);
        for (int i = 0; i < targetPopulationSize; i++) {
            double rnd = random.nextDouble();
            for (int j = 0; j < range; j++) {
                if (rnd <= fitnessValues[j]) {
                    selectedIndividuals
                            .add(selectableIndividuals.getIndividualsAsUnmodifiableList().get(j));
                    break;
                }
            }
        }
        
        return new Population<>(selectedIndividuals);
    }

}
