package jep.model.optimizationProblem;

import java.util.Objects;

/**
 * Abstract class which implements a general constructor for the {@link Solution}-interface.
 *
 * @param <T> problem for which this solution is defined
 */
public abstract class AbstractSolution<T extends OptimizationProblem> implements Solution<T> {

    private final T problem;

    /**
     * Constructs a new solution for the given <code>problem</code>.
     * 
     * @param problem for which this solution is constructed
     */
    public AbstractSolution(T problem) {
        this.problem = Objects.requireNonNull(problem);
    }

    /**
     * Returns the problem for which this solution is constructed.
     * 
     * @return
     */
    public T getProblem() {
        return problem;
    }
}
