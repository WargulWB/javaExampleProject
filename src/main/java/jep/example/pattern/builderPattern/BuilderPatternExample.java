package jep.example.pattern.builderPattern;

import jep.example.AbstractExample;

/**
 * This class displays the usage of a class {@link Person} which provides a {@link Person.Builder}
 * for the construction of {@link Person}-instances.
 *
 */
public class BuilderPatternExample extends AbstractExample {

    @Override
    public String run(String... arguments) {
        logNoArgumentsRequired();

        logln("Constructing a new person by using the builder. Set all required variables.");
        Person.Builder personBuilder = new Person.Builder();
        personBuilder.setFirstName("Amy").setLastName("Doe").setAge(27);
        Person person = personBuilder.build();
        logln("Constructed: " + person.toString());
        logln();

        logln("Constructing a new person by using the builder. Set all required variables as well as the optional mid names variable.");
        personBuilder = new Person.Builder();
        personBuilder.setFirstName("Bob").setMidNames("Trudy").setLastName("Doe").setAge(30);
        person = personBuilder.build();
        logln("Constructed: " + person.toString());
        logln();

        logln("Constructing a new person by using the builder. Set all required variables except the first name.");
        personBuilder = new Person.Builder();
        personBuilder.setLastName("Doe").setAge(41);
        try {
            person = personBuilder.build();
        } catch (AssertionError err) {
            logln("Caught: " + err.toString());
        }
        logln();

        logln("Constructing a new person by using the builder. Set invalid value for the age.");
        try {
            personBuilder = new Person.Builder();
            personBuilder.setAge(-1);
        } catch (IllegalArgumentException exc) {
            logln("Caught: " + exc.toString());
        }

        return getLoggedText();
    }

    @Override
    public String getDescription() {
        return String.format(super.getDescription(), BuilderPatternExample.class.getSimpleName(),
                Person.class.getSimpleName(),
                Person.class.getSimpleName() + "." + Person.Builder.class.getSimpleName());
    }

    @Override
    public String[] getRelevantClassesInformation() {
        String[] classes =
                {BuilderPatternExample.class.getSimpleName(), Person.class.getSimpleName(),
                        Person.class.getSimpleName() + "." + Person.Builder.class.getSimpleName()};
        return classes;
    }

    @Override
    public String getBundleKey() {
        return "example.builderPattern";
    }

}
