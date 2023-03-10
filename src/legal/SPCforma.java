/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legal;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vbatulevicius
 */
public class SPCforma extends javax.swing.JFrame {

    /**
     * Creates new form SPCforma
     */
    public SPCforma() {
        URL imgUrl = null;
        ImageIcon imgIcon = null;

        imgUrl = Forma.class.getResource("res/ico.gif");
        imgIcon = new ImageIcon(imgUrl);
        Image img = imgIcon.getImage();
        this.setIconImage(img);
        initComponents();
        setLocationRelativeTo(null);
         lblSudaromas.setVisible(false);
          lblSudaromasEp.setVisible(false);
       //   taSpcep.setText("SPC paraiškos:");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnVisiSpcLt = new javax.swing.JButton();
        lblSudaromas = new javax.swing.JLabel();
        btnSpcLtPar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taSpcLt = new javax.swing.JTextArea();
        txtParBySpcLt = new javax.swing.JTextField();
        btnParBySpcLt = new javax.swing.JButton();
        btnIsvalytiLt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnSpcEpPar = new javax.swing.JButton();
        btnXmlEp = new javax.swing.JButton();
        lblSudaromasEp = new javax.swing.JLabel();
        txtParBySpc = new javax.swing.JTextField();
        btnParBySpc = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taSpcep = new javax.swing.JTextArea();
        btnIsvalytiEp = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taIn = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        taOut = new javax.swing.JTextArea();
        btnConvCit = new javax.swing.JButton();

        setTitle("Papildomos apsaugos liudijimai");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnVisiSpcLt.setText("XML LT");
        btnVisiSpcLt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisiSpcLtActionPerformed(evt);
            }
        });

        lblSudaromas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSudaromas.setForeground(new java.awt.Color(102, 102, 255));
        lblSudaromas.setText("Sudaromas XMLas...");

        btnSpcLtPar.setText("LT SPC paraiškos Nr.");
        btnSpcLtPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpcLtParActionPerformed(evt);
            }
        });

        taSpcLt.setEditable(false);
        taSpcLt.setColumns(20);
        taSpcLt.setRows(5);
        taSpcLt.setText("LT SPC paraiškos:");
        jScrollPane1.setViewportView(taSpcLt);

        btnParBySpcLt.setText("Par. Nr. by SPC Nr.");
        btnParBySpcLt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParBySpcLtActionPerformed(evt);
            }
        });

        btnIsvalytiLt.setText("Išvalyti");
        btnIsvalytiLt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIsvalytiLtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(txtParBySpcLt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnParBySpcLt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnVisiSpcLt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSpcLtPar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnIsvalytiLt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblSudaromas, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSpcLtPar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVisiSpcLt)
                        .addGap(8, 8, 8)
                        .addComponent(btnIsvalytiLt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSudaromas))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtParBySpcLt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnParBySpcLt))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSpcEpPar.setText("EP SPC paraiškos Nr.");
        btnSpcEpPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpcEpParActionPerformed(evt);
            }
        });

        btnXmlEp.setText("XML EP");
        btnXmlEp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXmlEpActionPerformed(evt);
            }
        });

        lblSudaromasEp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSudaromasEp.setForeground(new java.awt.Color(102, 102, 255));
        lblSudaromasEp.setText("Sudaromas XML failas...");

        btnParBySpc.setText("Par. Nr. pagal SPC Nr.");
        btnParBySpc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParBySpcActionPerformed(evt);
            }
        });

        taSpcep.setEditable(false);
        taSpcep.setColumns(18);
        taSpcep.setRows(5);
        taSpcep.setText("EP SPC paraiškos:");
        taSpcep.setAutoscrolls(false);
        jScrollPane2.setViewportView(taSpcep);

        btnIsvalytiEp.setText("Išvalyti");
        btnIsvalytiEp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIsvalytiEpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtParBySpc))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnParBySpc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSpcEpPar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblSudaromasEp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIsvalytiEp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXmlEp, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSpcEpPar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXmlEp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIsvalytiEp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSudaromasEp))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtParBySpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnParBySpc))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("LT");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("EP");

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/legal/res/vincasoft5.png"))); // NOI18N

        taIn.setColumns(20);
        taIn.setRows(5);
        jScrollPane3.setViewportView(taIn);

        taOut.setColumns(20);
        taOut.setRows(5);
        jScrollPane4.setViewportView(taOut);

        btnConvCit.setText("Konvertuoti citavimus");
        btnConvCit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvCitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(btnConvCit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConvCit)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVisiSpcLtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisiSpcLtActionPerformed
      
        try {  
             lblSudaromas.setVisible(true);
            // tik LT spc
            Spc.getLT();
             lblSudaromas.setVisible(false);
        } catch (JAXBException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        }
    Toolkit.getDefaultToolkit().beep();    
        Object[] options = {"Gerai!"};
            int n = JOptionPane.showOptionDialog(SPCforma.this, //get outer class
                    "Darbas baigtas.",
                    "Vincas Batulevičius",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, // icon,

                    options,
                    options[0]);
            
    }//GEN-LAST:event_btnVisiSpcLtActionPerformed

    private void btnSpcEpParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpcEpParActionPerformed
        // TODO add your handling code here:
    String inputValue = JOptionPane.showInputDialog("EP SPC paraiškos Nr., pvz., PA 2012 012");
  if(inputValue==null || inputValue=="") return; 
    taSpcep.setText(taSpcep.getText() + "\n" + inputValue);
        if (Spc.getEp_spc() == null) {
            List<String> epspc = new ArrayList<>();
            epspc.add(inputValue);
            Spc.setEp_spc(epspc);
        } else {
            Spc.getEp_spc().add(inputValue);
        }
        System.out.println(inputValue);
        System.out.println(Spc.getEp_spc().toString());    
           
    }//GEN-LAST:event_btnSpcEpParActionPerformed

    private void btnXmlEpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXmlEpActionPerformed
        // TODO add your handling code here:
        
  try {  
             lblSudaromasEp.setVisible(true);
            
           Spc.getEPlist();   
             lblSudaromasEp.setVisible(false);
        } catch (JAXBException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(Forma.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Superpatentininkas", JOptionPane.ERROR_MESSAGE);
        }
    Toolkit.getDefaultToolkit().beep();    
        Object[] options = {"Gerai!"};
            int n = JOptionPane.showOptionDialog(SPCforma.this, //get outer class
                    "Darbas baigtas.",
                    "Vincas Batulevičius",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, // icon,

                    options,
                    options[0]);      
        
    }//GEN-LAST:event_btnXmlEpActionPerformed

    private void btnParBySpcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParBySpcActionPerformed
        try {
            // TODO add your handling code here:
            
             
            Class.forName("com.informix.jdbc.IfxDriver");
        

            String URL = "";
            Connection conn = DriverManager.getConnection(URL);
       
        
        String extidpatent = txtParBySpc.getText();
         String GET_ECANDT = "SELECT extidappli FROM addinfo, ptappli WHERE addinfo.idappli = ptappli.idappli  and exfield = 2 and value  = " 
                     + "\"" + extidpatent + "\"";  
         System.out.println(GET_ECANDT);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(GET_ECANDT);
  while (     rs.next()    )
  {    
      System.out.println(rs.getString("extidappli"));
      txtParBySpc.setText(rs.getString("extidappli")); 
  
  }
 conn.close();
     } catch (SQLException ex) {
            Logger.getLogger(SPCforma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SPCforma.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
    }//GEN-LAST:event_btnParBySpcActionPerformed

    private void btnSpcLtParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpcLtParActionPerformed
         String inputValue = JOptionPane.showInputDialog("LT SPC paraiškos Nr., pvz., PA 2004 001");
     if(inputValue==null || inputValue=="") return; 
         taSpcLt.setText(taSpcLt.getText() + "\n" + inputValue);
        if (Spc.getLt_spc() == null) {
            List<String> ltspc = new ArrayList<>();
            ltspc.add(inputValue);
            Spc.setLt_spc(ltspc);
        } else {
            Spc.getLt_spc().add(inputValue);
        }
        System.out.println(inputValue);
        System.out.println(Spc.getLt_spc().toString());
    }//GEN-LAST:event_btnSpcLtParActionPerformed

    private void btnParBySpcLtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParBySpcLtActionPerformed
       try {
         Class.forName("com.informix.jdbc.IfxDriver");
          String URL = "";
           Connection conn = DriverManager.getConnection(URL);
       String extidpatent = txtParBySpcLt.getText();
         String GET_ECANDT = "SELECT extidappli FROM addinfo, ptappli WHERE addinfo.idappli = ptappli.idappli  and exfield = 2 and value  = " 
                     + "\"" + extidpatent + "\"";  
         System.out.println(GET_ECANDT);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(GET_ECANDT);
  while (     rs.next()    )
  {    
      System.out.println(rs.getString("extidappli"));
      txtParBySpcLt.setText(rs.getString("extidappli")); 
  
  }
 conn.close();
     } catch (SQLException ex) {
            Logger.getLogger(SPCforma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SPCforma.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }//GEN-LAST:event_btnParBySpcLtActionPerformed

    private void btnIsvalytiLtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIsvalytiLtActionPerformed
        // TODO add your handling code here:
        taSpcLt.setText("LT SPC paraiškos:");
        Spc.setLt_spc(null);
        txtParBySpcLt.setText("");
    }//GEN-LAST:event_btnIsvalytiLtActionPerformed

    private void btnIsvalytiEpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIsvalytiEpActionPerformed
        // TODO add your handling code here:
         taSpcep.setText("EP SPC paraiškos:");
        Spc.setEp_spc(null);
        txtParBySpc.setText("");
    }//GEN-LAST:event_btnIsvalytiEpActionPerformed

    private void btnConvCitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvCitActionPerformed
        // TODO add your handling code here:
         String t = taIn.getText();
        t = Perkodavimas2.Perkoduoti(t);

        taOut.setText(t);
        
    }//GEN-LAST:event_btnConvCitActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SPCforma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SPCforma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SPCforma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SPCforma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SPCforma().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConvCit;
    private javax.swing.JButton btnIsvalytiEp;
    private javax.swing.JButton btnIsvalytiLt;
    private javax.swing.JButton btnParBySpc;
    private javax.swing.JButton btnParBySpcLt;
    private javax.swing.JButton btnSpcEpPar;
    private javax.swing.JButton btnSpcLtPar;
    private javax.swing.JButton btnVisiSpcLt;
    private javax.swing.JButton btnXmlEp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSudaromas;
    private javax.swing.JLabel lblSudaromasEp;
    private javax.swing.JTextArea taIn;
    private javax.swing.JTextArea taOut;
    private javax.swing.JTextArea taSpcLt;
    private javax.swing.JTextArea taSpcep;
    private javax.swing.JTextField txtParBySpc;
    private javax.swing.JTextField txtParBySpcLt;
    // End of variables declaration//GEN-END:variables
}
