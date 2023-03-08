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
    "publicationReference",
    "classificationIpc",
    "classificationsIpcr",
    "classificationNational",
    "classificationLocarno",
    "applicationReference",
    "languageOfPublication",
    "priorityClaims",
    "parties",
    "inventionTitle",
    "referencesCited",
    "relatedDocuments",
    "pctOrRegionalFilingData",
    "pctOrRegionalPublishingData"
})
@XmlRootElement(name = "bibliographic-data")
public class BibliographicData {

    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute(name = "lang")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String lang;
    @XmlAttribute(name = "country")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String country;
    @XmlAttribute(name = "status")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String status;
    @XmlElement(name = "publication-reference", required = true)
    protected PublicationReference publicationReference;
    @XmlElement(name = "classification-ipc")
    protected ClassificationIpc classificationIpc;
    @XmlElement(name = "classifications-ipcr")
    protected ClassificationsIpcr classificationsIpcr;
    @XmlElement(name = "classification-national")
    protected List<ClassificationNational> classificationNational;
    @XmlElement(name = "classification-locarno")
    protected ClassificationLocarno classificationLocarno;
    @XmlElement(name = "application-reference", required = true)
    protected ApplicationReference applicationReference;
    @XmlElement(name = "language-of-publication")
    protected String languageOfPublication;
    @XmlElement(name = "priority-claims")
    protected PriorityClaims priorityClaims;
    @XmlElement(required = true)
    protected Parties parties;
    @XmlElement(name = "invention-title", required = true)
    protected List<InventionTitle> inventionTitle;
    @XmlElement(name = "references-cited")
    protected ReferencesCited referencesCited;
    @XmlElement(name = "related-documents")
    protected RelatedDocuments relatedDocuments;
    @XmlElement(name = "pct-or-regional-filing-data")
    protected List<PctOrRegionalFilingData> pctOrRegionalFilingData;
    @XmlElement(name = "pct-or-regional-publishing-data")
    protected List<PctOrRegionalPublishingData> pctOrRegionalPublishingData;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the publicationReference property.
     * 
     * @return
     *     possible object is
     *     {@link PublicationReference }
     *     
     */
    public PublicationReference getPublicationReference() {
        return publicationReference;
    }

    /**
     * Sets the value of the publicationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicationReference }
     *     
     */
    public void setPublicationReference(PublicationReference value) {
        this.publicationReference = value;
    }

    /**
     * Gets the value of the classificationIpc property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationIpc }
     *     
     */
    public ClassificationIpc getClassificationIpc() {
        return classificationIpc;
    }

    /**
     * Sets the value of the classificationIpc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationIpc }
     *     
     */
    public void setClassificationIpc(ClassificationIpc value) {
        this.classificationIpc = value;
    }

    /**
     * Gets the value of the classificationsIpcr property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationsIpcr }
     *     
     */
    public ClassificationsIpcr getClassificationsIpcr() {
        return classificationsIpcr;
    }

    /**
     * Sets the value of the classificationsIpcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationsIpcr }
     *     
     */
    public void setClassificationsIpcr(ClassificationsIpcr value) {
        this.classificationsIpcr = value;
    }

    /**
     * Gets the value of the classificationNational property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classificationNational property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassificationNational().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClassificationNational }
     * 
     * 
     */
    public List<ClassificationNational> getClassificationNational() {
        if (classificationNational == null) {
            classificationNational = new ArrayList<ClassificationNational>();
        }
        return this.classificationNational;
    }

    /**
     * Gets the value of the classificationLocarno property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationLocarno }
     *     
     */
    public ClassificationLocarno getClassificationLocarno() {
        return classificationLocarno;
    }

    /**
     * Sets the value of the classificationLocarno property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationLocarno }
     *     
     */
    public void setClassificationLocarno(ClassificationLocarno value) {
        this.classificationLocarno = value;
    }

    /**
     * Gets the value of the applicationReference property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationReference }
     *     
     */
    public ApplicationReference getApplicationReference() {
        return applicationReference;
    }

    /**
     * Sets the value of the applicationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationReference }
     *     
     */
    public void setApplicationReference(ApplicationReference value) {
        this.applicationReference = value;
    }

    /**
     * Gets the value of the languageOfPublication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageOfPublication() {
        return languageOfPublication;
    }

    /**
     * Sets the value of the languageOfPublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageOfPublication(String value) {
        this.languageOfPublication = value;
    }

    /**
     * Gets the value of the priorityClaims property.
     * 
     * @return
     *     possible object is
     *     {@link PriorityClaims }
     *     
     */
    public PriorityClaims getPriorityClaims() {
        return priorityClaims;
    }

    /**
     * Sets the value of the priorityClaims property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriorityClaims }
     *     
     */
    public void setPriorityClaims(PriorityClaims value) {
        this.priorityClaims = value;
    }

    /**
     * Gets the value of the parties property.
     * 
     * @return
     *     possible object is
     *     {@link Parties }
     *     
     */
    public Parties getParties() {
        return parties;
    }

    /**
     * Sets the value of the parties property.
     * 
     * @param value
     *     allowed object is
     *     {@link Parties }
     *     
     */
    public void setParties(Parties value) {
        this.parties = value;
    }

    /**
     * Gets the value of the inventionTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inventionTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInventionTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InventionTitle }
     * 
     * 
     */
    public List<InventionTitle> getInventionTitle() {
        if (inventionTitle == null) {
            inventionTitle = new ArrayList<InventionTitle>();
        }
        return this.inventionTitle;
    }

    /**
     * Gets the value of the referencesCited property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencesCited }
     *     
     */
    public ReferencesCited getReferencesCited() {
        return referencesCited;
    }

    /**
     * Sets the value of the referencesCited property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencesCited }
     *     
     */
    public void setReferencesCited(ReferencesCited value) {
        this.referencesCited = value;
    }

    /**
     * Gets the value of the relatedDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link RelatedDocuments }
     *     
     */
    public RelatedDocuments getRelatedDocuments() {
        return relatedDocuments;
    }

    /**
     * Sets the value of the relatedDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedDocuments }
     *     
     */
    public void setRelatedDocuments(RelatedDocuments value) {
        this.relatedDocuments = value;
    }

    /**
     * Gets the value of the pctOrRegionalFilingData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pctOrRegionalFilingData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPctOrRegionalFilingData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PctOrRegionalFilingData }
     * 
     * 
     */
    public List<PctOrRegionalFilingData> getPctOrRegionalFilingData() {
        if (pctOrRegionalFilingData == null) {
            pctOrRegionalFilingData = new ArrayList<PctOrRegionalFilingData>();
        }
        return this.pctOrRegionalFilingData;
    }

    /**
     * Gets the value of the pctOrRegionalPublishingData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pctOrRegionalPublishingData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPctOrRegionalPublishingData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PctOrRegionalPublishingData }
     * 
     * 
     */
    public List<PctOrRegionalPublishingData> getPctOrRegionalPublishingData() {
        if (pctOrRegionalPublishingData == null) {
            pctOrRegionalPublishingData = new ArrayList<PctOrRegionalPublishingData>();
        }
        return this.pctOrRegionalPublishingData;
    }

}
