package jep.example.general;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jep.example.AbstractExample;

/**
 * This class implements an example showing how to use {@link Optional}.
 *
 */
public class OptionalExample extends AbstractExample {

    private Integer x = null;

    @Override
    public String run(String... arguments) {
        logln("We have a variable x which is not of a primitive type. This means the variable can be either null or and actual object.");
        logln("If the vsriable represents a local state, that is totally fine - since we know, when a variable can be null.");
        logln("The problem occurs, if the variable can be accessed from outside, e.x. the variable can be accessed via a getter-Method.");
        logln("In this case - the user might not know that the variable can be null. Which leads to NullPointerExceptions.");
        logln("Optional allows to avoid this issue by wrapping our variable inside an Optional. This forces the handling of the case x==null.");
        logln("We have one field x of type Integer, the value of x is currently: " + x);
        logLineSeparator();

        logln("We acces x via #getX():Optional<Integer> and store the return value in a variable optionalX.");
        logln("We can than use Optional#isPresent():boolean to check if the optioanl holds a value or if it is empty.");
        logln("We can use Optional<T>#get():T to acces the wrapped value in the case the optional is not empty.");
        Optional<Integer> optionalX = getX();
        if (optionalX.isPresent()) {
            logln(">> The variable optionalX is not empty an holds the value: " + optionalX.get());
        } else {
            logln(">> The variable optionalX is empty.");
        }
        logLineSeparator();

        x = new Integer(5);
        logln("We set x as " + x + " and store the return value of #getX() in optionalX.");
        optionalX = getX();
        if (optionalX.isPresent()) {
            logln(">> The variable optionalX is not empty an holds the value: " + optionalX.get());
        } else {
            logln(">> The variable optionalX is empty.");
        }
        logLineSeparator();

        logln("Optional can also be used to model a variable which only exist in some cases.");
        logln("We can assign Optional#empty():Optional to our variable to set the variable as empty.");
        logln("What should be avoided is to set the optional as null - since this would ruin the whole idea.");
        logln("We set the value of the variable y as empty.");
        Optional<Integer> y = Optional.empty();
        logln(">> The value of y is: " + y);
        logLineSeparator();

        logln("We can set our variable as Optional<T>#of(T t):Optional<T> - this will throw a NullPointerException if the variable t is null.");
        logln("We set the value of the variable y as Optional#of(5). (The type T being Integer.)");
        y = Optional.of(5);
        logln(">> The value of y is: " + y);
        logln(">> The stored value of y is: " + y.get());
        logln("We set the value of the variable y as Optional#of(null). (Catching the exception.)");
        try {
            y = Optional.of(null);
        } catch (NullPointerException exc) {
            logln(">> Caught: " + exc);
        }
        logLineSeparator();

        logln("We can set our variable as Optional<T>#ofNullable(T t):Optional<T> - this will allow the value of the variable t to be null.");
        logln("In the case of t being null, #ofNullable(T) returns an empty optional.");
        logln("The above #getX()-method actually uses this, since x is internally an Integer an can be null.");
        logln("We set the value of the variable y as Optional#ofNullable(null).");
        y = Optional.ofNullable(null);
        if (!y.isPresent()) {
            logln(">> The variable y is empty.");
        }
        logLineSeparator();

        logln("Some basic java classes actually use Optional, e.x. the Stream API.");
        logln("We instantiate a set digits of integers.");
        Set<Integer> digits = new HashSet<>();
        digits.add(0);
        digits.add(1);
        digits.add(2);
        digits.add(5);
        digits.add(7);
        digits.add(8);
        digits.add(9);
        log("digits: ");
        int i = 0;
        for (int d : digits) {
            log(d);
            if (i < digits.size() - 1) {
                log(", ");
            }
            i++;
        }
        logln();

        int threshold = 5;
        logln("We use a stream to sort the sets data and will than look for the first value greater "
                + threshold + ". The result is returned as Optional.");
        y = digits.stream().filter(x -> x > threshold).sorted(Integer::compare).findFirst();
        if (y.isPresent()) {
            logln(">> The first value found is: " + y.get());
        } else {
            logln(">> No such value was found.");
        }

        return getLoggedText();
    }

    /**
     * Returns x wrapped inside an {@link Optional}, since x may be null.
     * 
     * @return
     */
    public Optional<Integer> getX() {
        return Optional.ofNullable(x);
    }

    @Override
    public String[] getRelevantClassesInformation() {
        String[] relevantClasses =
                {OptionalExample.class.getSimpleName(), Optional.class.getSimpleName()};
        return relevantClasses;
    }

    @Override
    public String getDescription() {
        return String.format(super.getDescription(), Optional.class.getCanonicalName(),
                NullPointerException.class.getSimpleName());
    }

    @Override
    public String getBundleKey() {
        return "example.optional";
    }

}
