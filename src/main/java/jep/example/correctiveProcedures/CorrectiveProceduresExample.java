package jep.example.correctiveProcedures;

import java.util.Random;

import jep.example.AbstractExample;
import jep.model.optimizationProblem.AbstractSolution;
import jep.model.optimizationProblem.FitnessComparator;
import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.OptimizationProblem;
import jep.model.optimizationProblem.Solution;
import jep.model.optimizationProblem.correctiveProcedure.AbstractCorrectiveProcedure;
import jep.model.optimizationProblem.correctiveProcedure.AcceptanceFunction;
import jep.model.optimizationProblem.correctiveProcedure.AnnealingFunction;
import jep.model.optimizationProblem.correctiveProcedure.BreakCondition;
import jep.model.optimizationProblem.correctiveProcedure.NeighborFunction;
import jep.model.optimizationProblem.correctiveProcedure.SimulatedAnnealingProcedure;
import jep.model.optimizationProblem.correctiveProcedure.ThresholdAcceptingProcedure;
import jep.model.optimizationProblem.correctiveProcedure.ThresholdBreakCondition;
import jep.model.optimizationProblem.correctiveProcedure.ThresholdSinkingFunction;
import jep.model.optimizationProblem.correctiveProcedure.CorrectiveProcedure.ResultMode;
import jep.model.optimizationProblem.tsp.InitialTSPSolutionConstructor;
import jep.model.optimizationProblem.tsp.TSPSolution;
import jep.model.optimizationProblem.tsp.TravelingSalesmanProblem;
import jep.model.optimizationProblem.tsp.TravelingSalesmanProblem.City;

/**
 * This class implements an example which illustrates how corrective procedures could be implemented
 * and how this implementation can be used.
 *
 */
public class CorrectiveProceduresExample extends AbstractExample {

    @Override
    public void run(String... arguments) {
        TravelingSalesmanProblem tsp = new TravelingSalesmanProblem();
        long time = System.currentTimeMillis();
        Random random = new Random();

        logln("Initialize corrective procedure components.");
        InitialSolutionConstructor<TravelingSalesmanProblem> initialSolutionConstructor =
                new InitialTSPSolutionConstructor(tsp);

        // initial solution is one, where each city is reached, but the cities
        // are in random order
        Solution<TravelingSalesmanProblem> initialSolution =
                initialSolutionConstructor.getInitialSolution();
        logln("Initial Solution:");
        logln(">> " + initialSolution.getStringRepresentation());

        NeighborFunction<TravelingSalesmanProblem> neighborFunction = solution -> {
            City[] cities = ((TSPSolution) solution).getCities();
            // swap two cities
            int first = random.nextInt(cities.length);
            int second = random.nextInt(cities.length);
            while (first == second) {
                first = random.nextInt(cities.length);
                second = random.nextInt(cities.length);
            }
            City a = cities[first];
            cities[first] = cities[second];
            cities[second] = a;
            return new TSPSolution(tsp, cities);
        };

        BreakCondition<TravelingSalesmanProblem> breakCondition =
                (currentSolution, iterationCount, iterationWithAcceptanceCount) -> {
                    // terminate after two minutes (180.000ms)
                    if (System.currentTimeMillis() - time >= 180_000L) {
                        logln("Break procedure due to general break condition being fulfilled.");
                        return true;
                    }
                    return false;
                };

        FitnessComparator<TravelingSalesmanProblem> fitnessComparator = new FitnessComparator<>();

        logln("Initialize threshold accepting procedure.");
        // 25.000 being greater than the max distance between two cities algorithm specific break
        // condition
        double threshold = 25_000.0D;
        double alpha = 0.99D;
        int worseItrThreshold = 100;
        // a default condition, which is never fulfilled leaving the general break condition as only
        // active break condition
        ThresholdBreakCondition specificBreakCondition = (t) -> false;
        ThresholdAcceptingProcedure<TravelingSalesmanProblem> thresholdAcceptingProcedure =
                new ThresholdAcceptingProcedure<>(initialSolutionConstructor, fitnessComparator,
                        neighborFunction, breakCondition,
                        new ThresholdSinkingFunction(threshold, alpha, worseItrThreshold),
                        specificBreakCondition);

        logln("Run threshold accepting procedure.");
        TSPSolution bestSolution =
                (TSPSolution) thresholdAcceptingProcedure.run(ResultMode.TOTAL_BEST);
        logln("Number of iterations: " + thresholdAcceptingProcedure.getNumberOfIterations());
        logln("Best Solution:");
        logln(">> " + bestSolution.getStringRepresentation());

        logln("Manual Solution as comparison:");
        City[] cities = {City.DUBLIN, City.LONDON, City.PARIS, City.MADRID, City.ROME, City.BERLIN,
                City.MOSCOW, City.NEW_DEHLI, City.BEIJING, City.TOKYO, City.CANBERRA,
                City.WASHINGTON_DC, City.OTTAWA};
        Solution<TravelingSalesmanProblem> manualSolution = new TSPSolution(tsp, cities);
        logln(">> " + manualSolution.getStringRepresentation());
        logLineSeparator();

        logln("Initializing simulated annealing procedure.");
        // parameters are the same as above
        double temperature = 2000;
        SimulatedAnnealingProcedure<TravelingSalesmanProblem> simulatedAnnealingProcedure =
                new SimulatedAnnealingProcedure<>(initialSolutionConstructor, fitnessComparator,
                        neighborFunction, new AnnealingFunction<>(fitnessComparator, temperature,
                                alpha, worseItrThreshold),
                        breakCondition);
        logln("Run simulated annealing procedure.");
        bestSolution = (TSPSolution) simulatedAnnealingProcedure.run(ResultMode.TOTAL_BEST);
        logln("Number of iterations: " + thresholdAcceptingProcedure.getNumberOfIterations());
        logln("Best Solution:");
        logln(">> " + bestSolution.getStringRepresentation());

        logln("Manual Solution as comparison:");
        logln(">> " + manualSolution.getStringRepresentation());
        logLineSeparator();
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses = {CorrectiveProceduresExample.class, AbstractSolution.class,
                FitnessComparator.class, OptimizationProblem.class, Solution.class,
                AbstractCorrectiveProcedure.class, InitialSolutionConstructor.class,
                AcceptanceFunction.class, NeighborFunction.class, BreakCondition.class,
                ThresholdAcceptingProcedure.class, ThresholdBreakCondition.class,
                ThresholdSinkingFunction.class, SimulatedAnnealingProcedure.class,
                AnnealingFunction.class};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.correctiveProcedure";
    }

}
