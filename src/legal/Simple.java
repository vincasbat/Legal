/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.Class;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import lt.vinco.simple.*;

/**
 *
 * @author vbatulevicius
 */
public class Simple {

    public static List<String> lt_patentai = null;
    public static List<String> lt_paraiskos = null;
    public static List<String> lt_tparaiskos = null;

    public static List<String> getLt_paraiskos() {
        return lt_paraiskos;
    }

    public static void setLt_paraiskos(List<String> lt_paraiskos) {
        Simple.lt_paraiskos = lt_paraiskos;
    }

    public static List<String> getLt_tparaiskos() {
        return lt_tparaiskos;
    }

    public static void setLt_tparaiskos(List<String> lt_tparaiskos) {
        Simple.lt_tparaiskos = lt_tparaiskos;
    }

    public static List<String> getLt_patentai() {
        return lt_patentai;
    }

    public static void setLt_patentai(List<String> lt_patentai) {
        Simple.lt_patentai = lt_patentai;
    }

    public static String deleteeta(String sueta) {

        //istriname @ pabaigoje, jei yra
        String beetos = sueta.trim();
        if (beetos.length() > 0) {
            if ("@".equalsIgnoreCase(beetos.substring(beetos.length() - 1, beetos.length()))) {
                beetos = beetos.substring(0, beetos.length() - 1);
                beetos = beetos.trim();
            }

            if ("@".equalsIgnoreCase(beetos.substring(beetos.length() - 1, beetos.length()))) {
                beetos = beetos.substring(0, beetos.length() - 1);
                beetos = beetos.trim();
            }
        }

        return beetos;
    }

    static void getSimpleLt() throws JAXBException, IOException, ClassNotFoundException, SQLException {


        Class.forName("com.informix.jdbc.IfxDriver");
        String URL = "jdbc:informix-sqli";
        Connection conn = DriverManager.getConnection(URL);


String   skelb_data = Ut.getPublDate();
        skelb_data = JOptionPane.showInputDialog(null, "Skelbimo data, pvz., 20121227", skelb_data);
        String GET_PAT = "select idappli, dtappli, stitle, engtitle, ipcmclass, extidappli, bedat(dtappli) dtapp  from informix.ptappli  where extidpatent = ";

        Statement stmt = conn.createStatement();

  //  ---------------------   patentai    ---------------------------------
        int j = 0;
        if (lt_patentai != null) {
            while (j < lt_patentai.size()) {
                String idappli = null;
                String title = null;
                String engtitle = null;
                String ipcmclass = null;
                String extidappli = null;
                String dtapp = null;
                String dtpubli = null;
                String GET_PATID = GET_PAT + "\"" + lt_patentai.get(j).toString() + "\"";
                ResultSet rs = stmt.executeQuery(GET_PATID);
                while (rs.next()) {
                    idappli = rs.getString("idappli");  
                    title = rs.getString("stitle").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs.getString("engtitle").trim();
                    ipcmclass = rs.getString("ipcmclass").trim();  //F24F  12/00
                    extidappli = rs.getString("extidappli").trim();
                    dtapp = rs.getString("dtapp");

                    if (Forma.Isfailo) {
                        String GET_DTPUBLI = "select bedat(gazette.dtpubli) dtpb from informix.gazette, informix.publication where " + "idappli = " + "\"" + idappli + "\""
                                + " and gazette.nogazette = publication.nogazette and gazette.yygazette = publication.yygazette and nosect=2";
                        ResultSet rspub = stmt.executeQuery(GET_DTPUBLI);
                        while (rspub.next()) {
                            dtpubli = rspub.getString("dtpb");
                        }
                        skelb_data = dtpubli;
                        rspub.close();
                        System.out.println(dtpubli);
                    }


                    //while
                    String GET_ABSTR = "select txtform, txtformtr from abstract where idappli = " + "\"" + idappli + "\"";
                    ResultSet rsab = stmt.executeQuery(GET_ABSTR);
                    String abstrlttxt = null;
                    String abstrentxt = null;
                    while (rsab.next()) {
                        abstrlttxt = rsab.getString("txtform");
                        if (abstrlttxt != null) {
                            abstrlttxt = Perkodavimas.Perkoduoti(abstrlttxt).trim();  //istriname @ pabaigoje, jei yra
                            abstrlttxt = deleteeta(abstrlttxt);
                        }

                        abstrentxt = rsab.getString("txtformtr");
                        if (abstrentxt != null) {
                            abstrentxt = deleteeta(abstrentxt);
                        }

                    }//while

                    SimplePatentDocument spd = new SimplePatentDocument();
                    spd.setDocNumber(lt_patentai.get(j).toString());
                    spd.setKind("B");
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
                    di.setDocNumber(lt_patentai.get(j).toString());
                    di.setKind("B");
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
                        odclass = rscl.getInt("odclass");    //???
                        if (odclass == 1) {
                            symbolposition = "F";
                        } else {
                            symbolposition = "L";
                        }
                        clall = rscl.getString("ipcclass").trim();  //B61L  21/00       //???
                        dtvers = rscl.getString("dtvers");     //???
                        clall = clall.replace("  ", " ");
                        clall = clall.replace("  ", " ");
                        String[] mas = clall.split(" ");
                        section = mas[0].substring(0, 1);
                        klase = mas[0].substring(1, 3);
                        subklase = mas[0].substring(3);
                        String[] grupes = mas[1].trim().split("/");
                        maingroup = grupes[0];
                        subgroup = grupes[1];
   
                        ClassificationIpcr cipcr = new ClassificationIpcr();
                        IpcVersionIndicator ivi = new IpcVersionIndicator();
                        lt.vinco.simple.Date dtc = new lt.vinco.simple.Date();
                        dtc.setvalue(dtvers);  //????
                        ivi.setDate(dtc);    // 
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

                        csipcr.getClassificationIpcr().add(cipcr);    //ciklo pabaiga

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
                    
                    
 //patentams aplikantus dėti čia BEGIN
                    
                    
                    
             Applicants applicants = new Applicants();
             
                String GET_APPLICANTS = "select extidappli,fnowner, midnowner, nmowner, o.odowner, w.idcountry from ptappli p,own o, owner w where p.idappli= " + "\"" + idappli + "\" and o.idowner=w.idowner and p.idappli=o.idappli order by o.odowner";
                ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                    int ordowner = 0;
                    String nmowner = null;
                    String nmowner_neperkoduotas = null;
                    String midnowner = null;
                    String fnowner = null;
                    String idcountry = null;
                     extidappli = null;
extidappli = rsapps.getString("extidappli");

                    ordowner = rsapps.getInt("odowner");
                    nmowner = rsapps.getString("nmowner");
                    if (nmowner != null) {
                        nmowner_neperkoduotas = nmowner.trim();
                        nmowner = Perkodavimas.Perkoduoti(nmowner_neperkoduotas);
                    }
                    midnowner = rsapps.getString("midnowner");
                    if (midnowner != null) {
                        midnowner = midnowner.trim();
                        midnowner = Perkodavimas.Perkoduoti(midnowner);
                    }

                    fnowner = rsapps.getString("fnowner"); //.trim();
                    if (fnowner != null) {
                        fnowner = fnowner.trim();
                        fnowner = Perkodavimas.Perkoduoti(fnowner);
                    }
                    idcountry = rsapps.getString("idcountry");  if(idcountry == null) idcountry = "";

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   //  ??????????????
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
                
                    if (midnowner != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midnowner);
                        adrb.setMiddleName(middlename);
                    }

                    Orgname on = null;
                    if (fnowner == null) {
                        on = new Orgname();
                        on.setvalue(nmowner);
                        adrb.setOrgname(on);
                     } else {
                        FirstName fnm = new FirstName();
                        fnm.setvalue(fnowner);
                        adrb.setFirstName(fnm);
                        LastName ln = new LastName();
                        ln.setvalue(nmowner);
                        adrb.setLastName(ln);
                       }
                    adrb.setAddress(adr);
 
                    applicant.getAddressbook().add(adrb);
                    applicant.setDesignation("all");
                    
                 Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                     nty.setCountry(natctr);
                     applicant.setNationality(nty);    
                    
                    
                  Residence residence = new Residence();
                    residence.setCountry(gr_ctry);  ////????????????
                    applicant.setResidence(residence); 
                    
      ///-------------------------/ pareiškėjas yra išradėjas ? /-----------------------------
             if (on!=null)  applicant.setAppType("applicant");     
             else {     
                    
        String invsql = " select ptappli.extidappli, idowner, fnowner, midnowner,nmowner"
                + " from ptappli, initapply, owner, "
                + " (select ptappli.extidappli, invent.idinvent, ordinvent, fninventor, midninventor, nminventor, adinventor"
                + " from ptappli, invent, inventor"
                + " where ptappli.idappli=invent.idappli and inventor.idinvent=invent.idinvent and ptappli.extidappli=" + "\"" + extidappli + "\")"
                + " where ptappli.idappli=initapply.idappli and initapply.idinitapp=owner.idowner and ptappli.extidappli matches " + "\"" + extidappli + "\""
                + " and trim(fninventor)=trim(fnowner) and trim(nminventor)=trim(nmowner) and owner.nmowner =" + "\"" + nmowner_neperkoduotas + "\"";
        System.out.println(invsql);

        Statement invvst = conn.createStatement();
        ResultSet invvrs = invvst.executeQuery(invsql);
               
         if(invvrs.next())   applicant.setAppType("applicant-inventor"); else applicant.setAppType("applicant");
           invvst.close();
         invvrs.close();
         invvst=null;
         invvrs=null;
             }//else
    ///-------------------------/ pareiškėjas yra išradėjas ? /----------------------------- END   
         
                    applicants.getApplicant().add(applicant);
                }//while        applicants
rsapps.close();
rsapps = null;
                parties.setApplicants(applicants);                   
                     
                    
//patentams aplikantus dėti čia END
                
                
                    Inventors invs = new Inventors();
               
                    String GET_INVS = "select invent.idinvent, ordinvent, i.nminventor, i.midninventor, i.fninventor, i.idcountry from invent, inventor i where idappli = " + "\"" + idappli + "\" and invent.idinvent = i.idinvent order by ordinvent";
                    ResultSet rsinv = stmt.executeQuery(GET_INVS);

                    while (rsinv.next()) {

                        int idinvent = 0;
                        int ordinvent = 0;
                        String nminventor = null;
                        String midninventor = null;
                        String fninventor = null;
                        String idcountry = null;

                        idinvent = rsinv.getInt("idinvent");
                        ordinvent = rsinv.getInt("ordinvent");

                        nminventor = rsinv.getString("nminventor");
                        if (nminventor != null) {
                            nminventor = nminventor.trim();
                            nminventor = Perkodavimas.Perkoduoti(nminventor);
                        } else nminventor = "Neskelbiamas";

                        midninventor = rsinv.getString("midninventor");
                        if (midninventor != null) {
                            midninventor = midninventor.trim();
                            midninventor = Perkodavimas.Perkoduoti(midninventor);
                        }

                        fninventor = rsinv.getString("fninventor");

                        if (fninventor != null) {
                            fninventor = fninventor.trim();
                            fninventor = Perkodavimas.Perkoduoti(fninventor);
                        } else fninventor = "Neskelbiamas";
                        idcountry = rsinv.getString("idcountry");  if(idcountry == null) idcountry = "";

                        Inventor inv = new Inventor();
                        inv.setSequence(Integer.toString(ordinvent));
                        Addressbook adrb = new Addressbook();
                        Address adr = new Address();
                        Country inv_ctry = new Country();
                        inv_ctry.setvalue(idcountry);
                        adr.setCountry(inv_ctry);
                        FirstName fn = new FirstName();
                        fn.setvalue(fninventor);
                        adrb.setFirstName(fn);

                        if (midninventor != null) {
                            MiddleName middlename = new MiddleName();
                            middlename.setvalue(midninventor);
                            adrb.setMiddleName(middlename);
                        }

                        LastName ln = new LastName();
                        ln.setvalue(nminventor);
                        adrb.setLastName(ln);
                        
                        adrb.setAddress(adr);

                        inv.getAddressbook().add(adrb);
                        
                   Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                      nty.setCountry(natctr);
                     inv.setNationality(nty);      
                        
                        
                        
                 Residence residence = new Residence();
                    residence.setCountry(inv_ctry); 
                    inv.setResidence(residence);        
                     invs.getInventorOrDeceasedInventor().add(inv);

                    }//while        invs  

                    parties.setInventors(invs);
                    
   // patentu agentai ---------------------- 
                  
                
                  String agentsql = "select extidappli, ptappli.idagent, fnagent, midnagent, nmagent, adagent, idtown, nmtown, idcountry"
                + " from ptappli, agent where ptappli.idagent=agent.idagent and extidpatent=" + "\"" + lt_patentai.get(j).toString() + "\"";
        System.out.println("agentai: "+agentsql);
        Statement agentstmt = conn.createStatement();
        ResultSet agentrs = agentstmt.executeQuery(agentsql);
 
   // agentai

     Agents agents = new Agents();  
      
                    int ordagent = 0;
        while (agentrs.next()) {
           ordagent++;
                    String nmagent = null;
                    String midnagent = null;
                    String fnagent = null;
                    String idcountry = null;

                    nmagent = agentrs.getString("nmagent");
                    if (nmagent != null) {
                        nmagent = nmagent.trim();
                        nmagent = Perkodavimas.Perkoduoti(nmagent);
                    }

                    midnagent = agentrs.getString("midnagent");
                    if (midnagent != null) {
                        midnagent = midnagent.trim();
                        midnagent = Perkodavimas.Perkoduoti(midnagent);
                    }

                    fnagent = agentrs.getString("fnagent").trim();
                    if (fnagent != null) {
                        fnagent = fnagent.trim();
                        fnagent = Perkodavimas.Perkoduoti(fnagent);
                    }
                    idcountry = agentrs.getString("idcountry");  if(idcountry == null) idcountry = "";

                    Agent agent = new Agent();
                    agent.setRepType("agent");
                    agent.setSequence(Integer.toString(ordagent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country ag_ctry = new Country();
                    ag_ctry.setvalue(idcountry);
                    adr.setCountry(ag_ctry);
                     FirstName fnm = new FirstName();
                    fnm.setvalue(fnagent);

                    if (midnagent != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midnagent);
                        adrb.setMiddleName(middlename);
                    }

                    LastName ln = new LastName();
                    ln.setvalue(nmagent);
                    adrb.setFirstName(fnm);
                    adrb.setLastName(ln);
                    adrb.setAddress(adr);
        
                    agent.getAddressbook().add(adrb);
                    agent.setSequence(ordagent+"");
                    
                    agents.getCustomerNumberOrAgent().add(agent);
                    
       
        }// while
                                 
 parties.setAgents(agents);
        agentstmt.close();
        agentrs.close();  
 //-------------- patentu agentai  ----------- end
         
                    
                    
                    
                    

                   //  ----------  grantees -------------
                    Grantees grantees = new Grantees();
                    String GET_GRANTEES = "select own.odowner,  o.nmowner, o.midnowner, o.fnowner, o.idcountry from own, owner o where idappli = " + "\"" + idappli + "\" and o.idowner = own.idowner order by own.odowner";

                    ResultSet rsgr = stmt.executeQuery(GET_GRANTEES);

                    while (rsgr.next()) {

                        int idowner = 0;
                        int ordowner = 0;
                        String nmowner = null;
                        String midnowner = null;
                        String fnowner = null;
                        String idcountry = null;

// idowner = rsgr.getInt("idowner");
                        ordowner = rsgr.getInt("odowner");
                        nmowner = rsgr.getString("nmowner");
                        if (nmowner != null) {
                            nmowner = nmowner.trim();
                            nmowner = Perkodavimas.Perkoduoti(nmowner);
                        }
                        midnowner = rsgr.getString("midnowner");
                        if (midnowner != null) {
                            midnowner = midnowner.trim();
                            midnowner = Perkodavimas.Perkoduoti(midnowner);
                        }

                        fnowner = rsgr.getString("fnowner"); //.trim();
                        if (fnowner != null) {
                            fnowner = fnowner.trim();
                            fnowner = Perkodavimas.Perkoduoti(fnowner);
                        }
                        idcountry = rsgr.getString("idcountry");  if(idcountry == null) idcountry = "";

                        Grantee grantee = new Grantee();
                        grantee.setSequence(Integer.toString(ordowner));
                        Addressbook adrb = new Addressbook();
                        Address adr = new Address();
                        Country gr_ctry = new Country();
                        gr_ctry.setvalue(idcountry);
                        adr.setCountry(gr_ctry);
              
                        if (midnowner != null) {
                            MiddleName middlename = new MiddleName();
                            middlename.setvalue(midnowner);
                            adrb.setMiddleName(middlename);
                        }

                        Orgname on = null;
                        if (fnowner == null) {
                            on = new Orgname();
                            on.setvalue(nmowner);
                            adrb.setOrgname(on);
                        } else {
                            FirstName fnm = new FirstName();
                            fnm.setvalue(fnowner);
                            adrb.setFirstName(fnm);
                            LastName ln = new LastName();
                            ln.setvalue(nmowner);
                            adrb.setLastName(ln);
                        }
                        adrb.setAddress(adr);
     
                        grantee.getAddressbook().add(adrb);
                        
                      Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                      nty.setCountry(natctr);
                     grantee.setNationality(nty); 
                       
                        
                        
                    Residence residence = new Residence();
                    residence.setCountry(gr_ctry);  ////????????????
                    grantee.setResidence(residence); 
                    grantees.getGrantee().add(grantee);

                    }//while        grantees

                    parties.setGrantees(grantees);
                    bd.setParties(parties);

                    InventionTitle itlt = new InventionTitle();
                    itlt.setLang("lt");
                    itlt.setvalue(title);
                    InventionTitle iten = new InventionTitle();
                    iten.setLang("en");
                    iten.setvalue(engtitle);
                    bd.getInventionTitle().add(itlt);
                    bd.getInventionTitle().add(iten);

                    Abstract abstrlt = new Abstract();
                    abstrlt.setLang("lt");
                    P plt = new P();
                    plt.setvalue(abstrlttxt);
                    plt.setNum("0001");
                    abstrlt.getDocPageOrP().add(plt);

                    Abstract abstren = new Abstract();
                    abstren.setLang("en");
                    P pen = new P();
                    pen.setvalue(abstrentxt);
                    pen.setNum("0001");
                    abstren.getDocPageOrP().add(pen);

                    spd.getAbstract().add(abstrlt);
                    spd.getAbstract().add(abstren);
                    spd.setBibliographicData(bd);

                    JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
               
                    String fv = lt_patentai.get(j).toString().replace(" ", "");
                    
                    
   m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("LT_" + skelb_data + "_" + fv + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  //new FileOutputStream("spc.xml")
                    m.marshal(spd, System.out);
                    System.out.println();  //suderinus ištrinti
                    os.close();
                
                    j++;
                    
                }//while    patentai
            }//if (lt_patentai!=null) 
        }
        Forma.Isfailo = false;
        
        

      //  ---------------------   Paraiškos    ---------------------------------  
        
        GET_PAT = "select idappli, dtappli, stitle, engtitle, ipcmclass, extidappli, bedat(dtappli) dtapp from informix.ptappli  where extidappli = ";

        j = 0;
        if (lt_paraiskos != null) {
            while (j < lt_paraiskos.size()) {
                String idappli = null;
                String title = null;
                String engtitle = null;
                String ipcmclass = null;
                String extidappli = null;
                String dtpubli = null;
                String dtapp = null;
                String GET_PATID = GET_PAT + "\"" + lt_paraiskos.get(j).toString() + "\"";
                ResultSet rs = stmt.executeQuery(GET_PATID);
                while (rs.next()) {
                    idappli = rs.getString("idappli");
                    title = rs.getString("stitle").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs.getString("engtitle").trim();
                    ipcmclass = rs.getString("ipcmclass").trim();  //F24F  12/00
                    extidappli = rs.getString("extidappli").trim();
                    dtapp = rs.getString("dtapp");
                    System.out.println(idappli);  // der
                }      //while
                
                
         if (Forma.Isfailo2) {   
                        String GET_DTPUBLI = "select bedat(gazette.dtpubli) dtpb from informix.gazette, informix.publication where " + "idappli = " + "\"" + idappli + "\""
                                + " and gazette.nogazette = publication.nogazette and gazette.yygazette = publication.yygazette and (nosect=1 or nosect=10)";
                        ResultSet rspub = stmt.executeQuery(GET_DTPUBLI);
                        while (rspub.next()) {
                            dtpubli = rspub.getString("dtpb");
                        }
                        skelb_data = dtpubli;
                        rspub.close();
                        System.out.println(dtpubli);
                    }       
         
// paraiskos:     select bedat(gazette.dtpubli) dtpb from informix.gazette, informix.publication where idappli = "X?????"
//                                 and gazette.nogazette = publication.nogazette and gazette.yygazette = publication.yygazette and (nosect=1 or nosect=10)

                

                String GET_ABSTR = "select txtform, txtformtr from abstract where idappli = " + "\"" + idappli + "\"";
                ResultSet rsab = stmt.executeQuery(GET_ABSTR);
                String abstrlttxt = null;
                String abstrentxt = null;
                while (rsab.next()) {
                    abstrlttxt = rsab.getString("txtform");
                    if (abstrlttxt != null) {
                        abstrlttxt = Perkodavimas.Perkoduoti(abstrlttxt).trim();
                        abstrlttxt = deleteeta(abstrlttxt);
                    }
                    abstrentxt = rsab.getString("txtformtr");
                    if (abstrentxt != null) {
                        abstrentxt = abstrentxt.trim();
                        abstrentxt = deleteeta(abstrentxt);
                    }
                }//while

                SimplePatentDocument spd = new SimplePatentDocument();
                spd.setDocNumber(lt_paraiskos.get(j).toString().replace(" ", ""));
                spd.setKind("A");
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
                di.setDocNumber(lt_paraiskos.get(j).toString().replace(" ", ""));
                di.setKind("A");
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
                    odclass = rsab.getInt("odclass");
                    if (odclass == 1) {
                        symbolposition = "F";
                    } else {
                        symbolposition = "L";
                    }
                    clall = rsab.getString("ipcclass").trim();  //B61L  21/00    
                    dtvers = rsab.getString("dtvers");
                    clall = clall.replace("  ", " ");
                    clall = clall.replace("  ", " ");
                    String[] mas = clall.split(" ");
                    section = mas[0].substring(0, 1);
                    klase = mas[0].substring(1, 3);
                    subklase = mas[0].substring(3);
                    String[] grupes = mas[1].trim().split("/");
                    maingroup = grupes[0];
                    subgroup = grupes[1];

                    ClassificationIpcr cipcr = new ClassificationIpcr();
                    IpcVersionIndicator ivi = new IpcVersionIndicator();
                    lt.vinco.simple.Date dtc = new lt.vinco.simple.Date();
                    dtc.setvalue(dtvers);  //????
                    ivi.setDate(dtc);    // 
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

                    csipcr.getClassificationIpcr().add(cipcr);    //ciklo pabaiga

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
                
  //prios              
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
    
                
 //prios end               
                

                Parties parties = new Parties();

 // Irasyti pareiskejus (applicants):              
                Applicants applicants = new Applicants();
             
                String GET_APPLICANTS = "select extidappli,fnowner, midnowner, nmowner, o.odowner, w.idcountry from ptappli p,own o, owner w where p.idappli= " + "\"" + idappli + "\" and o.idowner=w.idowner and p.idappli=o.idappli order by o.odowner";
                ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                    int ordowner = 0;
                    String nmowner = null;
                    String nmowner_neperkoduotas = null;
                    String midnowner = null;
                    String fnowner = null;
                    String idcountry = null;
                     extidappli = null;
extidappli = rsapps.getString("extidappli");

                    ordowner = rsapps.getInt("odowner");
                    nmowner = rsapps.getString("nmowner");
                    if (nmowner != null) {
                        nmowner_neperkoduotas = nmowner.trim();
                        nmowner = Perkodavimas.Perkoduoti(nmowner_neperkoduotas);
                    }
                    midnowner = rsapps.getString("midnowner");
                    if (midnowner != null) {
                        midnowner = midnowner.trim();
                        midnowner = Perkodavimas.Perkoduoti(midnowner);
                    }

                    fnowner = rsapps.getString("fnowner"); //.trim();
                    if (fnowner != null) {
                        fnowner = fnowner.trim();
                        fnowner = Perkodavimas.Perkoduoti(fnowner);
                    }
                    idcountry = rsapps.getString("idcountry");

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   //  ??????????????
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
        
                    if (midnowner != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midnowner);
                        adrb.setMiddleName(middlename);
                    }

                    Orgname on = null;
                    if (fnowner == null) {
                        on = new Orgname();
                        on.setvalue(nmowner);
                        adrb.setOrgname(on);
                     } else {
                        FirstName fnm = new FirstName();
                        fnm.setvalue(fnowner);
                        adrb.setFirstName(fnm);
                        LastName ln = new LastName();
                        ln.setvalue(nmowner);
                        adrb.setLastName(ln);
                       }
                    adrb.setAddress(adr);
 
                    applicant.getAddressbook().add(adrb);
                    applicant.setDesignation("all");
                    
                    Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                      nty.setCountry(natctr);
                     applicant.setNationality(nty);  
                    
                    
                    
                  Residence residence = new Residence();
                    residence.setCountry(gr_ctry);  ////????????????
                    applicant.setResidence(residence); 
                    
      ///-------------------------/ pareiškėjas yra išradėjas ? /-----------------------------
             if (on!=null)  applicant.setAppType("applicant");     
             else {     
                    
        String invsql = " select ptappli.extidappli, idowner, fnowner, midnowner,nmowner"
                + " from ptappli, initapply, owner, "
                + " (select ptappli.extidappli, invent.idinvent, ordinvent, fninventor, midninventor, nminventor, adinventor"
                + " from ptappli, invent, inventor"
                + " where ptappli.idappli=invent.idappli and inventor.idinvent=invent.idinvent and ptappli.extidappli=" + "\"" + extidappli + "\")"
                + " where ptappli.idappli=initapply.idappli and initapply.idinitapp=owner.idowner and ptappli.extidappli matches " + "\"" + extidappli + "\""
                + " and trim(fninventor)=trim(fnowner) and trim(nminventor)=trim(nmowner) and owner.nmowner =" + "\"" + nmowner_neperkoduotas + "\"";
        System.out.println(invsql);

        Statement invvst = conn.createStatement();
        ResultSet invvrs = invvst.executeQuery(invsql);
               
         if(invvrs.next())   applicant.setAppType("applicant-inventor"); else applicant.setAppType("applicant");
           invvst.close();
         invvrs.close();
             }//else
    ///-------------------------/ pareiškėjas yra išradėjas ? /----------------------------- END   
         
                    applicants.getApplicant().add(applicant);
                }//while        applicants

                parties.setApplicants(applicants);

                Inventors invs = new Inventors();
                // String GET_INVS = "select idinvent, ordinvent from invent where idappli = " +  "\"" + idappli +  "\" order by ordinvent";
                String GET_INVS = "select invent.idinvent, ordinvent, i.nminventor, i.midninventor, i.fninventor, i.idcountry from invent, inventor i where idappli = " + "\"" + idappli + "\" and invent.idinvent = i.idinvent order by ordinvent";
                ResultSet rsinv = stmt.executeQuery(GET_INVS);

                while (rsinv.next()) {

                    int idinvent = 0;
                    int ordinvent = 0;
                    String nminventor = null;
                    String midninventor = null;
                    String fninventor = null;
                    String idcountry = null;

                    idinvent = rsinv.getInt("idinvent");
                    ordinvent = rsinv.getInt("ordinvent");

                    nminventor = rsinv.getString("nminventor");
                    if (nminventor != null) {
                        nminventor = nminventor.trim();
                        nminventor = Perkodavimas.Perkoduoti(nminventor);
                    }

                    midninventor = rsinv.getString("midninventor");
                    if (midninventor != null) {
                        midninventor = midninventor.trim();
                        midninventor = Perkodavimas.Perkoduoti(midninventor);
                    }

                    fninventor = rsinv.getString("fninventor").trim();
                    if (fninventor != null) {
                        fninventor = fninventor.trim();
                        fninventor = Perkodavimas.Perkoduoti(fninventor);
                    }
                    idcountry = rsinv.getString("idcountry");  if(idcountry == null) idcountry = "";

                    Inventor inv = new Inventor();
                    inv.setSequence(Integer.toString(ordinvent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country inv_ctry = new Country();
                    inv_ctry.setvalue(idcountry);
                    adr.setCountry( inv_ctry);
                       FirstName fnm = new FirstName();
                    fnm.setvalue(fninventor);

                    if (midninventor != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midninventor);
                        adrb.setMiddleName(middlename);
                    }

                    LastName ln = new LastName();
                    ln.setvalue(nminventor);
                    adrb.setFirstName(fnm);
                    adrb.setLastName(ln);
                    adrb.setAddress(adr);
        
                    inv.getAddressbook().add(adrb);
                    
                    Nationality nty = new Nationality();
                    Country natctr = new Country();
                    natctr.setvalue("");
                    nty.setCountry(natctr);
                    inv.setNationality(nty);
                    
                     Residence residence = new Residence();
                    residence.setCountry(inv_ctry);  ////????????????
                    inv.setResidence(residence);  
                    
                    
                    invs.getInventorOrDeceasedInventor().add(inv);

                }//while        invs  

                parties.setInventors(invs);
                
                
  //--------------  paraisku agentai  -----------  
                
    
         String agentsql = "select extidappli, ptappli.idagent, fnagent, midnagent, nmagent, adagent, idtown, nmtown, idcountry"
                + " from ptappli, agent where ptappli.idagent=agent.idagent and extidappli=" + "\"" + lt_paraiskos.get(j).toString() + "\"";
        System.out.println("agentai: "+agentsql);
        Statement agentstmt = conn.createStatement();
        ResultSet agentrs = agentstmt.executeQuery(agentsql);
 
   // agentai

     Agents agents = new Agents();  
      
                    int ordagent = 0;
        while (agentrs.next()) {
           ordagent++;
                    String nmagent = null;
                    String midnagent = null;
                    String fnagent = null;
                    String idcountry = null;

                    nmagent = agentrs.getString("nmagent");
                    if (nmagent != null) {
                        nmagent = nmagent.trim();
                        nmagent = Perkodavimas.Perkoduoti(nmagent);
                    }

                    midnagent = agentrs.getString("midnagent");
                    if (midnagent != null) {
                        midnagent = midnagent.trim();
                        midnagent = Perkodavimas.Perkoduoti(midnagent);
                    }

                    fnagent = agentrs.getString("fnagent").trim();
                    if (fnagent != null) {
                        fnagent = fnagent.trim();
                        fnagent = Perkodavimas.Perkoduoti(fnagent);
                    }
                    idcountry = agentrs.getString("idcountry");  if(idcountry == null) idcountry = "";

                    Agent agent = new Agent();
                    agent.setRepType("agent");
                    agent.setSequence(Integer.toString(ordagent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country ag_ctry = new Country();
                    ag_ctry.setvalue(idcountry);
                    adr.setCountry(ag_ctry);
                 //   adr.getAddress1OrAddress2OrAddress3OrAddress4OrAddress5OrMailcodeOrPoboxOrRoomOrAddressFloorOrBuildingOrStreetOrCityOrCountyOrStateOrPostcodeOrCountryOrText().add(ag_ctry);
                    FirstName fnm = new FirstName();
                    fnm.setvalue(fnagent);

                    if (midnagent != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midnagent);
                        adrb.setMiddleName(middlename);
                    }

                    LastName ln = new LastName();
                    ln.setvalue(nmagent);
                    adrb.setFirstName(fnm);
                    adrb.setLastName(ln);
                    adrb.setAddress(adr);
        
                    agent.getAddressbook().add(adrb);
                    agent.setSequence(ordagent+"");
                    
                    agents.getCustomerNumberOrAgent().add(agent);
                    
       
        }// while
                                 
 parties.setAgents(agents);
        agentstmt.close();
        agentrs.close();  
 //--------------  paraisku agentai  ----------- end
        
        
                bd.setParties(parties);

                InventionTitle itlt = new InventionTitle();
                itlt.setLang("lt");
                itlt.setvalue(title);
                InventionTitle iten = new InventionTitle();
                iten.setLang("en");
                iten.setvalue(engtitle);
                bd.getInventionTitle().add(itlt);
                bd.getInventionTitle().add(iten);

                Abstract abstrlt = new Abstract();
                abstrlt.setLang("lt");
                P plt = new P();
                plt.setvalue(abstrlttxt);
                plt.setNum("0001");
                abstrlt.getDocPageOrP().add(plt);

                Abstract abstren = new Abstract();
                abstren.setLang("en");
                P pen = new P();
                pen.setvalue(abstrentxt);
                pen.setNum("0001");
                     
                abstren.getDocPageOrP().add(pen);

                spd.getAbstract().add(abstrlt);
                spd.getAbstract().add(abstren);
                spd.setBibliographicData(bd);

                JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
       
                String fv = lt_paraiskos.get(j).toString().replace(" ", "");

 m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("LT_" + skelb_data + "_" + fv + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println(); 
                    os.close();
                 
                j++;
               
            }//while    paraiskos
        }//if (lt_paraiskos!=null) 
        
         Forma.Isfailo2 = false;

          GET_PAT = "select p.idappli, p.dtappli, p.stitle, p.engtitle, p.ipcmclass, p.extidappli, bedat(p.dtappli) dtapp, pc.orictry, pc.typctep, pc.nopctep, "
                + " bedat(pc.dtpctappli) dtpct  from ptappli p, pctref pc "
                + " where p.idappli = pc.idappli and extidappli =  ";
System.out.println(GET_PAT);
        j = 0;
        if (lt_tparaiskos != null) {
            while (j < lt_tparaiskos.size()) {
                String idappli = null;
                String title = null;
                String engtitle = null;
                String ipcmclass = null;
                String extidappli = null;
                String dtapp = null;
                //pct:    
                int typctep = 0;
                String nopctep = null;
                String dtpct = null;
                //prioritetas
                String noprio = null;
                String pr_idcountry = null;
                String pct_country = null;
                String dtprio = null;
                List<Prioritetas> prioritetai = new ArrayList<Prioritetas>();
                int odprio = 0;

                String GET_PATID = GET_PAT + "\"" + lt_tparaiskos.get(j).toString() + "\"";
                System.out.println(GET_PATID);
                ResultSet rs = stmt.executeQuery(GET_PATID);
                
                while (rs.next()) {
                    idappli = rs.getString("idappli").trim();
                    title = rs.getString("stitle").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs.getString("engtitle").trim();
                    ipcmclass = rs.getString("ipcmclass").trim();  //F24F  12/00
                    extidappli = rs.getString("extidappli").trim();
                    dtapp = rs.getString("dtapp");
                    System.out.println(idappli);  // der

                    typctep = rs.getInt("typctep");   //1 -PCT, 2-EP, 3-EuroPCT
                    nopctep = rs.getString("nopctep").trim();
                    dtpct = rs.getString("dtpct").trim();
                    pct_country = rs.getString("orictry").trim();
                    
                }      //while
                
                
                String GET_PRIO = "select noprio, typrio, idcountry, bedat(dtprio) dtpri, odprio from priority where idappli = "+ "\"" + idappli + "\"";
                ResultSet rsprio = stmt.executeQuery(GET_PRIO);
                System.out.println(GET_PRIO);
                while (rsprio.next()) {
                Prioritetas pr = new Prioritetas();
                int typrio = rs.getInt("typrio");
                
                switch (typrio) {
            case 1:  
            pr.kind ="international";
                     break;
            case 3:  
            pr.kind = "national";
                     break;
            default: 
            pr.kind = "----------------------------------nežinomas prioritetas---------------------------";
                     break;
        }            
                
                    noprio = rs.getString("noprio").trim();
                    dtprio = rs.getString("dtpri").trim();
                    pr_idcountry = rs.getString("idcountry").trim();
                    odprio = rs.getInt("odprio");
                    pr.dtprio = dtprio;
                    pr.idcountry = pr_idcountry;
                    pr.noprio = noprio;
                    pr.odprio = odprio;
                    prioritetai.add(pr);
                }//prioritetai
                rsprio.close();

                String GET_ABSTR = "select txtform, txtformtr from abstract where idappli = " + "\"" + idappli + "\"";
                ResultSet rsab = stmt.executeQuery(GET_ABSTR);
                String abstrlttxt = null;
                String abstrentxt = null;
                while (rsab.next()) {
                    abstrlttxt = rsab.getString("txtform");
                    if (abstrlttxt != null) {
                        abstrlttxt = Perkodavimas.Perkoduoti(abstrlttxt).trim();
                        abstrlttxt = deleteeta(abstrlttxt);
                    }
                    abstrentxt = rsab.getString("txtformtr");
                    if (abstrentxt != null) {
                        abstrentxt = abstrentxt.trim();
                        abstrentxt = deleteeta(abstrentxt);
                    }
                }//while

                SimplePatentDocument spd = new SimplePatentDocument();
                spd.setDocNumber(lt_tparaiskos.get(j).toString().replace(" ", ""));
                spd.setKind("A");
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
                di.setDocNumber(lt_tparaiskos.get(j).toString().replace(" ", ""));
                di.setKind("A");
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
                    odclass = rsab.getInt("odclass");
                    if (odclass == 1) {
                        symbolposition = "F";
                    } else {
                        symbolposition = "L";
                    }
                    clall = rsab.getString("ipcclass").trim();  //B61L  21/00    
                    dtvers = rsab.getString("dtvers");
                    clall = clall.replace("  ", " ");
                    clall = clall.replace("  ", " ");
                    String[] mas = clall.split(" ");
                    section = mas[0].substring(0, 1);
                    klase = mas[0].substring(1, 3);
                    subklase = mas[0].substring(3);
                    String[] grupes = mas[1].trim().split("/");
                    maingroup = grupes[0];
                    subgroup = grupes[1];


                    ClassificationIpcr cipcr = new ClassificationIpcr();
                    IpcVersionIndicator ivi = new IpcVersionIndicator();
                    lt.vinco.simple.Date dtc = new lt.vinco.simple.Date();
                    dtc.setvalue(dtvers);  //????
                    ivi.setDate(dtc);    // 
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

                    csipcr.getClassificationIpcr().add(cipcr);    //ciklo pabaiga

                }  // while ipcclass

                bd.setClassificationsIpcr(csipcr);
                System.out.println("ext: " + extidappli);
                System.out.println("ext: " + lt_tparaiskos.get(j).toString());
                ApplicationReference ar = new ApplicationReference();
                DocumentId diap = new DocumentId();
                diap.setCountry(ctr);
                //          diap.setDocNumber(extidappli.replace(" ", ""));
                diap.setDocNumber(lt_tparaiskos.get(j).toString());
                lt.vinco.simple.Date dtapl = new lt.vinco.simple.Date();
                dtapl.setvalue(dtapp);
                System.out.println(dtapp);
                diap.setDate(dtapl);
                System.out.println(dtapl);//paimti paraiškos datą iš sql
                ar.setDocumentId(diap);
                bd.setApplicationReference(ar);
                bd.setLanguageOfPublication("lt");

                Parties parties = new Parties();

 // Irasyti pareiskejus (applicants):              
                Applicants applicants = new Applicants();
                //String GET_APPLICANTS = "select   o.nmowner, o.midnowner, o.fnowner, o.idcountry, apply.odappli from apply, owner o where idappli = " +  "\"" + idappli +  "\" and o.idowner = apply.idperson order by apply.odappli";

                String GET_APPLICANTS = "select extidappli,fnowner, midnowner, nmowner, o.odowner, w.idcountry from ptappli p,own o, owner w where p.idappli= " + "\"" + idappli + "\" and o.idowner=w.idowner and p.idappli=o.idappli order by o.odowner";
                ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                    int ordowner = 0;
                    String nmowner = null;
                    String midnowner = null;
                    String fnowner = null;
                    String idcountry = null;


                    ordowner = rsapps.getInt("odowner");
                    nmowner = rsapps.getString("nmowner");
                    if (nmowner != null) {
                        nmowner = nmowner.trim();
                        nmowner = Perkodavimas.Perkoduoti(nmowner);
                    }
                    midnowner = rsapps.getString("midnowner");
                    if (midnowner != null) {
                        midnowner = midnowner.trim();
                        midnowner = Perkodavimas.Perkoduoti(midnowner);
                    }

                    fnowner = rsapps.getString("fnowner"); //.trim();
                    if (fnowner != null) {
                        fnowner = fnowner.trim();
                        fnowner = Perkodavimas.Perkoduoti(fnowner);
                    }
                    idcountry = rsapps.getString("idcountry");

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
              
                    if (midnowner != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midnowner);
                        adrb.setMiddleName(middlename);
                     }

                    Orgname on = null;
                    if (fnowner == null) {
                        on = new Orgname();
                        on.setvalue(nmowner);
                        adrb.setOrgname(on);
                    } else {
                        FirstName fnm = new FirstName();
                        fnm.setvalue(fnowner);
                        adrb.setFirstName(fnm);
                         LastName ln = new LastName();
                        ln.setvalue(nmowner);
                        adrb.setLastName(ln);
                    }
                    adrb.setAddress(adr);
      
                    applicant.getAddressbook().add(adrb);
                    applicant.setDesignation("all");
                    
                     Nationality nty = new Nationality();
                    Country natctr = new Country();
                    natctr.setvalue("");
                    nty.setCountry(natctr);
                    applicant.setNationality(nty);               
                    
                    ObjectFactory ob = new ObjectFactory();
                    Residence residence = ob.createResidence();
                    residence.setCountry(gr_ctry);  ////????????????
                    applicant.setResidence(residence);
                    
                    applicants.getApplicant().add(applicant);

                }//while        applicants

                parties.setApplicants(applicants);

                Inventors invs = new Inventors();
                // String GET_INVS = "select idinvent, ordinvent from invent where idappli = " +  "\"" + idappli +  "\" order by ordinvent";
                String GET_INVS = "select invent.idinvent, ordinvent, i.nminventor, i.midninventor, i.fninventor, i.idcountry from invent, inventor i where idappli = " + "\"" + idappli + "\" and invent.idinvent = i.idinvent order by ordinvent";
                ResultSet rsinv = stmt.executeQuery(GET_INVS);

                while (rsinv.next()) {

                    int idinvent = 0;
                    int ordinvent = 0;
                    String nminventor = null;
                    String midninventor = null;
                    String fninventor = null;
                    String idcountry = null;

                    idinvent = rsinv.getInt("idinvent");
                    ordinvent = rsinv.getInt("ordinvent");

                    nminventor = rsinv.getString("nminventor");
                    if (nminventor != null) {
                        nminventor = nminventor.trim();
                        nminventor = Perkodavimas.Perkoduoti(nminventor);
                    }

                    midninventor = rsinv.getString("midninventor");
                    if (midninventor != null) {
                        midninventor = midninventor.trim();
                        midninventor = Perkodavimas.Perkoduoti(midninventor);
                    }

                    fninventor = rsinv.getString("fninventor").trim();
                    if (fninventor != null) {
                        fninventor = fninventor.trim();
                        fninventor = Perkodavimas.Perkoduoti(fninventor);
                    }
                    idcountry = rsinv.getString("idcountry");  if(idcountry == null) idcountry = "";

                    Inventor inv = new Inventor();
                    inv.setSequence(Integer.toString(ordinvent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country inv_ctry = new Country();
                    inv_ctry.setvalue(idcountry);
                    adr.setCountry(inv_ctry);
                                  FirstName nm = new FirstName();
                    nm.setvalue(fninventor);
                    adrb.setFirstName(nm);

                    if (midninventor != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midninventor);
                        adrb.setMiddleName(middlename);
                     }

                    LastName ln = new LastName();
                    ln.setvalue(nminventor);
                    adrb.setLastName(ln);
                    adrb.setAddress(adr);
                    inv.getAddressbook().add(adrb);
                    
                    
                     Nationality nty = new Nationality();
                    Country natctr = new Country();
                    natctr.setvalue("");
                    nty.setCountry(natctr);
                    inv.setNationality(nty);
                    
                    
                      Residence residence = new Residence();
                    residence.setCountry(inv_ctry);  ////????????????
                    inv.setResidence(residence); 
                    invs.getInventorOrDeceasedInventor().add(inv);

                }//while        invs  

                parties.setInventors(invs);
                
  //---- Tarptautiniu paraisku agenatai --------------
                
                  String agentsql = "select extidappli, ptappli.idagent, fnagent, midnagent, nmagent, adagent, idtown, nmtown, idcountry"
                + " from ptappli, agent where ptappli.idagent=agent.idagent and extidappli=" + "\"" + lt_tparaiskos.get(j).toString() + "\"";
        System.out.println("agentai: "+agentsql);
        Statement agentstmt = conn.createStatement();
        ResultSet agentrs = agentstmt.executeQuery(agentsql);
 
   // agentai

     Agents agents = new Agents();  
      
                    int ordagent = 0;
        while (agentrs.next()) {
           ordagent++;
                    String nmagent = null;
                    String midnagent = null;
                    String fnagent = null;
                    String idcountry = null;

                    nmagent = agentrs.getString("nmagent");
                    if (nmagent != null) {
                        nmagent = nmagent.trim();
                        nmagent = Perkodavimas.Perkoduoti(nmagent);
                    }

                    midnagent = agentrs.getString("midnagent");
                    if (midnagent != null) {
                        midnagent = midnagent.trim();
                        midnagent = Perkodavimas.Perkoduoti(midnagent);
                    }

                    fnagent = agentrs.getString("fnagent").trim();
                    if (fnagent != null) {
                        fnagent = fnagent.trim();
                        fnagent = Perkodavimas.Perkoduoti(fnagent);
                    }
                    idcountry = agentrs.getString("idcountry");  if(idcountry == null) idcountry = "";

                    Agent agent = new Agent();
                    agent.setRepType("agent");
                    agent.setSequence(Integer.toString(ordagent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country ag_ctry = new Country();
                    ag_ctry.setvalue(idcountry);
                    adr.setCountry(ag_ctry);
                      FirstName fnm = new FirstName();
                    fnm.setvalue(fnagent);

                    if (midnagent != null) {
                        MiddleName middlename = new MiddleName();
                        middlename.setvalue(midnagent);
                        adrb.setMiddleName(middlename);
                    }

                    LastName ln = new LastName();
                    ln.setvalue(nmagent);
                    adrb.setFirstName(fnm);
                    adrb.setLastName(ln);
                    adrb.setAddress(adr);
        
                    agent.getAddressbook().add(adrb);
                    agent.setSequence(ordagent+"");
                    
                    agents.getCustomerNumberOrAgent().add(agent);
                    
       
        }// while
                                 
 parties.setAgents(agents);
        agentstmt.close();
        agentrs.close();  
 //-------------- tarptautiniu paraisku agentai  ----------- end
                
                
                
                

             bd.setParties(parties);

                InventionTitle itlt = new InventionTitle();
                itlt.setLang("lt");
                itlt.setvalue(title);
                InventionTitle iten = new InventionTitle();
                iten.setLang("en");
                iten.setvalue(engtitle);
                bd.getInventionTitle().add(itlt);
                bd.getInventionTitle().add(iten);

                Abstract abstrlt = new Abstract();
                abstrlt.setLang("lt");
                P plt = new P();
                plt.setvalue(abstrlttxt);
                plt.setNum("0001");
                abstrlt.getDocPageOrP().add(plt);

                Abstract abstren = new Abstract();
                abstren.setLang("en");
                P pen = new P();
                pen.setvalue(abstrentxt);
                pen.setNum("0001");
                abstren.getDocPageOrP().add(pen);

                spd.getAbstract().add(abstrlt);
                spd.getAbstract().add(abstren);

                String typct = null;
                switch (typctep) {
                    case 1:
                        typct = "PCT/";
                        break;
                    case 2:
                        typct = "EP/";
                        break;
                    case 3:
                        typct = "Euro-PCT/";
                    default:
                        typct = "__?__";
                        break;
                }

                PriorityClaims prClaims = new PriorityClaims();

//while pr.claims  
                for (int p = 0; p < prioritetai.size(); p++) {
                    PriorityClaim prClaim = new PriorityClaim();
                    Country prctr = new Country();
                    prctr.setvalue(prioritetai.get(p).idcountry);
                    prClaim.setCountry(prctr);
                    prClaim.setDocNumber(prioritetai.get(p).noprio);
                    lt.vinco.simple.Date prdate = new lt.vinco.simple.Date();
                    prdate.setvalue(prioritetai.get(p).dtprio);
                    prClaim.setDate(prdate);
                    prClaim.setSequence(prioritetai.get(p).odprio + "");
                        prClaim.setKind(prioritetai.get(p).kind);
                    prClaims.getPriorityClaim().add(prClaim);
//end while pr.claims   
                }
                if(prioritetai.size()<1) prClaims = null;
                bd.setPriorityClaims(prClaims);

                PctOrRegionalFilingData pctfd = new PctOrRegionalFilingData();
                DocumentId pctDocid = new DocumentId();
                pctDocid.setDocNumber(typct + nopctep);
                Country pct_ctr = new Country();
                pct_ctr.setvalue(pct_country);
                pctDocid.setCountry(pct_ctr);
                lt.vinco.simple.Date dtpct_v = new lt.vinco.simple.Date();
                dtpct_v.setvalue(dtpct);
                pctDocid.setDate(dtpct_v);
                pctfd.setDocumentId(pctDocid);

                bd.getPctOrRegionalFilingData().add(pctfd);

                spd.setBibliographicData(bd);

              //  JAXBContext jc = JAXBContext.newInstance("lt.vinco.simple");
                JAXBContext jc = JAXBContext.newInstance(SimplePatentDocument.class);
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                 m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                 
              
                String fv = lt_tparaiskos.get(j).toString().replace(" ", "");

 m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE simple-patent-document SYSTEM \"simple-patent-document-v1-9.dtd\">");
                   OutputStream os = new FileOutputStream("LT_" + skelb_data + "_" + fv + ".xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(spd, os);  
                    m.marshal(spd, System.out);
                    System.out.println();  
                    os.close();        

                
                j++;
              
            }//while    tparaiskos
        }//if (lt_tparaiskos!=null)      

  // TParaiškos end    -------------------------------------------------- 
        conn.close();

 //zipavimas:
        File[] zipuojami = new File(".").listFiles();
        String target = "LT" + skelb_data + "_EBD.zip";

        byte[] buf = new byte[1024];

        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));
            for (File failas : zipuojami) {
                if (Pattern.matches("LT_" + skelb_data + "_.....xml", failas.getName()) //patentai
                        || Pattern.matches("LT_" + skelb_data + "_........xml", failas.getName()) //paraiskos   
                         || Pattern.matches("\\S+.dtd", failas.getName()) //dtd 
                        ) {  //if (Pattern.matches("lt\\d\\d...a.txt", failas.getName())) { //lt05bb1a
                    FileInputStream in = new FileInputStream(failas);
                    out.putNextEntry(new ZipEntry(failas.getName()));

                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    // Complete the entry
                    out.closeEntry();
                    in.close();
                } // if

            }  // for
            out.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }  //getSimpleLt

}// class

