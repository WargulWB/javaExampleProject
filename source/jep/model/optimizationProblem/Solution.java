package jep.model.optimizationProblem;

/**
 * This interface defines a general solution setup. Actual solution implementations are to implement
 * this interface.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this solution is defined for
 */
public interface Solution<T extends OptimizationProblem>{

    /**
     * Returns a problem specific string representation.
     * 
     * @return
     */
    String getStringRepresentation();

    /**
     * Returns the fitness of this solution.
     * 
     * @return
     */
    double getFitness();

}
