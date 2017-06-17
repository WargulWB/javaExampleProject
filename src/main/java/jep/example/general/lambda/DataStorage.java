package jep.example.general.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple data storage which stores some data in a set and allows the access of the data via the
 * {@link #select(SelectionFunction)} method using {@link SelectionFunction}-instance.
 *
 * @param <T> type of the data stored in the data storage
 */
public class DataStorage<T> {

    private final Set<T> data;

    /**
     * Constructs a new {@link DataStorage}-instance which stored the given data set.
     * 
     * @param data set of data which is stored in this data storage instance
     */
    public DataStorage(Set<T> data) {
        this.data = Objects.requireNonNull(data);
    }

    /**
     * Constructs a new {@link DataStorage}-instance which stored the given data
     * 
     * @param data which is stored in this data storage instance
     */
    @SafeVarargs
    public DataStorage(T... data) {
        this.data =
                Arrays.asList(Objects.requireNonNull(data)).stream().collect(Collectors.toSet());
    }

    /**
     * Returns the data point of the data stored in this storage which is selected via the given
     * {@link SelectionFunction}-instance.
     * 
     * @param selectionFunction
     * @return
     */
    public Optional<T> select(SelectionFunction<T> selectionFunction) {
        return Optional.ofNullable(selectionFunction.selectFrom(data));
    }

    /**
     * Returns the data stored in this storage instance as unmodifiable set.
     * 
     * @return
     */
    public Set<T> getDataAsUnmodifiableSet() {
        return Collections.unmodifiableSet(data);
    }

}
