package jep.example.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import jep.example.AbstractExample;
import jep.model.optimizationProblem.AbstractSolution;
import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;
import jep.model.optimizationProblem.correctiveProcedure.ThresholdSinkingFunction;
import jep.model.optimizationProblem.ga.BreakCondition;
import jep.model.optimizationProblem.ga.CrossoverFunction;
import jep.model.optimizationProblem.ga.GeneticAlgorithm;
import jep.model.optimizationProblem.ga.GeneticAlgorithm.ResultMode;
import jep.model.optimizationProblem.ga.NoCrossoverFunction;
import jep.model.optimizationProblem.ga.Population;
import jep.model.optimizationProblem.ga.RouletteWheelSelection;
import jep.model.optimizationProblem.ga.SelectionFunction;
import jep.model.optimizationProblem.ga.mutation.FixedRateMutationFunction;
import jep.model.optimizationProblem.ga.mutation.MutationFunction;
import jep.model.optimizationProblem.tsp.InitialTSPSolutionConstructor;
import jep.model.optimizationProblem.tsp.TSPSolution;
import jep.model.optimizationProblem.tsp.TravelingSalesmanProblem;
import jep.model.optimizationProblem.tsp.TravelingSalesmanProblem.City;

public class GeneticAlgorithmExample extends AbstractExample {

    private final static FitnessComparator<TravelingSalesmanProblem> FITNESS_COPMPARATOR =
            new FitnessComparator<>();
    private final static TravelingSalesmanProblem TSP = new TravelingSalesmanProblem();
    private final static Random RANDOM = new Random();

    // this is the same implementation of the threshold sinking function as used in the
    // corrective procedure example
    private final static ThresholdSinkingFunction THRESHOLD_SINKING_FUNCTION =
            new ThresholdSinkingFunction(25_000.0D, 0.99D, 100);


    @Override
    public void run(String... arguments) {
        long time = System.currentTimeMillis();

        logln("Initializing components for a simple threshold accepting 1+1 (1 parent, 1 child, plus selection) genetic algorithm.");
        InitialTSPSolutionConstructor initialSolutionConstructor =
                new InitialTSPSolutionConstructor(TSP);
        // population of a single individual
        Population<TravelingSalesmanProblem> initialPopulation =
                new Population<>(initialSolutionConstructor, 1);

        // this implementation simply returns the input population as output therefore skipping the
        // crossover step
        CrossoverFunction<TravelingSalesmanProblem> crossoverFunction = new NoCrossoverFunction<>();

        MutationFunction<TravelingSalesmanProblem> mutationFunction =
                GeneticAlgorithmExample::createNeighborPopulation;

        SelectionFunction<TravelingSalesmanProblem> selectionFunction =
                GeneticAlgorithmExample::thresdholdAcceptingSelection;

        BreakCondition<TravelingSalesmanProblem> breakCondition =
                (currentPopulation, iterationCount, bestFoundSolution) -> {
                    if (iterationCount >= 2000000) {
                        logln("Breaking GA due to iteration counter. Iterations: " + iterationCount
                                + ".");
                        return true;
                    } else if (bestFoundSolution.getFitness() >= -45811.0D) {
                        logln("Breaking GA because optimal solution was found (fitness: "
                                + bestFoundSolution.getFitness() + "). Iterations: "
                                + iterationCount + ".");
                        return true;
                    } else if (System.currentTimeMillis() - time >= 120_000L) {
                        logln("Breaking GA because it ran for 2 minutes. Iterations: "
                                + iterationCount + ".");
                        return true;
                    }
                    return false;
                };

        GeneticAlgorithm<TravelingSalesmanProblem> geneticAlgorithm =
                new GeneticAlgorithm<>(initialPopulation, crossoverFunction, mutationFunction,
                        selectionFunction, breakCondition);

        logln("Running 1+1 threshold accepting genetic algorithm.");
        TSPSolution bestSolution = (TSPSolution) geneticAlgorithm.run(ResultMode.TOTAL_BEST);
        logln("Best Solution:");
        logln(">> " + bestSolution.getStringRepresentation());
        logLineSeparator();

        logln("Initializing components for a 10,10 (10 parents, 10 childs, comma selection) genetic algorithm.");
        int populationSize = 10;
        initialPopulation = createNewInitialPopulation(populationSize, initialSolutionConstructor);
        crossoverFunction = GeneticAlgorithmExample::crossover;
        mutationFunction =
                new FixedRateMutationFunction<>(0.5D, GeneticAlgorithmExample::mutateSolution);
        selectionFunction = new RouletteWheelSelection<>(false, populationSize);
        geneticAlgorithm = new GeneticAlgorithm<>(initialPopulation, crossoverFunction,
                mutationFunction, selectionFunction, breakCondition);
        logln("Running 10,10 genetic algorithm using crossover, fixed rate mutation and roulette wheel seelction.");
        bestSolution = (TSPSolution) geneticAlgorithm.run(ResultMode.TOTAL_BEST);
        logln("Best Solution:");
        logln(">> " + bestSolution.getStringRepresentation());

    }

    private Population<TravelingSalesmanProblem> createNewInitialPopulation(int populationSize,
            InitialTSPSolutionConstructor solutionConstructor) {
        List<Solution<TravelingSalesmanProblem>> individuals = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            individuals.add(solutionConstructor.getInitialSolution());
        }
        return new Population<>(individuals);
    }

    private static Population<TravelingSalesmanProblem> thresdholdAcceptingSelection(
            Population<TravelingSalesmanProblem> parentPopulation,
            Population<TravelingSalesmanProblem> childPopulation) {
        assert parentPopulation.size() == 1;
        assert childPopulation.size() == 1;
        TSPSolution parentSolution =
                (TSPSolution) parentPopulation.getIndividualsAsUnmodifiableList().get(0);
        TSPSolution childSolution =
                (TSPSolution) childPopulation.getIndividualsAsUnmodifiableList().get(0);

        TSPSolution acceptedSolution = parentSolution;
        if (FITNESS_COPMPARATOR.getFitnessDifference(parentSolution, childSolution)
                - THRESHOLD_SINKING_FUNCTION.getThreshold() <= 0) {
            acceptedSolution = childSolution;
        } else {
            THRESHOLD_SINKING_FUNCTION.notifyAboutWorseIteration();
        }
        return new Population<>(acceptedSolution);
    }

    private static Population<TravelingSalesmanProblem> crossover(
            Population<TravelingSalesmanProblem> parentPopulation) {
        List<TSPSolution> parentList = parentPopulation.getIndividualsAsUnmodifiableList().stream()
                .map(solution -> (TSPSolution) solution).collect(Collectors.toList());
        int populationSize = parentList.size();
        assert populationSize > 1;
        List<Solution<TravelingSalesmanProblem>> childList = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            TSPSolution parent1 = parentList.get(RANDOM.nextInt(populationSize));
            TSPSolution parent2 = parentList.get(RANDOM.nextInt(populationSize));
            while (parent1 == parent2) { // if same parent was picked randomly both times
                parent2 = parentList.get(RANDOM.nextInt(populationSize));
            }
            int range = parent1.getCities().length;
            assert range % 2 == 0 && range == parent2.getCities().length;
            int blockSize = range / 2;
            int splitPosition1 = RANDOM.nextInt(range);
            int splitPosition2 = (splitPosition1 + blockSize) % range;
            List<City> blockOfParent1 = new ArrayList<>(range);

            // the route ends where it begins, we can pick a block at any position of the route
            if (splitPosition2 > splitPosition1) {
                for (int j = splitPosition1; j < splitPosition2; j++) {
                    blockOfParent1.add(parent1.getCities()[j]);
                }
            } else {
                for (int j = splitPosition1; j < range; j++) {
                    blockOfParent1.add(parent1.getCities()[j]);
                }
                for (int j = 0; j < splitPosition2; j++) {
                    blockOfParent1.add(parent1.getCities()[j]);
                }
            }
            List<City> citiesOfParent2 = new ArrayList<>(blockSize);
            for (City city : parent2.getCities()) {
                if (!blockOfParent1.contains(city)) {
                    citiesOfParent2.add(city);
                }
            }
            // add cities of parent2 behind the block of parent1 in their original order
            for (City city : citiesOfParent2) {
                blockOfParent1.add(city);
            }
            City[] cities = new City[blockOfParent1.size()];
            childList.add(new TSPSolution(TSP, blockOfParent1.toArray(cities)));
        }

        return new Population<TravelingSalesmanProblem>(childList);
    }

    private static Solution<TravelingSalesmanProblem> mutateSolution(
            Solution<TravelingSalesmanProblem> solution) {
        TSPSolution currentSolution = (TSPSolution) solution;
        City[] cities = currentSolution.getCities();
        // swap two cities at random
        int first = RANDOM.nextInt(cities.length);
        int second = RANDOM.nextInt(cities.length);
        while (first == second) {
            first = RANDOM.nextInt(cities.length);
            second = RANDOM.nextInt(cities.length);
        }
        City a = cities[first];
        cities[first] = cities[second];
        cities[second] = a;
        return new TSPSolution(TSP, cities);
    }

    private static Population<TravelingSalesmanProblem> createNeighborPopulation(
            Population<TravelingSalesmanProblem> population) {
        assert population.size() == 1;
        TSPSolution currentSolution =
                (TSPSolution) population.getIndividualsAsUnmodifiableList().get(0);
        return new Population<>(mutateSolution(currentSolution));
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses = {AbstractSolution.class, FitnessComparator.class,
                OptimizationProblem.class, Solution.class, InitialSolutionConstructor.class,
                ThresholdSinkingFunction.class, BreakCondition.class, CrossoverFunction.class,
                GeneticAlgorithm.class, NoCrossoverFunction.class, Population.class,
                RouletteWheelSelection.class, SelectionFunction.class,
                FixedRateMutationFunction.class, MutationFunction.class};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.geneticAlgorithm";
    }

}
