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
    "textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno"
})
@XmlRootElement(name = "article")
public class Article {

    @XmlElements({
        @XmlElement(name = "text", required = true, type = Text.class),
        @XmlElement(name = "author", required = true, type = Author.class),
        @XmlElement(name = "atl", required = true, type = Atl.class),
        @XmlElement(name = "subname", required = true, type = Subname.class),
        @XmlElement(name = "serial", required = true, type = Serial.class),
        @XmlElement(name = "book", required = true, type = Book.class),
        @XmlElement(name = "absno", required = true, type = Absno.class),
        @XmlElement(name = "location", required = true, type = Location.class),
        @XmlElement(name = "class", required = true, type = Class.class),
        @XmlElement(name = "keyword", required = true, type = Keyword.class),
        @XmlElement(name = "cpyrt", required = true, type = Cpyrt.class),
        @XmlElement(name = "artid", required = true, type = Artid.class),
        @XmlElement(name = "refno", required = true, type = Refno.class)
    })
    protected List<Object> textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno;

    /**
     * Gets the value of the textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Text }
     * {@link Author }
     * {@link Atl }
     * {@link Subname }
     * {@link Serial }
     * {@link Book }
     * {@link Absno }
     * {@link Location }
     * {@link Class }
     * {@link Keyword }
     * {@link Cpyrt }
     * {@link Artid }
     * {@link Refno }
     * 
     * 
     */
    public List<Object> getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno() {
        if (textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno == null) {
            textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno = new ArrayList<Object>();
        }
        return this.textOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno;
    }

}
