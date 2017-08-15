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
