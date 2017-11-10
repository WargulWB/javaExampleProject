package jep.model.optimizationProblem.tsp;

import java.util.Objects;

import jep.model.optimizationProblem.AbstractSolution;
import jep.model.optimizationProblem.IllegalSolutionException;
import jep.model.optimizationProblem.tsp.TravelingSalesmanProblem.City;

/**
 * This class implements a solution of the {@link TravelingSalesmanProblem}.
 *
 */
public class TSPSolution extends AbstractSolution<TravelingSalesmanProblem> {

    private final int totalDistance;
    private final City[] cities;

    /**
     * Constructs a new {@link TSPSolution}-instance.
     * 
     * @param problem {@link TravelingSalesmanProblem}-instance for which this solutions is
     *        constructed
     * @param cities array of cities, which defines the order of visit of those cities (has to
     *        contain 13 cities)
     * @throws IllegalSolutionException
     */
    public TSPSolution(TravelingSalesmanProblem problem, City[] cities)
            throws IllegalSolutionException {
        super(problem);
        this.cities = Objects.requireNonNull(cities);
        validate();
        this.totalDistance = calculateTotalDistance();
    }

    /**
     * Validate if the input parameters are of correct format.
     * 
     * @throws IllegalSolutionException
     */
    private void validate() throws IllegalSolutionException {
        Objects.requireNonNull(cities);
        if (cities.length != City.values().length) {
            throw new IllegalSolutionException(
                    "Solution has to contain all " + City.values().length + " cities.");
        }
        for (City compare : City.values()) {
            boolean found = false;
            for (City city : cities) {
                if (city == compare) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalSolutionException(
                        "Solution has to contain each city of city enum, but did not contain: "
                                + compare.toString() + ".");
            }
        }
    }

    private int calculateTotalDistance() {
        int totalDistance = 0;
        // sum up distance between each two connected cities
        for (int i = 0; i < cities.length - 1; i++) {
            totalDistance += getProblem().getDistanceBetweenCities(cities[i], cities[i + 1]);
        }
        // add distance of last city to first city (to close the cycle)
        totalDistance +=
                getProblem().getDistanceBetweenCities(cities[cities.length - 1], cities[0]);
        return totalDistance;
    }

    /**
     * Returns the array of 13 cities stored in this solution. (The array defines the order of
     * visits for the cities.)
     * 
     * @return
     */
    public City[] getCities() {
        return cities;
    }

    @Override
    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < cities.length - 1; i++) {
            sb.append(cities[i].toString()).append(" -> ");
        }
        sb.append(cities[cities.length - 1]).append(", ").append(getTotalDistance()).append("]");
        return sb.toString();
    }

    @Override
    public double getFitness() {
        return -totalDistance;
    }

    /**
     * Returns the total distance (the sum of connections distances) of this route.
     * 
     * @return
     */
    public int getTotalDistance() {
        return totalDistance;
    }

}
