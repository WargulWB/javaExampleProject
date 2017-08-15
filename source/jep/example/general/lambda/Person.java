package jep.example.general.lambda;

import java.util.Objects;

/**
 * Simple class to model a person - defined only via its full name and age.
 *
 */
public class Person {
    protected final String fullName;
    protected final int age;

    /**
     * Constructs a new instance of {@link Person}.
     * 
     * @param fullName given full name of the person
     * @param age given age of the person
     */
    public Person(String fullName, int age) {
        assert (age > 0);
        this.fullName = Objects.requireNonNull(fullName);
        this.age = age;
    }

    /**
     * Returns the full name of this person.
     * 
     * @return
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the age of this person.
     * 
     * @return
     */
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "[name=" + fullName + ", age=" + age + ']';
    }
}
