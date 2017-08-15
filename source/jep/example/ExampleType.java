package jep.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

// TODO add java doc

public class ExampleType {

    private final String key;

    private final Optional<ExampleType> parent;

    private final Set<ExampleType> children = new HashSet<>();


    public ExampleType(String key) {
        this.key = Objects.requireNonNull(key);
        this.parent = Optional.empty();
    }

    public ExampleType(String key, Optional<ExampleType> parent) {
        this.key = Objects.requireNonNull(key);
        this.parent = Objects.requireNonNull(parent);
    }

    public String getBundleKey() {
        return key;
    }

    public Optional<ExampleType> getParent() {
        return parent;
    }

    public void addSubType(ExampleType type) {
        children.add(Objects.requireNonNull(type));
    }

    public Set<ExampleType> getSubTypesAsUnmodifiableSet() {
        return Collections.unmodifiableSet(children);
    }

    public boolean isRootType() {
        return !parent.isPresent();
    }

    public boolean isLeafType() {
        return children.isEmpty();
    }

}
