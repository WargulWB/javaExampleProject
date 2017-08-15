package jep.example.general.lambda;

import java.util.Set;

/**
 * Simple functional interface which defines a function which selects a certain value out of a set
 * of data.
 *
 * @param <T> type of the data set and type of the result
 */
@FunctionalInterface
public interface SelectionFunction<T> {

    /**
     * Returns the selected sample of the given data set.
     * 
     * @param data set of data from which a sample is selected
     * @return selected sample
     */
    T selectFrom(Set<T> data);

}
