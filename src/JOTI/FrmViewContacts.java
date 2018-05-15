/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Andrew Whitelaw
 */
public class FrmViewContacts extends javax.swing.JFrame {

    private PersonManager pers;
    private ContactTableModel contactModel;
    private CountryManager myCountries;

    /**
     * Creates new form ViewContacts
     */
    FrmViewContacts() {
        startupForm();
    }

    FrmViewContacts(FrmMainForm aThis, boolean b) {
        startupForm();
    }

    FrmViewContacts(String surName, String foreName, String mySection) {
        startupForm();
        this.surnameCombo.setSelectedItem(surName);
        this.updateForename();
        this.forenameCombo.setSelectedItem(foreName);
        this.updateSection();
        this.sectionCombo.setSelectedItem(mySection);
        this.updateText();
    }

    /**
     * Starts the form and initialises all the combo boxes
     */
    private void startupForm() {
        pers = new PersonManager();
        myCountries = new CountryManager();
        initComponents();
        setLocationRelativeTo(null);
        //Load the Surname lists
        List<String> surnames = pers.getSurnameList();
        this.setTitle("View Contacts");
        //Step through the List and add surnames
        for (String str : surnames) {
            this.surnameCombo.addItem(str);
        }
        this.forenameCombo.removeAllItems();
        this.sectionCombo.removeAllItems();
        this.surnameCombo.setSelectedItem("");
        this.forenameCombo.setSelectedItem("");
        this.sectionCombo.setSelectedItem("");
        this.fillCountryTables();
    }

    private void fillCountryTables() {
        PersonManager personList = new PersonManager();
        myCountries = new CountryManager();
        //set up Country Tables
        contactModel = new ContactTableModel();
        if (this.personValid()) {
            Person myPers = getPerson();
            contactModel.add(personList.getCountryList(myPers));
        } else {
            contactModel.add(myCountries.getContactedCountryList());
        }
        jCountryContacts.setModel(contactModel);
        jCountryContacts.setShowHorizontalLines(false);
        jCountryContacts.setShowVerticalLines(false);
        jCountryContacts.setRowMargin(0);
        jCountryContacts.setIntercellSpacing(new Dimension(0, 0));
        jCountryContacts.setFillsViewportHeight(true);
        jCountryContacts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableRowSorter<ContactTableModel> contactSorter = new TableRowSorter<>(contactModel);
        jCountryContacts.setRowSorter(contactSorter);
        this.autosizeTableColumns(jCountryContacts);
        //sort tables
        jCountryContacts.getRowSorter().toggleSortOrder(0);
        jCountryContacts.getRowSorter().toggleSortOrder(0);

    }

    private Boolean personValid() {
        Boolean valid = false;
        if ((this.forenameCombo.getSelectedItem() != null)
                && (this.forenameCombo.getSelectedItem() != null)
                && (this.forenameCombo.getSelectedItem() != null)) {
            valid = true;
        }
        return valid;
    }

    private void autosizeTableColumns(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    /**
     * Populates the SectionCombo text field with data from the Forename and
     * Surname combos
     */
    private void updateSection() {
        String mySurname = this.surnameCombo.getSelectedItem().toString();
        if (this.forenameCombo.getSelectedItem() != null) {
            String myForename = this.forenameCombo.getSelectedItem().toString();
            //Clear the section drop-down
            this.sectionCombo.removeAllItems();
            //Get the list section for the person and add to the Sections drop-down
            List<String> sections = pers.getSectionsforNames(myForename, mySurname);
            for (String str : sections) {
                this.sectionCombo.addItem(str);
            }
            this.updateText();
        }
    }

    /**
     * Populates the Forename text field with data from the Surname combo
     *
     * @param evt Calling event
     */
    private void updateForename() {
        String mySurname = this.surnameCombo.getSelectedItem().toString();
        //Clear the forename combo
        this.forenameCombo.removeAllItems();
        //Get the list of people
        List<String> forenames = pers.getForenamesforSurname(mySurname);
        for (String str : forenames) {
            this.forenameCombo.addItem(str);
        }
        this.updateText();
    }

    /**
     * Populates the main text area with Contact details appropriate for the
     * whole database or person if one is chosen
     *
     * @param ev event generated by form interaction
     */
    private void updateText() {
        pers = new PersonManager();
                //Is this every contact or a single person?
        if (this.surnameCombo.isVisible()) {
            if (this.personValid()) {
                Person myPers = getPerson();
                if (pers.getCountryList(myPers).size() > 0) {
                    contactModel.removeData();
                    contactModel.add(new PersonManager().getCountryList(myPers));
                }
            }
        } else {
            contactModel.removeData();
            contactModel.add(myCountries.getContactedCountryList());
        }
    }

    /**
     * Reads combos and returns a Person based on the data
     *
     * @return Person object
     */
    private Person getPerson() {
        String fore = this.forenameCombo.getSelectedItem().toString();
        String sur = this.surnameCombo.getSelectedItem().toString();
        String sect = this.sectionCombo.getSelectedItem().toString();
        Person myPers = pers.getPerson(fore, sur, sect);
        return myPers;
    }

    /**
     *
     * @param evt event generated by form interaction
     */
    private void selectorVisibility(java.awt.event.ActionEvent evt) {
        Boolean myVisibility;
        if (this.surnameCombo.isVisible()) {
            myVisibility = false;
        } else {
            myVisibility = true;
        }
        this.surnameLabel.setVisible(myVisibility);
        this.surnameCombo.setVisible(myVisibility);
        this.sectionCombo.setVisible(myVisibility);
        this.sectionCombo.setVisible(myVisibility);
        this.forenameLabel.setVisible(myVisibility);
        this.forenameCombo.setVisible(myVisibility);
        this.sectionCombo.setVisible(myVisibility);
        this.sectionLabel.setVisible(myVisibility);
        this.updateText();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        surnameLabel = new javax.swing.JLabel();
        surnameCombo = new javax.swing.JComboBox();
        forenameLabel = new javax.swing.JLabel();
        forenameCombo = new javax.swing.JComboBox();
        sectionLabel = new javax.swing.JLabel();
        sectionCombo = new javax.swing.JComboBox();
        allcontactsButton = new javax.swing.JRadioButton();
        jScrollCountryContacts1 = new javax.swing.JScrollPane();
        jCountryContacts = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        surnameLabel.setText("Surname");

        surnameCombo.setSelectedItem(surnameCombo);
        surnameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surnameComboActionPerformed(evt);
            }
        });

        forenameLabel.setText("Forename");

        forenameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forenameComboActionPerformed(evt);
            }
        });

        sectionLabel.setText("Section");

        sectionCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboActionPerformed(evt);
            }
        });

        allcontactsButton.setText("All Contacts");
        allcontactsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allcontactsButtonActionPerformed(evt);
            }
        });

        jCountryContacts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollCountryContacts1.setViewportView(jCountryContacts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sectionLabel)
                            .addComponent(surnameLabel))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(surnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(forenameLabel))
                            .addComponent(sectionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(forenameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(allcontactsButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollCountryContacts1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(allcontactsButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surnameLabel)
                    .addComponent(forenameLabel)
                    .addComponent(forenameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(surnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectionLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollCountryContacts1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void surnameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surnameComboActionPerformed
        this.forenameCombo.setSelectedItem(null);
        this.sectionCombo.setSelectedItem(null);
        this.updateForename();
    }//GEN-LAST:event_surnameComboActionPerformed

    private void forenameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forenameComboActionPerformed
        this.updateSection();
    }//GEN-LAST:event_forenameComboActionPerformed

    private void sectionComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboActionPerformed
    }//GEN-LAST:event_sectionComboActionPerformed

    private void allcontactsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allcontactsButtonActionPerformed
        this.selectorVisibility(evt);
    }//GEN-LAST:event_allcontactsButtonActionPerformed

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
            java.util.logging.Logger.getLogger(FrmViewContacts.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmViewContacts.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmViewContacts.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmViewContacts.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmViewContacts().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton allcontactsButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox forenameCombo;
    private javax.swing.JLabel forenameLabel;
    private javax.swing.JTable jCountryContacts;
    private javax.swing.JScrollPane jScrollCountryContacts1;
    private javax.swing.JComboBox sectionCombo;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JComboBox surnameCombo;
    private javax.swing.JLabel surnameLabel;
    // End of variables declaration//GEN-END:variables
}
