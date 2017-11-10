package jep.model.optimizationProblem.correctiveProcedure;

import java.util.Objects;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.OptimizationProblem;

/**
 * This class implements the threshold accepting procedure. Which is a corrective procedure using a
 * threshold - which is decreased over time- to allow the acceptance of worse iterations (within the
 * threshold-radius).
 *
 * @param <T> specific {@link OptimizationProblem}-instance this procedure is to be used for
 */
public class ThresholdAcceptingProcedure<T extends OptimizationProblem>
        extends AbstractCorrectiveProcedure<T> {

    private final ThresholdSinkingFunction thresholdSinkingFunction;

    /**
     * Constructs a new {@link ThresholdAcceptingProcedure}-instance.
     * 
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
     * @param thresholdSinkingFunction {@link ThresholdSinkingFunction}-instance which handles the
     *        threshold logic
     * @param thresholdBreakCondition algorithm specific break condition which is terminate the
     *        procedure parallel to the "general" break condition
     */
    public ThresholdAcceptingProcedure(InitialSolutionConstructor<T> initialSolutionCosntructor,
            FitnessComparator<T> fitnessComperator, NeighborFunction<T> neighborFunction,
            BreakCondition<T> breakCondition, ThresholdSinkingFunction thresholdSinkingFunction,
            ThresholdBreakCondition thresholdBreakCondition) {
        super(initialSolutionCosntructor, fitnessComperator, neighborFunction,
                (currentSolution, neighbor, comperator) -> {
                    if (fitnessComperator.getFitnessDifference(currentSolution, neighbor)
                            - thresholdSinkingFunction.getThreshold() <= 0) {
                        return true;
                    } else {
                        thresholdSinkingFunction.notifyAboutWorseIteration();
                        return false;
                    }
                }, (currentSolution, iterationCount, iterationWithAcceptanceCount) -> {
                    return breakCondition.isFulfilled(currentSolution, iterationCount,
                            iterationWithAcceptanceCount)
                            || thresholdBreakCondition
                                    .isFulfilled(thresholdSinkingFunction.getThreshold());
                });
        this.thresholdSinkingFunction = Objects.requireNonNull(thresholdSinkingFunction);
    }

    @Override
    public void reset() {
        thresholdSinkingFunction.reset();
    }

}
