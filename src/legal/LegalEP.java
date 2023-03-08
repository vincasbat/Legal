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
public class LegalEP {
private static  List<String> ecan = null; 
private  static   List<String> eprip = null;
    private  static   List<String> kcan = null; 
private  static   List<String> kprip = null;

    public static List<String> getEcan() {
        return ecan;
    }

    public static void setEcan(List<String> ecan) {
        LegalEP.ecan = ecan;
    }

    public static List<String> getEprip() {
        return eprip;
    }

    public static void setEprip(List<String> eprip) {
        LegalEP.eprip = eprip;
    }

    public static List<String> getKcan() {
        return kcan;
    }

    public static void setKcan(List<String> kcan) {
        LegalEP.kcan = kcan;
    }

    public static List<String> getKprip() {
        return kprip;
    }

    public static void setKprip(List<String> kprip) {
        LegalEP.kprip = kprip;
    }

    
   
   





    static  void getLegal()  throws JAXBException, IOException, ClassNotFoundException, SQLException
            
    {
  
     
        
        
        
        Class.forName("com.informix.jdbc.IfxDriver");
      
        String URL = "";
        Connection conn = DriverManager.getConnection(URL);


 

LS ls = new LS();
        List<LR> listLR = ls.getLR();
        //String GET_PAT = "select idappli, dtappli, title, ipcmclass from informix.ptappli  where idappli = 'X530660' ";
      String   skelb_data = Ut.getPublDate();
 skelb_data = JOptionPane.showInputDialog(null, "Skelbimo data, pvz., 20121227", skelb_data);  


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
        String  spcnation_nr =  null;
          String   spcnation_dt = null;
        String  spcregion_nr =  null;
          String   spcregion_dt = null;
        String l528 = null;
        String dtlegal = null;

               
        
 // ----------------------------  ecan MM9D epltMMla.txt  -----------------------------------       
        
 
  
        
    String    GET_PAT = "select ptappli.idappli ";
       GET_PAT += "from ptappli, agent ";
        GET_PAT += "where ptappli.idagent = agent.idagent and ptappli.extidpatent = ";

 String fnagent = null;
 String midnagent = null;
 String nmagent = null;
 
 
 
  Statement stmt = conn.createStatement();     

  int j = 0;
  if (ecan!=null)  {  
	while (j < ecan.size()) {
  
            String pat = ecan.get(j).toString();
            String GET_PATID = GET_PAT + "\"EP" + pat + "\"";
 
             
   ResultSet rs_ecan = stmt.executeQuery(GET_PATID);   
while ( rs_ecan.next())  {
             
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
            spcnation_nr =  null;
            spcnation_dt = null;
            spcregion_nr =  null;
            spcregion_dt = null;
             dtlegal = null;   
       
             idappli = rs_ecan.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }
    
    System.out.println(idappli);   
             String GET_ECANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " 
                     + "\"" + idappli + "\"" + " AND idoper='109' AND dtlegal is not null";   //idoper=?
      //  Statement st_ecandt = conn.createStatement();
        ResultSet rs_ecandt = stmt.executeQuery(GET_ECANDT);
  while (      rs_ecandt.next())     dtlegal = rs_ecandt.getString("dtlegal");
            
           
            
}//while   
            
          
     L500 l500 = new L500(); // list of additional attrs
          l500.setL501("LT");
          l500.setL502("MM9D");  
           l500.setL525(dtlegal);  
           LR lr = new LR();

       lr.setL500(l500);
//
       lr.setL001("EP");// <L001>   Country Code - ST.3 //??? EP - LT
       lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)

       lr.setL003(pat);  //extidpatent  be EP
lr.setL004("B");//<L004>   kind code of document number
        lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention

       lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)
        lr.setL008("REG");
       
        lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
i++;
        lr.setSequence(Integer.toString(i));// ???


        
       listLR.add(lr);
                
		j++; 
	}   //while ecan  
  }//if not null ecan 
      
        
        
        
        
        //  ---------------------------- eprip MG9D -----------------------------
        
  
        
        GET_PAT = "select ptappli.idappli ";
       GET_PAT += "from ptappli, agent ";
        GET_PAT += "where ptappli.idagent = agent.idagent and ptappli.extidpatent = ";

  fnagent = null;
  midnagent = null;
  nmagent = null;
 
 
 
 Statement  stmt2 = conn.createStatement();     

  j = 0;
  if (eprip!=null)  {  
	while (j < eprip.size()) {
  
            String pat = eprip.get(j).toString();
            String GET_PATID = GET_PAT + "\"EP" + pat + "\"";
 
             
   ResultSet rs_eprip = stmt2.executeQuery(GET_PATID);   
while ( rs_eprip.next())  {
             
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
            spcnation_nr =  null;
            spcnation_dt = null;
            spcregion_nr =  null;
            spcregion_dt = null;
             dtlegal = null;   
       
             idappli = rs_eprip.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }
    
    System.out.println(idappli);   
             String GET_ECANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " 
                     + "\"" + idappli + "\"" + " AND idoper='109PI' AND dtlegal is not null";   //idoper=?
        Statement st_epripdt = conn.createStatement();
        ResultSet rs_epripdt = st_epripdt.executeQuery(GET_ECANDT);
  while (      rs_epripdt.next())     dtlegal = rs_epripdt.getString("dtlegal");
            
           
            
}//while   
            
          
     L500 l500 = new L500(); // list of additional attrs
          l500.setL501("LT");
          l500.setL502("MG9D");  
           l500.setL525(dtlegal);  
           LR lr = new LR();

       lr.setL500(l500);
//
       lr.setL001("EP");// <L001>   Country Code - ST.3 //??? EP - LT
       lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
////lr.setL003("0592438");//<L003>   document number 
  //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
       lr.setL003(pat);  //extidpatent  be EP
lr.setL004("B");//<L004>   kind code of document number
        lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
//
////jei šio mėnesio (kai failas daromas mėnesiui pasibaigus) 25 diena išeiginė, tai artimiausia darbo diena
//
//
       lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
        lr.setL008("REG");
        //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
//
        lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
i++;
        lr.setSequence(Integer.toString(i));// ???


        
       listLR.add(lr);
                
		j++; 
	}   //while eprip  
  }//if not null eprip   
      
   // ------------------   MM4D  kcan  --------------------------------------------           
 
          
        GET_PAT = "select ptappli.idappli ";
       GET_PAT += "from ptappli, agent ";
        GET_PAT += "where ptappli.idagent = agent.idagent and ptappli.extidpatent = ";

  fnagent = null;
  midnagent = null;
  nmagent = null;
 
 
 
 Statement  stmt3 = conn.createStatement();     

  j = 0;
  if (kcan!=null)  {  
	while (j < kcan.size()) {
  
            String pat = kcan.get(j).toString();
            String GET_PATID = GET_PAT + "\"EP" + pat + "\"";
 
             
   ResultSet rs_kcan = stmt3.executeQuery(GET_PATID);   
while ( rs_kcan.next())  {
             
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
            spcnation_nr =  null;
            spcnation_dt = null;
            spcregion_nr =  null;
            spcregion_dt = null;
             dtlegal = null;   
       
             idappli = rs_kcan.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }
    
    System.out.println(idappli);   
             String GET_ECANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " 
                     + "\"" + idappli + "\"" + " AND idoper='109EA' AND dtlegal is not null";   //idoper=?
        Statement st_kcandt = conn.createStatement();
        ResultSet rs_kcandt = st_kcandt.executeQuery(GET_ECANDT);
  while (      rs_kcandt.next())     dtlegal = rs_kcandt.getString("dtlegal");
            
           
            
}//while   
            
          
     L500 l500 = new L500(); // list of additional attrs
          l500.setL501("LT");
          l500.setL502("MM4D");  
           l500.setL525(dtlegal);  
           LR lr = new LR();

       lr.setL500(l500);
//
       lr.setL001("EP");// <L001>   Country Code - ST.3 //??? EP - LT
       lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
////lr.setL003("0592438");//<L003>   document number 
  //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
       lr.setL003(pat);  //extidpatent  be EP
lr.setL004("B");//<L004>   kind code of document number
        lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
//
////jei šio mėnesio (kai failas daromas mėnesiui pasibaigus) 25 diena išeiginė, tai artimiausia darbo diena
//
//
       lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
        lr.setL008("REG");
        //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
//
        lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
i++;
        lr.setSequence(Integer.toString(i));// ???


        
       listLR.add(lr);
                
		j++; 
	}   //while kcan  
  }//if not null kcan
        
        
     // ----------  kprip  EP ab initio negaliojantys pagal 79 str.  MG4D   ----------------------
        
        
       GET_PAT = "select ptappli.idappli ";
       GET_PAT += "from ptappli, agent ";
        GET_PAT += "where ptappli.idagent = agent.idagent and ptappli.extidpatent = ";

  fnagent = null;
  midnagent = null;
  nmagent = null;
 
 
 
 Statement  stmt4 = conn.createStatement();     

  j = 0;
  if (kprip!=null)  {  
	while (j < kprip.size()) {
  
            String pat = kprip.get(j).toString();
            String GET_PATID = GET_PAT + "\"EP" + pat + "\"";
 
             
   ResultSet rs_kprip = stmt4.executeQuery(GET_PATID);   
while ( rs_kprip.next())  {
             
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
            spcnation_nr =  null;
            spcnation_dt = null;
            spcregion_nr =  null;
            spcregion_dt = null;
             dtlegal = null;   
       
             idappli = rs_kprip.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }
    
    System.out.println(idappli);   
             String GET_ECANDT = "SELECT MAX(bedat(dtlegal)) dtlegal FROM history WHERE idappli= " 
                     + "\"" + idappli + "\"" + " AND idoper='109EH' AND dtlegal is not null";   //idoper=?
        Statement st_kpripdt = conn.createStatement();
        ResultSet rs_kcandt = st_kpripdt.executeQuery(GET_ECANDT);
  while (      rs_kcandt.next())     dtlegal = rs_kcandt.getString("dtlegal");
            
           
            
}//while   
            
          
     L500 l500 = new L500(); // list of additional attrs
          l500.setL501("LT");
          l500.setL502("MG4D");  
           l500.setL525(dtlegal);  
           LR lr = new LR();

       lr.setL500(l500);
//
       lr.setL001("EP");// <L001>   Country Code - ST.3 //??? EP - LT
       lr.setL002("P");// <L002>   Indicator, if the record has filing  (F) or publication number (P)
////lr.setL003("0592438");//<L003>   document number 
  //      if(extidpatent!=null) lr.setL003(extidpatent); else  lr.setL003(extidappli); //????
       lr.setL003(pat);  //extidpatent  be EP
lr.setL004("B");//<L004>   kind code of document number
        lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
//
////jei šio mėnesio (kai failas daromas mėnesiui pasibaigus) 25 diena išeiginė, tai artimiausia darbo diena
//
//
       lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
        lr.setL008("REG");
        //<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
//
        lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
i++;
        lr.setSequence(Integer.toString(i));// ???


        
       listLR.add(lr);
                
		j++; 
	}   //while kprip  
        
  }//if not null krip      
          
        
   
 if (i==0) return;               
          
        
        
        
//----------------------- viso xml formavimas ----------------------
      
 ls.setCountry("LT");
        ls.setDeliveryDate(skelb_data);
        ls.setNoOfRecords(Integer.toString(i)); 

        JAXBContext jc = JAXBContext.newInstance("legal.lt.vinco");
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE legal-document SYSTEM \"legal-document-v1-0.dtd\">");
                   OutputStream os = new FileOutputStream("Legal_EP_" + skelb_data + ".xml");  
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(ls, os);  
                    m.marshal(ls, System.out);
                    System.out.println();  
                    os.close(); 
         
        conn.close();      
      
   
    
}
}
