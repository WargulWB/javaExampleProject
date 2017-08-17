package jep.example.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import jep.example.io.xml.Person.Role;

/**
 * This class implements the functionality to convert {@link Plane}- and {@link Person}-objects into
 * {@link XmlTypePlane}- and {@link XmlTypePerson}-objects, to write those objects into XML-files
 * and to read them from XML-files.
 *
 */
public class XmlIoPlane {

    private static final String XSD_PATH = "/jep/example/io/xml/plane.xsd";

    /**
     * The JAXBContext which is used to create the marshaller and the unmarshaller.
     */
    private final JAXBContext context;

    /**
     * The marshaller which is used to write XML files.
     */
    private final Marshaller marshaller;

    /**
     * The unmarshaller which is used to read XML files.
     */
    private final Unmarshaller unmarshaller;

    /**
     * The schema which a valid XML-file of label information must conform to.
     */
    private final Schema schema;

    /**
     * Constructs a new {@link XmlIoPlane}-instance.
     * 
     * @throws JAXBException
     * @throws SAXException
     */
    public XmlIoPlane() throws JAXBException, SAXException {
        context = JAXBContext.newInstance(XmlTypePlane.class);
        Source schemaSource = new StreamSource(XmlTypePlane.class.getResourceAsStream(XSD_PATH));
        schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(schemaSource);

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setSchema(schema);

        unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
    }

    /**
     * Writes the given <code>plane</code>-instance to the given <code>destination</code>-file.
     * 
     * @param plane {@link Plane}-instance which is written to the XML-file
     * @param destination {@link File}-instance which represents the XMl-file to which the data is
     *        written
     * @throws JAXBException
     */
    public void writePlane(Plane plane, File destination) throws JAXBException {
        Objects.requireNonNull(plane);
        Objects.requireNonNull(destination);
        XmlTypePlane planeInfo = new XmlTypePlane();
        planeInfo.setFlightId(plane.getFlightId());
        planeInfo.setPilot(convertToXmlTypePerson(plane.getPilot()));
        if (plane.getCoPilot().isPresent()) {
            planeInfo.setCoPilot(convertToXmlTypePerson(plane.getCoPilot().get()));
        }
        if (plane.getCabinCrewList().isPresent()) {
            planeInfo
                    .setCabinCrewList(convertToListOfXmlTypePerson(plane.getCabinCrewList().get()));
        }
        if (plane.getPassengerList().isPresent()) {
            planeInfo
                    .setPassengerList(convertToListOfXmlTypePerson(plane.getPassengerList().get()));
        }

        marshaller.marshal(planeInfo, destination);
    }

    /**
     * Reads the data from the given <code>source</code> and generates a corresponding {@link Plane}
     * -instance.
     * 
     * @param source {@link File}-instance which represents the source xml-file
     * @return {@link Plane}-instance of the read in data
     * @throws JAXBException
     */
    public Plane readPlaneXml(File source) throws JAXBException {
        XmlTypePlane planeType = (XmlTypePlane) unmarshaller.unmarshal(source);
        return convertXmlTypePlaneToPlane(planeType);
    }

    /**
     * Converts the given {@link XmlTypePlane}-instance <code>planeType</code> to a {@link Plane}
     * -instance which is returned.
     * 
     * @param planeType {@link XmlTypePlane}-instance which is converted
     * @return generated {@link Plane}-instance
     */
    private Plane convertXmlTypePlaneToPlane(XmlTypePlane planeType) {
        String flightId = planeType.getFlightId();
        Person pilot = convertXmlTypePersonToPerson(planeType.getPilot());
        Person coPilot = convertXmlTypePersonToPerson(planeType.getCoPilot());
        List<Person> cabinCrewList = convertToPersonList(planeType.getCabinCrewList());
        List<Person> passengerList = convertToPersonList(planeType.getPassengerList());
        return new Plane(flightId, pilot, coPilot, cabinCrewList, passengerList);
    }

    /**
     * Converts the given list of {@link XmlTypePerson}-instance <code>personTypeList</code> to a
     * list of {@link Person} -instances which is returned.
     * 
     * @param personTypeList list of {@link XmlTypePerson}-instances which is converted
     * @return generated list of {@link Person}-instance
     */
    private List<Person> convertToPersonList(List<XmlTypePerson> personTypeList) {
        if (personTypeList == null) {
            return null;
        }
        List<Person> personList = new ArrayList<>(personTypeList.size());
        for (XmlTypePerson p : personTypeList) {
            personList.add(convertXmlTypePersonToPerson(p));
        }
        return personList;
    }

    /**
     * Converts the given {@link XmlTypePerson}-instance <code>personType</code> to a {@link Person}
     * -instance which is returned.
     * 
     * @param personType {@link XmlTypePerson}-instance which is converted
     * @return generated {@link Person}-instance
     */
    private Person convertXmlTypePersonToPerson(XmlTypePerson personType) {
        if (personType == null) {
            return null;
        }
        String firstName = personType.getFirstName();
        String lastName = personType.getLastName();
        Role role = personType.getRole();
        return new Person(firstName, lastName, role);
    }

    /**
     * Converts the given list of {@link Person}-instances to a list of {@link XmlTypePerson}
     * -instances.
     * 
     * @param list list of person-instances
     * @return
     */
    private List<XmlTypePerson> convertToListOfXmlTypePerson(List<Person> list) {
        List<XmlTypePerson> xmlTypePersonList = new ArrayList<>(list.size());
        for (Person p : list) {
            xmlTypePersonList.add(convertToXmlTypePerson(p));
        }
        return xmlTypePersonList;
    }

    /**
     * Converts the given {@link Person}-instance to a {@link XmlTypePerson} -instance.
     * 
     * @param person person-instance to convert
     * @return
     */
    private XmlTypePerson convertToXmlTypePerson(Person person) {
        XmlTypePerson xmlTypePerson = new XmlTypePerson();
        xmlTypePerson.setFirstName(person.getFirstName());
        xmlTypePerson.setLastName(person.getLastName());
        xmlTypePerson.setRole(person.getRole());
        return xmlTypePerson;
    }

}
