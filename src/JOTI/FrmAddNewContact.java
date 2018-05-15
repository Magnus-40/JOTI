/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.List;
import javax.swing.JComboBox;

/**
 * Form to add a new person into the PeopleList
 *
 * @author Andrew Whitelaw
 */
public final class FrmAddNewContact extends javax.swing.JDialog {

    /**
     * Creates new form AddNewContact
     *
     * @param parent Parent Frame
     * @param modal Boolean value defining whether the form is modal
     */
    public FrmAddNewContact(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        PersonManager myPeople = new PersonManager();
        initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setTitle("Add a new contact");

        //Load the Surname lists
        List<String> surnames = myPeople.getSurnameList();
        this.surnameCombo.removeAllItems();
        //Add the surnames to the Surname drop down
        surnames.forEach((str) -> {
            this.surnameCombo.addItem(str);
        });
        
        this.forenameCombo.removeAllItems();
        this.sectionCombo.removeAllItems();
        // Add the sections to the section drop-down
        List<Sections> mySection = new SectionsManager().getSectionList();
        mySection.forEach((secn) -> {
            this.sectionCombo.addItem(secn.getMySectionName());
        });
        //Load the country lists
        CountryManager cntry = new CountryManager();
        
        this.africaList.removeAllItems();
        this.asiaList.removeAllItems();
        this.americasList.removeAllItems();
        this.australasiaList.removeAllItems();
        this.europeList.removeAllItems();
        this.setAsiaList(Utils.addDataToCombo(this.getAsiaList(), cntry.getCountryNamesFromContinent("Asia")));
        this.setAfricaList(Utils.addDataToCombo(this.getAfricaList(), cntry.getCountryNamesFromContinent("Africa")));
        this.setAmericasList(Utils.addDataToCombo(this.getAmericasList(), cntry.getCountryNamesFromContinent("Americas")));
        this.setAustralasiaList(Utils.addDataToCombo(this.getAustralasiaList(), cntry.getCountryNamesFromContinent("Australasia")));
        this.setEuropeList(Utils.addDataToCombo(this.getEuropeList(), cntry.getCountryNamesFromContinent("Europe")));
    }

    /**
     * Clears every Country drop-down except for the drop-down with the focus
     *
     * @param continent String value of the drop-down with the focus
     */
    private void resetDropDowns(String continent) {
        if (!continent.equals("Africa")) {
            this.africaList.setSelectedItem(null);
        }
        if (!continent.equals("Asia")) {
            this.asiaList.setSelectedItem(null);
        }
        if (!continent.equals("Americas")) {
            this.americasList.setSelectedItem(null);
        }
        if (!continent.equals("Australasia")) {
            this.australasiaList.setSelectedItem(null);
        }
        if (!continent.equals("Europe")) {
            this.europeList.setSelectedItem(null);
        }
        if (!continent.equals("All")) {
            this.africaList.setSelectedItem(null);
            this.asiaList.setSelectedItem(null);
            this.americasList.setSelectedItem(null);
            this.australasiaList.setSelectedItem(null);
            this.europeList.setSelectedItem(null);
            this.notesArea.setText("");
        }
    }

    /**
     * Populates the SectionCombo text field with data from the Forename and
     * Surname combos
     *
     * @param evt Calling event
     */
    private void updateSection(java.awt.event.ActionEvent evt) {
        String mySurname = this.surnameCombo.getSelectedItem().toString();
        if (this.forenameCombo.getSelectedItem() != null) {
            String myForename = this.forenameCombo.getSelectedItem().toString();
            //Clear the section drop-down
            this.sectionCombo.removeAllItems();
            //Get the list of people
            SectionsManager mySections = new SectionsManager();
            List<String> sections = new PersonManager().getSectionsforNames(myForename, mySurname);
            for (String str : sections) {
                this.sectionCombo.addItem(str);
            }
        }
    }

    /**
     * Populates the Forename text field with data from the Surname combo
     *
     * @param evt Calling event
     */
    private void updateForename(java.awt.event.ActionEvent evt) {
        String mySurname = this.surnameCombo.getSelectedItem().toString();
        //Clear the forename combo
        this.forenameCombo.removeAllItems();
        //Get the list of people
        PersonManager pers = new PersonManager();
        
        List<String> forenames = pers.getForenamesforSurname(mySurname);
        for (String str : forenames) {
            this.forenameCombo.addItem(str);
        }
    }

    /**
     * Checks that the data entered in the form is complete and prompts for any
     * errors to be corrected as appropriate
     */
    private void dataValidation() {
        String errorStr = "";

        //Check for empty data fields
        if (this.surnameCombo.getSelectedItem() == null) {
            errorStr = errorStr + "No Surname has been selected.\n";
        }
        if (this.forenameCombo.getSelectedItem() == null) {
            errorStr = errorStr + "No Forename has been selected.\n";
        }
        if (this.sectionCombo.getSelectedItem() == null) {
            errorStr = errorStr + "No Section has been selected.\n";
        }
        //check for country combo
        if ((this.africaList.getSelectedItem() == null)
                && (this.americasList.getSelectedItem() == null)
                && (this.asiaList.getSelectedItem() == null)
                && (this.australasiaList.getSelectedItem() == null)
                && (this.europeList.getSelectedItem() == null)) {
            errorStr = errorStr + "No Country has been selected.\n";
        }
        //Make the notes Non_null to prevent errors later
        if ((this.notesArea.getText() == null)) {
            this.notesArea.setText(" ");
        }
        //if there are errors then report
        if (errorStr.length() > 0) {
            // Do error reporting routines
            ErrorDialog err = new ErrorDialog(errorStr);
            System.out.println(errorStr);
            err.setModal(true);
            err.setName("Error");
            err.setVisible(true);
            //Otherwise
        } else {
            this.updateForm();
        }
    }

    /**
     * Updates the form, creates a Contact Object and call the contactList
     * object to add the contact to the list
     */
    private void updateForm() {
        String country = "";
        String fore;
        String sur;
        String sect;
        Person myPerson;
        Country myCountry;
        CountryManager cntry = new CountryManager();
        PersonManager pers = new PersonManager();
        //System.out.println("Adding Contact");
        if (this.africaList.getSelectedItem() != null) {
            country = this.africaList.getSelectedItem().toString();
        }
        if (this.americasList.getSelectedItem() != null) {
            country = this.americasList.getSelectedItem().toString();
        }
        if (this.asiaList.getSelectedItem() != null) {
            country = this.asiaList.getSelectedItem().toString();
        }
        if (this.australasiaList.getSelectedItem() != null) {
            country = this.australasiaList.getSelectedItem().toString();
        }
        if (this.europeList.getSelectedItem() != null) {
            country = this.europeList.getSelectedItem().toString();
        }
        //make a new contact object
        sur = (String) this.surnameCombo.getSelectedItem();
        fore = (String) this.forenameCombo.getSelectedItem();
        sect = (String) this.sectionCombo.getSelectedItem();
        
        myPerson = new PersonManager().getPerson(fore, sur, sect);
        myCountry = cntry.getCountryfromString(country);
        
        if (this.notesArea.getText().length() == 0) {
            this.notesArea.setText(" ");
        }
        //System.out.println("Country added " + myCountry);
        Contact cnt = new Contact(myCountry, this.notesArea.getText());
        //Add contact to contact list for person
        pers.addContact(myPerson, cnt);
        pers.updatePerson(myPerson);
        //reset form and indicate that the contact has been added
        this.resetDropDowns("All");
        this.statusBar.setText("Contact saved: " + country);
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OKButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        americasList = new javax.swing.JComboBox();
        africaList = new javax.swing.JComboBox();
        europeList = new javax.swing.JComboBox();
        australasiaList = new javax.swing.JComboBox();
        asiaList = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        notesArea = new javax.swing.JTextArea();
        surnameCombo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        forenameCombo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        sectionCombo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        statusBar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        OKButton.setText("OK ");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Africa");

        jLabel2.setText("Americas");

        jLabel3.setText("Asia");

        jLabel4.setText("Australasia");

        jLabel5.setText("Europe");

        americasList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                americasListFocusGained(evt);
            }
        });

        africaList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                africaListFocusGained(evt);
            }
        });

        europeList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                europeListFocusGained(evt);
            }
        });

        australasiaList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                australasiaListFocusGained(evt);
            }
        });

        asiaList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                asiaListFocusGained(evt);
            }
        });

        notesArea.setColumns(20);
        notesArea.setRows(5);
        jScrollPane1.setViewportView(notesArea);

        surnameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surnameComboActionPerformed(evt);
            }
        });

        jLabel6.setText("Surname");

        jLabel7.setText("Forename");

        forenameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forenameComboActionPerformed(evt);
            }
        });

        jLabel8.setText("Notes");

        sectionCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboActionPerformed(evt);
            }
        });

        jLabel10.setText("Section");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel8))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(europeList, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(australasiaList, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel6)))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(americasList, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(asiaList, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(surnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(forenameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(sectionCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(africaList, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(35, 35, 35)
                                                .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                        .addComponent(cancelButton)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(forenameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(surnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(africaList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OKButton)
                    .addComponent(cancelButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(americasList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(asiaList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(australasiaList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(europeList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(statusBar)
                .addGap(4, 4, 4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        this.dataValidation();
    }//GEN-LAST:event_OKButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void surnameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surnameComboActionPerformed
        this.updateForename(evt);
    }//GEN-LAST:event_surnameComboActionPerformed

    private void africaListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_africaListFocusGained
        this.resetDropDowns("Africa");
    }//GEN-LAST:event_africaListFocusGained

    private void americasListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_americasListFocusGained
        this.resetDropDowns("Americas");
    }//GEN-LAST:event_americasListFocusGained

    private void asiaListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_asiaListFocusGained
        this.resetDropDowns("Asia");
    }//GEN-LAST:event_asiaListFocusGained

    private void australasiaListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_australasiaListFocusGained
        this.resetDropDowns("Australasia");
    }//GEN-LAST:event_australasiaListFocusGained

    private void europeListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_europeListFocusGained
        this.resetDropDowns("Europe");
    }//GEN-LAST:event_europeListFocusGained

    private void forenameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forenameComboActionPerformed
        this.updateSection(evt);
    }//GEN-LAST:event_forenameComboActionPerformed

    private void sectionComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboActionPerformed
    }//GEN-LAST:event_sectionComboActionPerformed

    /**
     * Getter for the AfricaList
     *
     * @return Swing Combo box
     */
    public JComboBox getAfricaList() {
        return africaList;
    }

    /**
     * Setter for AfricaList
     *
     * @param africaList Swing Combo box
     */
    public void setAfricaList(JComboBox africaList) {
        this.africaList = africaList;
    }

    /**
     * Getter for the AmericaList
     *
     * @return Swing Combo box
     */
    public JComboBox getAmericasList() {
        return americasList;
    }

    /**
     * Setter for AmericasList
     *
     * @param americasList Swing Combo box
     */
    public void setAmericasList(JComboBox americasList) {
        this.americasList = americasList;
    }

    /**
     * Getter for the AsiaList
     *
     * @return Swing Combo box
     */
    public JComboBox getAsiaList() {
        return asiaList;
    }

    /**
     * Setter for AsiaList
     *
     * @param asiaList Swing Combo box
     */
    public final void setAsiaList(JComboBox asiaList) {
        this.asiaList = asiaList;
    }

    /**
     * Getter for the AustraliaList
     *
     * @return Swing Combo box
     */
    public JComboBox getAustralasiaList() {
        return australasiaList;
    }

    /**
     * Setter for AustraliaList
     *
     * @param australasiaList Swing Combo box
     */
    public void setAustralasiaList(JComboBox australasiaList) {
        this.australasiaList = australasiaList;
    }

    /**
     * Getter for the EuropeList
     *
     * @return Swing Combo box
     */
    public JComboBox getEuropeList() {
        return europeList;
    }

    /**
     * Setter for EuropeList
     *
     * @param europeList Swing Combo box
     */
    public void setEuropeList(JComboBox europeList) {
        this.europeList = europeList;
    }

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
            java.util.logging.Logger.getLogger(FrmAddNewContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmAddNewContact dialog = new FrmAddNewContact(new javax.swing.JFrame(), true);
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
    private static Jambo myJambo;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKButton;
    private javax.swing.JComboBox africaList;
    private javax.swing.JComboBox americasList;
    private javax.swing.JComboBox asiaList;
    private javax.swing.JComboBox australasiaList;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox europeList;
    private javax.swing.JComboBox forenameCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea notesArea;
    private javax.swing.JComboBox sectionCombo;
    private javax.swing.JLabel statusBar;
    private javax.swing.JComboBox surnameCombo;
    // End of variables declaration//GEN-END:variables
}
