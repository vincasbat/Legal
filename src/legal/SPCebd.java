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
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import static legal.EPebd.ep_patentai;
import lt.vinco.simple.Address;
import lt.vinco.simple.Address1;
import lt.vinco.simple.Addressbook;
import lt.vinco.simple.Agent;
import lt.vinco.simple.Agents;
import lt.vinco.simple.Applicants;
import lt.vinco.simple.ApplicationReference;
import lt.vinco.simple.BibliographicData;
import lt.vinco.simple.City;
import lt.vinco.simple.Country;
import lt.vinco.simple.Date;
import lt.vinco.simple.DocumentId;
import lt.vinco.simple.FirstName;
import lt.vinco.simple.InventionTitle;
import lt.vinco.simple.LastName;
import lt.vinco.simple.MiddleName;
import lt.vinco.simple.Parties;
import lt.vinco.simple.PublicationReference;
import lt.vinco.simple.RelatedDocuments;
import lt.vinco.simple.RelatedPublication;
import lt.vinco.simple.SimplePatentDocument;

/**
 *
 * @author vbatulevicius
 */
public class SPCebd {
    
     private static  List<String> ltspc = null;
     private static  List<String> ltspa = null;

    public static List<String> getLtspc() {
        return ltspc;
    }

    public static void setLtspc(List<String> ltspc) {
        SPCebd.ltspc = ltspc;
    }

    public static List<String> getLtspa() {
        return ltspa;
    }

    public static void setLtspa(List<String> ltspa) {
        SPCebd.ltspa = ltspa;
    }

    
     
     
     
    
   private static  List<String> epspc = null;  

    public static List<String> getEpspc() {
        return epspc;
    }

    public static void setEpspc(List<String> epspc) {
        SPCebd.epspc = epspc;
    }
    
    
    private static  List<String> epspa = null;  

    public static List<String> getEpspa() {
        return epspa;
    }

    public static void setEpspa(List<String> epspa) {
        SPCebd.epspa = epspa;
    }
    
 //EP SPC:   
 public static void getEpspcebd(String skelb_data)throws JAXBException, IOException, ClassNotFoundException, SQLException{
  Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "jdbc:informix-";
       Connection conn = DriverManager.getConnection(URL);
         
 
 
  
int j= 0;       
if(epspc!=null)     {   
     while (j < epspc.size()) {  
       if(epspc.get(j)!=null) {
           
         BibliographicData bd = new BibliographicData();
  SimplePatentDocument spd = new SimplePatentDocument();
  spd.setBibliographicData(bd);
  spd.setKind("I2");
  spd.setDatePubl(skelb_data);
  spd.setCountry("LT");
  spd.setLang("en");
  spd.setStatus("n");   
           
  
        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, bedat(dtappli) dtapp, title, engtitle, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, bedat(dtadvert) dtadv, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and extidappli =  " +  "\"" + epspc.get(j).toString() + "\"";
 System.out.println(GET_PAT);
  
  Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(GET_PAT);  
    while ( resultSet.next())  { 
   String  dtadvert = resultSet.getString("dtadv");
    String  dtappli = resultSet.getString("dtapp");
     String  title = resultSet.getString("title");
  
    String        idappli = resultSet.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }    
        
     
      
      
   
            String GET_ADDINFO = "SELECT exfield, value FROM addinfo where idappli = " + "\"" + idappli + "\"";
            Statement st_adinfo = conn.createStatement();
            ResultSet rs_addinfo = st_adinfo.executeQuery(GET_ADDINFO);
          String   spcnumber = null;
            String spcterm = null;
            String spcstart = null;
            while (rs_addinfo.next()) {
                int exfield = rs_addinfo.getInt("exfield");
                String value = Perkodavimas.Perkoduoti(rs_addinfo.getString("value"));

                switch (exfield) {
                    case 1:
                       break;
                    case 2:
                        spcnumber = value.trim().replace(" ", "");
                        break;
                    case 3:
                        spcterm = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;
                    case 4:
                        spcstart = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;

                    default:
                        break;
                }//switch
            }//while
            rs_addinfo.close();
   
      
        System.out.println(spcnumber);
      
  //Surandame  patento numeri extidpatent ir dtptexpi:
            String GET_SPCPAT = "SELECT bedat(p.dtptexpi) dtpex, p.extidpatent FROM ptappli p where "
                    + "p.idappli = (SELECT idappli from spc where idapplispc =  \"" + idappli + "\")";
            Statement st_spcpat = conn.createStatement();
            ResultSet rs_spcpat = st_spcpat.executeQuery(GET_SPCPAT);
String extidpatent = null;
String dtptexpi = null;
            while (rs_spcpat.next()) {
                extidpatent = rs_spcpat.getString("extidpatent");      //Cia ne SPC, o jo patento extidpatent
                if (extidpatent != null) {
                    extidpatent = extidpatent.trim();
                }
                dtptexpi = rs_spcpat.getString("dtpex");
            }
            rs_spcpat.close();     
        
 System.out.println(extidpatent);
 System.out.println(dtptexpi);
 
   
  String GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='38' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S126' and history.idappli = \"" + idappli + "\"";          
    //Ištraukiame publikacijos datą:
   Statement st_publdate = conn.createStatement();               
 ResultSet   rs_publdate = st_publdate.executeQuery(GET_PUBL_DATE);
            String publdate = null;
                while (rs_publdate.next()) {
                publdate = rs_publdate.getString("publdate").trim();            
            }   rs_publdate.close();
            
       if(publdate==null) {
    System.out.println("NERA PUBLDATE:   idappli: "+idappli +"    patento extidpatent: "+extidpatent);       
           continue;  }   
  System.out.println("idappli: "+idappli +"    publdate: "+publdate);  
 
  //jaxb:
   spd.setDocNumber(spcnumber);  //atributas
  PublicationReference pubref = new PublicationReference();
  bd.setPublicationReference(pubref);
  DocumentId did = new DocumentId();
  pubref.setDocumentId(did);
  did.setDocNumber(spcnumber);
  Country ctr = new Country();
  ctr.setvalue("LT");
  did.setCountry(ctr);
  did.setKind("I2");
  lt.vinco.simple.Date pbdt = new lt.vinco.simple.Date();   // kazkodel null?
  pbdt.setvalue(skelb_data); 
  did.setDate(pbdt);
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate); pbdt.setvalue(publdate); } //else {spd.setDatePubl(skelb_data); pbdt.setvalue(skelb_data); }
  
  
  
  ApplicationReference apr = new ApplicationReference();
  bd.setApplicationReference(apr);
  DocumentId didap = new DocumentId();
  apr.setDocumentId(didap);
  didap.setDocNumber(epspc.get(j).toString().replace(" ", "").trim());
  didap.setCountry(ctr);
  lt.vinco.simple.Date pbap = new lt.vinco.simple.Date();   
  pbap.setvalue(dtappli);      
  didap.setDate(pbap);
  
  bd.setLanguageOfPublication("lt");
  InventionTitle it = new InventionTitle();
  it.setLang("lt");
  it.setvalue(Perkodavimas.Perkoduoti(title.trim()));
  bd.getInventionTitle().add(it);
  
  
  RelatedDocuments rd = new RelatedDocuments();
  RelatedPublication rp =new RelatedPublication();
  bd.setRelatedDocuments(rd);
  rd.getAdditionOrDivisionOrContinuationOrPreviouslyFiledApplicationOrContinuationInPartOrContinuingReissueOrReissueOrReexaminationOrSubstitutionOrUtilityModelBasisOrCorrectionOrRelatedPublication().add(rp);
  DocumentId didrel = new DocumentId();
   rp.setDocumentId(didrel);
 Country ctrel = new Country();
 ctrel.setvalue("EP");
  didrel.setCountry(ctrel);
  didrel.setDocNumber(extidpatent.substring(2));
 didrel.setDate(Ut.getDtPublByExtidpatent(extidpatent));     
 
 
  
  Parties prts = new Parties();
  bd.setParties(prts);
 
  prts.setApplicants(Ut.getSpcApplicantsByIdappli(idappli));
  Agents agents = Ut.getAgentsByIdappli(idappli);
  prts.setAgents(agents);
  
  
  
  
  
 
  JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
               
       String sk_data = null;
     if(Forma.skelbDataIsDB) sk_data = publdate; else sk_data = skelb_data;  
        String leadzero = "0";
               if( j > 9) leadzero = "";
               leadzero = "_"+leadzero + j;
                    
   m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("EP_SPC_" + sk_data + "_" + spcnumber + leadzero + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println();  //suderinus ištrinti
                    os.close();


  
  
   

    }//while resultSet
       j++;
        resultSet.close();
       }//if not null
      }//while
   
 }//in not null 


  
        conn.close();
    
 }   //getEpspcebd  
 
 //**************************************************************************
 
 
 //EP SPA:
 public static void getEpspaebd(String skelb_data)throws JAXBException, IOException, ClassNotFoundException, SQLException{
  Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       
      System.out.println("EP SPA: "); 
      
       
int j= 0;       
if(epspa!=null)     {   
     while (j < epspa.size()) {  
       if(epspa.get(j)!=null) {
           
         BibliographicData bd = new BibliographicData();
  SimplePatentDocument spd = new SimplePatentDocument();
  spd.setBibliographicData(bd);
  spd.setKind("I1");
  spd.setDatePubl(skelb_data);
  spd.setCountry("LT");
  spd.setLang("en");
  spd.setStatus("n");   
           
  
        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, bedat(dtappli) dtapp, title, engtitle, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, bedat(dtadvert) dtadv, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and extidappli =  " +  "\"" + epspa.get(j).toString() + "\"";
 System.out.println(GET_PAT);
  
  Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(GET_PAT);  
    while ( resultSet.next())  { 
   String  dtadvert = resultSet.getString("dtadv");
    String  dtappli = resultSet.getString("dtapp");
     String  title = resultSet.getString("title");
  
    String        idappli = resultSet.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }    
        
     
  System.out.println("spa idappli:  "+idappli);    
        System.out.println("par.nr.: "+epspa.get(j).toString());    
   
            String GET_ADDINFO = "SELECT exfield, value FROM addinfo where idappli = " + "\"" + idappli + "\"";
            Statement st_adinfo = conn.createStatement();
            ResultSet rs_addinfo = st_adinfo.executeQuery(GET_ADDINFO);
     //     String   spcnumber = null;
            String spcterm = null;
            String spcstart = null;
            while (rs_addinfo.next()) {
                int exfield = rs_addinfo.getInt("exfield");
                String value = Perkodavimas.Perkoduoti(rs_addinfo.getString("value"));

                switch (exfield) {
                    case 1:
                       break;
                    case 2:
   //                     spcnumber = value.trim().replace(" ", "");
                        break;
                    case 3:
                        spcterm = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;
                    case 4:
                        spcstart = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;

                    default:
                        break;
                }//switch
            }//while
            rs_addinfo.close();
   
      
      
      
  //Surandame  patento numeri extidpatent ir dtptexpi:
            String GET_SPCPAT = "SELECT bedat(p.dtptexpi) dtpex, p.extidpatent, p.extidappli FROM ptappli p where "
                    + "p.idappli = (SELECT idappli from spc where idapplispc =  \"" + idappli + "\")";
            Statement st_spcpat = conn.createStatement();
            ResultSet rs_spcpat = st_spcpat.executeQuery(GET_SPCPAT);
String extidpatent = null;
String dtptexpi = null;
            while (rs_spcpat.next()) {
                extidpatent = rs_spcpat.getString("extidpatent");      //Cia ne SPC, o jo patento extidpatent
                if (extidpatent != null) {
                    extidpatent = extidpatent.trim();
                }
                dtptexpi = rs_spcpat.getString("dtpex");
            }
            rs_spcpat.close();     
        
 System.out.println(extidpatent);
 System.out.println(dtptexpi);
 
 ////jei spa:
  String GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='37' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                           + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                           + " and idoper='S120' and history.idappli = \"" + idappli + "\"";
                           + " and idoper='S126' and history.idappli = \"" + idappli + "\"";          
    //Ištraukiame publikacijos datą:
   Statement st_publdate = conn.createStatement();               
 ResultSet   rs_publdate = st_publdate.executeQuery(GET_PUBL_DATE);
            String publdate = null;
                while (rs_publdate.next()) {
                publdate = rs_publdate.getString("publdate").trim();            
            }   rs_publdate.close();
  System.out.println("publdate: "+publdate);  
    if(publdate==null) {
        System.out.println("NERA PUBLDATE:   idappli: "+idappli +"    spa: "+epspa.get(j).toString()); 
        continue;
    }
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate);} 
  //jaxb:
   spd.setDocNumber(epspa.get(j).toString().replace(" ", ""));  //atributas
  PublicationReference pubref = new PublicationReference();
  bd.setPublicationReference(pubref);
  DocumentId did = new DocumentId();
  pubref.setDocumentId(did);
  did.setDocNumber(epspa.get(j).toString().replace(" ", ""));
  Country ctr = new Country();
  ctr.setvalue("LT");
  did.setCountry(ctr);
  did.setKind("I1");
  lt.vinco.simple.Date pbdt = new lt.vinco.simple.Date();   
  pbdt.setvalue(skelb_data); 
  did.setDate(pbdt);
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate); pbdt.setvalue(publdate); }
  
  ApplicationReference apr = new ApplicationReference();
  bd.setApplicationReference(apr);
  DocumentId didap = new DocumentId();
  apr.setDocumentId(didap);
  didap.setDocNumber(epspa.get(j).toString().replace(" ", "").trim());
  didap.setCountry(ctr);
  lt.vinco.simple.Date pbap = new lt.vinco.simple.Date();   
  pbap.setvalue(dtappli);      
  didap.setDate(pbap);
  
  bd.setLanguageOfPublication("lt");
  InventionTitle it = new InventionTitle();
  it.setLang("lt");
  it.setvalue(Perkodavimas.Perkoduoti(title.trim()));
  bd.getInventionTitle().add(it);
  
  
  RelatedDocuments rd = new RelatedDocuments();
  RelatedPublication rp =new RelatedPublication();
  bd.setRelatedDocuments(rd);
  rd.getAdditionOrDivisionOrContinuationOrPreviouslyFiledApplicationOrContinuationInPartOrContinuingReissueOrReissueOrReexaminationOrSubstitutionOrUtilityModelBasisOrCorrectionOrRelatedPublication().add(rp);
  DocumentId didrel = new DocumentId();
   rp.setDocumentId(didrel);
 Country ctrel = new Country();
 ctrel.setvalue("EP");
  didrel.setCountry(ctrel);
  didrel.setDocNumber(extidpatent.substring(2));
 didrel.setDate(Ut.getDtPublByExtidpatent(extidpatent));     
 
 
  
  Parties prts = new Parties();
  bd.setParties(prts);
 
  prts.setApplicants(Ut.getSpaApplicantsByIdappli(idappli));   
  Agents agents = Ut.getAgentsByIdappli(idappli);
  prts.setAgents(agents);
  
  
  
  
  
 
  JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
               
           String sk_data = null;
     if(Forma.skelbDataIsDB) sk_data = publdate; else sk_data = skelb_data;       
               String leadzero = "0";
               if( j > 9) leadzero = "";
               leadzero = "_"+leadzero + j;
   m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("EP_SPA_" + sk_data + "_" + epspa.get(j).toString().replace(" ","") + leadzero + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println();  //suderinus ištrinti
                    os.close();


  
  
   

    }//while resultSet
       j++;
        resultSet.close();
       }//if not null
      }//while
   
 }//in not null 

      
     conn.close();
 }//getEpspaebd
 
 
 
 
 //LT SPC:
  public static void getLtspcebd(String skelb_data)throws JAXBException, IOException, ClassNotFoundException, SQLException{
  Class.forName("com.informix.jdbc.IfxDriver");
            String URL = "jdbc:informix";
       Connection conn = DriverManager.getConnection(URL);
       System.out.println("LT SPC: "); 
      
     
int j= 0;       
if(ltspc!=null)     {   
     while (j < ltspc.size()) {  
       if(ltspc.get(j)!=null) {
           
         BibliographicData bd = new BibliographicData();
  SimplePatentDocument spd = new SimplePatentDocument();
  spd.setBibliographicData(bd);
  spd.setKind("I2");
  spd.setDatePubl(skelb_data);
  spd.setCountry("LT");
  spd.setLang("en");
  spd.setStatus("n");   
           
  
        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, bedat(dtappli) dtapp, title, engtitle, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, bedat(dtadvert) dtadv, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and extidappli =  " +  "\"" + ltspc.get(j).toString() + "\"";
 System.out.println(GET_PAT);
  
  Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(GET_PAT);  
    while ( resultSet.next())  { 
   String  dtadvert = resultSet.getString("dtadv");
    String  dtappli = resultSet.getString("dtapp");
     String  title = resultSet.getString("title");
  
    String        idappli = resultSet.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }    
        
     
      
      
   
            String GET_ADDINFO = "SELECT exfield, value FROM addinfo where idappli = " + "\"" + idappli + "\"";
            Statement st_adinfo = conn.createStatement();
            ResultSet rs_addinfo = st_adinfo.executeQuery(GET_ADDINFO);
          String   spcnumber = null;
            String spcterm = null;
            String spcstart = null;
            while (rs_addinfo.next()) {
                int exfield = rs_addinfo.getInt("exfield");
                String value = Perkodavimas.Perkoduoti(rs_addinfo.getString("value"));

                switch (exfield) {
                    case 1:
                       break;
                    case 2:
                        spcnumber = value.trim().replace(" ", "");
                        break;
                    case 3:
                        spcterm = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;
                    case 4:
                        spcstart = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;

                    default:
                        break;
                }//switch
            }//while
            rs_addinfo.close();
   
      
        System.out.println(spcnumber);
      
  //Surandame  patento numeri extidpatent ir dtptexpi:
            String GET_SPCPAT = "SELECT bedat(p.dtptexpi) dtpex, p.extidpatent FROM ptappli p where "
                    + "p.idappli = (SELECT idappli from spc where idapplispc =  \"" + idappli + "\")";
            Statement st_spcpat = conn.createStatement();
            ResultSet rs_spcpat = st_spcpat.executeQuery(GET_SPCPAT);
String extidpatent = null;
String dtptexpi = null;
            while (rs_spcpat.next()) {
                extidpatent = rs_spcpat.getString("extidpatent");      //Cia ne SPC, o jo patento extidpatent
                if (extidpatent != null) {
                    extidpatent = extidpatent.trim();
                }
                dtptexpi = rs_spcpat.getString("dtpex");
            }
            rs_spcpat.close();     
        
 System.out.println(extidpatent);
 System.out.println(dtptexpi);
 
 
  //jei spc:      
  String GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='20' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S126' and history.idappli = \"" + idappli + "\"";          
    //Ištraukiame publikacijos datą:
   Statement st_publdate = conn.createStatement();               
 ResultSet   rs_publdate = st_publdate.executeQuery(GET_PUBL_DATE);
            String publdate = null;
                while (rs_publdate.next()) {
                publdate = rs_publdate.getString("publdate").trim();            
            }   rs_publdate.close();
  System.out.println("publdate: "+publdate);
    if(publdate==null) continue;
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate);} 
  //jaxb:
   spd.setDocNumber(spcnumber);  //atributas
  PublicationReference pubref = new PublicationReference();
  bd.setPublicationReference(pubref);
  DocumentId did = new DocumentId();
  pubref.setDocumentId(did);
  did.setDocNumber(spcnumber);
  Country ctr = new Country();
  ctr.setvalue("LT");
  did.setCountry(ctr);
  did.setKind("I2");
  lt.vinco.simple.Date pbdt = new lt.vinco.simple.Date();   // kazkodel null?
  pbdt.setvalue(skelb_data); 
  did.setDate(pbdt);
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate); pbdt.setvalue(publdate); }
  
  ApplicationReference apr = new ApplicationReference();
  bd.setApplicationReference(apr);
  DocumentId didap = new DocumentId();
  apr.setDocumentId(didap);
  didap.setDocNumber(ltspc.get(j).toString().replace(" ", "").trim());
  didap.setCountry(ctr);
  lt.vinco.simple.Date pbap = new lt.vinco.simple.Date();   
  pbap.setvalue(dtappli);      
  didap.setDate(pbap);
  
  bd.setLanguageOfPublication("lt");
  InventionTitle it = new InventionTitle();
  it.setLang("lt");
  it.setvalue(Perkodavimas.Perkoduoti(title.trim()));
  bd.getInventionTitle().add(it);
  
  
  RelatedDocuments rd = new RelatedDocuments();
  RelatedPublication rp =new RelatedPublication();
  bd.setRelatedDocuments(rd);
  rd.getAdditionOrDivisionOrContinuationOrPreviouslyFiledApplicationOrContinuationInPartOrContinuingReissueOrReissueOrReexaminationOrSubstitutionOrUtilityModelBasisOrCorrectionOrRelatedPublication().add(rp);
  DocumentId didrel = new DocumentId();
   rp.setDocumentId(didrel);
 Country ctrel = new Country();
 ctrel.setvalue("LT");
  didrel.setCountry(ctrel);
  didrel.setDocNumber(extidpatent);
 didrel.setDate(Ut.getLtDtPublByExtidpatent(extidpatent));     
 
 
  
  Parties prts = new Parties();
  bd.setParties(prts);
 
  prts.setApplicants(Ut.getLtSpcApplicantsByIdappli(idappli));
  Agents agents = Ut.getLtAgentsByIdappli(idappli);
  prts.setAgents(agents);
  
  
  
  
  
 
  JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
               
         String sk_data = null;
     if(Forma.skelbDataIsDB) sk_data = publdate; else sk_data = skelb_data;         
                    
   m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("LT_SPC_" + sk_data + "_" + spcnumber + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println();  //suderinus ištrinti
                    os.close();


  
  
   

    }//while resultSet
       j++;
        resultSet.close();
       }//if not null
      }//while
   
 }//in not null 

      
  conn.close();    
  }//getLtspcebd
 
  
  
  
  
  
  
  
  //LT SPA:
 public static void getLtspaebd(String skelb_data)throws JAXBException, IOException, ClassNotFoundException, SQLException{
  Class.forName("com.informix.jdbc.IfxDriver");
      String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       
      System.out.println("LT SPA:"); 
      
         
int j= 0;       
if(ltspa!=null)     {   
     while (j < ltspa.size()) {  
       if(ltspa.get(j)!=null) {
           
         BibliographicData bd = new BibliographicData();
  SimplePatentDocument spd = new SimplePatentDocument();
  spd.setBibliographicData(bd);
  spd.setKind("I1");
  spd.setDatePubl(skelb_data);
  spd.setCountry("LT");
  spd.setLang("en");
  spd.setStatus("n");   
           
  
        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, bedat(dtappli) dtapp, title, engtitle, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, bedat(dtadvert) dtadv, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and extidappli =  " +  "\"" + ltspa.get(j).toString() + "\"";
 System.out.println(GET_PAT);
  
  Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(GET_PAT);  
    while ( resultSet.next())  { 
   String  dtadvert = resultSet.getString("dtadv");
    String  dtappli = resultSet.getString("dtapp");
     String  title = resultSet.getString("title");
  
    String        idappli = resultSet.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }    
        
   System.out.println("idappli: "+idappli);  
      
      
   
            String GET_ADDINFO = "SELECT exfield, value FROM addinfo where idappli = " + "\"" + idappli + "\"";
            Statement st_adinfo = conn.createStatement();
            ResultSet rs_addinfo = st_adinfo.executeQuery(GET_ADDINFO);
          String   spcnumber = null;
            String spcterm = null;
            String spcstart = null;
            while (rs_addinfo.next()) {
                int exfield = rs_addinfo.getInt("exfield");
                String value = Perkodavimas.Perkoduoti(rs_addinfo.getString("value"));

                switch (exfield) {
                    case 1:
                       break;
                    case 2:
                   //     spcnumber = value.trim().replace(" ", "");
                        break;
                    case 3:
                        spcterm = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;
                    case 4:
                        spcstart = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;

                    default:
                        break;
                }//switch
            }//while
            rs_addinfo.close();
   
      
       
      
  //Surandame  patento numeri extidpatent ir dtptexpi:
            String GET_SPCPAT = "SELECT bedat(p.dtptexpi) dtpex, p.extidpatent FROM ptappli p where "
                    + "p.idappli = (SELECT idappli from spc where idapplispc =  \"" + idappli + "\")";
            Statement st_spcpat = conn.createStatement();
            ResultSet rs_spcpat = st_spcpat.executeQuery(GET_SPCPAT);
String extidpatent = null;
String dtptexpi = null;
            while (rs_spcpat.next()) {
                extidpatent = rs_spcpat.getString("extidpatent");      //Cia ne SPC, o jo patento extidpatent
                if (extidpatent != null) {
                    extidpatent = extidpatent.trim();
                }
                dtptexpi = rs_spcpat.getString("dtpex");
            }
            rs_spcpat.close();     
        
 System.out.println(extidpatent);
 System.out.println(dtptexpi);
 
 

  //jei spc:      
  String GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='19' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S120' and history.idappli = \"" + idappli + "\"";          
    //Ištraukiame publikacijos datą:
   Statement st_publdate = conn.createStatement();               
 ResultSet   rs_publdate = st_publdate.executeQuery(GET_PUBL_DATE);
            String publdate = null;
                while (rs_publdate.next()) {
                publdate = rs_publdate.getString("publdate").trim();            
            }   rs_publdate.close();
  System.out.println("publdate: "+publdate); 
    if(publdate==null) continue;
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate);} 
  //jaxb:
   spd.setDocNumber(ltspa.get(j).replace(" ", "").trim());  //atributas
  PublicationReference pubref = new PublicationReference();
  bd.setPublicationReference(pubref);
  DocumentId did = new DocumentId();
  pubref.setDocumentId(did);
  did.setDocNumber(ltspa.get(j).toString().replace(" ", "").trim());
  Country ctr = new Country();
  ctr.setvalue("LT");
  did.setCountry(ctr);
  did.setKind("I1");
  lt.vinco.simple.Date pbdt = new lt.vinco.simple.Date();   // kazkodel null?
  pbdt.setvalue(skelb_data); 
  did.setDate(pbdt);
  
  if(Forma.skelbDataIsDB){ spd.setDatePubl(publdate); pbdt.setvalue(publdate); }
  
  ApplicationReference apr = new ApplicationReference();
  bd.setApplicationReference(apr);
  DocumentId didap = new DocumentId();
  apr.setDocumentId(didap);
  didap.setDocNumber(ltspa.get(j).toString().replace(" ", "").trim());
  didap.setCountry(ctr);
  lt.vinco.simple.Date pbap = new lt.vinco.simple.Date();   
  pbap.setvalue(dtappli);      
  didap.setDate(pbap);
  
  bd.setLanguageOfPublication("lt");
  InventionTitle it = new InventionTitle();
  it.setLang("lt");
  it.setvalue(Perkodavimas.Perkoduoti(title.trim()));
  bd.getInventionTitle().add(it);
  
  
  RelatedDocuments rd = new RelatedDocuments();
  RelatedPublication rp =new RelatedPublication();
  bd.setRelatedDocuments(rd);
  rd.getAdditionOrDivisionOrContinuationOrPreviouslyFiledApplicationOrContinuationInPartOrContinuingReissueOrReissueOrReexaminationOrSubstitutionOrUtilityModelBasisOrCorrectionOrRelatedPublication().add(rp);
  DocumentId didrel = new DocumentId();
   rp.setDocumentId(didrel);
 Country ctrel = new Country();
 ctrel.setvalue("LT");
  didrel.setCountry(ctrel);
  didrel.setDocNumber(extidpatent);
 didrel.setDate(Ut.getLtDtPublByExtidpatent(extidpatent));     
 
 
  
  Parties prts = new Parties();
  bd.setParties(prts);
 
  prts.setApplicants(Ut.getLtSpaApplicantsByIdappli(idappli));
  Agents agents = Ut.getLtAgentsByIdappli(idappli);
  prts.setAgents(agents);
  
  
  
  
  
 
  JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
               
                  String sk_data = null;
     if(Forma.skelbDataIsDB) sk_data = publdate; else sk_data = skelb_data;
                    
   m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("LT_SPA_" + sk_data + "_" + ltspa.get(j).toString().replace(" ", "").trim() + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println();  //suderinus ištrinti
                    os.close();


  
  
   

    }//while resultSet
       j++;
        resultSet.close();
       }//if not null
      }//while
   
 }//in not null 
  
      
      
   conn.close();   
  }//getLtspaebd
    
    
    
}//class
