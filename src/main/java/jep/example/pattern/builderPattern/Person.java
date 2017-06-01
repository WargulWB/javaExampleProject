package jep.example.pattern.builderPattern;

import java.util.Objects;
import java.util.Optional;

/**
 * Class which represents a simple model of a person.
 *
 */
public class Person {

    private final String firstName;

    private final Optional<String[]> midNames;

    private final String lastName;

    private final int age;

    // private final Gender gender; //lets not start this discussion

    /**
     * This class provides an {@link #build()} to construct a new {@link Person}-instance as well as
     * some setter methods to set the required and optional parameters of the person which is to be
     * constructed.
     *
     */
    public static class Builder {

        private String firstName = null;

        /**
         * The mid names field does not have to be set, in that case it will be empty by default.
         */
        private Optional<String[]> midNames = Optional.empty();

        private String lastName = null;

        private int age = -1;

        /**
         * Constructs a new instance of {@link Builder}. Sometimes the builder can have parameters,
         * for example if there are variables which are definitely required like (ex. an id of some
         * sort).
         */
        public Builder() {}

        /**
         * Constructs a new instance of {@link Person} while validating if all required variables
         * are set.
         * 
         * @return
         */
        public Person build() {
            // check if first name was set, we could also use Objects.requireNonNull(firstName)
            // which would give us a NullPointerException, however that might not be as helpful as
            // an error (the usage of assertion error is for simplicities sake)
            if (firstName == null) {
                throw new AssertionError("The first name was not set.");
            }
            // analog to first name validation
            if (lastName == null) {
                throw new AssertionError("The last name was not set.");
            }

            if (age < 0) {
                throw new AssertionError("The age was not set.");
            }
            // return new person instance
            return new Person(this);
        }

        /**
         * Sets the first name field of this builder instance as the given <code>firstName</code>.
         * We could validate the parameter already, but in this case the validation is done in the
         * {@link #build()}-method.
         * 
         * @param firstName
         * @return
         */
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Sets the last name field of this builder instance as the given <code>lastName</code>. We
         * could validate the parameter already, but in this case the validation is done in the
         * {@link #build()}-method.
         * 
         * @param lastName
         * @return
         */
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Sets the mid names field of this builder instance as the given <code>midNames</code>. In
         * this case we validate the variable directly to be not null and to hold no fields whose
         * are null. We also prevent the mid names to be set as an optional of an empty array and
         * set the mid names as empty instead.
         * 
         * @param midNames
         * @return
         */
        public Builder setMidNames(String... midNames) {
            Objects.requireNonNull(midNames);
            if (midNames.length != 0) {
                for (String midName : midNames) {
                    if (midName == null) {
                        throw new IllegalArgumentException("One of the given midnames was null.");
                    }
                }
                this.midNames = Optional.of(midNames);
            } else {
                this.midNames = Optional.empty();
            }
            return this;
        }

        /**
         * Sets the age field of this builder instance as the given <code>age</code>. We validate
         * the age directly to be positive, since this allows to make the distinction if the value
         * was set as an invalid value or not set at all.
         * 
         * @param age
         * @return
         */
        public Builder setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("The given age was of value '" + age
                        + "' but only positive values are allowed.");
            }
            this.age = age;
            return this;
        }
    }

    /**
     * Constructs a new {@link Person}-instance whose parameters are set as the parameter of the
     * given builder. Note that the constructor is private and can only be accessed by
     * {@link Builder#build()}.
     * 
     * @param builder
     */
    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.midNames = builder.midNames;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    /**
     * Returns the first name of this person.
     * 
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns true if this person has any mid name and false otherwise.
     * 
     * @return
     */
    public boolean hasAnyMidname() {
        return midNames.isPresent();
    }

    /**
     * Returns the mid names of this person as optional, which might be empty if the person does not
     * have any mid names.
     * 
     * @return
     */
    public Optional<String[]> getMidNames() {
        return midNames;
    }

    /**
     * Returns the last name of this person.
     * 
     * @return
     */
    public String getLastName() {
        return lastName;
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
        String midNamesText = "";
        if (midNames.isPresent()) {
            String[] midNamesField = midNames.get();
            for (int i = 0; i < midNamesField.length; i++) {
                midNamesText += midNamesField[i];
                if (i < midNamesField.length - 1) {
                    midNamesText += ";";
                }
            }
        } else {
            midNamesText += "none";
        }
        return "Person [firstName=" + firstName + ", midNames=" + midNamesText + ", lastName="
                + lastName + ", age=" + age + "]";
    }

}
