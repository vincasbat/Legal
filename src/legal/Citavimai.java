/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import lt.vinco.simple.Article;
import lt.vinco.simple.Atl;
import lt.vinco.simple.Author;
import lt.vinco.simple.BibliographicData;
import lt.vinco.simple.Book;
import lt.vinco.simple.BookTitle;
import lt.vinco.simple.Category;
import lt.vinco.simple.Citation;
import lt.vinco.simple.Country;
import lt.vinco.simple.Date;
import lt.vinco.simple.DocumentId;
import lt.vinco.simple.Imprint;
import lt.vinco.simple.Location;
import lt.vinco.simple.Name;
import lt.vinco.simple.Notes;
import lt.vinco.simple.Nplcit;
import lt.vinco.simple.Online;
import lt.vinco.simple.Passage;
import lt.vinco.simple.Patcit;
import lt.vinco.simple.Pubdate;
import lt.vinco.simple.ReferencesCited;
import lt.vinco.simple.Refno;
import lt.vinco.simple.RelClaims;
import lt.vinco.simple.RelPassage;
import lt.vinco.simple.Serial;
import lt.vinco.simple.SimplePatentDocument;
import lt.vinco.simple.Text;

/**
 *
 * @author vincas
 */
public class Citavimai {

    static void Cituoti(String patento_numeris) throws JAXBException, IOException, ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver"); 
        
 
 String user = "";
 String password = "";
 
 String  connection = "jdbc:mysql://hhh:3306/...";
 Connection conn = DriverManager.getConnection(connection, user, password);
 
    
 
 DateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
 Calendar cal = Calendar.getInstance();
 String dateProduced =  sdf.format(cal.getTime());
        
      


        
        Statement stmt = conn.createStatement();

        String patcit_sql = "SELECT * FROM citation, patcit WHERE citation.id = patcit.citation_id AND patnr = " + "\"" + patento_numeris + "\"";
        ResultSet resultSet = stmt.executeQuery(patcit_sql);
        ReferencesCited rc = new ReferencesCited();
        SimplePatentDocument spd = new SimplePatentDocument();
        BibliographicData bd = new BibliographicData();
        spd.setBibliographicData(bd);
        bd.setReferencesCited(rc);
spd.setDocNumber(patento_numeris);
spd.setDateProduced(dateProduced);
        while (resultSet.next()) {
            
 RelClaims rclaims = null;
 
            String citedphase = resultSet.getString("cited_phase");
            String citedby = resultSet.getString("cited_by");
            String relpassage = resultSet.getString("rel_passage");
            String category = resultSet.getString("category");
            String relclaims = resultSet.getString("rel_claims");

            String dnum = resultSet.getString("dnum");
         if(dnum!=null) { dnum = dnum.replace(" ", ""); }
            
            
            String dnumtype = resultSet.getString("dnum_type");
            String url = resultSet.getString("url");
            String country = resultSet.getString("country");
            String docnumber = resultSet.getString("doc_number");
            String kind = resultSet.getString("kind");
            String date = resultSet.getString("date");
            String name = resultSet.getString("name");
            String lang = resultSet.getString("lang");

            Citation cit = new Citation();//taisyta Citation klase
            if ((citedby != null) && (!citedby.isEmpty())) {
                cit.setCitedBy(citedby);
            }
            if ((citedphase != null) && (!citedphase.isEmpty())) {
                cit.setCitedPhase(citedphase);
            }
            
            
            
        if ((relpassage != null) && (!relpassage.isEmpty())) {     
            RelPassage relPassage = new RelPassage();
            Passage passage = new Passage();
            passage.setvalue(relpassage);
            relPassage.getTextOrPassageOrCategoryOrRelClaims().add(passage);
              cit.getRelPassageOrCategoryOrRelClaims().add(relPassage);
        }   
            
           
            if ((relclaims != null) && (!relclaims.isEmpty())) {
                rclaims = new RelClaims();
                rclaims.setvalue(relclaims);
            }
            Category ctg = null;
            if ((category != null) && (!category.isEmpty())) {
                ctg = new Category();
                ctg.setvalue(category);
            }
            
          
            
            
            cit.getRelPassageOrCategoryOrRelClaims().add(ctg);
            cit.getRelPassageOrCategoryOrRelClaims().add(rclaims);

            rc.getTextOrCitation().add(cit);
            Patcit patcit = new Patcit();
            if ((dnum != null) && (!dnum.isEmpty())) {
                patcit.setDnum(dnum);
            }
            if ((dnumtype != null) && (!dnumtype.isEmpty())) {
                patcit.setDnumType(dnumtype);
            }
            if ((url != null) && (!url.isEmpty())) {
                patcit.setUrl(url);
            }

            cit.getPatcitOrNplcit().add(patcit);
            DocumentId did = new DocumentId();
            patcit.getTextOrDocumentId().add(did);
            Country ctry = new Country();
            ctry.setvalue(country);
            did.setCountry(ctry);
            Date dt = new Date();
            if ((date != null) && (!date.isEmpty())) {
                dt.setvalue(date);
                did.setDate(dt);
            }
            did.setDocNumber(docnumber);
            if (!kind.isEmpty()) {
                did.setKind(kind);
            }
            if ((name != null) && (!name.isEmpty())) {
                Name nm = new Name();
                nm.setvalue(name);
                did.setName(nm);
            }

            if ((lang != null) && (!lang.isEmpty())) {
                did.setLang(lang);
            }

        }//while

//++++++++++++++++   Articles    ++++++++++++++++++++++
        
        String article_sql = "SELECT * FROM citation, article WHERE citation.id = article.citation_id AND patnr =" + "\"" + patento_numeris + "\"";
        resultSet = stmt.executeQuery(article_sql);

        while (resultSet.next()) {

            String citedphase = resultSet.getString("cited_phase");
            String citedby = resultSet.getString("cited_by");
            String relpassage = resultSet.getString("rel_passage");
            String category = resultSet.getString("category");
            String relclaims = resultSet.getString("rel_claims");

            String autoriai = Perkodavimas2.Perkoduoti(resultSet.getString("autoriai"));
            String atl = Perkodavimas2.Perkoduoti(resultSet.getString("atl"));
            String refno = resultSet.getString("refno");
            String sertitle = Perkodavimas2.Perkoduoti(resultSet.getString("sertitle"));
            String imprint = resultSet.getString("imprint");
            String pubdate = resultSet.getString("pubdate");
            String notes = Perkodavimas2.Perkoduoti(resultSet.getString("notes"));
            String location = Perkodavimas2.Perkoduoti(resultSet.getString("location"));
            

            Citation cit = new Citation();//taisyta Citation klase
            if ((citedby != null) && (!citedby.isEmpty())) {
                cit.setCitedBy(citedby);
            }
            if ((citedphase != null) && (!citedphase.isEmpty())) {
                cit.setCitedPhase(citedphase);
            }
            
            
              if ((relpassage != null) && (!relpassage.isEmpty())) {     
            RelPassage relPassage = new RelPassage();
            Passage passage = new Passage();
            passage.setvalue(relpassage);
            relPassage.getTextOrPassageOrCategoryOrRelClaims().add(passage);
              cit.getRelPassageOrCategoryOrRelClaims().add(relPassage);
        }   
            
       
            
            
            RelClaims rclaims = null;
            if ((relclaims != null) && (!relclaims.isEmpty())) {
                rclaims = new RelClaims();
                rclaims.setvalue(relclaims);
            }
            Category ctg = null;
            if ((category != null) && (!category.isEmpty())) {
                ctg = new Category();
                ctg.setvalue(category);
            }
          
            cit.getRelPassageOrCategoryOrRelClaims().add(ctg);
            cit.getRelPassageOrCategoryOrRelClaims().add(rclaims);

            rc.getTextOrCitation().add(cit);
            Nplcit nplcit = new Nplcit();
            cit.getPatcitOrNplcit().add(nplcit);
            Article article = new Article();
            nplcit.getTextOrArticleOrBookOrOnlineOrOthercit().add(article);
            Author author = new Author();
            article.getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno().add(author);
            Name name = new Name();
            name.setvalue(autoriai);
            author.getNameOrPrefixOrLastNameOrOrgnameOrFirstNameOrMiddleNameOrFirstLastNameOrSecondLastNameOrSuffixOrIidOrRoleOrDepartmentOrSynonymOrRegisteredNumberOrAddressbook().add(name);
            Atl articleTitle = new Atl();
            articleTitle.setvalue(atl);
            article.getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno().add(articleTitle);
            Serial serial = new Serial();
            article.getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno().add(serial);
            serial.setSertitle(sertitle);

            if ((imprint != null) && (!imprint.isEmpty())) {
                Imprint impr = new Imprint();
                Text text = new Text();
                text.setvalue(imprint);
                impr.getTextOrAddressOrNameOrPubdate().add(text);
                serial.setImprint(impr);
            }

            if ((pubdate != null) && (!pubdate.isEmpty())) {
                Pubdate pbdate = new Pubdate();
                pbdate.setvalue(pubdate);
                serial.setPubdate(pbdate);
            }

            if ((notes != null) && (!notes.isEmpty())) {
                Notes ntes = new Notes();
                ntes.setvalue(notes);
                serial.setNotes(ntes);
            }

            if ((location != null) && (!location.isEmpty())) {
                Location loc = new Location();
                Text locationText = new Text();
                locationText.setvalue(location);
                loc.getTextOrSerpartOrSersectOrChapterOrPpOrColumnOrParaOrLine().add(locationText);
                article.getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno().add(loc);
            }

            if ((refno != null) && (!refno.isEmpty())) {
                Refno rfno = new Refno();
                rfno.setvalue(refno);
                article.getTextOrAuthorOrAtlOrSubnameOrSerialOrBookOrAbsnoOrLocationOrClazzOrKeywordOrCpyrtOrArtidOrRefno().add(rfno);
            }

        }//while article

//++++++++++++++++   Books    ++++++++++++++++++++++
        
        String book_sql = "SELECT * FROM citation, book WHERE citation.id = book.citation_id AND patnr = " + "\"" + patento_numeris + "\"";
        resultSet = stmt.executeQuery(book_sql);

        while (resultSet.next()) {

            String citedphase = resultSet.getString("cited_phase");
            String citedby = resultSet.getString("cited_by");
            String relpassage = resultSet.getString("rel_passage");
            String category = resultSet.getString("category");
            String relclaims = resultSet.getString("rel_claims");

            String autoriai = Perkodavimas2.Perkoduoti(resultSet.getString("autoriai"));
            String booktitle = Perkodavimas2.Perkoduoti(resultSet.getString("book_title"));
            String refno = resultSet.getString("refno");
            String imprint = resultSet.getString("imprint");
            String location = resultSet.getString("location");

            Citation cit = new Citation();//taisyta Citation klase
            if ((citedby != null) && (!citedby.isEmpty())) {
                cit.setCitedBy(citedby);
            }
            if ((citedphase != null) && (!citedphase.isEmpty())) {
                cit.setCitedPhase(citedphase);
            }
            
            
             if ((relpassage != null) && (!relpassage.isEmpty())) {     
            RelPassage relPassage = new RelPassage();
            Passage passage = new Passage();
            passage.setvalue(relpassage);
            relPassage.getTextOrPassageOrCategoryOrRelClaims().add(passage);
              cit.getRelPassageOrCategoryOrRelClaims().add(relPassage);
        }   
            
            
            
            
            RelClaims rclaims = null;
            if ((relclaims != null) && (!relclaims.isEmpty())) {
                rclaims = new RelClaims();
                rclaims.setvalue(relclaims);
            }
            Category ctg = null;
            if ((category != null) && (!category.isEmpty())) {
                ctg = new Category();
                ctg.setvalue(category);
            }
           
            cit.getRelPassageOrCategoryOrRelClaims().add(ctg);
            cit.getRelPassageOrCategoryOrRelClaims().add(rclaims);

            rc.getTextOrCitation().add(cit);
            Nplcit nplcit = new Nplcit();
            cit.getPatcitOrNplcit().add(nplcit);
            Book book = new Book();
            nplcit.getTextOrArticleOrBookOrOnlineOrOthercit().add(book);
            Author author = new Author();
            book.getTextOrAuthorOrBookTitleOrConferenceOrSubtitleOrSubnameOrEditionOrImprintOrVidOrInoOrDescripOrSeriesOrNotesOrAbsnoOrLocationOrPubidOrBooknoOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrRefno().add(author);
            Name name = new Name();
            name.setvalue(autoriai);
            author.getNameOrPrefixOrLastNameOrOrgnameOrFirstNameOrMiddleNameOrFirstLastNameOrSecondLastNameOrSuffixOrIidOrRoleOrDepartmentOrSynonymOrRegisteredNumberOrAddressbook().add(name);
            BookTitle bktitle = new BookTitle();
            bktitle.setvalue(booktitle);
            book.getTextOrAuthorOrBookTitleOrConferenceOrSubtitleOrSubnameOrEditionOrImprintOrVidOrInoOrDescripOrSeriesOrNotesOrAbsnoOrLocationOrPubidOrBooknoOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrRefno().add(bktitle);

            if ((imprint != null) && (!imprint.isEmpty())) {
                Imprint impr = new Imprint();
                Text text = new Text();
                text.setvalue(imprint);
                impr.getTextOrAddressOrNameOrPubdate().add(text);
                book.getTextOrAuthorOrBookTitleOrConferenceOrSubtitleOrSubnameOrEditionOrImprintOrVidOrInoOrDescripOrSeriesOrNotesOrAbsnoOrLocationOrPubidOrBooknoOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrRefno().add(impr);
            }

            if ((location != null) && (!location.isEmpty())) {
                Location loc = new Location();
                Text locationText = new Text();
                locationText.setvalue(location);
                loc.getTextOrSerpartOrSersectOrChapterOrPpOrColumnOrParaOrLine().add(locationText);
                book.getTextOrAuthorOrBookTitleOrConferenceOrSubtitleOrSubnameOrEditionOrImprintOrVidOrInoOrDescripOrSeriesOrNotesOrAbsnoOrLocationOrPubidOrBooknoOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrRefno().add(loc);
            }

            if ((refno != null) && (!refno.isEmpty())) {
                Refno rfno = new Refno();
                rfno.setvalue(refno);
                book.getTextOrAuthorOrBookTitleOrConferenceOrSubtitleOrSubnameOrEditionOrImprintOrVidOrInoOrDescripOrSeriesOrNotesOrAbsnoOrLocationOrPubidOrBooknoOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrRefno().add(rfno);
            }
        }//while book

//++++++++++++++++   Online    ++++++++++++++++++++++
    
        String online_sql = "SELECT * FROM citation, online WHERE citation.id = online.citation_id AND patnr = " + "\"" + patento_numeris + "\"";       
        resultSet = stmt.executeQuery(online_sql);

        while (resultSet.next()) {

            String citedphase = resultSet.getString("cited_phase");
            String citedby = resultSet.getString("cited_by");
            String relpassage = resultSet.getString("rel_passage");
            String category = resultSet.getString("category");
            String relclaims = resultSet.getString("rel_claims");

            String text = Perkodavimas2.Perkoduoti(resultSet.getString("text"));

            Citation cit = new Citation();//taisyta Citation klase
            if ((citedby != null) && (!citedby.isEmpty())) {
                cit.setCitedBy(citedby);
            }
            if ((citedphase != null) && (!citedphase.isEmpty())) {
                cit.setCitedPhase(citedphase);
            }
            
  
       
             if ((relpassage != null) && (!relpassage.isEmpty())) {     
            RelPassage relPassage = new RelPassage();
            Passage passage = new Passage();
            passage.setvalue(relpassage);
            relPassage.getTextOrPassageOrCategoryOrRelClaims().add(passage);
              cit.getRelPassageOrCategoryOrRelClaims().add(relPassage);
        }   
            
            

            if ((relclaims != null) && (!relclaims.isEmpty())) {
                RelClaims rclaims = new RelClaims();
                rclaims.setvalue(relclaims);
                cit.getRelPassageOrCategoryOrRelClaims().add(rclaims);
            }

            if ((category != null) && (!category.isEmpty())) {
                Category ctg = new Category();
                ctg.setvalue(category);
                cit.getRelPassageOrCategoryOrRelClaims().add(ctg);
            }

            rc.getTextOrCitation().add(cit);
            Nplcit nplcit = new Nplcit();
            cit.getPatcitOrNplcit().add(nplcit);
            Online online = new Online();
            nplcit.getTextOrArticleOrBookOrOnlineOrOthercit().add(online);
            Text txt = new Text();
            txt.setvalue(text);
            online.getTextOrAuthorOrOnlineTitleOrHosttitleOrSubnameOrEditionOrSerialOrBookOrImprintOrPubdateOrVidOrInoOrHistoryOrSeriesOrHostnoOrAbsnoOrLocationOrNotesOrAvailOrClazzOrKeywordOrCpyrtOrDoiOrIssnOrIsbnOrDatecitOrSrchtermOrSrchdateOrRefno().add(txt);

        }//while online

        //----------------------- viso xml formavimas ----------------------
      
        JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
        Marshaller m = jc.createMarshaller();
     
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

   
        OutputStream os = new FileOutputStream("LT"+patento_numeris+"B_citations.xml");  // +doc number
        os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));
        m.marshal(spd, os);
        m.marshal(spd, System.out);
        System.out.println();
        os.close();

        conn.close();
        // os.close();
    } //get LT 

}
