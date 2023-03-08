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
    "textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno"
})
@XmlRootElement(name = "online")
public class Online {

    @XmlElements({
        @XmlElement(name = "text", type = Text.class),
        @XmlElement(name = "author", type = Author.class),
        @XmlElement(name = "online-title", type = OnlineTitle.class),
        @XmlElement(name = "hosttitle", type = Hosttitle.class),
        @XmlElement(name = "subname", type = Subname.class),
        @XmlElement(name = "edition", type = Edition.class),
        @XmlElement(name = "serial", type = Serial.class),
        @XmlElement(name = "book", type = Book.class),
        @XmlElement(name = "imprint", type = Imprint.class),
        @XmlElement(name = "pubdate", type = Pubdate.class),
        @XmlElement(name = "vid", type = Vid.class),
        @XmlElement(name = "ino", type = Ino.class),
        @XmlElement(name = "history", type = History.class),
        @XmlElement(name = "series", type = Series.class),
        @XmlElement(name = "hostno", type = Hostno.class),
        @XmlElement(name = "absno", type = Absno.class),
        @XmlElement(name = "location", type = Location.class),
        @XmlElement(name = "notes", type = Notes.class),
        @XmlElement(name = "avail", type = Avail.class),
        @XmlElement(name = "class", type = Class.class),
        @XmlElement(name = "keyword", type = Keyword.class),
        @XmlElement(name = "cpyrt", type = Cpyrt.class),
        @XmlElement(name = "doi", type = Doi.class),
        @XmlElement(name = "issn", type = Issn.class),
        @XmlElement(name = "isbn", type = Isbn.class),
        @XmlElement(name = "datecit", type = Datecit.class),
        @XmlElement(name = "srchterm", type = Srchterm.class),
        @XmlElement(name = "srchdate", type = Srchdate.class),
        @XmlElement(name = "refno", type = Refno.class)
    })
    protected List<Object> textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno;

    /**
     * Gets the value of the textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Text }
     * {@link Author }
     * {@link OnlineTitle }
     * {@link Hosttitle }
     * {@link Subname }
     * {@link Edition }
     * {@link Serial }
     * {@link Book }
     * {@link Imprint }
     * {@link Pubdate }
     * {@link Vid }
     * {@link Ino }
     * {@link History }
     * {@link Series }
     * {@link Hostno }
     * {@link Absno }
     * {@link Location }
     * {@link Notes }
     * {@link Avail }
     * {@link Class }
     * {@link Keyword }
     * {@link Cpyrt }
     * {@link Doi }
     * {@link Issn }
     * {@link Isbn }
     * {@link Datecit }
     * {@link Srchterm }
     * {@link Srchdate }
     * {@link Refno }
     * 
     * 
     */
    public List<Object> getTextOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno() {
        if (textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno == null) {
            textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno = new ArrayList<Object>();
        }
        return this.textOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno;
    }

}
