package jep.model.optimizationProblem;

/**
 * This functional interface defines a constructor of the initial solution of a specific
 * {@link OptimizationProblem}-instance, allowing to easily change how an initial solution is
 * constructed. Actual implementation could be either fixed manually found solutions, heuristic or
 * random approaches.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this constructor is to be used for
 * @see Solution
 */
@FunctionalInterface
public interface InitialSolutionConstructor<T extends OptimizationProblem> {

    /**
     * Returns the constructed initial {@link Solution}-instance.
     * 
     * @return
     */
    Solution<T> getInitialSolution();

}
