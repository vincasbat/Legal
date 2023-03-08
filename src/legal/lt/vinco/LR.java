/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legal.lt.vinco;



import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "l001",
    "l002",
    "l003",
    "l004",
    "l005",
    "l007",
    "l008",
    "l013",
    "l500"
})
@XmlRootElement(name = "LR")
public class LR {

    @XmlAttribute(name = "sequence", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String sequence;
    @XmlElement(name = "L001", required = true)
    protected String l001;
    @XmlElement(name = "L002", required = true)
    protected String l002;
    @XmlElement(name = "L003", required = true)
    protected String l003;
    @XmlElement(name = "L004", required = true)
    protected String l004;
    @XmlElement(name = "L005")
    protected String l005;
    @XmlElement(name = "L007", required = true)
    protected String l007;
    @XmlElement(name = "L008", required = true)
    protected String l008;
    @XmlElement(name = "L013", required = true)
    protected String l013;
    @XmlElement(name = "L500")
    protected L500 l500;

    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequence(String value) {
        this.sequence = value;
    }

    /**
     * Gets the value of the l001 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL001() {
        return l001;
    }

    /**
     * Sets the value of the l001 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL001(String value) {
        this.l001 = value;
    }

    /**
     * Gets the value of the l002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL002() {
        return l002;
    }

    /**
     * Sets the value of the l002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL002(String value) {
        this.l002 = value;
    }

    /**
     * Gets the value of the l003 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL003() {
        return l003;
    }

    /**
     * Sets the value of the l003 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL003(String value) {
        this.l003 = value;
    }

    /**
     * Gets the value of the l004 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL004() {
        return l004;
    }

    /**
     * Sets the value of the l004 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL004(String value) {
        this.l004 = value;
    }

    /**
     * Gets the value of the l005 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL005() {
        return l005;
    }

    /**
     * Sets the value of the l005 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL005(String value) {
        this.l005 = value;
    }

    /**
     * Gets the value of the l007 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL007() {
        return l007;
    }

    /**
     * Sets the value of the l007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL007(String value) {
        this.l007 = value;
    }

    /**
     * Gets the value of the l008 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL008() {
        return l008;
    }

    /**
     * Sets the value of the l008 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL008(String value) {
        this.l008 = value;
    }

    /**
     * Gets the value of the l013 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL013() {
        return l013;
    }

    /**
     * Sets the value of the l013 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL013(String value) {
        this.l013 = value;
    }

    /**
     * Gets the value of the l500 property.
     * 
     * @return
     *     possible object is
     *     {@link L500 }
     *     
     */
    public L500 getL500() {
        return l500;
    }

    /**
     * Sets the value of the l500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link L500 }
     *     
     */
    public void setL500(L500 value) {
        this.l500 = value;
    }

}