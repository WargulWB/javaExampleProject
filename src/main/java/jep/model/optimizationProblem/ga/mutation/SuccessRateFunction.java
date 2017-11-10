package jep.model.optimizationProblem.ga.mutation;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements a success rate function. This function looks at multiple generations and
 * calculates the rate of successful improvements of a child generation over its parent generation.
 * A {@link SuccessFunction}-instance is used to determine if one population is "better" than
 * another.
 * 
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class SuccessRateFunction<T extends OptimizationProblem> {

    /**
     * Constructs a new {@link SuccessRateFunction}-instance.
     */
    public SuccessRateFunction() {}

    /**
     * Calculates the rate of success over the given <code>generations</code> using the given
     * <code>successFunction</code>
     * 
     * @param generations sorted list of generations where generation at list index 0 is the parent
     *        population of the child population at list index 1
     * @param successFunction {@link SuccessFunction}-instance used to determine if one population
     *        is "better" than another
     * @return
     */
    public double calculateSuccessRate(List<Population<T>> generations,
            SuccessFunction<T> successFunction) {
        Objects.requireNonNull(generations);
        Objects.requireNonNull(successFunction);
        if (generations.size() <= 1) {
            throw new IllegalArgumentException(
                    "List of generations has to contain at least 2 generations.");
        }
        int succesCount = 0;
        Iterator<Population<T>> itr = generations.iterator();
        Population<T> previous = itr.next();
        while (itr.hasNext()) {
            Population<T> current = itr.next();
            if (successFunction.wasSuccessfulImprovement(previous, current)) {
                succesCount++;
            }
            previous = current;
        }
        // generation size -1 is equal to the number of total comparisons
        return ((double) succesCount) / (generations.size() - 1);
    }

}
