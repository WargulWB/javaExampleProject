package jep.model.optimizationProblem.tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import jep.model.optimizationProblem.InitialSolutionConstructor;
import jep.model.optimizationProblem.tsp.TravelingSalesmanProblem.City;

public class InitialTSPSolutionConstructor
        implements InitialSolutionConstructor<TravelingSalesmanProblem> {

    private final Random random;
    private final TravelingSalesmanProblem problem;

    public InitialTSPSolutionConstructor(TravelingSalesmanProblem problem) {
        this.problem = problem;
        this.random = new Random();
    }

    public InitialTSPSolutionConstructor(TravelingSalesmanProblem problem, long seed) {
        this.problem = problem;
        this.random = new Random(seed);
    }

    public InitialTSPSolutionConstructor(TravelingSalesmanProblem problem, Random random) {
        this.problem = problem;
        this.random = Objects.requireNonNull(random);
    }

    @Override
    public TSPSolution getInitialSolution() {
        City[] cities = new City[City.values().length];
        List<City> cityList = new ArrayList<>();
        for (City c : City.values()) {
            cityList.add(c);
        }
        for (int i = 0; cityList.size() != 0; i++) {
            cities[i] = cityList.remove(random.nextInt(cityList.size()));
        }
        return new TSPSolution(Objects.requireNonNull(problem), cities);
    }

}
