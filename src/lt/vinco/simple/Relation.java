//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.06 at 04:03:49 PM EET 
//


package lt.vinco.simple;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parentDoc",
    "childDoc"
})
@XmlRootElement(name = "relation")
public class Relation {

    @XmlElement(name = "parent-doc", required = true)
    protected ParentDoc parentDoc;
    @XmlElement(name = "child-doc", required = true)
    protected ChildDoc childDoc;

    /**
     * Gets the value of the parentDoc property.
     * 
     * @return
     *     possible object is
     *     {@link ParentDoc }
     *     
     */
    public ParentDoc getParentDoc() {
        return parentDoc;
    }

    /**
     * Sets the value of the parentDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentDoc }
     *     
     */
    public void setParentDoc(ParentDoc value) {
        this.parentDoc = value;
    }

    /**
     * Gets the value of the childDoc property.
     * 
     * @return
     *     possible object is
     *     {@link ChildDoc }
     *     
     */
    public ChildDoc getChildDoc() {
        return childDoc;
    }

    /**
     * Sets the value of the childDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildDoc }
     *     
     */
    public void setChildDoc(ChildDoc value) {
        this.childDoc = value;
    }

}
