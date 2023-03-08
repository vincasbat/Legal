/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legal;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author vbatulevicius
 */
public class Perkodavimas2 {
    
    public static String Perkoduoti (String t)  {
     t = t.replace("Ä—", "ė"); 
    t = t.replace("Å³", "ų");
    t = t.replace("Å«", "ū");
    t = t.replace("Å¡", "š");
    t = t.replace("Ä", "č");
    t = t.replace("Å¾", "ž");
    
        t = t.replace("Åª", "Ū");
         t = t.replace("Ä–", "Ė");
          t = t.replace("Å½", "Ž");
           t = t.replace("Ä¯", "į");
            t = t.replace("Ä…", "ą");
             t = t.replace("Ä™", "ę");
     
             t = t.replace("Ä®", "Į");
           t = t.replace("ÄŒ", "Č");
            t = t.replace("Å ", "Š");
             t = t.replace("Ä„", "Ą");
      t = t.replace("Å²", "Ų");
       t = t.replace("Ä˜", "Ę");
      
        t = t.replace("Ã¼", "ü");
        t = t.replace("Ã¨", "è");
     t = t.replace("Ã§", "ç");
     t = t.replace("Ã¤", "ä");
     t = t.replace("Ã¶", "ö");  
       t = t.replace("Ã©", "é");  
       t = t.replace("Å¯", "ů");  
       
      t = t.replace("â€“", "-");
   
     
     
    return t;
    }
    
   
    
} //class



   