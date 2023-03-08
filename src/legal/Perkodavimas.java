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
public class Perkodavimas {
    
    public static String Perkoduoti (String t)  {
     t = t.replace("ë", "ė"); 
    t = t.replace("ø", "ų");
    t = t.replace("û", "ū");
    t = t.replace("ð", "š");
    t = t.replace("è", "č");
    t = t.replace("þ", "ž");
    
        t = t.replace("Û", "Ū");
         t = t.replace("Ë", "Ė");
          t = t.replace("Þ", "Ž");
           t = t.replace("á", "į");
            t = t.replace("à", "ą");
             t = t.replace("æ", "ę");
     
             t = t.replace("Á", "Į");
           t = t.replace("È", "Č");
            t = t.replace("Ð", "Š");
             t = t.replace("À", "Ą");
      t = t.replace("Ø", "Ų");
       t = t.replace("Æ", "Ę");
      
       t = t.replace("â€“", "-");
       //  &quot;
     
    return t;
    }
    
   public static String readFile(String pathname) throws IOException {

    File file = new File(pathname);
    StringBuilder fileContents = new StringBuilder((int)file.length());
    Scanner scanner = new Scanner(file);
    String lineSeparator = System.getProperty("line.separator");

    try {
        while(scanner.hasNextLine()) {        
            fileContents.append(scanner.nextLine() + lineSeparator);
        }
        return fileContents.toString();
    } finally {
        scanner.close();
    }
} 
    
 public static String getExtension(File f)
{
String ext = null;
String s = f.getName();
int i = s.lastIndexOf('.');
if (i > 0 && i < s.length() - 1)
ext = s.substring(i+1).toLowerCase();
if(ext == null)
return "";
return ext;
}  
   
   
   
   
    
} //class



   