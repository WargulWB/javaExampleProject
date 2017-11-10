package jep.model.optimizationProblem;

/**
 * This class implements a comparator which offers some useful methods to compare two
 * {@link Solution}-instances or their fitness values or to calculate the difference of their
 * fitness considering their {@link OptimizationProblem.ProblemType}.
 *
 * @param <T> specific {@link OptimizationProblem}-instance this comparator is to be used for
 */
public class FitnessComparator<T extends OptimizationProblem> {

    /**
     * Constructs a new {@link FitnessComparator}-instance..
     */
    public FitnessComparator() {}

    /**
     * Compares the two given solutions <code>a</code> and <code>b</code>.
     * 
     * @param a first solution to compare
     * @param b second solution to compare
     * @return 1 if solution <code>a</code> is "better" than <code>b</code>, -1 if solution
     *         <code>b</code> is "better" than <code>a</code> and 0 if solutions <code>a</code> and
     *         <code>b</code> are equal
     */
    public int compareSolutions(Solution<T> a, Solution<T> b) {
        return compareFitness(getFitnessDifference(a, b));
    }

    /**
     * Compares the two given fitness values <code>fitnessA</code> and <code>fitnessB</code>.
     * 
     * @param fitnessA fitness of first solution to compare
     * @param fitnessB fitness of second solution to compare
     * @return 1 if solution <code>a</code> is "better" than <code>b</code>, -1 if solution
     *         <code>b</code> is "better" than <code>a</code> and 0 if solutions <code>a</code> and
     *         <code>b</code> are equal
     */
    public int compareFitness(double fitnessA, double fitnessB) {
        return compareFitness(fitnessA - fitnessB);
    }

    private int compareFitness(double difference) {
        if (difference == 0) {
            return 0;
        } else if (difference > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Returns the difference between the solution of the first solution <code>a</code> and the
     * second solution <code>b</code>.
     * <p>
     * result = fitness(a) - fitness(b)
     * 
     * @param a first {@link Solution}-instance for which the difference is computed
     * @param b second {@link Solution}-instance for which the difference is computed
     * @return
     */
    public double getFitnessDifference(Solution<T> a, Solution<T> b) {
        return a.getFitness() - b.getFitness();
    }


    /**
     * Returns <code>true</code> if solution <code>a</code> is better than or equal to
     * <code>b</code>, otherwise return <code>false</code>.
     * 
     * @param a first solution which is compared to the second solution <code>b</code>
     * @param b second solution which is compared to first solution <code>a</code>
     * @return
     */
    public boolean checkIfFirstIsBetterOrEqual(Solution<T> a, Solution<T> b) {
        return compareSolutions(a, b) >= 0;
    }

    /**
     * Returns <code>true</code> if solution <code>a</code> is better than <code>b</code>, otherwise
     * return <code>false</code>.
     * 
     * @param a first solution which is compared to the second solution <code>b</code>
     * @param b second solution which is compared to first solution <code>a</code>
     * @return
     */
    public boolean checkIfFirstSolutionIsBetter(Solution<T> a, Solution<T> b) {
        return compareSolutions(a, b) == 1;
    }

    /**
     * Returns <code>true</code> if <code>fitnessA</code> is better than or equal than
     * <code>fitnessB</code>, otherwise return <code>false</code>.
     * 
     * @param fitnessA first fitness value which is compared to the second fitness value
     *        <code>fitnessB</code>
     * @param fitnessB second fitness value which is compared to the first fitness value
     *        <code>fitnessA</code>
     * @return
     */
    public boolean checkIfFirstIsBetterOrEqual(double fitnessA, double fitnessB) {
        return compareFitness(fitnessA, fitnessB) >= 0;
    }

    /**
     * Returns <code>true</code> if <code>fitnessA</code> is better than <code>fitnessB</code>,
     * otherwise return <code>false</code>.
     * 
     * @param fitnessA first fitness value which is compared to the second fitness value
     *        <code>fitnessB</code>
     * @param fitnessB second fitness value which is compared to the first fitness value
     *        <code>fitnessA</code>
     * @return
     */
    public boolean checkIfFirstSolutionIsBetter(double fitnessA, double fitnessB) {
        return compareFitness(fitnessA, fitnessB) == 1;
    }
}
