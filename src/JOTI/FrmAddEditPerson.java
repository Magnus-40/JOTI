/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Form for adding a person to the database or editing an existing person.
 *
 * @author Andrew Whitelaw
 */
public class FrmAddEditPerson extends javax.swing.JDialog {

    private static PersonManager myPeople;
    private static Person myPerson;
    boolean update;
    String newSection;

    /**
     * Creates new form AddPerson and changes look depending upon whether or not
     * a Person object has been supplied.
     *
     * @param parent Parent Frame object
     * @param modal Boolean value true for a modal dialog box
     * @param aPerson Person object. Required if editing otherwise null.
     */
    public FrmAddEditPerson(java.awt.Frame parent, boolean modal, Person aPerson) {
        super(parent, modal);
        myPerson = aPerson;
        myPeople = new PersonManager();
        this.pack();
        this.setLocationRelativeTo(null);
        initComponents();
        this.updateSectionCombo();
        if (this.sectionCombo.getItemCount() > 0) {
            this.sectionCombo.setSelectedIndex(0);
        }
        //Change form according to Person object                
        if (myPerson == null) {
            this.update = false;
            this.setTitle("Add a new person to the database");
            this.btnOK.setText("Save");
        } else {
            this.update = true;
            this.setTitle("Edit existing person");
            this.btnOK.setText("Update");
            this.surName.setText(myPerson.getSurName());
            this.firstName.setText(myPerson.getForeName());
            this.sectionCombo.setSelectedItem(myPerson.getMySection().toString());
        }

    }

    FrmAddEditPerson() {
        super();
    }

    private void updateSectionCombo() {
        this.sectionCombo.removeAllItems();
        new SectionsManager().getSectionNames().forEach((str) -> {
            this.sectionCombo.addItem(str);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        firstName = new javax.swing.JTextField();
        surName = new javax.swing.JTextField();
        sectionCombo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnNewSection = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        firstName.setToolTipText("The first name of the JOTI participant");
        firstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameActionPerformed(evt);
            }
        });
        firstName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                firstNameFocusLost(evt);
            }
        });

        surName.setToolTipText("The surname of the JOTI participant");
        surName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                surNameFocusLost(evt);
            }
        });

        jLabel1.setText("First Name");

        jLabel2.setText("Surname");

        jLabel3.setText("Section");

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnNewSection.setLabel("New Section");
        btnNewSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(firstName)
                    .addComponent(surName)
                    .addComponent(sectionCombo, 0, 184, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNewSection)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnNewSection))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNewSection.getAccessibleContext().setAccessibleDescription("Open Add Section Dialog");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        Boolean valid = true;
        //Check for valid data
        if (this.firstName.getText().length() < 1) {
            valid = false;
        }
        if (this.surName.getText().length() < 1) {
            valid = false;
        }
        if (valid) {
            String aForeName = this.firstName.getText();
            String aSurName = this.surName.getText();
            String aSect = this.sectionCombo.getSelectedItem().toString();
            //Update or create a new person
            if (update) {
                myPerson.setForeName(aForeName);
                myPerson.setSurName(aSurName);
                myPerson.setMySection(new SectionsManager().getSectionListfromName(aSect));
                myPeople.updatePerson(myPerson);
            } else {
                Person pers = new Person(aForeName, aSurName, aSect);
                myPeople.addNewPerson(pers);
            }
        } else {
            ErrorDialog err = new ErrorDialog("Trying to create a person that already exists in the database");
            err.setVisible(true);
        }
        //close the form
        this.dispose();
    }//GEN-LAST:event_btnOKActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        //Add Section names to combobox
        ArrayList<String> mySect = new ArrayList();
        for (String aSect : mySect) {
            this.sectionCombo.addItem(aSect);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void firstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameActionPerformed
    /**
     * Automagically capitalises the first letter on exiting the textbox
     *
     * @param evt event generated by form interaction
     */
    private void firstNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_firstNameFocusLost
        if (this.firstName.getText().length() > 0) {
            this.firstName.setText(Utils.capitaliseString(this.firstName.getText()));
        }
    }//GEN-LAST:event_firstNameFocusLost
    /**
     * Automagically capitalises the first letter on exiting the textbox
     *
     * @param evt event generated by form interaction
     */
    private void surNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_surNameFocusLost
        if (this.surName.getText().length() > 0) {
            this.surName.setText(Utils.capitaliseString(this.surName.getText()));
        }
    }//GEN-LAST:event_surNameFocusLost

    private void btnNewSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSectionActionPerformed
        String newName = JOptionPane.showInputDialog("Enter the name of the new Section.");

        if (!newName.isEmpty() || newName != null) {
            new SectionsManager().addNewSection(new Sections(newName));
        }
        //Save the entry in the combo if one exists
        if (this.sectionCombo.getSelectedItem() != null) {
            String tempString = this.sectionCombo.getSelectedItem().toString();
            this.updateSectionCombo();
            this.sectionCombo.setSelectedItem(tempString);
        } else {
            this.updateSectionCombo();
        }
    }//GEN-LAST:event_btnNewSectionActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAddEditPerson.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrmAddEditPerson dialog = new FrmAddEditPerson(new javax.swing.JFrame(), true, myPerson);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNewSection;
    private javax.swing.JButton btnOK;
    private javax.swing.JTextField firstName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox sectionCombo;
    private javax.swing.JTextField surName;
    // End of variables declaration//GEN-END:variables
}
