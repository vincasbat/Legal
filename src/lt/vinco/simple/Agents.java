//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.06 at 04:03:49 PM EET 
//


package lt.vinco.simple;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "customerNumberOrAgent"
})
@XmlRootElement(name = "agents")
public class Agents {

    @XmlElements({
        @XmlElement(name = "customer-number", required = true, type = CustomerNumber.class),
        @XmlElement(name = "agent", required = true, type = Agent.class)
    })
    protected List<Object> customerNumberOrAgent;

    /**
     * Gets the value of the customerNumberOrAgent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerNumberOrAgent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerNumberOrAgent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerNumber }
     * {@link Agent }
     * 
     * 
     */
    public List<Object> getCustomerNumberOrAgent() {
        if (customerNumberOrAgent == null) {
            customerNumberOrAgent = new ArrayList<Object>();
        }
        return this.customerNumberOrAgent;
    }

}
