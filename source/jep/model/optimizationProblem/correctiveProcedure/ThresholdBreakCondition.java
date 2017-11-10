package jep.model.optimizationProblem.correctiveProcedure;

@FunctionalInterface
public interface ThresholdBreakCondition {

    boolean isFulfilled(double treshold);
    
}
