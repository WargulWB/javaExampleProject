package jep.example.io.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class implements a representation of {@link Plane} which uses XML-binding which allows the
 * class to be used in a {@link javax.xml.bind.JAXBContext}.
 *
 */
@XmlRootElement(name = "plane")
@XmlType(name = "PlaneType",
        propOrder = {"flightId", "pilot", "coPilot", "cabinCrewList", "passengerList"})
public class XmlTypePlane {

    private String flightId;

    private XmlTypePerson pilot;

    private XmlTypePerson coPilot = null;

    private List<XmlTypePerson> cabinCrewList = null;

    private List<XmlTypePerson> passengerList = null;

    @XmlElement(name = "flightId")
    public String getFlightId() {
        return flightId;
    }

    @XmlElement(name = "pilot")
    public XmlTypePerson getPilot() {
        return pilot;
    }

    @XmlElement(name = "coPilot")
    public XmlTypePerson getCoPilot() {
        return coPilot;
    }

    @XmlElement(name = "cabinCrew")
    public List<XmlTypePerson> getCabinCrewList() {
        return cabinCrewList;
    }

    @XmlElement(name = "passenger")
    public List<XmlTypePerson> getPassengerList() {
        return passengerList;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setPilot(XmlTypePerson pilot) {
        this.pilot = pilot;
    }

    public void setCoPilot(XmlTypePerson coPilot) {
        this.coPilot = coPilot;
    }

    public void setCabinCrewList(List<XmlTypePerson> cabinCrewList) {
        this.cabinCrewList = cabinCrewList;
    }

    public void setPassengerList(List<XmlTypePerson> passengersList) {
        this.passengerList = passengersList;
    }

}
