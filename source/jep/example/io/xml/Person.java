package jep.example.io.xml;

import java.util.Objects;

/**
 * This class implements a very simple model of a person for the usage in plane flight scheduling at
 * an airport. Which is why an enum {@link Role} is provided by the class.
 *
 */
public class Person {

    /**
     * Enum which provides the four roles pilot, co-pilot, cabin crew and passenger.
     *
     */
    public enum Role {
        PILOT,

        CO_PILOT,

        CABIN_CREW,

        PASSENGER;
    }

    private final String firstName;
    private final String lastName;
    private final Role role;

    /**
     * Constructs a new instance of {@link Person} for the given parameters.
     * 
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param role role of the person for the flight
     * @see Role
     */
    public Person(String firstName, String lastName, Role role) {
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
        this.role = Objects.requireNonNull(role);
    }

    /**
     * Return the first name of this person.
     * 
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Return the last name of this person.
     * 
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Return the role of this person.
     * 
     * @return
     * @see Role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Return <code>true</code> if the role of this person is pilot, return <code>false</code>
     * otherwise.
     * 
     * @return
     */
    public boolean isPilot() {
        return role == Role.PILOT;
    }

    /**
     * Return <code>true</code> if the role of this person is co-pilot, return <code>false</code>
     * otherwise.
     * 
     * @return
     */
    public boolean isCoPilot() {
        return role == Role.CO_PILOT;
    }

    /**
     * Return <code>true</code> if the role of this person is cabin crew, return <code>false</code>
     * otherwise.
     * 
     * @return
     */
    public boolean isCabinCrew() {
        return role == Role.CABIN_CREW;
    }

    /**
     * Return <code>true</code> if the role of this person is passenger, return <code>false</code>
     * otherwise.
     * 
     * @return
     */
    public boolean isPassenger() {
        return role == Role.PASSENGER;
    }

    /**
     * Return the the persons information in the format:
     * 
     * <pre>
     * {role}': '{lastName}', '{firstName}
     * </pre>
     * 
     * @return
     */
    public String getInformationAsFormattedText() {
        return this.role.toString() + ": " + lastName + ", " + firstName;
    }
}
