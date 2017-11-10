package jep.model.optimizationProblem.ga;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;
import jep.model.optimizationProblem.ga.mutation.MutationFunction;
import jep.model.optimizationProblem.ga.mutation.NoMutationFunction;

/**
 * This class implements a genetic algorithm for optimizing {@link OptimizationProblem}-instances.
 * <p>
 * Following implementations/interfaces are used for this implementation:
 * <ul>
 * <li>crossover - {@link CrossoverFunction}: combines individuals of a parent population to
 * generate a new child population</li>
 * <li>mutation - {@link MutationFunction}: mutates individuals of a population (child population
 * generated via crossover)</li>
 * <li>selection - {@link SelectionFunction}: selects individuals from the mutated child population
 * to as new generation</li>
 * <li>breakCondition - {@link BreakCondition}: used to determine if the algorithm shall continue or
 * stop
 * </ul>
 *
 * @param <T> specific {@link OptimizationProblem}-instance this algorithm is to be used for
 * @see OptimizationProblem
 * @see CrossoverFunction
 * @see MutationFunction
 * @see SelectionFunction
 * @see BreakCondition
 */
public class GeneticAlgorithm<T extends OptimizationProblem> {

    /**
     * Enum which defines the result modes of an genetic algorithm
     * {@link GeneticAlgorithm#run(ResultMode)}-method.
     * <ul>
     * <li>{@link #TOTAL_BEST}: Best individual of all iteration/generations is returned.</li>
     * <li>{@link #BEST_OF_LAST_POPULATION}: Best individual of last generation is returned.</li>
     * </ul>
     *
     */
    public enum ResultMode {
        TOTAL_BEST,

        BEST_OF_LAST_POPULATION;
    }

    private final Set<GeneticAlgorithmIterationListener<T>> iterationListener = new HashSet<>();

    private final Population<T> initialPopulation;
    private final CrossoverFunction<T> crossoverFunction;
    private final MutationFunction<T> mutationFunction;
    private final SelectionFunction<T> selectionFunction;
    private final BreakCondition<T> breakCondition;
    private final FitnessComparator<T> fitnessComparator;

    /**
     * Constructs a new {@link GeneticAlgorithm}-instance.
     * 
     * @param initialPopulation initial population which is used as first parent population (must be
     *        of size >= 1)
     * @param crossoverFunction {@link CrossoverFunction}-instance which defines how the crossover
     *        is to be done (if no crossover is used you can use a {@link NoCrossoverFunction}
     *        -instance)
     * @param mutationFunction {@link MutationFunction}-instance which defines how the mutation is
     *        to be done (if no mutation is used you can use a {@link NoMutationFunction} -instance)
     * @param selectionFunction {@link SelectionFunction}-instance which defines how the selection
     *        is to be done
     * @param breakCondition {@link BreakCondition}-instance which defines when to continue and when
     *        to stop the algorithm. Note that if this condition does not return <code>true</code>
     *        at some point the algorithm will never stop/return a result.
     */
    public GeneticAlgorithm(Population<T> initialPopulation, CrossoverFunction<T> crossoverFunction,
            MutationFunction<T> mutationFunction, SelectionFunction<T> selectionFunction,
            BreakCondition<T> breakCondition) {
        this.initialPopulation = Objects.requireNonNull(initialPopulation);
        if (initialPopulation.size() <= 0) {
            throw new IllegalArgumentException(
                    "Initial population has to be of a true positive size (> 0).");
        }
        this.crossoverFunction = Objects.requireNonNull(crossoverFunction);
        this.mutationFunction = Objects.requireNonNull(mutationFunction);
        this.selectionFunction = Objects.requireNonNull(selectionFunction);
        this.breakCondition = Objects.requireNonNull(breakCondition);
        this.fitnessComparator = new FitnessComparator<>();
    }

    /**
     * Runs the genetic algorithm and returns a single individual as result. Depending on the
     * <code>resultMode</code> the best individual of the current generation or the best individual
     * of all generations.
     * 
     * @param resultMode determines if the total best individual or the best individual of the last
     *        generation is to be returned
     * @return
     */
    public Solution<T> run(ResultMode resultMode) {
        Objects.requireNonNull(resultMode);
        Population<T> population = initialPopulation;
        Solution<T> totalBestIndividual = searchBestIndividualOf(population);
        updateListeners(population, totalBestIndividual, -1);
        for (long itrCount = 0; !breakCondition.isFulfilled(population, itrCount,
                totalBestIndividual); itrCount++) {
            Population<T> childPopulation = crossoverFunction.crossover(population);
            childPopulation = mutationFunction.mutate(childPopulation);
            population = selectionFunction.select(population, childPopulation);
            totalBestIndividual = searchTotalBestIndividual(population, totalBestIndividual);
            updateListeners(population, totalBestIndividual, itrCount);
        }

        switch (resultMode) {
            case BEST_OF_LAST_POPULATION:
                return searchBestIndividualOf(population);
            case TOTAL_BEST:
                return totalBestIndividual;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Runs the genetic algorithm and returns the population of the last generation.
     * 
     * @return
     */
    public Population<T> run() {
        Population<T> population = initialPopulation;
        Solution<T> totalBestIndividual = searchBestIndividualOf(population);
        updateListeners(population, totalBestIndividual, -1);
        for (long itrCount = 0; !breakCondition.isFulfilled(population, itrCount,
                totalBestIndividual); itrCount++) {
            Population<T> childPopulation = crossoverFunction.crossover(population);
            childPopulation = mutationFunction.mutate(childPopulation);
            population = selectionFunction.select(population, childPopulation);
            totalBestIndividual = searchTotalBestIndividual(population, totalBestIndividual);
            updateListeners(population, totalBestIndividual, itrCount);
        }
        return population;
    }

    private void updateListeners(Population<T> population, Solution<T> currentBestIndividual,
            long iterationCount) {
        iterationListener.forEach(
                listener -> listener.update(population, currentBestIndividual, iterationCount));
    }

    /**
     * Iterates over the individuals of the given <code>population</code> and returns an individual
     * with the best (maximal) fitness.
     * 
     * @param population population over which is iterated
     * @return
     */
    private Solution<T> searchBestIndividualOf(Population<T> population) {
        List<Solution<T>> populationsIndividuals = population.getIndividualsAsUnmodifiableList();
        Iterator<Solution<T>> itr = populationsIndividuals.iterator();
        Solution<T> bestSolution = itr.next();
        while (itr.hasNext()) {
            Solution<T> currentSolution = itr.next();
            if (fitnessComparator.checkIfFirstSolutionIsBetter(currentSolution, bestSolution)) {
                bestSolution = currentSolution;
            }
        }
        return bestSolution;
    }

    /**
     * Compares the best individual of the given <code>population</code> with the given
     * <code>currentBestIndividual</code>. Returns the latter if its fitness is better and the
     * former otherwise.
     * 
     * @param population whose best individual is compared to the <code>currentBestIndividual</code>
     * @param currentBestIndividual which is compared to the populations best individual
     * @return
     */
    private Solution<T> searchTotalBestIndividual(Population<T> population,
            Solution<T> currentBestIndividual) {
        Solution<T> bestIndividualOfPop = searchBestIndividualOf(population);
        if (fitnessComparator.checkIfFirstSolutionIsBetter(bestIndividualOfPop,
                currentBestIndividual)) {
            return bestIndividualOfPop;
        }
        return currentBestIndividual;
    }

    /**
     * Adds the given {@link GeneticAlgorithmIterationListener}-instance as listener to this
     * algorithm.
     * 
     * @param listener added to the algorithms listener list
     * @return <code>true</code> if given listener was not added previously, <code>false</code>
     *         otherwise
     */
    public boolean add(GeneticAlgorithmIterationListener<T> listener) {
        return iterationListener.add(Objects.requireNonNull(listener));
    }

    /**
     * Removes the given {@link GeneticAlgorithmIterationListener}-instance from this algorithms
     * listener list.
     * 
     * @param listener remove from the algorithms listener list
     * @return <code>true</code> if given listener was in the algorithms listener list,
     *         <code>false</code> otherwise
     */
    public boolean remove(GeneticAlgorithmIterationListener<T> listener) {
        return iterationListener.remove(listener);
    }

}
