/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lt.vinco.simple.Address;
import lt.vinco.simple.Address1;
import lt.vinco.simple.Addressbook;
import lt.vinco.simple.Agent;
import lt.vinco.simple.Agents;
import lt.vinco.simple.Applicant;
import lt.vinco.simple.Applicants;
import lt.vinco.simple.City;
import lt.vinco.simple.Country;
import lt.vinco.simple.Date;
import lt.vinco.simple.FirstName;
import lt.vinco.simple.Grantee;
import lt.vinco.simple.Grantees;
import lt.vinco.simple.Inventor;
import lt.vinco.simple.Inventors;
import lt.vinco.simple.LastName;
import lt.vinco.simple.MiddleName;
import lt.vinco.simple.Name;
import lt.vinco.simple.Nationality;
import lt.vinco.simple.Orgname;
import lt.vinco.simple.Residence;

/**
 *
 * @author vbatulevicius
 */
public class Ut {
    
 public static String getPublDate () {
 Calendar cal = Calendar.getInstance();
int dom = cal.get(Calendar.DAY_OF_MONTH);
 System.out.println("day of month: "+ dom);
if (dom<10) {
    cal.set(Calendar.DAY_OF_MONTH, 10);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        if (dow == 1) {
            cal.set(Calendar.DAY_OF_MONTH, 11);                  //jei sekmadienis 
        }
        if (dow == 7) {
            cal.set(Calendar.DAY_OF_MONTH, 12);                   //jei sestadienis 
        }
}
else if (dom > 25 ){ 
     cal.add(Calendar.MONTH, 1);
cal.set(Calendar.DAY_OF_MONTH, 10);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        if (dow == 1) {
            cal.set(Calendar.DAY_OF_MONTH, 11);                  //jei sekmadienis 
        }
        if (dow == 7) {
            cal.set(Calendar.DAY_OF_MONTH, 12);                   //jei sestadienis 
        }
      
}

else {
        cal.set(Calendar.DAY_OF_MONTH, 25);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        if (dow == 1) {
            cal.set(Calendar.DAY_OF_MONTH, 26);                  //jei sekmadienis 
        }
        if (dow == 7) {
            cal.set(Calendar.DAY_OF_MONTH, 27);                   //jei sestadienis 
        }
}       
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String skelb_data = sdf.format(cal.getTime());
        return skelb_data;
 }  
 
 public static void zipuoti(String skelb_data, String patt, String targ) {
   //zipavimas:
        File[] zipuojami = new File(".").listFiles();
       
        byte[] buf = new byte[1024];

        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targ));
            for (File failas : zipuojami) {
                if ( Pattern.matches(patt, failas.getName()) //paraiskos   
                         || Pattern.matches("\\S+9.dtd", failas.getName()) //dtd 
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
        
 }
 
 
 
 public static Agents getAgentsByIdappli (String idappli) throws ClassNotFoundException, SQLException{
     
       Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       
  Agents agents = new Agents();
    
 //Agentai ****************************
         String agentsql = "select extidappli, ptappli.idagent, fnagent, midnagent, nmagent, adagent, idtown, nmtown, idcountry"
                + " from ptappli, agent where ptappli.idagent=agent.idagent and idappli =" + "\"" + idappli + "\"";
        System.out.println("agentai: "+agentsql);
        Statement agentstmt = conn.createStatement();
        ResultSet agentrs = agentstmt.executeQuery(agentsql);
 
   // agentai

   
                    int ordagent = 0;
        while (agentrs.next()) {
           ordagent++;
                    String nmagent = null;
                    String midnagent = null;
                    String fnagent = null;
                    String idcountry = null;
                     String adagent = null;
                     
             fnagent = agentrs.getString("fnagent");
                     FirstName fnm = new FirstName();
                   
                       if (fnagent != null) {
                        fnagent = fnagent.trim();
                        fnagent = Perkodavimas.Perkoduoti(fnagent);
                         fnm.setvalue(fnagent);
                            
                     
                      adagent = agentrs.getString("adagent");
                    if (adagent != null) {
                        adagent = adagent.trim();
                        adagent = Perkodavimas.Perkoduoti(adagent);
                    } 
                    
               String      idtown = null;     
                      idtown = agentrs.getString("idtown");     
                    if (idtown != null) {
                        idtown = idtown.trim();
                        idtown = Perkodavimas.Perkoduoti(idtown);
                    } else {idtown = "";}
                      
                   String   nmtown = null;  
                    nmtown = agentrs.getString("nmtown");
                    if (nmtown != null) {
                        nmtown = nmtown.trim();
                        nmtown = Perkodavimas.Perkoduoti(nmtown);
                    }  else {nmtown = "";}
                
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

                    
                    idcountry = agentrs.getString("idcountry");

                    Agent agent = new Agent();
                    agent.setRepType("agent");
                    agent.setSequence(Integer.toString(ordagent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country ag_ctry = new Country();
                    ag_ctry.setvalue(idcountry);
                    adr.setCountry(ag_ctry);
                  // adr.getAddress1OrAddress2OrAddress3OrAddress4OrAddress5OrMailcodeOrPoboxOrRoomOrAddressFloorOrBuildingOrStreetOrCityOrCountyOrStateOrPostcodeOrCountryOrText().add(ag_ctry);
                    
                  Address1 adr1  = new Address1();
                  adr1.setvalue(adagent);
                  adr.setAddress1(adr1);
  //adr.getAddress1OrAddress2OrAddress3OrAddress4OrAddress5OrMailcodeOrPoboxOrRoomOrAddressFloorOrBuildingOrStreetOrCityOrCountyOrStateOrPostcodeOrCountryOrText().add(adr1);
                    

  City ct = new City();
  String c = idtown + " " + nmtown;
  ct.setvalue(c.trim());
  adr.setCity(ct);
 // adr.getAddress1OrAddress2OrAddress3OrAddress4OrAddress5OrMailcodeOrPoboxOrRoomOrAddressFloorOrBuildingOrStreetOrCityOrCountyOrStateOrPostcodeOrCountryOrText().add(ct);

  
                   

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
               }//if fnagent != null     
            System.out.println("agent::"+nmagent);     
        }// while
                                 
        agentstmt.close();
        agentrs.close();  
        conn.close();
 return agents;
 // Agentai END  ********************* 
        
 }
 
 public static lt.vinco.simple.Date  getDtPublByExtidpatent (String extidpatent) throws SQLException, ClassNotFoundException{
 
 Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
  
   String dtpub = null;
   lt.vinco.simple.Date pbrel = new Date();
      String GET_DTPUBLI = "select bedat(dtgrant) dtgr from ptappli where extidpatent = " + "\"" + extidpatent + "\"";
                         Statement stmtrspub = conn.createStatement();
                        ResultSet rspub = stmtrspub.executeQuery(GET_DTPUBLI);
                        while (rspub.next()) {
                            dtpub = rspub.getString("dtgr");
                            }
                        System.out.println("dtpub " +dtpub);
                         
                 if (dtpub!=null)  {
                     pbrel.setvalue(dtpub);
                        } else {  
                   pbrel.setvalue("unknown");       
                }
                 System.out.println("pbrel "+pbrel.getvalue());
  rspub.close();
 stmtrspub.close();
 conn.close();
     
 return pbrel;
 }
   
 
public static Grantees getGranteesByIdappli (String idappli) throws ClassNotFoundException, SQLException  {
    Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
   Grantees grantees = new Grantees();
                    String GET_GRANTEES = "select own.odowner,  o.nmowner, o.midnowner, o.fnowner, o.idcountry from own, owner o where idappli = " + "\"" + idappli + "\" and o.idowner = own.idowner order by own.odowner";
Statement stmt = conn.createStatement();
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
                        idcountry = rsgr.getString("idcountry");

                        Grantee grantee = new Grantee();
                        grantee.setSequence(Integer.toString(ordowner));
                        Addressbook adrb = new Addressbook();
                        Address adr = new Address();
                        Country gr_ctry = new Country();
                        gr_ctry.setvalue(idcountry);
                        adr.setCountry(gr_ctry);
                      //  adr.getAddress1OrAddress2OrAddress3OrAddress4OrAddress5OrMailcodeOrPoboxOrRoomOrAddressFloorOrBuildingOrStreetOrCityOrCountyOrStateOrPostcodeOrCountryOrText().add(gr_ctry);

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
System.out.println("owner::"+nmowner); 
                    }//while        grantees

return grantees;
} // getGrantees
 

public static Inventors getInventorsByIdappli(String idappli) throws SQLException, ClassNotFoundException{
    
     Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       Statement stmt = conn.createStatement();
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
                  //          nminventor = Perkodavimas.Perkoduoti(nminventor);
                        }


                        idcountry = rsinv.getString("idcountry");
                        
                        if (idcountry==null) idcountry = "";   //testuoju
                        

                        Inventor inv = new Inventor();   
                        inv.setSequence(Integer.toString(ordinvent));
                        Addressbook adrb = new Addressbook();
                        Address adr = new Address();
                        Country inv_ctry = new Country();
                        inv_ctry.setvalue(idcountry);
                        adr.setCountry(inv_ctry);
                                  Name nm = new Name();
                        nm.setvalue(nminventor); 
 System.out.println("nminventor: "+nminventor);
                      
                         adrb.setName(nm);

                        
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

return invs;

}//getInventorsByIdappli


public static Applicants getSpaApplicantsByIdappli(String idappli) throws SQLException, ClassNotFoundException{
    
     Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       Statement stmt = conn.createStatement();
Applicants applicants = new Applicants();
           
             
                //spa:
                 String GET_APPLICANTS = "select nmowner, nmtown, adowner, i.odinitapp, w.idcountry from ptappli p,initapply i, owner w where p.idappli= " + "\"" + idappli 
                        + "\" and i.idinitapp=w.idowner and p.idappli=i.idappli order by i.odinitapp";
                
             
              ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                                      
                    String idcountry = null;
                int    ordowner = rsapps.getInt("odinitapp");
                
               String     nmowner = rsapps.getString("nmowner");
                             if (nmowner != null) {
                        nmowner = nmowner.trim();
                                  }
                   
               String     nmtown = rsapps.getString("nmtown");
                             if (nmtown != null) {
                        nmtown = nmtown.trim();
                                  }   
                String     adowner = rsapps.getString("adowner");
                             if (adowner != null) {
                        adowner = adowner.trim();
                                  }                
                             
                    idcountry = rsapps.getString("idcountry");

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   //  ??????????????
                    
                    applicant.setAppType("applicant");
                    
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
                      adrb.setAddress(adr);
   Name nm = new Name();
   nm.setvalue(nmowner);
   nm.setNameType("natural");   //natural | legal
                    adrb.setName(nm);
                  Address1 ad1 = new Address1();
                  City ct = new City();
                  ct.setvalue(nmtown);
                  adr.setCity(ct);
               ad1.setvalue(adowner);
 adr.setAddress1(ad1);
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
                    
      
                    applicants.getApplicant().add(applicant);
                }//while        applicants
                
rsapps.close();
rsapps = null;
return applicants;
}//getSpaApplicantsByIdappli


public static Applicants getSpcApplicantsByIdappli(String idappli) throws SQLException, ClassNotFoundException{
    
     Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       Statement stmt = conn.createStatement();
Applicants applicants = new Applicants();
             
                String GET_APPLICANTS = "select nmowner, nmtown, adowner, o.odowner, w.idcountry from ptappli p,own o, owner w where p.idappli= " + "\"" + idappli 
                        + "\" and o.idowner=w.idowner and p.idappli=o.idappli order by o.odowner";
              ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                                      
                    String idcountry = null;
                int    ordowner = rsapps.getInt("odowner");
                
               String     nmowner = rsapps.getString("nmowner");
                             if (nmowner != null) {
                        nmowner = nmowner.trim();
                                  }
                   
               String     nmtown = rsapps.getString("nmtown");
                             if (nmtown != null) {
                        nmtown = nmtown.trim();
                                  }   
                String     adowner = rsapps.getString("adowner");
                             if (adowner != null) {
                        adowner = adowner.trim();
                                  }                
                             
                    idcountry = rsapps.getString("idcountry");

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   
                    
                    applicant.setAppType("applicant");
                    
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
                         adrb.setAddress(adr);
   Name nm = new Name();
   nm.setvalue(nmowner);
   nm.setNameType("natural");   //natural | legal
                    adrb.setName(nm);
                  Address1 ad1 = new Address1();
                  City ct = new City();
                  ct.setvalue(nmtown);
                  adr.setCity(ct);
    ad1.setvalue(adowner);
 adr.setAddress1(ad1);
                    applicant.getAddressbook().add(adrb);
                    applicant.setDesignation("all");
                    
                 Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                     nty.setCountry(natctr);
                     applicant.setNationality(nty);    
                    
                    
                  Residence residence = new Residence();
                    residence.setCountry(gr_ctry);  
                    applicant.setResidence(residence); 
                    
      
                    applicants.getApplicant().add(applicant);
                }//while        applicants
                
rsapps.close();
rsapps = null;
return applicants;
}//getSpcApplicantsByIdappli



 public static Agents getLtAgentsByIdappli (String idappli) throws ClassNotFoundException, SQLException{
     
       Class.forName("com.informix.jdbc.IfxDriver");
      String URL = "j";
       Connection conn = DriverManager.getConnection(URL);
       
  Agents agents = new Agents();
    
 //Agentai ****************************
         String agentsql = "select extidappli, ptappli.idagent, fnagent, midnagent, nmagent, adagent, idtown, nmtown, idcountry"
                + " from ptappli, agent where ptappli.idagent=agent.idagent and idappli =" + "\"" + idappli + "\"";
        System.out.println("agentai: "+agentsql);
        Statement agentstmt = conn.createStatement();
        ResultSet agentrs = agentstmt.executeQuery(agentsql);
 
   // agentai

   
                    int ordagent = 0;
        while (agentrs.next()) {
           ordagent++;
                    String nmagent = null;
                    String midnagent = null;
                    String fnagent = null;
                    String idcountry = null;
                     String adagent = null;
                     
             fnagent = agentrs.getString("fnagent");
                     FirstName fnm = new FirstName();
                   
                       if (fnagent != null) {
                        fnagent = fnagent.trim();
                        fnagent = Perkodavimas.Perkoduoti(fnagent);
                         fnm.setvalue(fnagent);
                            
                     
                      adagent = agentrs.getString("adagent");
                    if (adagent != null) {
                        adagent = adagent.trim();
                        adagent = Perkodavimas.Perkoduoti(adagent);
                    } 
                    
               String      idtown = null;     
                      idtown = agentrs.getString("idtown");     
                    if (idtown != null) {
                        idtown = idtown.trim();
                        idtown = Perkodavimas.Perkoduoti(idtown);
                    } else {idtown = "";}
                      
                   String   nmtown = null;  
                    nmtown = agentrs.getString("nmtown");
                    if (nmtown != null) {
                        nmtown = nmtown.trim();
                        nmtown = Perkodavimas.Perkoduoti(nmtown);
                    }  else {nmtown = "";}
                
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

                    
                    idcountry = agentrs.getString("idcountry");

                    Agent agent = new Agent();
                    agent.setRepType("agent");
                    agent.setSequence(Integer.toString(ordagent));
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country ag_ctry = new Country();
                    ag_ctry.setvalue(idcountry);
                    adr.setCountry(ag_ctry);
                      
                  Address1 adr1  = new Address1();
                  adr1.setvalue(adagent);
                  adr.setAddress1(adr1);
  
  City ct = new City();
  String c = idtown + " " + nmtown;
  ct.setvalue(c.trim());
  adr.setCity(ct);
  
                   

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
               }//if fnagent != null     
            System.out.println("agent::"+nmagent);     
        }// while
                                 
        agentstmt.close();
        agentrs.close();  
        conn.close();
 return agents;
 // Agentai END  ********************* 
        
 }
 
 
public static Applicants getLtSpcApplicantsByIdappli(String idappli) throws SQLException, ClassNotFoundException{
    
     Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       Statement stmt = conn.createStatement();
Applicants applicants = new Applicants();
           
            
               
                String GET_APPLICANTS = "select nmowner, nmtown, adowner, o.odowner, w.idcountry from ptappli p,own o, owner w where p.idappli= " + "\"" + idappli 
                        + "\" and o.idowner=w.idowner and p.idappli=o.idappli order by o.odowner";
              ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                                      
                    String idcountry = null;
                int    ordowner = rsapps.getInt("odowner");
                
               String     nmowner = rsapps.getString("nmowner");
                             if (nmowner != null) {
                        nmowner = nmowner.trim();
                                  }
                   
               String     nmtown = rsapps.getString("nmtown");
                             if (nmtown != null) {
                        nmtown = nmtown.trim();
                                  }   
                String     adowner = rsapps.getString("adowner");
                             if (adowner != null) {
                        adowner = adowner.trim();
                                  }                
                             
                    idcountry = rsapps.getString("idcountry");

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   //  ??????????????
                    
                    applicant.setAppType("applicant");
                    
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
                       adrb.setAddress(adr);
   Name nm = new Name();
   nm.setvalue(nmowner);
   nm.setNameType("natural");   //natural | legal
                    adrb.setName(nm);
                  Address1 ad1 = new Address1();
                  City ct = new City();
                  ct.setvalue(nmtown);
                  adr.setCity(ct);
            ad1.setvalue(adowner);
 adr.setAddress1(ad1);
                    applicant.getAddressbook().add(adrb);
                    applicant.setDesignation("all");
                    
                 Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                     nty.setCountry(natctr);
                     applicant.setNationality(nty);    
                    
                    
                  Residence residence = new Residence();
                    residence.setCountry(gr_ctry);  
                    applicant.setResidence(residence); 
                    
      
                    applicants.getApplicant().add(applicant);
                }//while        applicants
                
rsapps.close();
rsapps = null;
return applicants;
}//getLtSpcApplicantsByIdappli
 
 
 

public static Applicants getLtSpaApplicantsByIdappli(String idappli) throws SQLException, ClassNotFoundException{
    
     Class.forName("com.informix.jdbc.IfxDriver");
       String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       Statement stmt = conn.createStatement();
Applicants applicants = new Applicants();
           
            
      String GET_APPLICANTS = "select nmowner, nmtown, adowner, i.odinitapp, w.idcountry from ptappli p,initapply i, owner w where p.idappli= " + "\"" + idappli 
                        + "\" and i.idinitapp=w.idowner and p.idappli=i.idappli order by i.odinitapp";           
                
                
                
              ResultSet rsapps = stmt.executeQuery(GET_APPLICANTS);

                while (rsapps.next()) {

                    int idowner = 0;
                                      
                    String idcountry = null;
                int    ordowner = rsapps.getInt("odinitapp");
                
               String     nmowner = rsapps.getString("nmowner");
                             if (nmowner != null) {
                        nmowner = nmowner.trim();
                                  }
                   
               String     nmtown = rsapps.getString("nmtown");
                             if (nmtown != null) {
                        nmtown = nmtown.trim();
                                  }   
                String     adowner = rsapps.getString("adowner");
                             if (adowner != null) {
                        adowner = adowner.trim();
                                  }                
                             
                    idcountry = rsapps.getString("idcountry");

                    Applicant applicant = new Applicant();
                    applicant.setSequence(Integer.toString(ordowner));   //  ??????????????
                    
                    applicant.setAppType("applicant");
                    
                    Addressbook adrb = new Addressbook();
                    Address adr = new Address();
                    Country gr_ctry = new Country();
                    gr_ctry.setvalue(idcountry);
                    adr.setCountry(gr_ctry);
                           adrb.setAddress(adr);
   Name nm = new Name();
   nm.setvalue(nmowner);
   nm.setNameType("natural");   //natural | legal
                    adrb.setName(nm);
                  Address1 ad1 = new Address1();
                  City ct = new City();
                  ct.setvalue(nmtown);
                  adr.setCity(ct);
   ad1.setvalue(adowner);
 adr.setAddress1(ad1);
                   applicant.getAddressbook().add(adrb);
                    applicant.setDesignation("all");
                    
                 Nationality nty = new Nationality();
                      Country natctr = new Country();
                      natctr.setvalue("");
                     nty.setCountry(natctr);
                     applicant.setNationality(nty);    
                    
                    
                  Residence residence = new Residence();
                    residence.setCountry(gr_ctry);  
                    applicant.setResidence(residence); 
                    
      
                    applicants.getApplicant().add(applicant);
                }//while        applicants
                
rsapps.close();
rsapps = null;
return applicants;
}//getLtSpaApplicantsByIdappli


public static lt.vinco.simple.Date  getLtDtPublByExtidpatent (String extidpatent) throws SQLException, ClassNotFoundException{
 
 Class.forName("com.informix.jdbc.IfxDriver");
        String URL = "";
       Connection conn = DriverManager.getConnection(URL);
       
      
   String dtpub = null;
    String idappli = null;
       String GET_IDAPPLI = "select idappli from ptappli where extidpatent  = \"" + extidpatent + "\"";
       Statement stmtid = conn.createStatement();
                        ResultSet rsid = stmtid.executeQuery(GET_IDAPPLI);
                        while (rsid.next()) {
                            idappli = rsid.getString("idappli").trim();
                            }
                        System.out.println("idappli " +idappli);
       stmtid.close();
   
   lt.vinco.simple.Date pbrel = new Date();
     // String GET_DTPUBLI = "select bedat(dtgrant) dtgr from ptappli where extidpatent = " + "\"" + extidpatent + "\"";
 String GET_DTPUBLI =   "select bedat(dtpubli) publdate from ptappli,publication,history, gazette where nosect='2' and ptappli.idappli=publication.idappli and publication.idappli=history.idappli"
                            + " and publication.yygazette=gazette.yygazette and publication.nogazette=gazette.nogazette"
                            + " and idoper='143' and history.idappli = \"" + idappli + "\"";  
      
      
      
                        
                        Statement stmtrspub = conn.createStatement();
                        ResultSet rspub = stmtrspub.executeQuery(GET_DTPUBLI);
                        while (rspub.next()) {
                            dtpub = rspub.getString("publdate");
                            }
                        System.out.println("dtpub " +dtpub);
                         
                 if (dtpub!=null)  {
                     pbrel.setvalue(dtpub);
                        } else {  
                   pbrel.setvalue("unknown");       
                }
                 System.out.println("pbrel "+pbrel.getvalue());
  rspub.close();
 stmtrspub.close();
 conn.close();
     
 return pbrel;
 }

    static void getLtspclist() throws SQLException, ClassNotFoundException {
     try {
         Class.forName("com.informix.jdbc.IfxDriver");
         String URL = "";
         Connection conn = DriverManager.getConnection(URL);
         List<String> visispa = new ArrayList<>();
         List<String> visispc = new ArrayList<>();
         
         String spcs = "select idappli, dtappli, stitle, engtitle, ipcmclass, extidappli, bedat(dtappli) dtapp  from informix.ptappli where  extidappli like 'PA%'";  //-- kdpatent = 7   
         
         System.out.println("spcs: "+spcs);
         Statement stmt = conn.createStatement();
         ResultSet rs_spcs = stmt.executeQuery(spcs);
         
         while (rs_spcs.next()) {
             String idappli = rs_spcs.getString("idappli");
             String extidappli = rs_spcs.getString("extidappli");
             System.out.println("spcs: "+extidappli);
             if (extidappli != null) visispa.add(extidappli.trim());
             String addinfo = "select value  from addinfo where exfield = 2 and idappli = \"" + idappli  + "\"";
             System.out.println("addinfo: "+addinfo);
             Statement stmta = conn.createStatement();
             ResultSet rs_addinfo = stmta.executeQuery(addinfo);
             while (rs_addinfo.next()){
                 String spc = rs_addinfo.getString("value");
                     if (spc != null) visispc.add(extidappli+"\t"+spc.trim());
                 System.out.println("value: "+spc);
                 
             }
             stmta.close();
             rs_addinfo.close();
             
         }//while
         
         System.out.println(visispa.toString());
         System.out.println(visispc.toString());
         
         writetofile("visispalt.txt", visispa);
         writetofile("visispclt.txt", visispc);
         
         
         rs_spcs.close();
         conn.close();
     } //getltspclist
     catch (IOException ex) {
         Logger.getLogger(Ut.class.getName()).log(Level.SEVERE, null, ex);
     }
           

    }
    
    
   static void getEpspclist() throws SQLException, ClassNotFoundException {
     try {
         Class.forName("com.informix.jdbc.IfxDriver");
        String URL = "";
         
         Connection conn = DriverManager.getConnection(URL);
         List<String> visispa = new ArrayList<>();
         List<String> visispc = new ArrayList<>();
         
         String spcs = "select idappli, dtappli, stitle, engtitle, ipcmclass, extidappli, bedat(dtappli) dtapp  from informix.ptappli where  extidappli like 'PA%'";   
         
         System.out.println("spcs: "+spcs);
         Statement stmt = conn.createStatement();
         ResultSet rs_spcs = stmt.executeQuery(spcs);
         
         while (rs_spcs.next()) {
             String idappli = rs_spcs.getString("idappli");
             String extidappli = rs_spcs.getString("extidappli");
             System.out.println("spcs: "+extidappli);
             if (extidappli != null) visispa.add(extidappli.trim());
             String addinfo = "select value  from addinfo where exfield = 2 and idappli = \"" + idappli  + "\"";
             System.out.println("addinfo: "+addinfo);
             Statement stmta = conn.createStatement();
             ResultSet rs_addinfo = stmta.executeQuery(addinfo);
             while (rs_addinfo.next()){
                 String spc = rs_addinfo.getString("value");
                 if (spc != null) visispc.add(extidappli+"\t"+spc.trim());
                 System.out.println("value: "+spc);
                 
             }
             stmta.close();
             rs_addinfo.close();
             
         }//while
         
         System.out.println(visispa.toString());
         System.out.println(visispc.toString());
         
         writetofile("visispaep.txt", visispa);
         writetofile("visispcep.txt", visispc);
          
         rs_spcs.close();
         conn.close();
     } //getltspclist
     catch (IOException ex) {
         Logger.getLogger(Ut.class.getName()).log(Level.SEVERE, null, ex);
     }
        
  } 
    
    
    
    
    
    
   private static void writetofile (String filename, List<String> list) throws IOException {
   Path newFilePath = Paths.get(filename);
        
       if (Files.exists(newFilePath)) {
                
                    Files.delete(newFilePath);
                    Files.createFile(newFilePath);
                
       } else { Files.createFile(newFilePath); }
      
              
    for (String sp : list)   {
               
                    Files.write(newFilePath, ("(21)\t"+sp+System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
   
    }         
       
       return;
   }
    

 
}//class
