package jep.model.optimizationProblem.ga.mutation;

/**
 * This functional interface defines a constant mutation rate change rule which defines how the
 * mutation rate is to be changed. This interface is designed for usage in a
 * {@link DynamicRateMutationFunction}.
 *
 */
@FunctionalInterface
public interface RateChangeRule {

    /**
     * Alters the given <code>rate</code> and returns the result.
     * 
     * @param rate given mutation rate which is altered and returned
     * @return
     */
    double changeRate(double rate);

}
