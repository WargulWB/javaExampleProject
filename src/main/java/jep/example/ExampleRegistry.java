package jep.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// TODO complete java doc

public class ExampleRegistry {

    private final Map<ExampleType, Set<Example>> exampleMap = new HashMap<>();

    private final Set<ExampleType> rootTypes = new HashSet<>();

    public void register(Example example, ExampleType type) throws UnregisteredTypeException {
        Objects.requireNonNull(type);
        Objects.requireNonNull(example);
        if (checkIfTypeWasRegistered(type)) {
            if (exampleMap.containsKey(type)) {
                exampleMap.get(type).add(example);
            } else {
                Set<Example> examples = new HashSet<>();
                examples.add(example);
                exampleMap.put(type, examples);
            }
        } else {
            throw new UnregisteredTypeException(type);
        }
    }

    /**
     * Returns a set of all registered {@link ExampleType}-instances which are root types (have no
     * sup types).
     * 
     * @return
     */
    public Set<ExampleType> getRootTypes() {
        return rootTypes;
    }

    /**
     * Returns a set of all registered {@link ExampleType}-instances.
     * 
     * @return
     */
    public Set<ExampleType> getTypes() {
        return exampleMap.keySet();
    }

    /**
     * Returns the registered examples of the given <code>type</code> as an unmodifiable set. If
     * there does not exist any example of the given type an empty set is returned.
     * 
     * @param type
     * @return
     */
    public Set<Example> getRegisteredExamplesOfType(ExampleType type) {
        Set<Example> examples = exampleMap.get(type);
        if (examples != null) {
            return Collections.unmodifiableSet(exampleMap.get(type));
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Returns all registered examples as one set.
     * 
     * @return
     */
    public Set<Example> getAllRegisteredExamples() {
        Set<Example> completeSet = new HashSet<>();
        for (ExampleType type : exampleMap.keySet()) {
            completeSet.addAll(exampleMap.get(type));
        }
        return completeSet;
    }

    public void registerType(ExampleType type) {
        Objects.requireNonNull(type);
        if (type.isRootType()) {
            rootTypes.add(type);
        } else {
            addTypeToRegisteredTypes(type);
        }
    }

    private void addTypeToRegisteredTypes(ExampleType type) {
        ExampleType rootType = type.getParent().get();
        List<ExampleType> layerInBetween = new ArrayList<>();
        while (!rootType.isRootType()) {
            layerInBetween.add(rootType);
            rootType = rootType.getParent().get();
        }
        ListIterator<ExampleType> itr = layerInBetween.listIterator(layerInBetween.size());
        ExampleType current = rootType;
        while (itr.hasPrevious()) {
            ExampleType previous = itr.previous();
            if (!current.getSubTypesAsUnmodifiableSet().contains(previous)) {
                current.addSubType(previous);
            }
            current = previous;
        }

        if (!current.getSubTypesAsUnmodifiableSet().contains(type)) {
            current.addSubType(type);
        }
    }

    private boolean checkIfTypeWasRegistered(ExampleType type) {
        if (type.isRootType()) {
            return rootTypes.contains(type);
        }
        ExampleType rootType = type.getParent().get();
        List<ExampleType> layerInBetween = new ArrayList<>();
        while (!rootType.isRootType()) {
            layerInBetween.add(rootType);
            rootType = rootType.getParent().get();
        }
        ListIterator<ExampleType> itr = layerInBetween.listIterator(layerInBetween.size());
        ExampleType current = rootType;
        while (itr.hasPrevious()) {
            ExampleType previous = itr.previous();
            if (!current.getSubTypesAsUnmodifiableSet().contains(previous)) {
                return false;
            }
            current = previous;
        }

        if (!current.getSubTypesAsUnmodifiableSet().contains(type)) {
            return false;
        }
        return true;
    }
}
