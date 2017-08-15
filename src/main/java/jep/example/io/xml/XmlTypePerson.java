package jep.example.io.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import jep.example.io.xml.Person.Role;


@XmlType(name = "PersonType")
public class XmlTypePerson {

    private String firstName;

    private String lastName;

    private Role role;

    @XmlElement(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    @XmlElement(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    @XmlElement(name = "role")
    public Role getRole() {
        return role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
