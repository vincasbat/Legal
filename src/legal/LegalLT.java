/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legal;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import legal.lt.vinco.L500;
import legal.lt.vinco.LR;
import legal.lt.vinco.LS;

/**
 *
 * @author vbatulevicius
 */
public class LegalLT {

    private static List<String> age = null;  //agento pakeitimai  TC9A
    private static List<String> pak = null;  //savininko pakeitimai  PD9A
    private static List<String> perd = null;  //perleidimas  PC9A
    private static List<String> ats = null;  //atšaukimas  FD9A
    private static List<String> lic = null;  //viešosios licencijos (buvo: paskelbimas) suteikimas  QB9A
    private static List<String> lrig = null;  //viešosios licencijos paskelbimas  QA9A
    private static List<String> ican = null;  //LT patentai, panaikinti pagal 36 str. MM9A
    private static List<String> exp = null;  //LT patentai, kuriu galiojimo laikas pasibaiges pagal 36 str. MK9A
    private static List<String> inpat = null; // MG9A Patentai, pripažinti negaliojančiais pagal Lietuvos Respublikos patentų įstatymo 63 straipsnį
    private static List<String> teis = null; //prip. negaliojančiais pagal 63 str. MG9A
    
    private static List<String> ipat_legal = null; // FG9A
    private static List<String> ipar_legal = null; // BB1A
    private static List<String> tpar_legal = null; //  (BB1A) gal nurodyti PCT nr?
     private static List<String> lats = null;

    public static List<String> getTeis() {
        return teis;
    }

    public static void setTeis(List<String> teis) {
        LegalLT.teis = teis;
    }
     
     

     public static List<String> getLats() {
        return lats;
    }

    public static void setLats(List<String> lats) {
        LegalLT.lats = lats;
    }
     
    public static List<String> getLrig() {
        return lrig;
    }

    public static void setLrig(List<String> lrig) {
        LegalLT.lrig = lrig;
    }

    public static List<String> getAge() {
        return age;
    }

    public static void setAge(List<String> age) {
        LegalLT.age = age;
    }

    public static List<String> getAts() {
        return ats;
    }

    public static void setAts(List<String> ats) {
        LegalLT.ats = ats;
    }

    public static List<String> getIcan() {
        return ican;
    }

    public static void setIcan(List<String> ican) {
        LegalLT.ican = ican;
    }

    public static List<String> getLic() {
        return lic;
    }

    public static void setLic(List<String> lic) {
        LegalLT.lic = lic;
    }

    public static List<String> getPak() {
        return pak;
    }

    public static void setPak(List<String> pak) {
        LegalLT.pak = pak;
    }

    public static List<String> getPerd() {
        return perd;
    }

    public static void setPerd(List<String> perd) {
        LegalLT.perd = perd;
    }

    public static List<String> getExp() {
        return exp;
    }

    public static void setExp(List<String> exp) {
        LegalLT.exp = exp;
    }

    public static List<String> getInpat() {
        return inpat;
    }

    public static void setInpat(List<String> inpat) {
        LegalLT.inpat = inpat;
    }

    public static List<String> getIpat_legal() {
        return ipat_legal;
    }

    public static void setIpat_legal(List<String> ipat_legal) {
        LegalLT.ipat_legal = ipat_legal;
    }

    public static List<String> getIpar_legal() {
        return ipar_legal;
    }

    public static void setIpar_legal(List<String> ipar_legal) {
        LegalLT.ipar_legal = ipar_legal;
    }

    public static List<String> getTpar_legal() {
        return tpar_legal;
    }

    public static void setTpar_legal(List<String> tpar_legal) {
        LegalLT.tpar_legal = tpar_legal;
    }

    static void getLegal() throws JAXBException, IOException, ClassNotFoundException, SQLException {
       



        Class.forName("com.informix.jdbc.IfxDriver");
        
        String URL = "";
    
       
        Connection conn = DriverManager.getConnection(URL);





        
        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, dtappli, dtrecept, title, engtitle, ";
        GET_PAT += "engtitle, dtopen, rmappli, dtlgstappli, lgstappli, dttcstappli, tcstappli, dtupdate, iduser, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, dtadvert, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and dtappli > (MDY(MONTH(TODAY), 1, YEAR(TODAY)) - 10 UNITS YEAR) and dtappli < MDY(MONTH(TODAY), 1, YEAR(TODAY)) ";






       

        LS ls = new LS();
        List<LR> listLR = ls.getLR();
      String   skelb_data = Ut.getPublDate();
        skelb_data = JOptionPane.showInputDialog(null, "Skelbimo data, pvz., 20121227", skelb_data);



        Statement stmt = conn.createStatement();
       
        int i = 0;

        String idappli = "";
        String dtappli = null;
        String ipcmclass = null;
        String title = null;
        String engtitle = null;
        String idpatent = null;
        String extidpatent = null;
        String extidappli = null;
        String spcnation = null;
        String spcregion = null;
        String l008 = null;
        String spcnumber = null;
        String dtptexpi = null;
        String spcnation_nr = null;
        String spcnation_dt = null;
        String spcregion_nr = null;
        String spcregion_dt = null;
        String l528 = null;



        // ----------------------------  age  -----------------------------------       

       

        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, title, engtitle, ";
        GET_PAT += "fnagent, midnagent, nmagent ";
        GET_PAT += "from ptappli, agent ";
        GET_PAT += "where ptappli.idagent = agent.idagent and ptappli.extidpatent = ";



        String dtlegal = null;
        String fnagent = null;
        String midnagent = null;
        String nmagent = null;

        int j = 0;
        if (age != null) {
            while (j < age.size()) {



                String pat = age.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
                  ResultSet rs_age = stmt.executeQuery(GET_PATID);
                while (rs_age.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    spcnation = null;
                    spcregion = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;

                    title = rs_age.getString("title").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs_age.getString("engtitle").trim();
                    extidpatent = rs_age.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    idappli = rs_age.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }

                    extidappli = rs_age.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }

                    fnagent = rs_age.getString("fnagent");
                    if (fnagent != null) {
                        fnagent = fnagent.trim();
                        fnagent = Perkodavimas.Perkoduoti(fnagent);
                    }

                    midnagent = rs_age.getString("midnagent");
                    if (midnagent != null) {
                        midnagent = midnagent.trim() + ", LT";
                        fnagent = fnagent + " ";
                        midnagent = Perkodavimas.Perkoduoti(midnagent);
                    } else {
                        midnagent = "";
                        fnagent = fnagent + ", LT";
                    }


                    nmagent = rs_age.getString("nmagent");
                    if (nmagent != null) {
                        nmagent = nmagent.trim();
                        nmagent = Perkodavimas.Perkoduoti(nmagent);
                    }


                    String GET_DTL = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND idoper='412ZK'  AND dtlegal is not null";
                    Statement st_dtl = conn.createStatement();
                    ResultSet rs_dtl = st_dtl.executeQuery(GET_DTL);
                    while (rs_dtl.next()) {
                        dtlegal = rs_dtl.getString("dtlegal");
                    }



                }//while agent  


                L500 l500 = new L500(); 

                l500.setL517(nmagent + ", " + fnagent + midnagent);//<L517>  representative name (in case of change of representative=patent attorney)
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);
//
                lr.setL001("LT");// <L001>   Country Code - ST.3 
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention

                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("TC9A");
             
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???



                listLR.add(lr);

                j++;
            }   //while age   
        }//if not null age   



        // ----------------------------  pak  -----------------------------------       


        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, title, ";
        GET_PAT += "engtitle, ";
        GET_PAT += " fnowner, midnowner, nmowner, unmowner, idcountry  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";

        String fnowner = null;
        String midnowner = null;
        String nmowner = null;
        String idcountry = null;
        String owner = null;
        dtlegal = null;
        j = 0;
        if (pak != null) {
            while (j < pak.size()) {



                String pat = pak.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
              
                ResultSet rs_pak = stmt.executeQuery(GET_PATID);
                while (rs_pak.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;

                    title = rs_pak.getString("title").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs_pak.getString("engtitle").trim();

                    idappli = rs_pak.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_pak.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_pak.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }


                    fnowner = rs_pak.getString("fnowner");
                    if (fnowner != null) {
                        fnowner = fnowner.trim();
                        fnowner = Perkodavimas.Perkoduoti(fnowner);
                    } else {
                        fnowner = "";
                    }

                    midnowner = rs_pak.getString("midnowner");
                    if (midnowner != null) {
                        midnowner = midnowner.trim() + ", LT";
                        midnowner = midnowner + " ";
                        midnowner = Perkodavimas.Perkoduoti(midnowner);
                    } else {
                        midnowner = "";
                    }

                    nmowner = rs_pak.getString("nmowner");
                    if (nmowner != null) {
                        nmowner = nmowner.trim();
                        nmowner = Perkodavimas.Perkoduoti(nmowner);
                    } else {
                        nmowner = "";
                    }

                    idcountry = rs_pak.getString("idcountry");
                    if (idcountry != null) {
                        idcountry = idcountry.trim();
                    }

                    owner = nmowner + ", " + fnowner + " " + midnowner + ", " + idcountry;
                    owner = owner.replace("  ", " ");
                    owner = owner.replace(", ,", ", ");
                    owner = owner.replace("  ", " ");
                    owner = owner.replace(" ,", ",");
 

                    String GET_OWNER = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND (idoper='454' OR idoper='454B' OR idoper='454C') AND dtlegal is not null";
                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);
                    while (rs_owner.next()) {
                        dtlegal = rs_owner.getString("dtlegal");
                    }

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs

                l500.setL509(owner);//<L517>  representative name (in case of change of representative=patent attorney)
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);
//
                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("PD9A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while pak  
        }//if not null pak


        // ----------------------------  perd  -----------------------------------       


        GET_PAT = "select ptappli.idappli, extidappli, extidpatent, title, engtitle, ";
        GET_PAT += " fnowner, midnowner, nmowner, idcountry  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";

        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (perd != null) {
            while (j < perd.size()) {



                String pat = perd.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
          
                ResultSet rs_perd = stmt.executeQuery(GET_PATID);
                while (rs_perd.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;

                    title = rs_perd.getString("title").trim();
                    title = Perkodavimas.Perkoduoti(title);
                    engtitle = rs_perd.getString("engtitle").trim();

                    idappli = rs_perd.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_perd.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_perd.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }


                    fnowner = rs_perd.getString("fnowner");
                    if (fnowner != null) {
                        fnowner = fnowner.trim();
                        fnowner = Perkodavimas.Perkoduoti(fnowner);
                    } else {
                        fnowner = "";
                    }

                    midnowner = rs_perd.getString("midnowner");
                    if (midnowner != null) {
                        midnowner = midnowner.trim() + ", LT";
                        midnowner = midnowner + " ";
                        midnowner = Perkodavimas.Perkoduoti(midnowner);
                    } else {
                        midnowner = "";
                    }

                    nmowner = rs_perd.getString("nmowner");
                    if (nmowner != null) {
                        nmowner = nmowner.trim();
                        nmowner = Perkodavimas.Perkoduoti(nmowner);
                    } else {
                        nmowner = "";
                    }

                    idcountry = rs_perd.getString("idcountry");
                    if (idcountry != null) {
                        idcountry = idcountry.trim();
                    }

                    owner = nmowner + ", " + fnowner + " " + midnowner + ", " + idcountry;
                    owner = owner.replace("  ", " ");
                    owner = owner.replace(", ,", ", ");
                    owner = owner.replace("  ", " ");
                    owner = owner.replace(" ,", ",");

           

                    String GET_OWNER = "SELECT MAX(bedat(dtlegal)) dtleg FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND (idoper='457' OR idoper='457B' OR idoper='457C') AND dtlegal is not null";
                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtleg");

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs

                l500.setL509(owner);//<L517>  representative name (in case of change of representative=patent attorney)
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("PC9A");
               
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while perd  
        }//if not null perd           



        // ----------------------------  ats  -----------------------------------       



        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.idrecept = ";

        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (ats != null) {
            while (j < ats.size()) {



                String par = ats.get(j).toString();    //idrecept tb su tarpu
                String GET_PATID = GET_PAT + "\"" + par + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_ats = stmt.executeQuery(GET_PATID);
                while (rs_ats.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_ats.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_ats.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_ats.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }



                    String GET_OWNER = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND (idoper='145') AND dtlegal is not null";
                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtlegal");

                }//while ats 


                L500 l500 = new L500(); // list of additional attrs

            
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(par.replace(" ", "")); //ne extidpatent
                lr.setL004("A");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("FD9A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while ats  
        }// if not null ats          


        // ----------------------------  lic  -----------------------------------       


        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        // GET_PAT += " fnowner, midnowner, nmowner, unmowner, snmowner, idcountry, idtown, nmtown  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";

        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        String licencija_suteikta = null;
        dtlegal = null;
        j = 0;
        if (lic != null) {
            while (j < lic.size()) {



                String pat = lic.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";

                ResultSet rs_lic = stmt.executeQuery(GET_PATID);
                while (rs_lic.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    licencija_suteikta = null;
                    dtlegal = null;


                    idappli = rs_lic.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_lic.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_lic.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }


idappli + "\"" + " AND idoper='468' AND dtlegal is not null";
                    String GET_OWNER = "select bedat(h.dtlegal) dtlegal ,fnperson,midnperson,nmperson,p.idcountry from license l, person p, history h where  ";
                    GET_OWNER += "l.idperson=p.idperson and h.idappli=l.idappli and idoper='468' and l.idappli = " + "\"" + idappli + "\"" + "AND dtlegal is not null";

                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);  
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtlegal");
                    String ctry = rs_owner.getString("idcountry").trim();
                    String fnperson = rs_owner.getString("fnperson");
                    String midnperson = rs_owner.getString("midnperson");
                    String nmperson = rs_owner.getString("nmperson");
                    if (fnperson == null) {
                        licencija_suteikta = nmperson.trim() + ", " + ctry;
                    } else {
                        if (midnperson == null) {
                            licencija_suteikta = fnperson.trim() + " " + nmperson.trim() + ", " + ctry;
                        } else {
                            licencija_suteikta = fnperson.trim() + " " + midnperson.trim() + " " + nmperson.trim() + ", " + ctry;
                        }
                    }
                    licencija_suteikta = Perkodavimas.Perkoduoti(licencija_suteikta);


                }//while licencija suteikta  


                L500 l500 = new L500(); // list of additional attrs

                      l500.setL525(dtlegal);
                l500.setL522(licencija_suteikta);

                LR lr = new LR();

                lr.setL500(l500);
//
                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");
                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("QB9A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while lic 
        }//if not null lic   



        //---------------------------   lrig   -----------------------------------------------




        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        // GET_PAT += " fnowner, midnowner, nmowner, unmowner, snmowner, idcountry, idtown, nmtown  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";

        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        licencija_suteikta = null;
        dtlegal = null;
        j = 0;
        if (lrig != null) {
            while (j < lrig.size()) {



                String pat = lrig.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";

                ResultSet rs_lic = stmt.executeQuery(GET_PATID);
                while (rs_lic.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    licencija_suteikta = null;
                    dtlegal = null;


                    idappli = rs_lic.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_lic.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_lic.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }




                    String GET_OWNER = "select bedat(h.dtlegal) dtlegal ,fnowner,midnowner,nmowner, ow.idcountry from own o, owner ow, history h where  ";
                    GET_OWNER += "o.idowner=ow.idowner and h.idappli=o.idappli and idoper='468D' and o.idappli = " + "\"" + idappli + "\"" + " AND dtlegal is not null";


                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);  //468D - offering of license of right (QA9A?); 468 - license key-in for a patent (QB9A?)
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtlegal");
                    String ctry = rs_owner.getString("idcountry").trim();
                    fnowner = rs_owner.getString("fnowner");   //nereikia, nes tai viešosios licencijos paskelbimas
                    midnowner = rs_owner.getString("midnowner");
                    nmowner = rs_owner.getString("nmowner");


                }//while 


                L500 l500 = new L500(); // list of additional attrs

              
                l500.setL525(dtlegal);
              

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
 
                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("QA9A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while lic 
        }//if not null lrig           




        //-------------------------------- ican  --------------------------------------


      GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";



        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (ican != null) {
            while (j < ican.size()) {



                String pat = ican.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_ican = stmt.executeQuery(GET_PATID);
                while (rs_ican.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_ican.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_ican.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_ican.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }




                    String GET_ICANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND idoper='109' AND dtlegal is not null";
                    Statement st_icandt = conn.createStatement();
                    ResultSet rs_icandt = st_icandt.executeQuery(GET_ICANDT);
                    rs_icandt.next();
                    dtlegal = rs_icandt.getString("dtlegal");

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs


                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);
                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("MM9A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while ican 
        }//if not null ican   






        //-------------------------------- iexp  --------------------------------------


     GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";



        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (exp != null) {
            while (j < exp.size()) {



                String pat = exp.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
              
                ResultSet rs_exp = stmt.executeQuery(GET_PATID);
                while (rs_exp.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_exp.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_exp.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_exp.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }




                    String GET_ICANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND idoper='109B' AND dtlegal is not null";
                    Statement st_icandt = conn.createStatement();
                    ResultSet rs_icandt = st_icandt.executeQuery(GET_ICANDT);
                    rs_icandt.next();
                    dtlegal = rs_icandt.getString("dtlegal");

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs


                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
 
             
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("MK9A");   //exp
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while iexp 
        }//if not null iexp          



 //-------------------------------- lats  --------------------------------------


      GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";



        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (lats != null) {
            while (j < lats.size()) {



                String pat = lats.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_lats = stmt.executeQuery(GET_PATID);
                while (rs_lats.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_lats.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_lats.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_lats.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }




                    String GET_ICANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND idoper='468E' AND dtlegal is not null";
                    Statement st_icandt = conn.createStatement();
                    ResultSet rs_icandt = st_icandt.executeQuery(GET_ICANDT);
                    rs_icandt.next();
                    dtlegal = rs_icandt.getString("dtlegal");

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs


                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("QC9A");   // Lats
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while lats 
        }//if not null lats          



        
        
        

        //-------------------------------- inpat  --------------------------------------

        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";



        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (inpat != null) {
            while (j < inpat.size()) {



                String pat = inpat.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";

                ResultSet rs_inpat = stmt.executeQuery(GET_PATID);
                while (rs_inpat.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_inpat.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_inpat.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_inpat.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }




                    String GET_ICANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND idoper='109C' AND dtlegal is not null";
                    Statement st_icandt = conn.createStatement();
                    ResultSet rs_icandt = st_icandt.executeQuery(GET_ICANDT);
                    rs_icandt.next();
                    dtlegal = rs_icandt.getString("dtlegal");

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs


                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");
                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("MG9A");   //inpat
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;

            }   //while inpat 
        }//if not null inpat  

        
        
        
        
        
        
        
        
        
        
        //------------------------   ITEIS  -----------------------------
         

        GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent  ";
        GET_PAT += "from ptappli, own, owner ";
        GET_PAT += "where ptappli.idappli=own.idappli and own.idowner=owner.idowner and ptappli.extidpatent = ";



        fnowner = null;
        midnowner = null;
        nmowner = null;
        idcountry = null;
        owner = null;
        dtlegal = null;
        j = 0;
        if (teis != null) {
            while (j < teis.size()) {



                String pat = teis.get(j).toString();
                String GET_PATID = GET_PAT + "\"" + pat + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_teis = stmt.executeQuery(GET_PATID);
                while (rs_teis.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;



                    idappli = rs_teis.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    extidpatent = rs_teis.getString("extidpatent");
                    if (extidpatent != null) {
                        extidpatent = extidpatent.trim();
                    }

                    extidappli = rs_teis.getString("extidappli");
                    if (extidappli != null) {
                        extidappli = extidappli.trim();
                    }



                    String GET_ICANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND idoper='109C' AND dtlegal is not null";
                    Statement st_iteisdt = conn.createStatement();
                    ResultSet rs_teisdt = st_iteisdt.executeQuery(GET_ICANDT);
                    rs_teisdt.next();
                    dtlegal = rs_teisdt.getString("dtlegal");

                }//while naujas savininkas  


                L500 l500 = new L500(); // list of additional attrs


                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);

                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

                lr.setL003(extidpatent);
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("MG9A");
               
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while iteis 
        }//if not null iteis   

        





        // ------------------------------------  IPAR_LEGAL BB1A  -----------------------------------------



        GET_PAT = "select p.idappli  from ptappli p where   p.extidappli =  ";
        idcountry = null;
        dtlegal = null;
        j = 0;
        if (ipar_legal != null) {
            while (j < ipar_legal.size()) {



                String par = ipar_legal.get(j).toString();    //idrecept tb su tarpu
                String GET_PATID = GET_PAT + "\"" + par + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_ipar_legal = stmt.executeQuery(GET_PATID);
                while (rs_ipar_legal.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_ipar_legal.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    String GET_OWNER = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND (idoper='138') AND dtlegal is not null";
                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtlegal");

                }//while ipar_legal 


                L500 l500 = new L500(); // list of additional attrs

                //       l500.setL509(owner);//<L517>  representative name (in case of change of representative=patent attorney)
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);
//
                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
////lr.setL003("0592438");//<L003>   document number 
                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(par.replace(" ", "")); //ne extidpatent
                lr.setL004("A");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("BB1A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while ipar_legal  
        }// if not null ipar_legal      
        //-- end ipar_legal



        // ------------------------------  TPAR_LEGAL -----------------------------------------

GET_PAT = "select p.idappli  from ptappli p where   p.extidappli =  ";
        idcountry = null;
        dtlegal = null;
        j = 0;
        if (tpar_legal != null) {
            while (j < tpar_legal.size()) {



                String par = tpar_legal.get(j).toString();    //idrecept tb su tarpu
                String GET_PATID = GET_PAT + "\"" + par + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_ipar_legal = stmt.executeQuery(GET_PATID);
                while (rs_ipar_legal.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_ipar_legal.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    String GET_OWNER = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND (idoper='138-e') AND dtlegal is not null";
                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtlegal");

                }//while ipar_legal 


                L500 l500 = new L500(); // list of additional attrs

                //       l500.setL509(owner);//<L517>  representative name (in case of change of representative=patent attorney)
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);
//
                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
////lr.setL003("0592438");//<L003>   document number 
                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(par.replace(" ", "")); //ne extidpatent
                lr.setL004("A");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("BB1A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while tpar_legal  
        }// if not null tpar_legal      
          //end tpar_legal


//  --------------------------    IPAT_LEGAL  ----------------------------------------
        
GET_PAT = "select p.idappli  from ptappli p where   p.extidpatent =  ";
        idcountry = null;
        dtlegal = null;
        j = 0;
        if (ipat_legal != null) {
            while (j < ipat_legal.size()) {



                String par = ipat_legal.get(j).toString();    //idrecept tb su tarpu
                String GET_PATID = GET_PAT + "\"" + par + "\"";
                //    System.out.println("xxxxx " + GET_PATID);     
                //        Statement stage = conn.createStatement();
                ResultSet rs_ipar_legal = stmt.executeQuery(GET_PATID);
                while (rs_ipar_legal.next()) {

                    idappli = null;
                    dtappli = null;
                    ipcmclass = null;
                    title = null;
                    engtitle = null;
                    idpatent = null;
                    extidpatent = null;
                    l008 = null;
                    spcnumber = null;
                    spcnation_nr = null;
                    spcnation_dt = null;
                    spcregion_nr = null;
                    spcregion_dt = null;
                    fnowner = null;
                    midnowner = null;
                    nmowner = null;
                    idcountry = null;
                    owner = null;
                    dtlegal = null;


                    idappli = rs_ipar_legal.getString("idappli");
                    if (idappli != null) {
                        idappli = idappli.trim();
                    }


                    String GET_OWNER = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " + "\"" + idappli + "\"" + " AND (idoper='143') AND dtlegal is not null";
                    Statement st_owner = conn.createStatement();
                    ResultSet rs_owner = st_owner.executeQuery(GET_OWNER);
                    rs_owner.next();
                    dtlegal = rs_owner.getString("dtlegal");

                }//while ipar_legal 


                L500 l500 = new L500(); // list of additional attrs

                //       l500.setL509(owner);//<L517>  representative name (in case of change of representative=patent attorney)
                l500.setL525(dtlegal);

                LR lr = new LR();

                lr.setL500(l500);
//
                lr.setL001("LT");// <L001>   Country Code - ST.3 //??? EP - LT
                lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
////lr.setL003("0592438");//<L003>   document number 
                //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
                lr.setL003(par.replace(" ", "")); //ne extidpatent
                lr.setL004("B");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
                lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                lr.setL008("FG9A");
                //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???


                listLR.add(lr);
                j++;
            }   //while ipat_legal  
        }// if not null ipat_legal      
        //ipat_legal end

        if (i == 0) {
            return;
        }



//----------------------- viso xml formavimas ----------------------
        //<Header line with number of records and delivery data>   e.g. <LS country="CN" NoOfRecords="7789" deliveryDate="20080201"> 

      //  OutputStream os = new FileOutputStream("Legal_LT_" + skelb_data + ".xml");


        ls.setCountry("LT");
        ls.setDeliveryDate(skelb_data);
        ls.setNoOfRecords(Integer.toString(i));  //????? tikslinti  

        JAXBContext jc = JAXBContext.newInstance("legal.lt.vinco");
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
       m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
       
m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE legal-document SYSTEM \"legal-document-v1-0.dtd\">");
                   OutputStream os = new FileOutputStream("Legal_LT_" + skelb_data + ".xml");  
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(ls, os);  
                    m.marshal(ls, System.out);
                    System.out.println();  
                    os.close(); 
     
          conn.close();
 
    }//getLegal
}
