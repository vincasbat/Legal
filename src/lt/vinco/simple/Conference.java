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
    "textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor"
})
@XmlRootElement(name = "conference")
public class Conference {

    @XmlElements({
        @XmlElement(name = "text", required = true, type = Text.class),
        @XmlElement(name = "conftitle", required = true, type = Conftitle.class),
        @XmlElement(name = "confdate", required = true, type = Confdate.class),
        @XmlElement(name = "confno", required = true, type = Confno.class),
        @XmlElement(name = "confplace", required = true, type = Confplace.class),
        @XmlElement(name = "confsponsor", required = true, type = Confsponsor.class)
    })
    protected List<Object> textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor;

    /**
     * Gets the value of the textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Text }
     * {@link Conftitle }
     * {@link Confdate }
     * {@link Confno }
     * {@link Confplace }
     * {@link Confsponsor }
     * 
     * 
     */
    public List<Object> getTextOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor() {
        if (textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor == null) {
            textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor = new ArrayList<Object>();
        }
        return this.textOrConftitleOrConfdateOrConfnoOrConfplaceOrConfsponsor;
    }

}
