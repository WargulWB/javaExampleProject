package jep.model.optimizationProblem.ga.mutation;

/**
 * This class implements a {@link SuccessValidator} using the the Rosenberg definition of successful
 * improvement. It is designed to be used with a {@link RosenbergAdaptiveRateChangeRule}.
 * 
 * Rosenbergs definition of successful improvement as used here is given as:
 * 
 * <pre>
 * t = 1/5
 * <b>IF</b> successRate > t <b>THEN</b>
 *   return 1
 * <b>IF</b> successRate < t <b>THEN</b>
 *   return -1
 * <b>ELSE</b>
 *   return 0
 * </pre>
 * 
 * However, this implementation allows to use a value within (0,1) for t.
 *
 */
public class RosenbergSuccessValidator implements SuccessValidator {

    private final double threshold;

    /**
     * Constructs a new {@link RosenbergSuccessValidator}-instance using a threshold t = 1/5.
     */
    public RosenbergSuccessValidator() {
        this.threshold = 0.2D; // = 1/5
    }


    /**
     * Constructs a new {@link RosenbergSuccessValidator}-instance using the given value
     * <code>threshold</code> value. Note that only values within (0,1) are allowed.
     */
    public RosenbergSuccessValidator(double threshold) {
        if (threshold <= 0 || threshold >= 1) {
            throw new IllegalArgumentException("Threshold must be within (0,1).");
        }
        this.threshold = threshold;
    }

    @Override
    public byte validate(double succesRate) {
        if (succesRate > threshold) {
            return 1;
        } else if (succesRate == threshold) {
            return 0;
        }
        return -1;
    }

}
