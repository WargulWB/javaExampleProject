package jep.model.optimizationProblem.tsp;

import java.util.Objects;

import jep.model.optimizationProblem.OptimizationProblem;

/**
 * This class implements a specific traveling salesman problem. With 13 capitals stored in the
 * {@link City}-enum. Using the greater circle distance (flying distance) as measure of distance
 * between the capitals.
 *
 */
public class TravelingSalesmanProblem implements OptimizationProblem {

    /**
     * Enum which stores 13 "more or less" randomly picked capitals.
     *
     */
    public enum City {
        BEIJING, NEW_DEHLI, TOKYO, MOSCOW, LONDON, BERLIN, MADRID, ROME, PARIS, DUBLIN, OTTAWA, WASHINGTON_DC, CANBERRA;
    }

    /**
     * Matrix of distances of direct connections between each pair of capitals.
     */
    private static final int[][] DISTANCE_MATRIX = {
            {0, 3784, 2095, 5800, 8150, 7366, 9233, 8134, 8226, 8292, 10463, 11158, 9018},
            {3784, 0, 5844, 4347, 6719, 5787, 7282, 5923, 6594, 7086, 11351, 12059, 10365},
            {2095, 5844, 0, 7487, 9569, 8926, 10774, 9864, 9723, 9595, 10333, 10916, 7961},
            {5800, 4347, 7487, 0, 2503, 1611, 3444, 2378, 2489, 2798, 7166, 7830, 14498},
            {8150, 6719, 9569, 2503, 0, 933, 1265, 1435, 344, 464, 5367, 5904, 17001},
            {7366, 5787, 8926, 1611, 933, 0, 1871, 1184, 878, 1318, 6135, 6718, 16084},
            {9233, 7282, 10774, 3444, 1265, 1871, 0, 1366, 1054, 1453, 5696, 6095, 17593},
            {8134, 5923, 9864, 2378, 1435, 1184, 1366, 0, 1107, 1888, 6737, 7225, 16235},
            {8226, 6594, 9723, 2489, 344, 878, 1054, 1107, 0, 782, 5655, 6172, 16939},
            {8292, 7086, 9595, 2798, 464, 1318, 1453, 1888, 782, 0, 4905, 5448, 17256},
            {10463, 11351, 10333, 7166, 5367, 6135, 5696, 6737, 5655, 4905, 0, 734, 16126},
            {11158, 12059, 10916, 7830, 5904, 6718, 6095, 7225, 6172, 5448, 734, 0, 15962},
            {9018, 10365, 7961, 14498, 17001, 16084, 17593, 16235, 16939, 17256, 16126, 15962, 0}};

    /**
     * Returns the great circle distance (air distance) between the two given cities <code>a</code>
     * and <code>b</code> in kilometers.
     * 
     * @param a first city
     * @param b second city
     * @return
     */
    public int getDistanceBetweenCities(City a, City b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return DISTANCE_MATRIX[a.ordinal()][b.ordinal()];
    }

    @Override
    public ProblemType getType() {
        return ProblemType.MINIMIZATION;
    }

}
