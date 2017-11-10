package jep.model.optimizationProblem.correctiveProcedure;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.OptimizationProblem;

public class SimulatedAnnealingProcedure<T extends OptimizationProblem>
        extends AbstractCorrectiveProcedure<T> {

    private final AnnealingFunction<T> annealingFunction;

    public SimulatedAnnealingProcedure(InitialSolutionConstructor<T> initialSolutionCosntructor,
            FitnessComparator<T> fitnessComperator, NeighborFunction<T> neighborFunction,
            AnnealingFunction<T> annealingFunction, BreakCondition<T> breakCondition) {
        super(initialSolutionCosntructor, fitnessComperator, neighborFunction,
                (currentSolution, neighbor, fitnessComp) -> {
                    if (fitnessComp.checkIfFirstIsBetterOrEqual(neighbor, currentSolution)) {
                        return true;
                    } else if (annealingFunction.acceptWithLikelihood(neighbor, currentSolution)) {
                        return true;
                    } else {
                        annealingFunction.notifyAboutWorseIteration();
                    }
                    return false;
                }, breakCondition);
        this.annealingFunction = annealingFunction;
    }

    @Override
    public void reset() {
        annealingFunction.reset();
    }

}
