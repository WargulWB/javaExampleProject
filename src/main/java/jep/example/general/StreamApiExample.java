package jep.example.general;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import jep.example.AbstractExample;

public class StreamApiExample extends AbstractExample {

    private enum Gender {
        MALE, FEMALE;
    }

    @Override
    public void run(String... arguments) {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person("Bob", "Andrews", 25, Gender.MALE));
        persons.add(new Person("Alice", "Liddell", 13, Gender.FEMALE));
        persons.add(new Person("Trudy", "Chacon", 32, Gender.FEMALE));
        persons.add(new Person("Peter", "Shaw", 24, Gender.MALE));
        persons.add(new Person("Justus", "Jonas", 25, Gender.MALE));
        logln("Initialized set of " + persons.size() + " persons:");
        for (Person person : persons) {
            logln(person.toString());
        }
        logLineSeparator();

        logln("Sorting persons by age, storing them into a list - result:");
        List<Person> personsSorted = persons.stream()
                .sorted((person1, person2) -> Integer.compare(person1.getAge(), person2.getAge()))
                .collect(Collectors.toList());
        personsSorted.stream().forEach(person -> logln(person.toString()));
        logLineSeparator();

        logln("Sorting persons by age than last name than first name, then storing them into a list - result:");
        personsSorted = persons.stream().sorted((person1, person2) -> {
            int comparisonValue = Integer.compare(person1.getAge(), person2.getAge());
            if (comparisonValue != 0) {
                return comparisonValue;
            }
            comparisonValue = person1.getLastName().compareTo(person2.getLastName());
            if (comparisonValue != 0) {
                return comparisonValue;
            }
            return person1.getFirstName().compareTo(person2.getFirstName());
        }).collect(Collectors.toList());
        personsSorted.stream().forEach(person -> logln(person.toString()));
        logLineSeparator();

        logln("Filtering the sorted persons by gender, only allowing persons of gender "
                + Gender.MALE.name() + ". Then storing them into a list - result:");
        List<Person> malePersonsSorted =
                personsSorted.stream().filter(person -> person.getGender().equals(Gender.MALE))
                        .collect(Collectors.toList());
        malePersonsSorted.stream().forEach(person -> logln(person.toString()));
        logLineSeparator();

        logln("Filtering the sorted persons by gender and age, only allowing persons of gender "
                + Gender.FEMALE.name()
                + " and age above 20. Then storing them into a list - result:");
        List<Person> femalePersonsOfAgeGreater20 = personsSorted.stream().filter(person -> {
            boolean requirement = person.getGender().equals(Gender.FEMALE);
            if (requirement == false) {
                return false;
            }
            return person.getAge() > 20;
        }).collect(Collectors.toList());
        femalePersonsOfAgeGreater20.stream().forEach(person -> logln(person.toString()));
        logLineSeparator();

        logln("Filtering the original set of persons by gender (" + Gender.MALE.name()
                + ") and sorting the result by using the Person#compareTo(Person) method, then pickking the first in the list - result:");
        Optional<Person> firstMaleInSortedList =
                persons.stream().filter(p -> p.getGender().equals(Gender.MALE))
                        .sorted((p1, p2) -> p1.compareTo(p2)).findFirst();
        if (firstMaleInSortedList.isPresent()) {
            logln(firstMaleInSortedList.get().toString());
        } else {
            logln("None found.");
        }
        logLineSeparator();

        int minAge = 40;
        logln("Filtering the original set of persons by gender (" + Gender.FEMALE.name()
                + ") and age (> " + minAge
                + "), then picking any person found in the result - result:");
        Optional<Person> anyFemalePersonOlderThanMinAge = persons.stream().filter(p -> {
            boolean comp = p.getGender().equals(Gender.FEMALE);
            if (comp == false) {
                return false;
            }
            return p.getAge() > minAge;
        }).findAny();
        if (anyFemalePersonOlderThanMinAge.isPresent()) {
            logln(anyFemalePersonOlderThanMinAge.get().toString());
        } else {
            logln("None found.");
        }
        logLineSeparator();

        logln("Mapping the set of persons to a set of persons using Person#getAge():int, sorting the result and returning it as a list - result:");
        List<Integer> ages = persons.stream().map(person -> person.getAge())
                .sorted((age1, age2) -> Integer.compare(age1, age2)).collect(Collectors.toList());
        int i = 0;
        for (int age : ages) {
            log(age);
            if (i < ages.size() - 1) {
                log(", ");
            }
            i++;
        }
        logln();
        logLineSeparator();

        int border = 50;
        int[] values = {1, 3, 10, -5, 105, 61};
        logln("Array:");
        i = 0;
        for (int val : values) {
            log(val);
            if (i < values.length - 1) {
                log(", ");
            }
            i++;
        }
        logln();
        logln("Turning above array into a stream, filtering the stream (allowing values < " + border
                + ") and returning the maximal of the remaining values as result:");
        OptionalInt max = Arrays.stream(values).filter(val -> val < border).max();
        if (max.isPresent()) {
            logln(max.getAsInt());
        } else {
            logln("None found.");
        }
    }

    @Override
    public String getDescription() {
        return String.format(super.getDescription(), Person.class.getSimpleName());
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses = {StreamApiExample.class, Person.class};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.streamApi";
    }

    public class Person implements Comparable<Person> {

        private final String firstName;
        private final String lastName;
        private final int age;
        private final Gender gender;

        public Person(String firstName, String lastName, int age, Gender gender) {
            super();
            if (age <= 0) {
                throw new IllegalArgumentException(
                        "Age has to be a true positive number, but was " + age + ".");
            }
            this.firstName = Objects.requireNonNull(firstName);
            this.lastName = Objects.requireNonNull(lastName);
            this.age = age;
            this.gender = Objects.requireNonNull(gender);
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getAge() {
            return age;
        }

        public Gender getGender() {
            return gender;
        }

        @Override
        public String toString() {
            return lastName + ", " + firstName + ", " + age + ", " + gender.name();
        }

        @Override
        public int compareTo(Person p) {
            if (p == null) {
                return 1;
            }
            int comp = lastName.compareTo(p.lastName);
            if (comp != 0) {
                return comp;
            }
            comp = firstName.compareTo(p.firstName);
            if (comp != 0) {
                return comp;
            }
            return Integer.compare(age, p.age);
        }
    }

}
