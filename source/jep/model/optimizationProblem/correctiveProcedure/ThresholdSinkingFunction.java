package jep.model.optimizationProblem.correctiveProcedure;

/**
 * This class handles the threshold for a threshold accepting procedure.
 *
 */
public class ThresholdSinkingFunction {

    private final double alpha;
    private final int worseItrThreshold;
    private final double initialThreshold;
    private int iteration = 0;
    private double threshold;

    /**
     * Constructs a new {@link ThresholdSinkingFunction}-instance.
     * 
     * @param initialThreshold initial value of the threshold (has to be true positive [> 0])
     * @param alpha factor which defines how the threshold is decreased - new_t = old_t * alpha
     *        (with 0 < alpha <1)
     * @param worseItrThreshold this values defines after how many iterations with a worse result
     *        (without improvement) the threshold is decreased
     */
    public ThresholdSinkingFunction(double initialThreshold, double alpha,
            int worseItrThreshold) {
        if (initialThreshold <= 0) {
            throw new IllegalArgumentException("Initial threshold must be true positive (> 0).");
        }
        if (alpha <= 0 || alpha >= 1) {
            throw new IllegalArgumentException("Values of alpha must be within 0 < aplha < 1.");
        }
        if (worseItrThreshold <= 0) {
            throw new IllegalArgumentException(
                    "Worse iterations threshold must be true positive (> 0).");
        }
        this.initialThreshold = initialThreshold;
        this.threshold = initialThreshold;
        this.alpha = alpha;
        this.worseItrThreshold = worseItrThreshold;
    }


    /**
     * This method is to be called in each iteration the procedure generated a worse result.
     */
    public void notifyAboutWorseIteration() {
        iteration++;
        sinkThresholdIfRequired();
    }

    /**
     * Calculates the new threshold over the original threshold (if and only if the internal states
     * allows threshold sinking, otherwise the original threshold is returned).
     */
    private void sinkThresholdIfRequired() {
        if (iteration > worseItrThreshold) {
            iteration = 0;
            threshold *= alpha;
        }
    }

    /**
     * Return the current threshold.
     * 
     * @return
     */
    public double getThreshold() {
        return threshold;
    }



    /**
     * Resets the functions parameters.
     */
    public void reset() {
        this.threshold = initialThreshold;
        this.iteration = 0;
    }

}
