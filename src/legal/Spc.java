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
import java.util.Iterator;
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
public class Spc {
    
    private static  List<String> lt_spc = null;

    public static List<String> getLt_spc() {
        return lt_spc;
    }

    public static void setLt_spc(List<String> lt_spc) {
        Spc.lt_spc = lt_spc;
    }
    
    
    private static  List<String> ep_spc = null;

    public static List<String> getEp_spc() {
        return ep_spc;
    }

    public static void setEp_spc(List<String> ep_spc) {
        Spc.ep_spc = ep_spc;
    }
    
    


    static void getLT() throws JAXBException, IOException, ClassNotFoundException, SQLException {

        Class.forName("com.informix.jdbc.IfxDriver");
       
        String URL = "";



        Connection conn = DriverManager.getConnection(URL);




        LS ls = new LS();
    String   skelb_data = Ut.getPublDate();
     skelb_data = JOptionPane.showInputDialog(null, "Skelbimo data, pvz., 20121227", skelb_data);  
     
         int j= 0; 
    if(lt_spc!=null)        
     while (j < lt_spc.size()) {       
        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, dtappli, title, engtitle, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, bedat(dtadvert) dtadv, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and extidappli =  " +  "\"" + lt_spc.get(j).toString() + "\"";
        List<LR> listLR = ls.getLR();
 
        
        
     Statement st_publdate = conn.createStatement(); 


        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(GET_PAT);
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
        
       

          while (resultSet.next()) {

            idappli = "";
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
            l528 = null;


            String dtadvert = null;

            dtadvert = resultSet.getString("dtadv");

            idappli = resultSet.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }
            dtappli = resultSet.getString("dtappli").replace("-", "").replace(" ", "").replace(".", "");
            ipcmclass = resultSet.getString("ipcmclass");
            if (ipcmclass != null) {
                ipcmclass = ipcmclass.trim();
            }

            title = resultSet.getString("title").trim();
            title = Perkodavimas.Perkoduoti(title);
            engtitle = resultSet.getString("engtitle").trim();


            extidpatent = resultSet.getString("extidpatent");    //----- visada null? Cia SPC extidpatent
            if (extidpatent != null) {
                extidpatent = extidpatent.trim();
            }

            extidappli = resultSet.getString("extidappli");    // ----- PA 2004 006     
            if (extidappli != null) {
                extidappli = extidappli.trim();
            }

            spcnation = resultSet.getString("spcnation");
            if (spcnation != null) {
                spcnation = spcnation.trim();
                spcnation_nr = spcnation.substring(0, spcnation.length() - 12);
                spcnation_dt = spcnation.substring(spcnation.length() - 10).replace("-", "").replace(" ", "").replace(".", "");

            }
            spcregion = resultSet.getString("spcregion");
            if (spcregion != null) {
                spcregion = spcregion.trim(); // l008 = "REG";    //???????
                spcregion_nr = spcregion.substring(0, spcregion.length() - 12);
                spcregion_dt = spcregion.substring(spcregion.length() - 10).replace("-", "").replace(" ", "").replace(".", "");
            }







            String GET_ADDINFO = "SELECT exfield, value FROM addinfo where idappli = " + "\"" + idappli + "\"";
            Statement st_adinfo = conn.createStatement();
            ResultSet rs_addinfo = st_adinfo.executeQuery(GET_ADDINFO);
            l528 = null;
            String spcterm = null;
            String spcstart = null;
            while (rs_addinfo.next()) {
                int exfield = rs_addinfo.getInt("exfield");
                String value = Perkodavimas.Perkoduoti(rs_addinfo.getString("value"));

                switch (exfield) {
                    case 1:
                        l528 = value.trim();
                        break;
                    case 2:

                        spcnumber = value.trim();
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

            while (rs_spcpat.next()) {
                extidpatent = rs_spcpat.getString("extidpatent");      //Cia ne SPC, o jo patento extidpatent
                if (extidpatent != null) {
                    extidpatent = extidpatent.trim();
                }
                dtptexpi = rs_spcpat.getString("dtpex");
            }
            rs_spcpat.close();
            
 //******************************    ++++++++++       
    //Randame paskutinį įvykį:   
            Integer odhisto = 0;
      String HISTO = "SELECT odhisto, idoper, bedat(dtlegal) dtlg from history where idappli = \"" + idappli + "\"";
            System.out.println(HISTO);
             System.out.println(extidpatent);
            Statement st_histo = conn.createStatement();
            ResultSet rs_histo = st_histo.executeQuery(HISTO);
            String hdtlegal = null;
            String idoper = null;
            while (rs_histo.next()) {
               
                idoper = rs_histo.getString("idoper").trim();
                hdtlegal = rs_histo.getString("dtlg");
                System.out.println(idoper);
                System.out.println(hdtlegal);
                 


              

                switch (idoper) {
                    case "S126P":
                        odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "468S":                            
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "454S":
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "S120":
                      odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                    break;
                    case "412S":
                        odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "S126":
                        odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "S128":
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "S120P":
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "S125":     
                        odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                        
                    case "S125P":     
                      odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odgisto: " + odhisto);
                        break;
                        
                    case "457S":     
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odhisto: " + odhisto);
                        break;
                        
                         case "S109":     
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odhisto: " + odhisto);
                        break;
                             
                              case "S109A":     
                       odhisto = rs_histo.getInt("odhisto");
                        System.out.println("odhisto: " + odhisto);
                        break;
                             
                    

                    default:
                        continue;
                }//switch              

            }//while
               
rs_histo.close(); 
            
//*******************************      +++++++++++
 // Atrenkame reikiamus įvykius iš history:
            String HIST = "SELECT idoper, bedat(dtlegal) dtlg from history where idappli = \"" + idappli + "\"" + " and odhisto = " + odhisto;
            System.out.println(HIST);
             System.out.println(extidpatent);
            Statement st_hist = conn.createStatement();
            ResultSet rs_hist = st_hist.executeQuery(HIST);
             hdtlegal = null;
             idoper = null;
             
   L500 l500 = null;          
   String l509 = null;          
            while (rs_hist.next()) {
                idoper = rs_hist.getString("idoper").trim();
                hdtlegal = rs_hist.getString("dtlg");
                System.out.println(idoper);
                System.out.println(hdtlegal);


                // if (idoper="kd") ... then SPCX, dtlegal



                 l500 = new L500(); // list of additional attrs
                String GET_PUBL_DATE = null;
                switch (idoper) {
                    case "S126P":
                        l008 = "SPCZ";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                        break;
                    case "468S":                            
                        l008 = "SPCO";    // licence
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                        break;
                    case "454S":
                        l008 = "SPCT";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                      
                 
                   String      GET_GRANTEES = "select own.odowner,  o.nmowner, o.midnowner, o.fnowner, o.idcountry from own, owner o where idappli = " +  "\"" + idappli +  "\" and o.idowner = own.idowner order by own.odowner";
  Statement st_gr = conn.createStatement();
 ResultSet rsgr = st_gr.executeQuery(GET_GRANTEES);  //  ???????????? stmt ?
 while ( rsgr.next())  { //????
 int idowner = 0;
  int ordowner = 0;
  String nmowner = null;
  String midnowner = null;
  String fnowner = null;
  String idcountry = null; 
     
// idowner = rsgr.getInt("idowner");
 ordowner = rsgr.getInt("odowner");
 nmowner =  rsgr.getString("nmowner");
 if (nmowner!=null) { nmowner =  nmowner.trim(); nmowner = Perkodavimas.Perkoduoti(nmowner);  } else nmowner = "";
 
 midnowner =  rsgr.getString("midnowner");
if (midnowner!=null) { midnowner=midnowner.trim(); midnowner = Perkodavimas.Perkoduoti(midnowner); } else midnowner = "";
 
 fnowner =  rsgr.getString("fnowner"); //.trim();
 if(fnowner!=null) { fnowner = fnowner.trim(); fnowner = Perkodavimas.Perkoduoti(fnowner); } else fnowner = "";
 idcountry =  rsgr.getString("idcountry");  
 l509 = nmowner + " " + midnowner + fnowner + ", " + idcountry; 
 l509 = l509.replace("  ", " ");  l509 = l509.replace("  ", " "); l509 = l509.replace(" ,", ","); 
 }//while                      
 l500.setL509(l509);   
 l500.setL510("CHANGE OF OWNER DETAILS");
 rsgr.close(); st_gr.close();
                        break;
                      
                        
                        
                        
                    case "S120":
                        l008 = "SPCF";
                        l500.setL511(extidappli);
                    GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='19' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S120' and history.idappli = \"" + idappli + "\"";
                    break;
                    case "412S":
                        l008 = "SPCM";  //agent
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                        break;
                    case "S126":
                        l008 = "SPCG";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='20' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S126' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "S128":
                        l008 = "SPCW";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='22' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S128' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "S120P":
                        l008 = "SPCE";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                         }
                        GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='19' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S120P' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "S125":     
                        l008 = "SPCR";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='21' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S125' and history.idappli = \"" + idappli + "\"";
                        break;
                        
                    case "S125P":     
                        l008 = "SPCY";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                        break;
                        
                         case "S109A":     //******************************************************  spcx  ************
                        l008 = "SPCX";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                       break;
                        
                             
                              case "S109":     
                        l008 = "SPCL";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                       break;
                        
                    case "457S":     
                        l008 = "SPC+457S";    // perdavimas/assignment
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = null;
                        break;

                    default:
                        continue;
                }//switch              


 //Ištraukiame publikacijos datą:
                 String publdate = null;
        if(GET_PUBL_DATE!=null){        
 ResultSet   rs_publdate = st_publdate.executeQuery(GET_PUBL_DATE);
                 while (rs_publdate.next()) {
                publdate = rs_publdate.getString("publdate").trim();            
            }   rs_publdate.close();
  System.out.println("publdate: "+publdate);              

        } else publdate = skelb_data;

                //***************          
               
                l500.setL512(dtappli);//<L512>  filing date of an SPC
                l500.setL513(dtptexpi);//<L513>  expiry date of a patent (not the SPC, see L523)
      
      if(!idoper.equalsIgnoreCase("S120"))          
                l500.setL523(spcterm);//ar cia pratesimo pabaiga?spcterm??extension date (in case protection of patent or product based on a patent has been extended after maximum date of patent protection)
              
                
                l500.setL528(l528);// <L528> product name, protected in an SPC or an extension of term
                
      if (idoper.equalsIgnoreCase("S128") || idoper.equalsIgnoreCase("S125") || idoper.equalsIgnoreCase("S125P") || idoper.equalsIgnoreCase("454S"))          
                l500.setL525(hdtlegal);
                
                
                if (spcnation != null) {
                    l500.setL529(spcnation_nr);  //national reg. number
                    l500.setL530(spcnation_dt); //date of the national registration
         //           l500.setL531("LT");
                }
                if (spcregion != null) {
                    l500.setL532(spcregion_nr);  //regional reg. number
                    l500.setL533(spcregion_dt); //date of the regional registration
           //         l500.setL531("EU");
                }

                LR lr = new LR();

                lr.setL500(l500);
                lr.setL001("LT"); // kadangi dirbame tik su Lietuvos spc    
                lr.setL003(extidpatent);
//lr.setL003("0592438");//<L003>   document number 
                lr.setL002("P");
                if (spcnumber == null) l500.setL511(extidappli);  // -----------  ???
                    
               
//lr.setL004("E");//<L004>   kind code of document number
                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
//jei šio mėnesio (kai failas daromas mėnesiui pasibaigus) 25 diena išeiginė, tai artimiausia darbo diena
                //   lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                
               lr.setL007(publdate);
                
                
                lr.setL008(l008);//<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(j+1));// ???
                listLR.add(lr);
//***********              
         } //while rs_hist.next
            rs_hist.close();   
rs_histo.close();
        } //while  
         j++;
        st_publdate.close();
     }//while one record
   
    

        
        //----------------------- viso xml formavimas ----------------------
        //<Header line with number of records and delivery data>   e.g. <LS country="CN" NoOfRecords="7789" deliveryDate="20080201"> 
        ls.setCountry("LT");
        ls.setDeliveryDate(skelb_data);
        ls.setNoOfRecords(Integer.toString(j));  //????? tikslinti  

        JAXBContext jc = JAXBContext.newInstance("legal.lt.vinco");
        Marshaller m = jc.createMarshaller();
       // m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE legal-document SYSTEM \"legal-document-v1-0.dtd\">");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
 // OutputStream os = new FileOutputStream("SPC_" + skelb_data + "_LT.xml");
         
         
m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE legal-document SYSTEM \"legal-document-v1-0.dtd\">");
                   OutputStream os = new FileOutputStream("SPC_" + skelb_data + "_LT.xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(ls, os);  
                    m.marshal(ls, System.out);
                    System.out.println();  
                    os.close(); 


        conn.close();
       // os.close();
    } //get LT 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
// ====================   EP LIST ===========      
 
    static void getEPlist() throws JAXBException, IOException, ClassNotFoundException, SQLException {

        Class.forName("com.informix.jdbc.IfxDriver");
       
        String URL = "";

       



        Connection conn = DriverManager.getConnection(URL);


        
        
        
 String   skelb_data = Ut.getPublDate();
  skelb_data = JOptionPane.showInputDialog(null, "Skelbimo data, pvz., 20121227", skelb_data);
 
 Statement st_publdate = conn.createStatement(); 
 
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
        String spc_ext_term = null;
 LS ls = new LS();       
 int j= 0;       
if(ep_spc!=null)        
     while (j < ep_spc.size()) {  
       if(ep_spc.get(j)!=null) {
  //        String pat = age.get(j).toString();
   //String GET_PATID = GET_PAT + "\"" + pat + "\"";      
    //atrinkti pagal spc par. nr.    

        String GET_PAT = "select ptappli.idappli, extidappli, idpatent, extidpatent, dtappli, title, engtitle, ";
        GET_PAT += "dtptexpi, extidrecept, dtnextpay, bedat(dtadvert) dtadv, offerlic, spcnation, spcregion, k_ptappli, kdpatent, ";
        GET_PAT += " ipcmclass ";
        GET_PAT += "from ptappli ";
        GET_PAT += "where kdpatent=7 and extidappli =  " +  "\"" + ep_spc.get(j).toString() + "\"";
        //and dtappli > (MDY(MONTH(TODAY), 1, YEAR(TODAY)) - 10 UNITS YEAR) and dtappli < MDY(MONTH(TODAY), 1, YEAR(TODAY)) ";

        //MDY(MONTH(TODAY), 1, YEAR(TODAY)) - 1 UNITS MONTH  as pirma_diena, MDY(MONTH(TODAY), 1, YEAR(TODAY)) - 1 as paskutine_diena

        
        List<LR> listLR = ls.getLR();
        //String GET_PAT = "select idappli, dtappli, title, ipcmclass from informix.ptappli  where idappli = 'X530660' ";

        
      
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(GET_PAT);  
    while ( resultSet.next())  { 

        
      
            idappli = "";
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
            l528 = null;
spc_ext_term = null;

            String dtadvert = null;

            dtadvert = resultSet.getString("dtadv");

            idappli = resultSet.getString("idappli");
            if (idappli != null) {
                idappli = idappli.trim();
            }
            dtappli = resultSet.getString("dtappli").replace("-", "").replace(" ", "").replace(".", "");
            ipcmclass = resultSet.getString("ipcmclass");
            if (ipcmclass != null) {
                ipcmclass = ipcmclass.trim();
            }

            title = resultSet.getString("title").trim();
            title = Perkodavimas.Perkoduoti(title);
            engtitle = resultSet.getString("engtitle").trim();

//            idpatent = resultSet.getString("idpatent");
//            if (idpatent != null) {
//                idpatent = idpatent.trim();
//            }

            extidpatent = resultSet.getString("extidpatent");    //----- visada null? Cia SPC extidpatent
            if (extidpatent != null) {
                extidpatent = extidpatent.trim();
            }

            extidappli = resultSet.getString("extidappli");    // ----- PA 2004 006     
            if (extidappli != null) {
                extidappli = extidappli.trim();
            }

            spcnation = resultSet.getString("spcnation");
            if (spcnation != null) {
                spcnation = spcnation.trim();
                spcnation_nr = spcnation.substring(0, spcnation.length() - 12);
                spcnation_dt = spcnation.substring(spcnation.length() - 10).replace("-", "").replace(" ", "").replace(".", "");

            }
            spcregion = resultSet.getString("spcregion");
            if (spcregion != null) {
                spcregion = spcregion.trim(); // l008 = "REG";    //???????
                if(spcregion.length()> 12) 
                    spcregion_nr = spcregion.substring(0, spcregion.length() - 12);
                if(spcregion.length()>= 10)
                spcregion_dt = spcregion.substring(spcregion.length() - 10).replace("-", "").replace(" ", "").replace(".", "");
            }




            String GET_ADDINFO = "SELECT exfield, value FROM addinfo where idappli = " + "\"" + idappli + "\"";
            Statement st_adinfo = conn.createStatement();
            ResultSet rs_addinfo = st_adinfo.executeQuery(GET_ADDINFO);
            l528 = null;
            String spcterm = null;
            String spcstart = null;
            while (rs_addinfo.next()) {
                int exfield = rs_addinfo.getInt("exfield");
                String value = Perkodavimas.Perkoduoti(rs_addinfo.getString("value"));

                switch (exfield) {
                    case 1:
                        l528 = value.trim();
                        break;
                    case 2:

                        spcnumber = value.trim();
                        break;
                    case 3:
                        spcterm = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;
                    case 4:
                        spcstart = value.replace("-", "").replace(" ", "").replace(".", "");
                        break;
                        
                         case 5:
   
                        break;
                    case 7:
                        spc_ext_term  = value.replace("-", "").replace(" ", "").replace(".", "");   
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

            while (rs_spcpat.next()) {
                extidpatent = rs_spcpat.getString("extidpatent");      //Cia ne SPC, o jo patento extidpatent
                if (extidpatent != null) {
                    extidpatent = extidpatent.trim();
                }
                dtptexpi = rs_spcpat.getString("dtpex");
            }
            rs_spcpat.close();
            
 //>>>>>>>>>>>>>>>>>>>>>>>>>>>>   
 List<Integer> istorija = new ArrayList<Integer>();
   String HISTO = "SELECT odhisto, idoper, bedat(dtlegal) dtlg from history where idappli = \"" + idappli + "\"";
            System.out.println(HISTO);
             System.out.println(extidpatent);
            Statement st_histo = conn.createStatement();
            ResultSet rs_histo = st_histo.executeQuery(HISTO);
            Integer odhisto = 0;
            String idoper = null;
            
            
            while (rs_histo.next()) {
                            
                
                idoper = rs_histo.getString("idoper").trim();
      
                switch (idoper) {
                     case "129HS":     // jei keiciamas galiojimo terminas
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "S126P":
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                        break;
                    case "468S":
                            odhisto = rs_histo.getInt("odhisto");
                            istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                    
                       break;
                    case "454S":
                       odhisto = rs_histo.getInt("odhisto");
                       istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
 
                        
                        break;
                    case "S120":
                         odhisto = rs_histo.getInt("odhisto");
                         istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                       
                     break;
                    case "412S":
                         odhisto = rs_histo.getInt("odhisto");
                         istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
               
                         
                         break;
                    case "S126":
                         odhisto = rs_histo.getInt("odhisto");
                         istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                       
                          break;
                    case "S128":
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                        
                         break;
                    case "S120P":
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
    
                        break;
                    case "S125":   
                      case "S129":  //spc paraišką atšaukia pareiškėjas
                          odhisto = rs_histo.getInt("odhisto");
                          istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                        
                                          break;
                        
                    case "S125P":  
                           odhisto = rs_histo.getInt("odhisto");
                           istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
                     
                      break;
                        
                    case "457S":     
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
 
                         break;
                        
                         case "S109L":     
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
 
                         break;
                             
                              case "S109E":     
                        odhisto = rs_histo.getInt("odhisto");
                        istorija.add(odhisto);
                        System.out.println("odgisto: " + odhisto);
 
                         break;

                    default:
                        continue;
                }//switch              
            }//while histo         
            
          System.out.println("istorija: " + istorija.toString());  
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

if(!Forma.ist){

Integer od = istorija.get(istorija.size()-1);
istorija = new ArrayList<Integer>();
istorija.add(od);
}

  for (Integer ist : istorija) { 
    

  
 // Atrenkame reikiamus įvykius iš history:
            String HIST = "SELECT idoper, bedat(dtlegal) dtlg from history where idappli = \"" + idappli + "\"" + " and odhisto = " + ist;
            System.out.println(HIST);
             System.out.println(extidpatent);
            Statement st_hist = conn.createStatement();
            ResultSet rs_hist = st_hist.executeQuery(HIST);
            String hdtlegal = null;
             idoper = null;
            String l509 = null;  //owner  / assignee
            String l517 = null;   //agent
            String l522 = null;   //lic
            
            while (rs_hist.next()) {
                            
                
                idoper = rs_hist.getString("idoper").trim();
                if(idoper.equalsIgnoreCase("S126P")) {
                
                }
                        
                hdtlegal = rs_hist.getString("dtlg");
                System.out.println(idoper);
                System.out.println(hdtlegal);


                // if (idoper="kd") ... then SPCX, dtlegal



                L500 l500 = new L500(); // list of additional attrs
                String GET_PUBL_DATE = null;
                String GET_GRANTEES = null;
                switch (idoper) {
                    
                   case "S109L":
                        l008 = "SPCL";  //ep spc laps
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);     
                        }
               
                        break;
                       
                        case "S109E":
                        l008 = "SPCX";  //ep spc expiry
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);     
                        }
               
                        break;
                    
                    
                    
                     case "129HS":
                        l008 = "SPCD";  //keiciama galiojimo data
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);     
                        }
               
                        break;
                    
                    
                    case "S126P":
                        l008 = "SPCZ";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);     
                        }
                        GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='38' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S126P' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "468S":
                        l008 = "SPCO";   //license       
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='42' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='468S' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "454S":
                        l008 = "SPCT";    //change of owner
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='43' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='454S' and history.idappli = \"" + idappli + "\"";
                         GET_GRANTEES = "select own.odowner,  o.nmowner, o.midnowner, o.fnowner, o.idcountry from own, owner o where idappli = " +  "\"" + idappli +  "\" and o.idowner = own.idowner order by own.odowner";
  Statement st_gr = conn.createStatement();
 ResultSet rsgr = st_gr.executeQuery(GET_GRANTEES);  //  ???????????? stmt ?
 while ( rsgr.next())  { //????
 int idowner = 0;
  int ordowner = 0;
  String nmowner = null;
  String midnowner = null;
  String fnowner = null;
  String idcountry = null; 
     
// idowner = rsgr.getInt("idowner");
 ordowner = rsgr.getInt("odowner");
 nmowner =  rsgr.getString("nmowner");
 if (nmowner!=null) { nmowner =  nmowner.trim(); nmowner = Perkodavimas.Perkoduoti(nmowner);  } else nmowner = "";
 
 midnowner =  rsgr.getString("midnowner");
if (midnowner!=null) { midnowner=midnowner.trim(); midnowner = Perkodavimas.Perkoduoti(midnowner); } else midnowner = "";
 
 fnowner =  rsgr.getString("fnowner"); //.trim();
 if(fnowner!=null) { fnowner = fnowner.trim(); fnowner = Perkodavimas.Perkoduoti(fnowner); } else fnowner = "";
 idcountry =  rsgr.getString("idcountry");  
 l509 = nmowner + " " + midnowner + fnowner + ", " + idcountry; 
 l509 = l509.replace("  ", " ");  l509 = l509.replace("  ", " "); l509 = l509.replace(" ,", ","); 
 }//while                      
 l500.setL509(l509);   
 l500.setL510("CHANGE OF OWNER DETAILS");
 rsgr.close(); st_gr.close();
                        break;
                    case "S120":
                        l008 = "SPCF";
                        l500.setL511(extidappli);
                    GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='37' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S120' and history.idappli = \"" + idappli + "\"";
                    break;
                    case "412S":
                        l008 = "SPCM";   //agent
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='43' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='412S' and history.idappli = \"" + idappli + "\"";
                     String   GET_AGENT = "select fnagent, midnagent, nmagent, idcountry from ptappli, agent where ptappli.idagent = agent.idagent and ptappli.idappli = \"" + idappli + "\"";
  Statement st_ga = conn.createStatement();
 ResultSet rsa = st_ga.executeQuery(GET_AGENT);  
 while ( rsa.next())  { //????

  int ordowner = 0;
  String nmagent = null;
  String midnagent = null;
  String fnagent = null;
  String idcountry = null; 
     
// idowner = rsgr.getInt("idowner");
// ordowner = rsgrs.getInt("odowner");
 nmagent =  rsa.getString("nmagent");
 if (nmagent!=null) { nmagent =  nmagent.trim(); nmagent = Perkodavimas.Perkoduoti(nmagent);  } else nmagent = "";
 
 midnagent =  rsa.getString("midnagent");
if (midnagent!=null) { midnagent=midnagent.trim(); midnagent = Perkodavimas.Perkoduoti(midnagent); } else midnagent = "";
 
 fnagent =  rsa.getString("fnagent"); //.trim();
 if(fnagent!=null) { fnagent = fnagent.trim(); fnagent = Perkodavimas.Perkoduoti(fnagent); } else fnagent = "";
 idcountry =  rsa.getString("idcountry");  
 l517 = nmagent + " " + midnagent + fnagent + ", " + idcountry; 
 l517 = l517.replace("  ", " ");  l517 = l517.replace("  ", " "); l517 = l517.replace(" ,", ",");
 }//while                      
 l500.setL517(l517); 
     rsa.close(); st_ga.close();                        
                         
                         
                         
                         
                         break;
                    case "S126":
                        l008 = "SPCG";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='38' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S126' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "S128":
                        l008 = "SPCW";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='40' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S128' and history.idappli = \"" + idappli + "\"";
                        break;
                    case "S120P":
                        l008 = "SPCE";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                         }
                        GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='37' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S120P' and history.idappli = \"" + idappli + "\"";
    
                        break;
                    case "S125":   
                      case "S129":  //spc paraišką atšaukia pareiškėjas
                        l008 = "SPCR";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='39' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S125' and history.idappli = \"" + idappli + "\"";
                        break;
                        
                    case "S125P":     
                        l008 = "SPCY";
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                        GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='39' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='S125P' and history.idappli = \"" + idappli + "\"";
                        break;
                        
                    case "457S":     
                        l008 = "SPCA";   //SPC+??_457S_assingnment
                        if (spcnumber != null) {
                            l500.setL511(extidappli + ", " +spcnumber);
                        }
                         GET_PUBL_DATE = "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='41' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='457S' and history.idappli = \"" + idappli + "\"";
                 //---------      
                         
                  GET_GRANTEES = "select own.odowner,  o.nmowner, o.midnowner, o.fnowner, o.idcountry from own, owner o where idappli = " +  "\"" + idappli +  "\" and o.idowner = own.idowner order by own.odowner";
  Statement st_grs = conn.createStatement();
 ResultSet rsgrs = st_grs.executeQuery(GET_GRANTEES);  
 while ( rsgrs.next())  { //????
 int idowner = 0;
  int ordowner = 0;
  String nmowner = null;
  String midnowner = null;
  String fnowner = null;
  String idcountry = null; 
     
// idowner = rsgr.getInt("idowner");
// ordowner = rsgrs.getInt("odowner");
 nmowner =  rsgrs.getString("nmowner");
 if (nmowner!=null) { nmowner =  nmowner.trim(); nmowner = Perkodavimas.Perkoduoti(nmowner);  } else nmowner = "";
 
 midnowner =  rsgrs.getString("midnowner");
if (midnowner!=null) { midnowner=midnowner.trim(); midnowner = Perkodavimas.Perkoduoti(midnowner); } else midnowner = "";
 
 fnowner =  rsgrs.getString("fnowner"); //.trim();
 if(fnowner!=null) { fnowner = fnowner.trim(); fnowner = Perkodavimas.Perkoduoti(fnowner); } else fnowner = "";
 idcountry =  rsgrs.getString("idcountry");  
 l509 = nmowner + " " + midnowner + fnowner + ", " + idcountry; 
 l509 = l509.replace("  ", " ");  l509 = l509.replace("  ", " "); l509 = l509.replace(" ,", ","); 
 }//while                      
 l500.setL509(l509); 
     rsgrs.close(); st_grs.close();                    
                 //---------- 
                         break;

                    default:
                        continue;
                }//switch              


 //Ištraukiame publikacijos datą:
   String publdate = null;
   if(GET_PUBL_DATE!=null) {
 ResultSet   rs_publdate = st_publdate.executeQuery(GET_PUBL_DATE);
                 while (rs_publdate.next()) {
                publdate = rs_publdate.getString("publdate").trim();            
            }   rs_publdate.close();
   }//if 
   else {publdate = skelb_data;}           
  System.out.println("publdate: "+publdate);              



                //***************          
               
                l500.setL501("LT"); // if L008 is "REG " then country code of own office , e.g. "AT"   ?????
  l500.setL502(l008);
                l500.setL512(dtappli);//<L512>  filing date of an SPC
                l500.setL513(dtptexpi);//<L513>  expiry date of a patent (not the SPC, see L523)
      
      if(!idoper.equalsIgnoreCase("S120"))          
                l500.setL523(spcterm);//ar cia pratesimo pabaiga?spcterm??extension date (in case protection of patent or product based on a patent has been extended after maximum date of patent protection)
        
      if(idoper.equalsIgnoreCase("S126P")&&(spc_ext_term!=null))          
                l500.setL523(spc_ext_term);
      
      
                
                l500.setL528(l528);// <L528> product name, protected in an SPC or an extension of term
                

      
       if (!(idoper.equalsIgnoreCase("S120") || idoper.equalsIgnoreCase("S126")))        // -------------   ?????????????? jei ne  SPCF, SPCG
                l500.setL525(hdtlegal);
                
                
                if (spcnation != null) {
                    l500.setL529(spcnation_nr);  //national reg. number
                    l500.setL530(spcnation_dt); //date of the national registration
        
                }
                if (spcregion != null) {
                    l500.setL532(spcregion_nr);  //regional reg. number
                    l500.setL533(spcregion_dt); //date of the regional registration
      
                }

                LR lr = new LR();

                lr.setL500(l500);
                lr.setL001("EP"); // kadangi dirbame tik su Lietuvos spc    
                lr.setL003(extidpatent.substring(2));
                lr.setL002("P");
                if (spcnumber == null)     l500.setL511(extidappli);  
                  
                

                lr.setL005("PI");//<L005>   type of protection "UM" = utility model; "PI" = patent of invention
//jei šio mėnesio (kai failas daromas mėnesiui pasibaigus) 25 diena išeiginė, tai artimiausia darbo diena
                //   lr.setL007(skelb_data);//<L007>   PRS date (publication date of legal event)//Biuletenio isleidimo data?
                
               lr.setL007(publdate);
                
                
                lr.setL008("REG");//<L008>   PRS code - e.g. according ST.17 ; if L001 is code of regional patent  authority or "WO"  then "REG "
                lr.setL013("N");//<L013>   normaly "N" for new data, "D" if former sent data should be deleted
                i++;
                lr.setSequence(Integer.toString(i));// ???
                listLR.add(lr);


            } //while rs_hist.next
            
            
         
            
            
            rs_hist.close();   
rs_histo.close(); 


}//for (Integer ist : istorija)

//if(i>5) break;

    }//while   one record
    j++;
     }//if ep_spc.get(j) ne null
        } //while  List
       
    // }// while (ist.hasNext())    
        st_publdate.close();


        //----------------------- viso xml formavimas ----------------------
      
        ls.setCountry("LT");
        ls.setDeliveryDate(skelb_data);
        ls.setNoOfRecords(Integer.toString(i));  //????? tikslinti  

        JAXBContext jc = JAXBContext.newInstance("legal.lt.vinco");
        Marshaller m = jc.createMarshaller();
      
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
 //       OutputStream os = new FileOutputStream("SPC_" + skelb_data + "_EP_LT.xml");
m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE legal-document SYSTEM \"legal-document-v1-0.dtd\">");
                   OutputStream os = new FileOutputStream("SPC_" + skelb_data + "_EP_LT.xml");  // +doc number
     os.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8")));              
                    m.marshal(ls, os);  
                    m.marshal(ls, System.out);
                    System.out.println();  
                    os.close(); 
        conn.close();
     
        
     
        
    } //get EPlist
        
 //  ==========  EPLIST pabaiga  ====================   
    
    
    
}//class
