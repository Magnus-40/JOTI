/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.List;

/**
 *  Form for listing and handling the uncontacted countries.
 * @author Andrew Whitelaw
 */
public class FrmMissingInAction extends javax.swing.JDialog {

    /**
     * Creates new form MissingInAction
     *
     * @param parent Parent object
     * @param modal Boolean value for Modality. True for Modal
     */
    public FrmMissingInAction(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.pack();
        setLocationRelativeTo(null);
        this.UpdateText();
        this.setTitle("Countries with no contacts made");
    }

    private void UpdateText() {
        String africaList = "";
        String americaList = "";
        String asiaList = "";
        String australasiaList = "";
        String europeList = "";
        String worldList = "";
        List<String> myArr;
        ContactManager myContacts = new ContactManager();
        //Build Strings
        myArr = myContacts.getMissingInAction("");
       
        for (String countryName : myArr) {
            worldList = worldList + countryName + "\n";
        }
        myArr = myContacts.getMissingInAction("Africa");
        this.numAfrica.setText(Integer.toString(myArr.size()));
        for (String countryName : myArr) {
            africaList = africaList + countryName + "\n";
        }
        myArr = myContacts.getMissingInAction("Americas");
        this.numAmericas.setText(Integer.toString(myArr.size()));
        for (String countryName : myArr) {
            americaList = americaList + countryName + "\n";
        }
        myArr = myContacts.getMissingInAction("Asia");
        this.numAsia.setText(Integer.toString(myArr.size()));
        for (String countryName : myArr) {
            asiaList = asiaList + countryName + "\n";
        }
        myArr = myContacts.getMissingInAction("Australasia");
        this.numAustralasia.setText(Integer.toString(myArr.size()));
        for (String countryName : myArr) {
            australasiaList = australasiaList + countryName + "\n";
        }
        myArr = myContacts.getMissingInAction("Europe");
        this.numEurope.setText(Integer.toString(myArr.size()));
        for (String countryName : myArr) {
            europeList = europeList + countryName + "\n";
        }
        //fill panels with text
        this.textAfrica.setText(africaList);
        this.textAmericas.setText(americaList);
        this.textAsia.setText(asiaList);
        this.textAustralasia.setText(australasiaList);
        this.textEurope.setText(europeList);
        this.textWorld.setText(worldList);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        tabbedWorld = new javax.swing.JScrollPane();
        textWorld = new javax.swing.JTextArea();
        tabbedAfrica = new javax.swing.JScrollPane();
        textAfrica = new javax.swing.JTextArea();
        tabbedAmericas = new javax.swing.JScrollPane();
        textAmericas = new javax.swing.JTextArea();
        tabbedAsia = new javax.swing.JScrollPane();
        textAsia = new javax.swing.JTextArea();
        tabbedAustralasia = new javax.swing.JScrollPane();
        textAustralasia = new javax.swing.JTextArea();
        tabbedEurope = new javax.swing.JScrollPane();
        textEurope = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        numAfrica = new javax.swing.JTextField();
        numAmericas = new javax.swing.JTextField();
        numAsia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        numAustralasia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        numEurope = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        textWorld.setColumns(20);
        textWorld.setRows(5);
        tabbedWorld.setViewportView(textWorld);

        tabbedPane.addTab("World", tabbedWorld);

        textAfrica.setColumns(20);
        textAfrica.setRows(5);
        tabbedAfrica.setViewportView(textAfrica);

        tabbedPane.addTab("Africa", tabbedAfrica);

        textAmericas.setColumns(20);
        textAmericas.setRows(5);
        tabbedAmericas.setViewportView(textAmericas);

        tabbedPane.addTab("Americas", tabbedAmericas);

        textAsia.setColumns(20);
        textAsia.setRows(5);
        tabbedAsia.setViewportView(textAsia);

        tabbedPane.addTab("Asia", tabbedAsia);

        textAustralasia.setColumns(20);
        textAustralasia.setRows(5);
        tabbedAustralasia.setViewportView(textAustralasia);

        tabbedPane.addTab("Australasia", tabbedAustralasia);

        textEurope.setColumns(2);
        textEurope.setRows(5);
        tabbedEurope.setViewportView(textEurope);

        tabbedPane.addTab("Europe", tabbedEurope);

        jLabel1.setText("Africa");

        jLabel2.setText("Americas");

        numAmericas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numAmericasActionPerformed(evt);
            }
        });

        jLabel3.setText("Asia");

        jLabel4.setText("Australasia");

        jLabel5.setText("Europe");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(33, 33, 33))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(19, 19, 19)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numEurope, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numAustralasia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numAsia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numAfrica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numAmericas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(numAfrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(numAmericas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(numAsia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(numAustralasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(numEurope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        UpdateText();
    }//GEN-LAST:event_formWindowActivated

    private void numAmericasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numAmericasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numAmericasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMissingInAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMissingInAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMissingInAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMissingInAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmMissingInAction dialog = new FrmMissingInAction(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField numAfrica;
    private javax.swing.JTextField numAmericas;
    private javax.swing.JTextField numAsia;
    private javax.swing.JTextField numAustralasia;
    private javax.swing.JTextField numEurope;
    private javax.swing.JScrollPane tabbedAfrica;
    private javax.swing.JScrollPane tabbedAmericas;
    private javax.swing.JScrollPane tabbedAsia;
    private javax.swing.JScrollPane tabbedAustralasia;
    private javax.swing.JScrollPane tabbedEurope;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JScrollPane tabbedWorld;
    private javax.swing.JTextArea textAfrica;
    private javax.swing.JTextArea textAmericas;
    private javax.swing.JTextArea textAsia;
    private javax.swing.JTextArea textAustralasia;
    private javax.swing.JTextArea textEurope;
    private javax.swing.JTextArea textWorld;
    // End of variables declaration//GEN-END:variables
}
