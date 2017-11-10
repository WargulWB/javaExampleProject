package jep.model.optimizationProblem.ga.mutation;

/**
 * This functional interface defines an adaptive mutation rate change rule which alters the mutation
 * rate depending on the result put out by an {@link SuccessValidator}-instance for a calculated
 * success rate.
 *
 */
@FunctionalInterface
public interface AdaptiveRateChangeRule {

    /**
     * Returns the given <code>rate</code> parameter if <code>successValidatorResult == 0</code>
     * otherwise a rule of change will be applied to <code>rate</code> and the result is returned.
     * <p>
     * This could be done like this, say x in (0, 1] is some rate of change variable:
     * <ul>
     * <li>If <code>successValidatorResult == -1</code> then <code>rate *= x</code> (in case of
     * failure, make rate smaller)</li>
     * <li>If <code>successValidatorResult == 1</code> then <code>rate /= x</code> (in case of
     * success, make rate wider)</li>
     * </ul>
     * 
     * @param rate mutation rate which is altered depending on the
     *        <code>successValidatorResult</code> value and returned
     * @param successValidatorResult value generated via an {@link SuccessValidator}-instance which
     *        states if the last generations lead to an successful improvement
     * @return
     */
    double changeRate(double rate, byte successValidatorResult);

}
