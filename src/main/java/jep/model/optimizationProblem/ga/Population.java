package jep.model.optimizationProblem.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;

/**
 * This class implements a population used for {@link GeneticAlgorithm}-implementations. A
 * population is a group of individuals. In this particular case each individual is a
 * {@link Solution}-instance. This population implementation does store the individuals in a list
 * (rather than a set) which allows access via index.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this population is designed for
 */
public class Population<T extends OptimizationProblem> {

    private final List<Solution<T>> individuals;
    private final int size;

    /**
     * Constructs a new {@link Population}-instance storing the given list of individuals as
     * individuals of the population.
     * 
     * @param individuals list of individuals which are to be stored in this population. Note that
     *        only a non-null, non-empty list of individuals is allowed.
     */
    public Population(List<Solution<T>> individuals) {
        this.individuals = Objects.requireNonNull(individuals);
        this.size = individuals.size();
        if (size < 1) {
            throw new IllegalArgumentException("Population size must be at least 1.");
        }
    }

    /**
     * Constructs a new population of individuals generated via the given
     * {@link InitialSolutionConstructor}-instance.
     * 
     * @param initialSolutionConstructor utility item used to generate new individuals (
     *        {@link Solution}-instances
     * @param populationSize number of individuals which are to be generated and stored in this
     *        population. Note that only sizes >= 1 are allowed.
     */
    public Population(InitialSolutionConstructor<T> initialSolutionConstructor,
            int populationSize) {
        if (populationSize < 1) {
            throw new IllegalArgumentException("Population size must be 1 at least.");
        }
        List<Solution<T>> solutions = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            solutions.add(initialSolutionConstructor.getInitialSolution());
        }
        this.individuals = solutions;
        this.size = populationSize;
    }

    /**
     * Constructs a new population which only holds the given {@link Solution}-instance as
     * individual.
     * 
     * @param solution single individual stored in this population
     */
    public Population(Solution<T> solution) {
        Objects.requireNonNull(solution);
        this.individuals = new ArrayList<>(1);
        individuals.add(solution);
        this.size = 1;
    }

    /**
     * Returns the size (number of individuals) of this population.
     * 
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Returns the individuals of this population as an unmodifiable list.
     * 
     * @return
     */
    public List<Solution<T>> getIndividualsAsUnmodifiableList() {
        return Collections.unmodifiableList(individuals);
    }

    /**
     * Returns a new population which holds the individuals of both given populations
     * <code>population1</code> and <code>population2</code>.
     * <p>
     * resultPopSize = pop1Size + pop2Size
     * 
     * @param population1 first population which is merged with the second population to generate a
     *        new population
     * @param population2 second population which is merged with the first population to generate a
     *        new population
     * @return
     */
    public static <R extends OptimizationProblem> Population<R> merge(Population<R> population1,
            Population<R> population2) {
        List<Solution<R>> individuals = new ArrayList<>(population1.size() + population2.size());
        individuals.addAll(population1.individuals);
        individuals.addAll(population2.individuals);
        return new Population<>(individuals);
    }

}
