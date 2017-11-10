package jep.model.optimizationProblem.ga.mutation;

/**
 * This class implements the Rosenberg rule as a {@link AdaptiveRateChangeRule}. It is designed to
 * be used with the result of an {@link RosenbergSuccessValidator}-instance as input.
 * <p>
 * The Rosenberg rule is defined here as follows:
 * 
 * <pre>
 * successResult = successValidatorResult()
 * <b>IF</b> successResult == 1 <b>THEN</b>
 *   increase mutation rate
 * <b>IF</b> successResult == -1 <b>THEN</b>
 *   decrease mutation rate
 * <b>IF</b> successResult == 0 <b>THEN</b>
 *   leave mutation rate as is
 * </pre>
 */
public class RosenbergAdaptiveRateChangeRule implements AdaptiveRateChangeRule {

    /**
     * Constructs a new {@link RosenbergAdaptiveRateChangeRule}-instance.
     */
    public RosenbergAdaptiveRateChangeRule() {}

    @Override
    public double changeRate(double rate, byte successValidatorResult) {
        if (successValidatorResult == 1) {
            rate *= 2;
        } else if (successValidatorResult == -1) {
            rate /= 2;
        }
        return rate;
    }

}
