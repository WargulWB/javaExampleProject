package jep.model.optimizationProblem.ga.mutation;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements a {@link MutationFunction} which is using a dynamic mutation rate. The
 * mutation rate being the probability of an individual of a population to be picked for mutation.
 * Dynamic means the mutation rate is changed over the course of genetic algorithm iterations.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class DynamicRateMutationFunction<T extends OptimizationProblem>
        implements MutationFunction<T> {

    private final Random random = new Random();
    private final MutationRule<T> mutationRule;
    private final RateChangeRule rateChangeRule;
    private final int iterationsUntilChange;
    private double rate;
    private int itr = 0;

    /**
     * Constructs a new {@link DynamicRateMutationFunction}-instance using the given
     * <code>initialRate</code> as initial mutation rate, which is changed using the given
     * <code>rateChangeRule</code> after <code>iterationsUntilChange</code> number of iterations,
     * and the given <code>mutationRule</code> as the rule of which is applied to a selected
     * individual.
     * 
     * @param initialRate initial value of the mutation rate
     * @param rateChangeRule {@link RateChangeRule}-instance which defines how the mutation rate is
     *        changed
     * @param iterationsUntilChange number of iterations until the <code>rateChangeRule</code> is
     *        applied to the mutation rate
     * @param mutationRule {@link MutationRule} which is applied to a individual which was selected
     *        to be mutated
     */
    public DynamicRateMutationFunction(double initialRate, RateChangeRule rateChangeRule,
            int iterationsUntilChange, MutationRule<T> mutationRule) {
        if (initialRate <= 0 || initialRate > 1) {
            throw new IllegalArgumentException(
                    "The fixed mutation rate has to be in the range (0, 1].");
        }
        if (iterationsUntilChange <= 0) {
            throw new IllegalArgumentException(
                    "The iterations until change parameter has to be set as a true positive value (> 0).");
        }
        this.rate = initialRate;
        this.iterationsUntilChange = iterationsUntilChange;
        this.mutationRule = Objects.requireNonNull(mutationRule);
        this.rateChangeRule = Objects.requireNonNull(rateChangeRule);
    }

    @Override
    public Population<T> mutate(Population<T> population) {
        // mutation gets called each GA iteration therefore we can count this methods calls to get
        // the number of iterations
        if (itr >= iterationsUntilChange) {
            itr = 0;
            rate = rateChangeRule.changeRate(rate);
            assert rate > 0 || rate <= 1;
        } else {
            itr++;
        }
        return new Population<>(
                population.getIndividualsAsUnmodifiableList().stream().map((solution) -> {
                    if (random.nextDouble() < rate) {
                        return mutationRule.mutate(solution);
                    } else {
                        return solution;
                    }
                }).collect(Collectors.toList()));
    }

}
