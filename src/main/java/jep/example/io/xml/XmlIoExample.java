package jep.example.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import jep.example.AbstractExample;
import jep.example.io.xml.Person.Role;

public class XmlIoExample extends AbstractExample {

    @Override
    public String run(String... arguments) {
        log("Initializing persons and plane.");
        Person pilot = new Person("Peter", "Griffin", Role.PILOT);
        Person co_pilot = new Person("Homer", "Simpson", Role.CO_PILOT);
        List<Person> passengers = new ArrayList<>();
        passengers.add(new Person("Louis", "Griffin", Role.PASSENGER));
        passengers.add(new Person("Marge", "Simpson", Role.PASSENGER));
        Plane plane = new Plane("S1989-S1999", pilot, co_pilot, null, passengers);
        logln("The plane id is " + plane.getFlightId() + ". On board are:");
        logln(pilot.getInformationAsFormattedText());
        logln(co_pilot.getInformationAsFormattedText());
        for (Person p : passengers) {
            logln(p.getInformationAsFormattedText());
        }

        try {
            XmlIoPlane xmlIoPlane = new XmlIoPlane();
            xmlIoPlane.writePlane(plane, new File("C:\\Users\\kjelb\\Desktop\\test.txt"));
        } catch (JAXBException | SAXException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }


        return getLoggedText();
    }

    @Override
    public Class<?>[] getRelevantClasses() {
        Class<?>[] relevantClasses = {XmlIoExample.class, Plane.class, Person.class};
        return relevantClasses;
    }

    @Override
    public String getBundleKey() {
        return "example.xmlIo";
    }

}
