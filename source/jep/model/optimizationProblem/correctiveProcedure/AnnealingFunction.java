package jep.model.optimizationProblem.correctiveProcedure;

import java.util.Objects;
import java.util.Random;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

public class AnnealingFunction<T extends OptimizationProblem> {

    private final FitnessComparator<T> fitnessComparator;
    private final int worseIterationThreshold;
    private final double initialTemperature;
    private final double alpha;
    private final Random rng = new Random();
    private int countOfWorseIterations = 0;
    private double temperature;

    public AnnealingFunction(FitnessComparator<T> fitnessComparator,
            double initialTemperature, double alpha, int worseIterationThreshold) {
        validate(initialTemperature, worseIterationThreshold, alpha);
        this.fitnessComparator = Objects.requireNonNull(fitnessComparator);
        this.worseIterationThreshold = worseIterationThreshold;
        this.alpha = alpha;
        this.temperature = initialTemperature;
        this.initialTemperature = initialTemperature;
    }

    private void validate(double temperature, double worseIterationThreshold, double alpha) {
        if (temperature <= 0) {
            throw new IllegalArgumentException(
                    "The temperature has to be set as a true positive value (> 0).");
        }
        if (worseIterationThreshold <= 0) {
            throw new IllegalArgumentException(
                    "The worse iteration threshold has to be true positive (> 0).");
        }
        if (alpha <= 0 || alpha >= 1) {
            throw new IllegalArgumentException(
                    "Alpha has to be set as value within 0 < alpha < 1, optimaly as value within [0.8, 0.99].");
        }
    }

    /**
     * Increments the count of worse iterations and sink the temperature if the count exceedds the
     * worse iteration count threshold.
     * 
     */
    public void notifyAboutWorseIteration() {
        countOfWorseIterations++;
        sinkTemperaturIfRequired();
    }

    /**
     * Computes the likelihood of solution <code>a</code> to be accepted as new solution (compared
     * to <code>b</code> as current solution). Computes a random value and returns <code>true</code>
     * if the random value is within the likelihood and <code>false</code> otherwise.
     * 
     * @param a first solution which probably will be accepted
     * @param b second solution which is compared to the solution <code>a</code>
     * @return
     */
    public boolean acceptWithLikelihood(Solution<T> a, Solution<T> b) {
        double difference = fitnessComparator.getFitnessDifference(a, b);
        double likelihood = Math.exp(difference / temperature); // TODO check if correct
        double random = rng.nextDouble();
        return random <= likelihood;
    }

    private void sinkTemperaturIfRequired() {
        if (countOfWorseIterations >= worseIterationThreshold) {
            temperature *= alpha;
            countOfWorseIterations = 0;
        }
    }

    /**
     * Resets temperature as initial temperature, sets the worse iteration count as 0.
     */
    public void reset() {
        temperature = initialTemperature;
        countOfWorseIterations = 0;
    }

}
