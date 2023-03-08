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
    "patcitOrNplcit",
    "relPassageOrCategoryOrRelClaims"
})
@XmlRootElement(name = "corresponding-docs")
public class CorrespondingDocs {

    @XmlElements({
        @XmlElement(name = "patcit", required = true, type = Patcit.class),
        @XmlElement(name = "nplcit", required = true, type = Nplcit.class)
    })
    protected List<Object> patcitOrNplcit;
    @XmlElements({
        @XmlElement(name = "rel-passage", type = RelPassage.class),
        @XmlElement(name = "category", type = Category.class),
        @XmlElement(name = "rel-claims", type = RelClaims.class)
    })
    protected List<Object> relPassageOrCategoryOrRelClaims;

    /**
     * Gets the value of the patcitOrNplcit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patcitOrNplcit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatcitOrNplcit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Patcit }
     * {@link Nplcit }
     * 
     * 
     */
    public List<Object> getPatcitOrNplcit() {
        if (patcitOrNplcit == null) {
            patcitOrNplcit = new ArrayList<Object>();
        }
        return this.patcitOrNplcit;
    }

    /**
     * Gets the value of the relPassageOrCategoryOrRelClaims property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relPassageOrCategoryOrRelClaims property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelPassageOrCategoryOrRelClaims().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelPassage }
     * {@link Category }
     * {@link RelClaims }
     * 
     * 
     */
    public List<Object> getRelPassageOrCategoryOrRelClaims() {
        if (relPassageOrCategoryOrRelClaims == null) {
            relPassageOrCategoryOrRelClaims = new ArrayList<Object>();
        }
        return this.relPassageOrCategoryOrRelClaims;
    }

}