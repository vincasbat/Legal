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
import java.nio.file.Files;
import static java.nio.file.Files.lines;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import lt.vinco.simple.ApplicationReference;
import lt.vinco.simple.BibliographicData;
import lt.vinco.simple.ClassificationIpcr;
import lt.vinco.simple.ClassificationsIpcr;
import lt.vinco.simple.Clazz;
import lt.vinco.simple.Country;
import lt.vinco.simple.DocumentId;
import lt.vinco.simple.InventionTitle;
import lt.vinco.simple.IpcVersionIndicator;
import lt.vinco.simple.MainGroup;
import lt.vinco.simple.Parties;
import lt.vinco.simple.PctOrRegionalFilingData;
import lt.vinco.simple.PctOrRegionalPublishingData;
import lt.vinco.simple.PriorityClaim;
import lt.vinco.simple.PriorityClaims;
import lt.vinco.simple.PublicationReference;
import lt.vinco.simple.Section;
import lt.vinco.simple.SimplePatentDocument;
import lt.vinco.simple.Subclass;
import lt.vinco.simple.Subgroup;
import lt.vinco.simple.SymbolPosition;

/**
 *
 * @author vbatulevicius
 */
public class EPebd {
    public static List<String> ep_patentai = null;
    public static List<String> getEP_patentai() {
        return ep_patentai;
    } 
    
 static   List<String> klasif_klaidos  = new ArrayList<>();
  public static void setEp_patentai(List<String> ep_patentai) {
        EPebd.ep_patentai = ep_patentai;
    }   
 

    static  void getEPebd(String skelb_data)  throws JAXBException, IOException, ClassNotFoundException, SQLException
    {
 Class.forName("com.informix.jdbc.IfxDriver");
  
        String URL = "";
        Connection conn = DriverManager.getConnection(URL);
        

   //  String   skelb_data = Ut.getPublDate();
  //  skelb_data = JOptionPane.showInputDialog(null, "Skelbimo data, pvz., 20161227", skelb_data);        
        
        String GET_PAT = "select idappli, dtappli, stitle, engtitle, ipcmclass, extidappli, bedat(dtappli) dtapp  from informix.ptappli  where extidpatent = ";

// GET_PAT = "select p.idappli, p.dtappli, p.stitle, p.engtitle, p.ipcmclass, p.extidappli, bedat(p.dtappli) dtapp, pc.orictry, pc.typctep, pc.nopctep,  bedat(pc.dtpctappli) dtpct  from ptappli p, pctref pc  where p.idappli = pc.idappli and extidpatent = ";
        
        
        
        System.out.println(ep_patentai.size());
     Statement stmt = conn.createStatement();  
         int j = 0;
        if (ep_patentai != null) {
            while (j < ep_patentai.size()) {
              
                String idappli = null;
                String title = null;
                String engtitle = null;
                String ipcmclass = null;
                String extidappli = null;
                String dtapp = null;
                String dtpubli = null;
                String GET_PATID = GET_PAT + "\"EP" + ep_patentai.get(j).toString() + "\"";
 System.out.println(GET_PATID);               
                ResultSet rs = stmt.executeQuery(GET_PATID);
                while (rs.next()) {
                    idappli = rs.getString("idappli");
                    title = rs.getString("stitle").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs.getString("engtitle").trim();
                    ipcmclass = rs.getString("ipcmclass").trim();  //F24F  12/00
                    extidappli = rs.getString("extidappli").trim();
                    dtapp = rs.getString("dtapp");

           
                    
                    
        if(Forma.skelbDataIsDB)       {
    
                        String GET_DTPUBLI = "select bedat(gazette.dtpubli) dtpb from informix.gazette, informix.publication where " + "idappli = " + "\"" + idappli.trim() + "\""
                                + " and gazette.nogazette = publication.nogazette and gazette.yygazette = publication.yygazette and (nosect=2 or nosect=52)";
                        Statement stmtrspub = conn.createStatement();
                        ResultSet rspub = stmtrspub.executeQuery(GET_DTPUBLI);
                        while (rspub.next()) {
                            skelb_data = rspub.getString("dtpb");
                            }
                         rspub.close();
                        stmtrspub.close();
                     
                            System.out.println(ep_patentai.get(j).toString() + "skelb_data: "+skelb_data);
                if (skelb_data==null)  {
                 skelb_data = "unknown";
               } 
      
  }      //if(Forma.skelbDataIsDB)               
                    
                    
                    

                    SimplePatentDocument spd = new SimplePatentDocument();
                    spd.setDocNumber(ep_patentai.get(j).toString());
                    spd.setKind("T");
                    spd.setDatePubl(skelb_data);
                    spd.setStatus("n");
                    spd.setCountry("LT");
                    spd.setLang("en");   //?? lt?

                    BibliographicData bd = new BibliographicData();
                    PublicationReference pr = new PublicationReference();
                    DocumentId di = new DocumentId();
                    Country ctr = new Country();
                    ctr.setvalue("LT");
                    di.setCountry(ctr);
                    di.setDocNumber(ep_patentai.get(j).toString());
                    di.setKind("T");
                    lt.vinco.simple.Date dt = new lt.vinco.simple.Date();
                    dt.setvalue(skelb_data);
                    di.setDate(dt);
                    pr.setDocumentId(di);
                    bd.setPublicationReference(pr);

                    String GET_CLASS = "select ipcclass, bedat(dtversion) dtvers, odclass from classin where idappli = " + "\"" + idappli + "\" order by odclass";
                    ResultSet rscl = stmt.executeQuery(GET_CLASS);
                    String clall = null;
                    String dtvers = null;
                    String section = null;
                    String klase = null;
                    String subklase = null;
                    String maingroup = null;
                    String subgroup = null;
                    int odclass = 0;
                    String symbolposition = null;

                    ClassificationsIpcr csipcr = new ClassificationsIpcr();

                    while (rscl.next()) {
                        symbolposition = null;
                        clall = null;
                        dtvers = null;
                        odclass = 0;
                        odclass = rscl.getInt("odclass");   
                        if (odclass == 1) {
                            symbolposition = "F";
                        } else {
                            symbolposition = "L";
                        }
                        clall = rscl.getString("ipcclass").trim();  
                        dtvers = rscl.getString("dtvers");  
                        clall = clall.replace("  ", " ");
                        clall = clall.replace("  ", " ");
                        String[] mas = clall.split(" ");
                        section = mas[0].substring(0, 1);
                        klase = mas[0].substring(1, 3);
                        subklase = mas[0].substring(3);
             if(mas.length>1 )   {        
                 if(mas[1].contains("/"))  {
                     
                        String[] grupes = mas[1].trim().split("/");
                        maingroup = grupes[0];
                        subgroup = grupes[1];
           } 
             }             else { 
          klasif_klaidos.add(ep_patentai.get(j).toString());    //test klaidos
               maingroup = "";
               subgroup = ""; 
           }
           
                        ClassificationIpcr cipcr = new ClassificationIpcr();
                        IpcVersionIndicator ivi = new IpcVersionIndicator();
                        lt.vinco.simple.Date dtc = new lt.vinco.simple.Date();
                        if(dtvers!=null)   { 
                          dtc.setvalue(dtvers); 
                           ivi.setDate(dtc);    //
                        }//????
                        
                        
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(ivi);
                        Section sc = new Section();
                        sc.setvalue(section);  //????
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(sc);
                        Clazz clz = new Clazz();
                        clz.setvalue(klase);  //????
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(clz);
                        Subclass sclz = new Subclass();
                        sclz.setvalue(subklase);  //????
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(sclz);
                        MainGroup mg = new MainGroup();
                        mg.setvalue(maingroup);  //????
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(mg);
                        Subgroup sg = new Subgroup();
                        sg.setvalue(subgroup);  //????
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(sg);
                        SymbolPosition sp = new SymbolPosition();
                        sp.setvalue(symbolposition);  //????
                        cipcr.getIpcVersionIndicatorOrClassificationLevelOrSectionOrClazzOrSubclassOrMainGroupOrSubgroupOrSymbolPositionOrClassificationValueOrActionDateOrGeneratingOfficeOrClassificationStatusOrClassificationDataSourceOrText().add(sp);

                        csipcr.getClassificationIpcr().add(cipcr);    

                    }  // while ipcclass

                    bd.setClassificationsIpcr(csipcr);

                    ApplicationReference ar = new ApplicationReference();
                    DocumentId diap = new DocumentId();
                    diap.setCountry(ctr);
                    diap.setDocNumber(extidappli.replace(" ", ""));
                    lt.vinco.simple.Date dtapl = new lt.vinco.simple.Date();
                    dtapl.setvalue(dtapp);
                    diap.setDate(dtapl);  //paimti paraiškos datą iš sql
                    ar.setDocumentId(diap);
                    bd.setApplicationReference(ar);
                    bd.setLanguageOfPublication("lt");

                    PriorityClaims priorityClaims = new PriorityClaims();
                    String GET_PRIO = "select idappli, odprio, typrio, idcountry, noprio, bedat(dtprio) prio_data from priority where idappli = " + "\"" + idappli + "\"";

                    ResultSet rsprio = stmt.executeQuery(GET_PRIO);
                    int typrio = 0;
                    int odprio = 0;
                    int prios = 0;
                    String idcountry_prio = null;
                    String noprio = null;
                    String prio_data = null;

                    while (rsprio.next()) {
                        typrio = rsprio.getInt("typrio");
                        odprio = rsprio.getInt("odprio");
                        idcountry_prio = rsprio.getString("idcountry");
                        noprio = rsprio.getString("noprio").trim();
                        prio_data = rsprio.getString("prio_data");
                        PriorityClaim priorityClaim = new PriorityClaim();
                        priorityClaim.setSequence(Integer.toString(odprio));
            switch (typrio) {
            case 1:  
            priorityClaim.setKind("international");
                     break;
            case 3:  
            priorityClaim.setKind("national");
                     break;
            default: 
            priorityClaim.setKind("----------------------------------nežinomas prioritetas----------------------------");
                     break;
        }             
                        
                        lt.vinco.simple.Date dtprio = new lt.vinco.simple.Date();
                        dtprio.setvalue(prio_data);
                        priorityClaim.setDate(dtprio);
                        priorityClaim.setDocNumber(noprio);
                        Country prio_ctry = new Country();
                        prio_ctry.setvalue(idcountry_prio);
                        priorityClaim.setCountry(prio_ctry);
                        priorityClaims.getPriorityClaim().add(priorityClaim);
                        prios++;
                    } // while prio

                    if (prios > 0) {

                        bd.setPriorityClaims(priorityClaims);
                    }//if prios>0                        

                    Parties parties = new Parties();
                  
                    
   parties.setInventors(Ut.getInventorsByIdappli(idappli));       
 
   parties.setAgents(Ut.getAgentsByIdappli(idappli));                 
  
   parties.setGrantees(Ut.getGranteesByIdappli(idappli));
                    
                   
                    bd.setParties(parties);

                    InventionTitle itlt = new InventionTitle();
                    itlt.setLang("lt");
                    itlt.setvalue(title);
                    InventionTitle iten = new InventionTitle();
                    iten.setLang("en");
                    iten.setvalue(engtitle);
                    bd.getInventionTitle().add(itlt);
                    bd.getInventionTitle().add(iten);
       
 

String GET_PCT = "select odpctep,  orictry, typctep, nopctep, nopubli,  bedat(dtpctappli) dtpct,  bedat(dtpctpubli) dtpctpub  from  pctref WHERE odpctep = 2 AND idappli = ";
      GET_PCT = GET_PCT + "\"" + idappli + "\"";             
  System.out.println(GET_PCT);                  
                    
   ResultSet rspct = stmt.executeQuery(GET_PCT);

                    while (rspct.next()) {
   String     odpctep = rspct.getString("odpctep");   
   String     orictry = rspct.getString("orictry"); 
   String     typctep = rspct.getString("typctep");                     
    String     nopctep = rspct.getString("nopctep");
    String     nopubli = rspct.getString("nopubli");
    String     dtpctpubli = rspct.getString("dtpctpub");
    String     dtpctappli = rspct.getString("dtpct");
    

    
    
    PctOrRegionalFilingData pctfd = new PctOrRegionalFilingData();     
    DocumentId pctDocid = new DocumentId();
                pctDocid.setDocNumber(nopctep);
                Country pct_filing_ctr = new Country();
                pct_filing_ctr.setvalue(nopctep.substring(0,2));
                pctDocid.setCountry(pct_filing_ctr);
                lt.vinco.simple.Date dtpct_v = new lt.vinco.simple.Date();
                dtpct_v.setvalue(dtpctappli);   
                pctDocid.setDate(dtpct_v);
                pctfd.setDocumentId(pctDocid);

                bd.getPctOrRegionalFilingData().add(pctfd);
    
    
 
        
         
     PctOrRegionalPublishingData pctpubd = new PctOrRegionalPublishingData();    
    DocumentId pctDocidp = new DocumentId();
                pctDocidp.setDocNumber(nopubli);
                 Country pct_ctrp = new Country();
                pct_ctrp.setvalue(orictry);
                pctDocidp.setCountry(pct_ctrp);
                lt.vinco.simple.Date dtpctpub = new lt.vinco.simple.Date();
                dtpctpub.setvalue(dtpctpubli);
                pctDocidp.setDate(dtpctpub);
                pctpubd.setDocumentId(pctDocidp);
                bd.getPctOrRegionalPublishingData().add(pctpubd);
   
    
                    } //while                 
                    
 
        
  
                    spd.setBibliographicData(bd);

                    JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
               
                    String fv = ep_patentai.get(j).toString().replace(" ", "");
                    
                    
   m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("EP_" + skelb_data + "_" + fv + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println();  //suderinus ištrinti
                    os.close();
 
                
                   
                
               }//while    patentai rst.next
               j++;  
            } // while size
        } //if (ep_patentai!=null) 
        
  
Path file = Paths.get("klaidos.txt");
Files.write(file, klasif_klaidos);
     
   //ZIP     
        
       
    //END ZIP    
        
    }//getEPebd       
        
        
        
        
        
}
