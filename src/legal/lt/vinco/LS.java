/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.lt.vinco;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "lr"
})
@XmlRootElement(name = "LS")
public class LS {

    @XmlAttribute(name = "country", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String country;
    @XmlAttribute(name = "NoOfRecords", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String noOfRecords;
    @XmlAttribute(name = "deliveryDate", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String deliveryDate;
    @XmlElement(name = "LR")
    protected List<LR> lr;

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the noOfRecords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoOfRecords() {
        return noOfRecords;
    }

    /**
     * Sets the value of the noOfRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoOfRecords(String value) {
        this.noOfRecords = value;
    }

    /**
     * Gets the value of the deliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Sets the value of the deliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryDate(String value) {
        this.deliveryDate = value;
    }

    /**
     * Gets the value of the lr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LR }
     * 
     * 
     */
    public List<LR> getLR() {
        if (lr == null) {
            lr = new ArrayList<LR>();
        }
        return this.lr;
    }

}
