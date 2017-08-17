package jep.example.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import jep.example.AbstractExample;
import jep.example.io.xml.Person.Role;

/**
 * This class implements an example of how to write a java 'complex' java object to a file in
 * xml-encoding and how to read it back in.
 *
 */
public class XmlIoExample extends AbstractExample {

    private static final String XML_FILE_NAME = "plane.xml";

    @Override
    public String run(String... arguments) {
        if (!validateArguments(arguments)) {
            return getLoggedText();
        }
        String path = arguments[0] + File.separator + XML_FILE_NAME;
        File xmlFile = new File(path);

        log("Initializing persons and plane.");
        Person pilot = new Person("Peter", "Griffin", Role.PILOT);
        Person co_pilot = new Person("Homer", "Simpson", Role.CO_PILOT);
        List<Person> passengers = new ArrayList<>();
        passengers.add(new Person("Louis", "Griffin", Role.PASSENGER));
        passengers.add(new Person("Marge", "Simpson", Role.PASSENGER));
        Plane plane = new Plane("S1989-S1999", pilot, co_pilot, null, passengers);
        logPlane(plane);
        logLineSeparator();

        logln("Writing plane to '" + path + "'.");
        try {
            XmlIoPlane xmlIoPlane = new XmlIoPlane();
            xmlIoPlane.writePlane(plane, xmlFile);
        } catch (JAXBException | SAXException exc) {
            logln("Something went wrong during the write operation of the xml-file.");
            logln(exc.getMessage());
            exc.printStackTrace();
        }
        logLineSeparator();

        logln("Reading plane from '" + path + "'.");
        plane = null;
        try {
            XmlIoPlane xmlIoPlane = new XmlIoPlane();
            plane = xmlIoPlane.readPlaneXml(xmlFile);
        } catch (JAXBException | SAXException exc) {
            logln("Something went wrong during the read operation of the xml-file.");
            logln(exc.getMessage());
            exc.printStackTrace();
        }
        logPlane(plane);

        return getLoggedText();
    }

    /**
     * Validates the given arguments. This example requires one argument, the path to a valid
     * directory.
     * 
     * @param arguments arguments which are given by the user
     * @return
     */
    private boolean validateArguments(String[] arguments) {
        if (arguments.length != 1) {
            logln("This example requires one argument, the path of the directory,\n"
                    + " where the xml-file shall be written.");
            return false;
        }
        File file = new File(arguments[0]);
        if (!file.isDirectory()) {
            logln("The given argument is not a path to a valid directory.");
            return false;
        }

        return true;
    }

    /**
     * Logs the planes information as formatted text to the example log.
     * 
     * @param plane {@link Plane}-instance whose information is written to the log
     */
    private void logPlane(Plane plane) {
        logln("FLIGHT_ID: " + plane.getFlightId());
        logln(plane.getPilot().getInformationAsFormattedText());
        if (plane.getCoPilot().isPresent()) {
            logln(plane.getCoPilot().get().getInformationAsFormattedText());
        } else {
            logln(Role.CO_PILOT + ": NONE");
        }
        if (plane.getCabinCrewList().isPresent()) {
            for (Person p : plane.getCabinCrewList().get()) {
                logln(p.getInformationAsFormattedText());
            }
        } else {
            logln(Role.CABIN_CREW + ": NONE");
        }
        if (plane.getPassengerList().isPresent()) {
            for (Person p : plane.getPassengerList().get()) {
                logln(p.getInformationAsFormattedText());
            }
        } else {
            logln(Role.PASSENGER + ": NONE");
        }
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses = {XmlIoExample.class, Plane.class, Person.class,
                XmlIoPlane.class, XmlTypePerson.class, XmlTypePlane.class};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.xmlIo";
    }

}
