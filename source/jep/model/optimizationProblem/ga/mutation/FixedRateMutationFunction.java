package jep.model.optimizationProblem.ga.mutation;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.ga.Population;

/**
 * This class implements a {@link MutationFunction} which is using a fixed mutation rate. The
 * mutation rate being the probability of an individual of a population to be picked for mutation.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this function is to be used for
 */
public class FixedRateMutationFunction<T extends OptimizationProblem>
        implements MutationFunction<T> {

    private final double rate;
    private final MutationRule<T> mutationRule;
    private final Random random = new Random();

    /**
     * Constructs a new {@link FixedRateMutationFunction}-instance using the given <code>rate</code>
     * as mutation rate and the given <code>mutationRule</code> as the rule of which is applied to a
     * selected individual.
     * 
     * @param rate probability of a individual of a population to be picked for mutation. Has to be
     *        picked within (0,1].
     * @param mutationRule {@link MutationRule} which is applied to a individual which was selected
     *        to be mutated
     */
    public FixedRateMutationFunction(double rate, MutationRule<T> mutationRule) {
        if (rate <= 0 || rate > 1) {
            throw new IllegalArgumentException(
                    "The fixed mutation rate has to be in the range (0, 1].");
        }
        this.rate = rate;
        this.mutationRule = Objects.requireNonNull(mutationRule);
    }

    @Override
    public Population<T> mutate(Population<T> population) {
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
