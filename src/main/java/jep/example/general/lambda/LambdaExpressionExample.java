package jep.example.general.lambda;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import jep.example.AbstractExample;
import jep.example.general.StreamApiExample;

public class LambdaExpressionExample extends AbstractExample {

    @Override
    public String run(String... arguments) {
        logln("This first example shows the usage of the functional interface "
                + MathFunctionInt.class.getSimpleName() + "\n"
                + "to define a mathematical operation via a lambda expression and how to calculate the result.");
        MathFunctionInt f = (x1, x2) -> x1 + x2;
        int a = 5;
        int b = 3;
        int y = f.apply(a, b);
        logln("The function is defined as: (x1, x2) -> x1 + x2");
        logln("The parameters are: x1=a:=" + a + ", x2=b:=" + b);
        logln("The result is y=" + y);
        logLineSeparator();

        logln("The function can be redefined.");
        logln("We redefine the function as : (x1, x2) -> x1 * x2");
        f = (x1, x2) -> x1 * x2;
        y = f.apply(a, b);
        logln("The result is y=" + y + " for x1=a:=" + a + ", x2=b:=" + b);
        logLineSeparator();

        logln("We actually would not need to define our own interface.");
        logln("We can use the " + Function.class.getSimpleName() + "-interface of the package "
                + getPackageNameOfClass(Function.class) + ".");
        logln("We define the function as the increment function: (x) -> ++x");
        Function<Integer, Integer> g = x -> ++x;
        y = g.apply(a);
        logln("The result is y=" + y + " for x=a:=" + a);
        logLineSeparator();

        logln("The " + Function.class.getSimpleName()
                + "-interface only allows one parameter our previous examples required two parameters.");
        logln("We can use the " + BiFunction.class.getSimpleName()
                + "-interface which will allow exactly that.");
        BiFunction<Integer, Integer, Integer> h = (x1, x2) -> x1 + x2;
        y = h.apply(a, b);
        logln("We define the function  as: (x1, x2) -> x1 + x2");
        logln("The result is y=" + y + " for x1=a:=" + a + ", x2=b:=" + b);
        logLineSeparator();

        logln("The " + BiFunction.class.getSimpleName()
                + "-interface also shows how you can assemble a function chain.");
        h = h.andThen(g);
        logln("We redefine the function h: h':=g(h(x,y)), were h:=(x1,x2) -> x1 + x2 and g:=x -> ++x");
        y = h.apply(a, b);
        logln("The result is y=" + y + " for x1=a:=" + a + ", x2=b:=" + b);
        logLineSeparator();

        logln("In most situations a lambda expression will not be assigned to a variable but rather be given to a method.");
        logln("A quite common example is the Stream API - see "
                + StreamApiExample.class.getSimpleName() + " for details.");
        logln("We can use a functional interface as a method parameter, which actually allows us to give an implementation via an lambda expression a parameter.");
        logln("We use the local method check(BiFunction<Integer, Integer, Boolean>,int,int):boolean and define the function as: (x1, x2) -> x1 > x2");
        boolean result = check((x1, x2) -> x1 > x2, a, b);
        logln("The result is '" + result + "' for x1=a:=" + a + ", x2=b:=" + b);
        result = check((x1, x2) -> x1 > x2, b, a);
        logln("The result is '" + result + "' for x1=b:=" + b + ", x2=a:=" + a);
        logLineSeparator();

        logln("Until this point we only defined mathematical/logical functions over numbers.");
        logln("But as the generic " + Function.class.getSimpleName() + "-, and "
                + BiFunction.class.getSimpleName()
                + "-interface allready suggest we can define lambda expressions for any kind of object.");
        Person kai = new Person("Kai Wingenfelder", 58);
        Person thorsten = new Person("Thorsten Wingenfelder", 51);
        logln("Let's assume we have two persons:");
        logln(kai);
        logln(thorsten);
        int ageComparisonValue =
                apply((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()), kai, thorsten);
        logln("We define our function as: (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge())");
        logln("For p1:=" + kai + " and p2:=" + thorsten + " the function returns y="
                + ageComparisonValue + " (meaning Kai is older than Thorsten)");
        logLineSeparator();


        logln("The above example is quite similar to the " + Comparator.class.getName()
                + " which can for example be used to sort elements within a stream.");
        Set<Person> persons = new HashSet<>();
        persons.add(kai);
        persons.add(thorsten);
        logln("Assume we have a set of " + kai + " and " + thorsten + ".");
        logln("We can use the above lambda expression as " + Comparator.class.getSimpleName()
                + "-implementation to sort the elements within the sets stream by age.");
        logln("The resulting list is: ");
        List<Person> sortedPersons =
                persons.stream().sorted((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()))
                        .collect(Collectors.toList());
        int i = 0;
        for (Person person : sortedPersons) {
            log(person);
            if (i < sortedPersons.size() - 1) {
                log(", ");
            }
            i++;
        }
        logln();
        logLineSeparator();

        logln("In some situations a class may provide a static method which serves the purpose you want to achieve with a certain lambda expression.");
        logln("In this case you can write '<className>::<methodName>' as your lambda expression.");
        logln("The only requirement is that the methods arguments match the arguments of the lambda expressions fuctional interfaces method.");
        logln("We define a static method " + LambdaExpressionExample.class.getSimpleName()
                + "#compareByAge(Person,Person):int which we than use for sorting the sets stream like in the above example.");
        sortedPersons = persons.stream().sorted(LambdaExpressionExample::compareByAge)
                .collect(Collectors.toList());
        logln("The resulting list is: ");
        sortedPersons =
                persons.stream().sorted((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()))
                        .collect(Collectors.toList());
        i = 0;
        for (Person person : sortedPersons) {
            log(person);
            if (i < sortedPersons.size() - 1) {
                log(", ");
            }
            i++;
        }
        logln();


        return getLoggedText();
    }

    /**
     * Compares the ages of the two given persons via {@link Integer#compare(int, int)}.
     * 
     * @param p1 first parameter
     * @param p2 second parameter
     * @return
     * @see Integer#compare(int, int)
     */
    private static int compareByAge(Person p1, Person p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }

    /**
     * Returns the result of the application of the given <code>function</code> to the given
     * parameter <code>p1</code> and <code>p2</code>.
     * 
     * @param functionwhich is to be applied
     * @param p1 first parameter
     * @param p2 second parameter
     * @return result of the function application
     * @see BiFunction
     */
    private int apply(BiFunction<Person, Person, Integer> function, Person p1, Person p2) {
        return function.apply(p1, p2);
    }

    /**
     * Returns the result of the application of the given <code>function</code> to the given
     * parameter <code>x1</code> and <code>x2</code>.
     * 
     * @param function which is to be applied
     * @param x1 first parameter
     * @param x2 second parameter
     * @return result of the function application
     * @see BiFunction
     */
    private boolean check(BiFunction<Integer, Integer, Boolean> function, int x1, int x2) {
        return function.apply(x1, x2);
    }

    @Override
    public String[] getRelevantClassesInformation() {
        String[] relevantClasses = {LambdaExpressionExample.class.getSimpleName(),
                MathFunctionInt.class.getSimpleName(), Function.class.getSimpleName(),
                BiFunction.class.getSimpleName()};
        return relevantClasses;
    }

    @Override
    public String[] getRelevantPackagesInformation() {
        String[] relevantPackages = {getPackageNameOfClass(LambdaExpressionExample.class),
                getPackageNameOfClass(Function.class)};
        return relevantPackages;
    }

    @Override
    public String getBundleKey() {
        return "example.lambdaExpression";
    }


    /**
     * Simple class to model a person - defined only via its full name and age.
     *
     */
    private class Person {
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
}