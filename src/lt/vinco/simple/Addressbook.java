//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.06 at 04:03:49 PM EET 
//


package lt.vinco.simple;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "lastName",
    "firstName",
    "middleName",
    "orgname",
    "name",
    "address"

})
@XmlRootElement(name = "addressbook")
public class Addressbook {
    
    
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute(name = "lang")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String lang;
   
    
    @XmlElement(name = "last-name", required = true)
    protected LastName lastName;
    @XmlElement(name = "first-name", required = true)
    protected FirstName firstName;
    @XmlElement(name = "middle-name", required = true)
    protected MiddleName middleName;
    @XmlElement(name = "orgname", required = true)
    protected Orgname orgname;
      @XmlElement(name = "address", required = true)
    protected Address address;
    //mano:
       @XmlElement(name = "name", required = false)
    protected Name name;

   
    
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    
    public String getLang() {
        return lang;
    }
    public void setLang(String value) {
        this.lang = value;
    }

    public LastName getLastName() {
        return lastName;
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = firstName;
    }

    public MiddleName getMiddleName() {
        return middleName;
    }

    public void setMiddleName(MiddleName middleName) {
        this.middleName = middleName;
    }

    public Orgname getOrgname() {
        return orgname;
    }

    public void setOrgname(Orgname orgname) {
        this.orgname = orgname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    

}//class
