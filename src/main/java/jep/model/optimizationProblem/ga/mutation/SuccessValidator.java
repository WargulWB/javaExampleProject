package jep.model.optimizationProblem.ga.mutation;

/**
 * This functional interface defines a success validator which states given a success rate if the
 * rate indicates a successful improvement, a failure or no change of success. It is designed to be
 * used in {@link AdaptiveRateMutationFunction}-instances with an {@link SuccessRateFunction}
 * -instance providing the success rate.
 */
@FunctionalInterface
public interface SuccessValidator {

    /**
     * If the <code>successRate</code> indicates a successful improvement return <code>1</code>, if
     * the <code>successRate</code> indicates a worsening return <code>-1</code>, otherwise return
     * <code>0</code>.
     * 
     * @param successRate given rate of success used to determine if a successful improvement was
     *        made
     * @return
     */
    byte validate(double successRate);

}
