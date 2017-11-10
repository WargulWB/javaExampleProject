package jep.model.optimizationProblem.correctiveProcedure;

import java.util.Objects;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This abstract class implements the general constructor and {@link #run(ResultMode)}-method
 * required for a corrective procedure. Actual corrective procedure implementations are to extend
 * this class.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this procedure is to be used for
 */
public abstract class AbstractCorrectiveProcedure<T extends OptimizationProblem>
        implements CorrectiveProcedure<T> {

    private final FitnessComparator<T> fitnessComparator;
    private final NeighborFunction<T> neighborFunction;
    private final AcceptanceFunction<T> acceptanceFunction;
    private final BreakCondition<T> breakCondition;
    private final Solution<T> initialSolution;
    private Solution<T> bestSolution;
    long totalIterationsCount = 0;

    /**
     * Constructs a new {@link AbstractCorrectiveProcedure}-instance.
     * 
     * @param problem specific {@link OptimizationProblem}-instance this procedure is to be used for
     * @param initialSolutionCosntructor {@link InitialSolutionConstructor}-instance which is used
     *        to construct the initial solution
     * @param fitnessComperator {@link FitnessComparator}-instance used to compare the fitness of
     *        two solutions to validate if a neighbor is accepted as new solution
     * @param neighborFunction {@link NeighborFunction}-instance which is used to construct
     *        neighbors
     * @param acceptanceFunction {@link AcceptanceFunction}-instance which is used to validate if a
     *        new neighbor is accepted
     * @param breakCondition {@link BreakCondition}-instance which is used to check if the procedure
     *        is to be terminated
     */
    public AbstractCorrectiveProcedure(
            InitialSolutionConstructor<T> initialSolutionCosntructor,
            FitnessComparator<T> fitnessComperator, NeighborFunction<T> neighborFunction,
            AcceptanceFunction<T> acceptanceFunction, BreakCondition<T> breakCondition) {
        this.fitnessComparator = Objects.requireNonNull(fitnessComperator);
        this.neighborFunction = Objects.requireNonNull(neighborFunction);
        this.acceptanceFunction = Objects.requireNonNull(acceptanceFunction);
        this.breakCondition = Objects.requireNonNull(breakCondition);
        this.initialSolution = initialSolutionCosntructor.getInitialSolution();
        this.bestSolution = initialSolution;
    }

    @Override
    public Solution<T> run(ResultMode resultMode) {
        totalIterationsCount = 0;
        long iterationWithAcceptanceCount = 0;
        Solution<T> currentSolution = initialSolution;
        while (!breakCondition.isFulfilled(currentSolution, totalIterationsCount,
                iterationWithAcceptanceCount)) {
            Solution<T> neighbor = neighborFunction.constructNeighbor(currentSolution);
            if (acceptanceFunction.accept(currentSolution, neighbor, fitnessComparator)) {
                currentSolution = neighbor;
                iterationWithAcceptanceCount++;
            }
            if (resultMode == ResultMode.TOTAL_BEST
                    && fitnessComparator.compareSolutions(neighbor, bestSolution) == 1) {
                bestSolution = neighbor;
            }
            totalIterationsCount++;
        }
        reset();
        switch (resultMode) {
            case LAST:
                return currentSolution;
            case TOTAL_BEST:
                return bestSolution;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public long getNumberOfIterations() {
        return totalIterationsCount;
    }



}
