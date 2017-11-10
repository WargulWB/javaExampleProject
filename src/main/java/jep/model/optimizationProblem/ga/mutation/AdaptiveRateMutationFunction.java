package jep.model.optimizationProblem.ga.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements a {@link MutationFunction} which is using a adaptive mutation rate. The
 * mutation rate being the probability of an individual of a population to be picked for mutation.
 * Adaptive means the mutation rate is changed over the course of genetic algorithm iterations
 * depending of the successfulness of the last iterations.
 * <p>
 * For example, if most of the last few generations were successes (each generation was better than
 * the previous generation) than make the mutation rate greater to avoid local optima. If most of
 * the last few generations were failures (each generation was worse than the previous generation)
 * make the mutation rate smaller to avoid to much mutation. Otherwise keep the mutation rate as is.
 * 
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class AdaptiveRateMutationFunction<T extends OptimizationProblem>
        implements MutationFunction<T> {

    private final Random random = new Random();
    private final MutationRule<T> mutationRule;
    private final SuccessFunction<T> successFunction;
    private final SuccessRateFunction<T> successRateFunction;
    private final AdaptiveRateChangeRule rateChangeRule;
    private final SuccessValidator successValidator;
    private final List<Population<T>> generations;
    private final int iterationsUntilChange;
    private double rate;
    private int itr = 0;

    /**
     * Constructs a new {@link AdaptiveRateMutationFunction}-instance.
     * 
     * @param initialRate initial value of the mutation rate
     * @param rateChangeRule {@link AdaptiveRateChangeRule} which defines how to alter the mutation
     *        rate
     * @param iterationsUntilChange number of iterations until a mutation rate check is done,
     *        depending of the result the mutation rate is altered
     * @param successFunction {@link SuccessFunction} which defines if a child generation was a
     *        successful improvement to its parent generation
     * @param successRateFunction {@link SuccessRateFunction} used to determine the success rate of
     *        the last few iterations
     * @param successValidator {@link SuccessValidator} which states depending of the success rate
     *        if the last few iterations were successful improvements or failures
     * @param mutationRule {@link MutationRule} which is applied to a individual which was selected
     *        to be mutated
     * @param initialPopulation initial population of the {@link GeneticAlgorithm}-instance this
     *        function is used for. This population is needed to be able to make a comparison of the
     *        first iteration.
     */
    public AdaptiveRateMutationFunction(double initialRate, AdaptiveRateChangeRule rateChangeRule,
            int iterationsUntilChange, SuccessFunction<T> successFunction,
            SuccessRateFunction<T> successRateFunction, SuccessValidator successValidator,
            MutationRule<T> mutationRule, Population<T> initialPopulation) {
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
        this.successFunction = Objects.requireNonNull(successFunction);
        this.successRateFunction = Objects.requireNonNull(successRateFunction);
        this.successValidator = Objects.requireNonNull(successValidator);
        this.generations = new ArrayList<>(iterationsUntilChange);
        this.generations.add(Objects.requireNonNull(initialPopulation));
    }

    @Override
    public Population<T> mutate(Population<T> population) {
        // mutation gets called each GA iteration therefore we can count this methods calls to get
        // the number of iterations
        if (itr >= iterationsUntilChange) {
            itr = 0;
            rate = rateChangeRule.changeRate(rate, successValidator.validate(
                    successRateFunction.calculateSuccessRate(generations, successFunction)));
            generations.clear();
            generations.add(population);
            assert rate > 0 || rate <= 1;
        } else {
            generations.add(population);
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
