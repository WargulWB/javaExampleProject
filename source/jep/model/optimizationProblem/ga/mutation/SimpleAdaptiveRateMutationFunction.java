package jep.model.optimizationProblem.ga.mutation;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements a simple {@link AdaptiveRateMutationFunction} by using a
 * {@link RosenbergAdaptiveRateChangeRule} as {@link AdaptiveRateChangeRule}-instance, a
 * {@link RosenbergSuccessValidator} as {@link SuccessValidator}-instance and a
 * {@link SuccessRateFunction}-instance to calculate the success rate.
 * 
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class SimpleAdaptiveRateMutationFunction<T extends OptimizationProblem>
        extends AdaptiveRateMutationFunction<T> {

    /**
     * Constructs a new {@link SimpleAdaptiveRateMutationFunction}-instance.
     * 
     * @param initialRate initial mutation rate which is altered by this function during the
     *        progress of the genetic algorithm this function is used for (has to be within (0, 1])
     * @param iterationsUntilChange number of iterations until an mutation rate alteration is to be
     *        applied
     * @param mutationRule {@link MutationRule} which is applied to a individual which was selected
     *        to be mutated
     * @param initialPopulation initial population of the {@link GeneticAlgorithm}-instance this
     *        function is used for. This population is needed to be able to make a comparison of the
     *        first iteration.
     */
    public SimpleAdaptiveRateMutationFunction(double initialRate, int iterationsUntilChange,
            MutationRule<T> mutationRule, Population<T> initialPopulation) {
        super(initialRate, new RosenbergAdaptiveRateChangeRule(), iterationsUntilChange,
                new PopulationMeanSuccessFunction<>(), new SuccessRateFunction<>(),
                new RosenbergSuccessValidator(), mutationRule, initialPopulation);
    }

}
