package jep.example.io.xml;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class implements a very simple model of a plane (planed for one flight) for airport
 * coordination purposes. The plane must provide a flight id and must have a pilot. It can also have
 * a co-pilot, a cabin crew and passengers, also all of these are optional.
 *
 */
public class Plane {

    private final String flightId;
    private final Person pilot;
    private final Person coPilot;
    private final List<Person> cabineCrew;
    private final List<Person> passangers;

    /**
     * Constructs a new instance of {@link Plane} for the given parameters.
     * 
     * @param flightId an id which identifies the plane for on flight (is non optional [not allowed
     *        to be null] and is supposed to be unique)
     * @param pilot the pilot who flies the plane for one flight (is non optional [not allowed to be
     *        null])
     * @param coPilot the co pilot who assists the pilot for the flight (is optional [can be set as
     *        null])
     * @param cabinCrew the cabin crew which handles the cabin related work for the flight (is
     *        optional [can be set as null])
     * @param passangers the passengers who are on board of the plane for the flight (is optional
     *        [can be set as null])
     */
    public Plane(String flightId, Person pilot, Person coPilot, List<Person> cabinCrew,
            List<Person> passangers) {
        this.flightId = Objects.requireNonNull(flightId);
        this.pilot = Objects.requireNonNull(pilot);
        this.coPilot = coPilot;
        this.cabineCrew = cabinCrew;
        this.passangers = passangers;
    }

    /**
     * Return the id of the plane for the current flight.
     * 
     * @return
     */
    public String getFlightId() {
        return flightId;
    }

    /**
     * Return the pilot of the plane for the current flight.
     * 
     * @return
     */
    public Person getPilot() {
        return pilot;
    }

    /**
     * Return the co pilot of the plane for the current flight.
     * 
     * @return
     */
    public Optional<Person> getCoPilot() {
        return Optional.ofNullable(coPilot);
    }

    /**
     * Return the cabin crew list of the plane for the current flight.
     * 
     * @return
     */
    public Optional<List<Person>> getCabinCrewList() {
        return Optional.ofNullable(cabineCrew);
    }

    /**
     * Return the passenger list of the plane for the current flight.
     * 
     * @return
     */
    public Optional<List<Person>> getPassengerList() {
        return Optional.ofNullable(passangers);
    }


}
