/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 * The main form for the application
 *
 * @author Andrew Whitelaw
 */
public final class FrmMainForm extends javax.swing.JFrame {

    private PersonManager myPeople;
    private ContactManager myContactList;
    private SectionsManager mySections;
    private PersonTableModel personModel;
    private ContactTableModel contactModel;
    private CountryManager myCountries;
    private static FrmMainForm mainformInstance = new FrmMainForm();
    private Jambo myJam;
    javax.swing.JFrame mainForm = this;

    /**
     * Creates new form MainForm
     */
    private FrmMainForm() {
    }

    /**
     * init sets up the variables for the MainForm
     *
     * @param mJ Jambo object for serializing
     */
    public void init(Jambo mJ) {
        this.myJam = mJ;

        try {
            this.setIconImage(ImageIO.read(new File("jj2017badge.jpg")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        myPeople = new PersonManager();
        myContactList = new ContactManager();
        mySections = new SectionsManager();
        myCountries = new CountryManager();
        initComponents();
        //set up Person Tables
        personModel = new PersonTableModel();
        personModel.add(myPeople.getPersonList());
        jPersonContacts.setModel(personModel);
        jPersonContacts.setShowHorizontalLines(false);
        jPersonContacts.setShowVerticalLines(false);
        jPersonContacts.setRowMargin(0);
        jPersonContacts.setIntercellSpacing(new Dimension(0, 0));
        jPersonContacts.setFillsViewportHeight(true);
        jPersonContacts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableRowSorter<PersonTableModel> personSorter = new TableRowSorter<>(personModel);
        jPersonContacts.setRowSorter(personSorter);
        this.autosizeTableColumns(jPersonContacts);

        //set up Country Tables
        contactModel = new ContactTableModel();
        contactModel.add(myCountries.getContactedCountryList());
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
        jPersonContacts.getRowSorter().toggleSortOrder(0);
        jPersonContacts.getRowSorter().toggleSortOrder(0);
        this.pack();
        this.setLocationRelativeTo(null);

        this.setTitle("JOTI Logger");
        this.setVisible(true);
        this.updateForm();
    }

    /**
     * Singleton class getInstance
     *
     * @return MainForm mainformInstance
     */
    public static FrmMainForm getMainformInstance() {
        return mainformInstance;
    }

    /**
     * displays the supplied string in the form statusbar
     *
     * @param status String value to be displayed in the status bar
     */
    public void setStatusBar(String status) {
        this.statusBar.setText(status);
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
     * Method for updating the form with new data
     */
    protected void updateForm() {
        //Set the number of contacts
        Long numContacts = myContactList.rowCount();
        this.textContactWorld.setText(String.valueOf(numContacts));
        numContacts = myContactList.getCountbyContinent("Africa");
        this.textContactAfrica.setText(String.valueOf(numContacts));
        numContacts = myContactList.getCountbyContinent("Americas");
        this.textContactAmericas.setText(String.valueOf(numContacts));
        numContacts = myContactList.getCountbyContinent("Asia");
        this.textContactAsia.setText(String.valueOf(numContacts));
        numContacts = myContactList.getCountbyContinent("Australasia");
        this.textContactAustralasia.setText(String.valueOf(numContacts));
        numContacts = myContactList.getCountbyContinent("Europe");
        this.textContactEurope.setText(String.valueOf(numContacts));

    }

    /**
     * Handler for the View Contact actions opens the ViewContacts form and
     * shows contacts for a specified person
     *
     */
    private void viewAwards() {

        FrmViewAwards frm = new FrmViewAwards();
        frm.setVisible(true);
        frm.setTitle("View Awards");
        this.updateForm();
    }

    private void updatePersonEditSubMenus() {
        List<String> peopleNames = new ArrayList();
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Person object
            String[] parts = event.getActionCommand().split(",");
            String foreName = Utils.getForename(parts[0]);
            String surName = Utils.getSurname(parts[0]);
            String sectionName = parts[1].trim();
            //get person object and sends person object to edit form
            Person newPers = myPeople.getPerson(foreName, surName, sectionName);
            FrmAddEditPerson frm = new FrmAddEditPerson(mainForm, true, newPers);
            frm.setVisible(true);
            mainformInstance.updateForm();
        };
        this.mnuEditPerson.removeAll();
        //add persons in the PeronList to the menu
        List<Person> ppl = myPeople.getPersonList();
        Collections.sort(ppl, (Person p1, Person p2)
                -> p1.getSurName().compareTo(p2.getSurName()));
        //ensure unique list
        for (Person myPers : ppl) {
            if (!peopleNames.contains(myPers.toString())) {
                peopleNames.add(myPers.toString());
            }
        }
        for (String aPers : peopleNames) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(aPers);
            mnu.addActionListener(menuListener);
            this.mnuEditPerson.add(mnu);
        }
    }

    /**
     * method for creating a series of submenus each one based on a person
     */
    private void updatePersonAwardsSubMenus() {
        List<String> peopleNames = new ArrayList();
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Person object
            String[] parts = event.getActionCommand().split(",");
            String foreName = Utils.getForename(parts[0]);
            String surName = Utils.getSurname(parts[0]);
            String sectionName = parts[1].trim();
            //get person object and sends person object to edit form
            Person newPers = myPeople.getPerson(foreName, surName, sectionName);
            FrmViewAwards frm = new FrmViewAwards(newPers);
            frm.setTitle("View Awards");
            frm.setVisible(true);
            mainformInstance.updateForm();
        };
        this.mnuViewAwardByPerson.removeAll();
        //add persons in the PeronList to the menu
        List<Person> ppl = myPeople.getPersonList();
        Collections.sort(ppl, (Person p1, Person p2)
                -> p1.getSurName().compareTo(p2.getSurName()));
        //ensure unique list
        for (Person myPers : ppl) {
            if (!peopleNames.contains(myPers.toString())) {
                peopleNames.add(myPers.toString());
            }
        }
        for (String aPers : peopleNames) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(aPers);
            mnu.addActionListener(menuListener);
            this.mnuViewAwardByPerson.add(mnu);
        }
    }

    /**
     * method for creating a series of submenus each one based on a person
     */
    private void updatePersonCertSubMenus() {
        List<String> peopleNames = new ArrayList();
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Person object
            String[] parts = event.getActionCommand().split(",");
            String foreName = Utils.getForename(parts[0]);
            String surName = Utils.getSurname(parts[0]);
            String sectionName = parts[1].trim();
            //get person object and sends person object to edit form
            Person newPers = myPeople.getPerson(foreName, surName, sectionName);
            Certificate myCert = new Certificate(newPers);
            myCert.prepareSheet();
            myCert.certificateToExcel();
            myCert.saveToDataFile();
            mainformInstance.updateForm();
        };
        this.mnuCertbyPerson.removeAll();
        //add persons in the PeronList to the menu
        List<Person> ppl = myPeople.getPersonList();
        Collections.sort(ppl, (Person p1, Person p2)
                -> p1.getSurName().compareTo(p2.getSurName()));
        //ensure unique list
        for (Person myPers : ppl) {
            if (!peopleNames.contains(myPers.toString())) {
                peopleNames.add(myPers.toString());
            }
        }
        for (String aPers : peopleNames) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(aPers);
            mnu.addActionListener(menuListener);
            this.mnuCertbyPerson.add(mnu);
        }
    }

    private void updatePersonViewSubMenus() {
        List<String> peopleNames = new ArrayList();
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Person object
            String[] parts = event.getActionCommand().split(",");
            String foreName = Utils.getForename(parts[0]);
            String surName = Utils.getSurname(parts[0]);
            String sectionName = parts[1].trim();
            //get person object and sends person object to edit form
            Person newPers = myPeople.getPerson(foreName, surName, sectionName);
            new Certificate(newPers).summaryDialog();
            mainformInstance.updateForm();
        };
        this.mnuViewByPerson.removeAll();
        //add persons in the PeronList to the menu
        List<Person> ppl = myPeople.getPersonList();
        Collections.sort(ppl, (Person p1, Person p2)
                -> p1.getSurName().compareTo(p2.getSurName()));
        //ensure unique list
        for (Person myPers : ppl) {
            if (!peopleNames.contains(myPers.toString())) {
                peopleNames.add(myPers.toString());
            }
        }
        for (String aPers : peopleNames) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(aPers);
            mnu.addActionListener(menuListener);
            this.mnuViewByPerson.add(mnu);
        }
    }

    private void updateSectionEditEditSubMenus() {
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Person object
            String sectionName = event.getActionCommand();
            //get person object and sends person object to edit form
            Sections mySect = mySections.getSectionListfromName(sectionName);
            String newSection = "";

            newSection = (String) JOptionPane.showInputDialog("Enter section name. This cannot be blank", sectionName);

            if (newSection != null && !newSection.isEmpty()) {
                //System.out.println("New Section Name '" + newSection + "'");
                mySect.setMySectionName(newSection);
                mySections.updateSection(mySect);
            }
            mainformInstance.updateForm();
        };
        this.mnuEditSectionNames.removeAll();
        //add persons in the PeronList to the menu
        for (String sect : mySections.getSectionNames()) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(sect);
            mnu.addActionListener(menuListener);
            this.mnuEditSectionNames.add(mnu);
        }
    }

    private void updateSectionViewBySubMenus() {
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Person object
            String sectionName = event.getActionCommand();
            //get person object and sends person object to edit form
            Sections mySect = mySections.getSectionListfromName(sectionName);
            String newSection = "";

            JOptionPane.showMessageDialog(null, "To Be Implemented");

            mainformInstance.updateForm();
        };
        this.mnuViewBySection.removeAll();
        //add persons in the PeronList to the menu
        for (String sect : mySections.getSectionNames()) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(sect);
            mnu.addActionListener(menuListener);
            this.mnuViewBySection.add(mnu);
        }
    }

    private void updateSectionCertSubMenus() {
        ActionListener menuListener = (ActionEvent event) -> {
            //build a Section object
            String sectionName = event.getActionCommand();
            //get person object and sends person object to edit form
            List<Person> myPeopleList = myPeople.getPeopleFromSection(sectionName);
            Certificate myCert = new Certificate(myPeopleList);
            mainformInstance.updateForm();
        };
        this.mnuCertbySection.removeAll();
        //add persons in the PeronList to the menu
        for (String sect : mySections.getSectionNames()) {
            JMenuItem mnu = new JMenuItem();
            mnu.setText(sect);
            mnu.addActionListener(menuListener);
            this.mnuCertbySection.add(mnu);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTotalContacts = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textContactWorld = new javax.swing.JTextField();
        textContactAsia = new javax.swing.JTextField();
        textContactAmericas = new javax.swing.JTextField();
        textContactAustralasia = new javax.swing.JTextField();
        textContactAfrica = new javax.swing.JTextField();
        textContactEurope = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        statusBar = new javax.swing.JLabel();
        jpnlContactsperPerson = new javax.swing.JPanel();
        jScrollPersonContacts = new javax.swing.JScrollPane();
        jPersonContacts = new javax.swing.JTable();
        lblContactperPerson = new javax.swing.JLabel();
        pnlContactsperCountry = new javax.swing.JPanel();
        lblContactperCountry = new javax.swing.JLabel();
        jScrollCountryContacts1 = new javax.swing.JScrollPane();
        jCountryContacts = new javax.swing.JTable();
        jMainMenu = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuLoad = new javax.swing.JMenuItem();
        mnuSave = new javax.swing.JMenuItem();
        mnuPeople = new javax.swing.JMenu();
        mnuAddNewPerson = new javax.swing.JMenuItem();
        mnuViewAward = new javax.swing.JMenuItem();
        mnuViewAwardByPerson = new javax.swing.JMenu();
        mnuEditPerson = new javax.swing.JMenu();
        mnuDeletePerson = new javax.swing.JMenuItem();
        mnuContacts = new javax.swing.JMenu();
        addNewContact = new javax.swing.JMenuItem();
        deleteContact = new javax.swing.JMenuItem();
        mnuViewContact = new javax.swing.JMenuItem();
        mnuResults = new javax.swing.JMenu();
        mnuViewContacts = new javax.swing.JMenuItem();
        mnuMissingInAction = new javax.swing.JMenuItem();
        mnuViewAwards = new javax.swing.JMenuItem();
        mnuViewSummary = new javax.swing.JMenuItem();
        mnuViewByPerson = new javax.swing.JMenu();
        mnuViewBySection = new javax.swing.JMenu();
        mnuCert = new javax.swing.JMenu();
        mnuPrintAllCert = new javax.swing.JMenuItem();
        mnuCertbyPerson = new javax.swing.JMenu();
        mnuCertbySection = new javax.swing.JMenu();
        mnuUtilities = new javax.swing.JMenu();
        mnuSetUp = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuExportItem = new javax.swing.JMenuItem();
        mnuImportData = new javax.swing.JMenuItem();
        mnuBackupDatabase = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuItemSections = new javax.swing.JMenuItem();
        mnuEditSectionNames = new javax.swing.JMenu();
        mnuHelp = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanelTotalContacts.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("World");

        jLabel3.setText("Africa");

        jLabel4.setText("Americas");

        jLabel5.setText("Asia");

        jLabel6.setText("Australasia");

        jLabel7.setText("Europe");

        textContactWorld.setEditable(false);
        textContactWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textContactWorldActionPerformed(evt);
            }
        });

        textContactAsia.setEditable(false);

        textContactAmericas.setEditable(false);

        textContactAustralasia.setEditable(false);
        textContactAustralasia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textContactAustralasiaActionPerformed(evt);
            }
        });

        textContactAfrica.setEditable(false);
        textContactAfrica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textContactAfricaActionPerformed(evt);
            }
        });

        textContactEurope.setEditable(false);

        jLabel1.setText("Total Contacts");

        javax.swing.GroupLayout jPanelTotalContactsLayout = new javax.swing.GroupLayout(jPanelTotalContacts);
        jPanelTotalContacts.setLayout(jPanelTotalContactsLayout);
        jPanelTotalContactsLayout.setHorizontalGroup(
            jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textContactAmericas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textContactWorld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                        .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(33, 33, 33))
                            .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel7))
                                .addGap(44, 44, 44)))
                        .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textContactEurope, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textContactAustralasia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textContactAfrica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textContactAsia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTotalContactsLayout.setVerticalGroup(
            jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalContactsLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textContactWorld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textContactAfrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textContactAmericas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textContactAsia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textContactAustralasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTotalContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textContactEurope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlContactsperPerson.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPersonContacts.setModel(new javax.swing.table.DefaultTableModel(
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
        jPersonContacts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPersonContactsMouseClicked(evt);
            }
        });
        jScrollPersonContacts.setViewportView(jPersonContacts);

        lblContactperPerson.setText("Contacts per Person");

        javax.swing.GroupLayout jpnlContactsperPersonLayout = new javax.swing.GroupLayout(jpnlContactsperPerson);
        jpnlContactsperPerson.setLayout(jpnlContactsperPersonLayout);
        jpnlContactsperPersonLayout.setHorizontalGroup(
            jpnlContactsperPersonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlContactsperPersonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlContactsperPersonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlContactsperPersonLayout.createSequentialGroup()
                        .addComponent(lblContactperPerson)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPersonContacts, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlContactsperPersonLayout.setVerticalGroup(
            jpnlContactsperPersonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlContactsperPersonLayout.createSequentialGroup()
                .addComponent(lblContactperPerson)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPersonContacts, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContactsperCountry.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblContactperCountry.setText("Contacts per Country");

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

        javax.swing.GroupLayout pnlContactsperCountryLayout = new javax.swing.GroupLayout(pnlContactsperCountry);
        pnlContactsperCountry.setLayout(pnlContactsperCountryLayout);
        pnlContactsperCountryLayout.setHorizontalGroup(
            pnlContactsperCountryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContactsperCountryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContactsperCountryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContactperCountry)
                    .addComponent(jScrollCountryContacts1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlContactsperCountryLayout.setVerticalGroup(
            pnlContactsperCountryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContactsperCountryLayout.createSequentialGroup()
                .addComponent(lblContactperCountry)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollCountryContacts1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        mnuFile.setText("File");

        mnuLoad.setText("Load Data");
        mnuLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLoadActionPerformed(evt);
            }
        });
        mnuFile.add(mnuLoad);

        mnuSave.setText("Save Data");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSave);

        jMainMenu.add(mnuFile);

        mnuPeople.setText("People");

        mnuAddNewPerson.setText("Add New Person");
        mnuAddNewPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddNewPersonActionPerformed(evt);
            }
        });
        mnuPeople.add(mnuAddNewPerson);

        mnuViewAward.setText("View Awards");
        mnuViewAward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewAwardActionPerformed(evt);
            }
        });
        mnuPeople.add(mnuViewAward);

        mnuViewAwardByPerson.setText("View Award by Person");
        mnuViewAwardByPerson.setAutoscrolls(true);
        mnuViewAwardByPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuViewAwardByPersonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuViewAwardByPersonMouseEntered(evt);
            }
        });
        mnuPeople.add(mnuViewAwardByPerson);

        mnuEditPerson.setText("Edit Person");
        mnuEditPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuEditPersonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuEditPersonMouseEntered(evt);
            }
        });
        mnuPeople.add(mnuEditPerson);

        mnuDeletePerson.setText("Delete Person");
        mnuDeletePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDeletePersonActionPerformed(evt);
            }
        });
        mnuPeople.add(mnuDeletePerson);

        jMainMenu.add(mnuPeople);

        mnuContacts.setText("Contacts");

        addNewContact.setText("Add New Contact");
        addNewContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewContactActionPerformed(evt);
            }
        });
        mnuContacts.add(addNewContact);

        deleteContact.setText("Delete Contact");
        mnuContacts.add(deleteContact);

        mnuViewContact.setText("View Contact");
        mnuViewContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewContactActionPerformed(evt);
            }
        });
        mnuContacts.add(mnuViewContact);

        jMainMenu.add(mnuContacts);

        mnuResults.setText("Results");

        mnuViewContacts.setText("View Contacts");
        mnuViewContacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewContactsActionPerformed(evt);
            }
        });
        mnuResults.add(mnuViewContacts);

        mnuMissingInAction.setText("View Missing Countries");
        mnuMissingInAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMissingInActionActionPerformed(evt);
            }
        });
        mnuResults.add(mnuMissingInAction);

        mnuViewAwards.setText("View Awards");
        mnuViewAwards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewAwardsActionPerformed(evt);
            }
        });
        mnuResults.add(mnuViewAwards);

        mnuViewSummary.setText("View Summary");
        mnuViewSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewSummaryActionPerformed(evt);
            }
        });
        mnuResults.add(mnuViewSummary);

        mnuViewByPerson.setText("View By Person");
        mnuViewByPerson.setAutoscrolls(true);
        mnuViewByPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuViewByPersonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuViewByPersonMouseEntered(evt);
            }
        });
        mnuResults.add(mnuViewByPerson);

        mnuViewBySection.setText("View By Section");
        mnuViewBySection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuViewBySectionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuViewBySectionMouseEntered(evt);
            }
        });
        mnuViewBySection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewBySectionActionPerformed(evt);
            }
        });
        mnuResults.add(mnuViewBySection);

        jMainMenu.add(mnuResults);

        mnuCert.setText("Certificate");

        mnuPrintAllCert.setText("Print All Certificates");
        mnuPrintAllCert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPrintAllCertActionPerformed(evt);
            }
        });
        mnuCert.add(mnuPrintAllCert);

        mnuCertbyPerson.setText("Print by Person");
        mnuCertbyPerson.setAutoscrolls(true);
        mnuCertbyPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuCertbyPersonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuCertbyPersonMouseEntered(evt);
            }
        });
        mnuCert.add(mnuCertbyPerson);

        mnuCertbySection.setText("Print by Section");
        mnuCertbySection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuCertbySectionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuCertbySectionMouseEntered(evt);
            }
        });
        mnuCert.add(mnuCertbySection);

        jMainMenu.add(mnuCert);

        mnuUtilities.setText("Utilities");
        mnuUtilities.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuUtilitiesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuUtilitiesMouseEntered(evt);
            }
        });

        mnuSetUp.setText("Initial Set Up");
        mnuSetUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSetUpActionPerformed(evt);
            }
        });
        mnuUtilities.add(mnuSetUp);
        mnuUtilities.add(jSeparator1);

        mnuExportItem.setText("Export Data");
        mnuExportItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExportItemActionPerformed(evt);
            }
        });
        mnuUtilities.add(mnuExportItem);

        mnuImportData.setText("Import Data");
        mnuImportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuImportDataActionPerformed(evt);
            }
        });
        mnuUtilities.add(mnuImportData);

        mnuBackupDatabase.setText("Backup Database");
        mnuUtilities.add(mnuBackupDatabase);
        mnuUtilities.add(jSeparator3);

        mnuItemSections.setText("Add/Remove Sections");
        mnuItemSections.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSectionsActionPerformed(evt);
            }
        });
        mnuUtilities.add(mnuItemSections);

        mnuEditSectionNames.setText("Edit Section Name");
        mnuEditSectionNames.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuEditSectionNamesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuEditSectionNamesMouseEntered(evt);
            }
        });
        mnuUtilities.add(mnuEditSectionNames);

        jMainMenu.add(mnuUtilities);

        mnuHelp.setText("Help");
        jMainMenu.add(mnuHelp);

        setJMenuBar(jMainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelTotalContacts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpnlContactsperPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlContactsperCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelTotalContacts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnlContactsperPerson, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlContactsperCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)))
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addNewContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewContactActionPerformed
        FrmAddNewContact frm = new FrmAddNewContact(this, true);
        frm.setVisible(true);
        this.updateForm();
    }//GEN-LAST:event_addNewContactActionPerformed

    private void mnuAddNewPersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddNewPersonActionPerformed
        FrmAddEditPerson frm = new FrmAddEditPerson(this, true, null);
        frm.setVisible(true);
        this.updateForm();
    }//GEN-LAST:event_mnuAddNewPersonActionPerformed

    private void mnuMissingInActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMissingInActionActionPerformed
        FrmMissingInAction frm = new FrmMissingInAction(this, true);
        frm.setVisible(true);
        this.updateForm();
    }//GEN-LAST:event_mnuMissingInActionActionPerformed

    private void mnuViewContactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewContactsActionPerformed
        FrmViewContacts frm = new FrmViewContacts(this, true);
        frm.setVisible(true);
        this.updateForm();
    }//GEN-LAST:event_mnuViewContactsActionPerformed

    private void textContactAfricaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textContactAfricaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textContactAfricaActionPerformed

    private void mnuViewContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewContactActionPerformed
        FrmViewContacts frm = new FrmViewContacts(this, true);
        frm.setVisible(true);
        this.updateForm();
    }//GEN-LAST:event_mnuViewContactActionPerformed

    private void mnuViewAwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewAwardActionPerformed
        this.viewAwards();
    }//GEN-LAST:event_mnuViewAwardActionPerformed

    private void mnuViewAwardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewAwardsActionPerformed
        this.viewAwards();
    }//GEN-LAST:event_mnuViewAwardsActionPerformed

    private void mnuExportItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExportItemActionPerformed
        FileOutputStream f_out;
        JFileChooser chooser = new JFileChooser();
        chooser.setApproveButtonText("Save");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JOTI Images", "joti");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                f_out = new FileOutputStream(chooser.getCurrentDirectory() + "\\" + chooser.getSelectedFile().getName() + ".JOTI");
                // Write object with ObjectOutputStream
                ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                // Write object out to disk
                obj_out.writeObject(this.myJam);
                f_out.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FrmMainForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FrmMainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mnuExportItemActionPerformed
    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
        new PersonManager().exportDatabase();
    }//GEN-LAST:event_mnuSaveActionPerformed

    private void mnuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLoadActionPerformed
        JOptionPane.showMessageDialog(null, "To Be Completed");
    }//GEN-LAST:event_mnuLoadActionPerformed

    private void mnuItemSectionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSectionsActionPerformed
        FrmSectionsForm mySectionsForm = new FrmSectionsForm(this, true);
        mySectionsForm.setVisible(true);
    }//GEN-LAST:event_mnuItemSectionsActionPerformed

    private void mnuImportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuImportDataActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setApproveButtonText("Load");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "TXT file", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Utils.importData(chooser.getSelectedFile().toString());
            Utils.importPeople(chooser.getSelectedFile().toString());
        }
        this.updateForm();
    }//GEN-LAST:event_mnuImportDataActionPerformed

    private void textContactWorldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textContactWorldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textContactWorldActionPerformed

    private void textContactAustralasiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textContactAustralasiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textContactAustralasiaActionPerformed

    private void mnuEditPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuEditPersonMouseClicked
        this.updatePersonEditSubMenus();
    }//GEN-LAST:event_mnuEditPersonMouseClicked

    private void mnuEditPersonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuEditPersonMouseEntered
        this.updatePersonEditSubMenus();
    }//GEN-LAST:event_mnuEditPersonMouseEntered

    private void mnuUtilitiesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuUtilitiesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuUtilitiesMouseClicked

    private void mnuUtilitiesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuUtilitiesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuUtilitiesMouseEntered

    private void mnuEditSectionNamesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuEditSectionNamesMouseEntered
        this.updateSectionEditEditSubMenus();
    }//GEN-LAST:event_mnuEditSectionNamesMouseEntered

    private void mnuEditSectionNamesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuEditSectionNamesMouseClicked
        this.updateSectionEditEditSubMenus();
    }//GEN-LAST:event_mnuEditSectionNamesMouseClicked

    private void mnuSetUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSetUpActionPerformed

// TODO add your handling code here:
    }//GEN-LAST:event_mnuSetUpActionPerformed

    private void mnuViewAwardByPersonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuViewAwardByPersonMouseEntered
        this.updatePersonAwardsSubMenus();
    }//GEN-LAST:event_mnuViewAwardByPersonMouseEntered

    private void mnuViewAwardByPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuViewAwardByPersonMouseClicked
        this.updatePersonAwardsSubMenus();
    }//GEN-LAST:event_mnuViewAwardByPersonMouseClicked

    private void mnuViewSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewSummaryActionPerformed
        String myString = myContactList.getSummary();
        Utils.writeToClipboard(myString, null);
        JOptionPane.showMessageDialog(null, "This summary has been copied to the clipboard\n\n" + myString, "JOTI Summary", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_mnuViewSummaryActionPerformed

    private void mnuViewBySectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewBySectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuViewBySectionActionPerformed

    private void mnuViewBySectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuViewBySectionMouseClicked
        this.updateSectionViewBySubMenus();
    }//GEN-LAST:event_mnuViewBySectionMouseClicked

    private void mnuViewBySectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuViewBySectionMouseEntered
        //System.out.println("Building Sections...");
        this.updateSectionViewBySubMenus();
    }//GEN-LAST:event_mnuViewBySectionMouseEntered

    private void mnuPrintAllCertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPrintAllCertActionPerformed
        int peopleCount = myPeople.getPersonList().size();
        int yesNo = JOptionPane.showConfirmDialog(null, "This will print " + peopleCount
                + " certificates, this could take some time. Do you want to continue?", "Alert", JOptionPane.YES_NO_OPTION);
        if (yesNo == JOptionPane.YES_OPTION) {
            Certificate myCert = new Certificate(myPeople.getPersonList());
        }
    }//GEN-LAST:event_mnuPrintAllCertActionPerformed

    private void mnuCertbyPersonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuCertbyPersonMouseEntered
        this.updatePersonCertSubMenus();
    }//GEN-LAST:event_mnuCertbyPersonMouseEntered

    private void mnuCertbyPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuCertbyPersonMouseClicked
        this.updatePersonCertSubMenus();

    }//GEN-LAST:event_mnuCertbyPersonMouseClicked

    private void mnuCertbySectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuCertbySectionMouseClicked
        this.updateSectionCertSubMenus();
    }//GEN-LAST:event_mnuCertbySectionMouseClicked

    private void mnuCertbySectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuCertbySectionMouseEntered
        this.updateSectionCertSubMenus();
    }//GEN-LAST:event_mnuCertbySectionMouseEntered

    private void mnuViewByPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuViewByPersonMouseClicked
        this.updatePersonViewSubMenus();
    }//GEN-LAST:event_mnuViewByPersonMouseClicked

    private void mnuViewByPersonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuViewByPersonMouseEntered
        this.updatePersonViewSubMenus();
    }//GEN-LAST:event_mnuViewByPersonMouseEntered

    private void mnuDeletePersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDeletePersonActionPerformed
        Integer yesNo = 0;

        FrmSelectPerson myFrm = new FrmSelectPerson(this, true);
        Person myPers = myFrm.returnPerson;
        myFrm.setVisible(false);
        myFrm.dispose();
        if (myPers != null) {
            yesNo = JOptionPane.showConfirmDialog(null, "Deleting " + myPers.getForeName() + " "
                    + myPers.getSurName() + " CANNOT be undone. Do you Really want to delete this person and all their contacts?");
            if (yesNo == 0) {
                //myPeople.deletePersonList(myPers);
                //this.updateForm();
                JOptionPane.showMessageDialog(null, "To Be Completed");
            }
        }
    }//GEN-LAST:event_mnuDeletePersonActionPerformed

    private void jPersonContactsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPersonContactsMouseClicked
        int selectedRow = jPersonContacts.getSelectedRow();
        String foreName = Utils.getForename(jPersonContacts.getValueAt(selectedRow, 1).toString());
        String surName = Utils.getSurname(jPersonContacts.getValueAt(selectedRow, 1).toString());
        String section = jPersonContacts.getValueAt(selectedRow, 2).toString();
        Person aPers = new Person(foreName, surName, section);
        FrmViewContacts cnt = new FrmViewContacts(surName, foreName, section);
        cnt.setVisible(true);
    }//GEN-LAST:event_jPersonContactsMouseClicked

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
            java.util.logging.Logger.getLogger(FrmMainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmMainForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addNewContact;
    private javax.swing.JMenuItem deleteContact;
    private javax.swing.JTable jCountryContacts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMainMenu;
    private javax.swing.JPanel jPanelTotalContacts;
    private javax.swing.JTable jPersonContacts;
    private javax.swing.JScrollPane jScrollCountryContacts1;
    private javax.swing.JScrollPane jScrollPersonContacts;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPanel jpnlContactsperPerson;
    private javax.swing.JLabel lblContactperCountry;
    private javax.swing.JLabel lblContactperPerson;
    private javax.swing.JMenuItem mnuAddNewPerson;
    private javax.swing.JMenuItem mnuBackupDatabase;
    private javax.swing.JMenu mnuCert;
    private javax.swing.JMenu mnuCertbyPerson;
    private javax.swing.JMenu mnuCertbySection;
    private javax.swing.JMenu mnuContacts;
    private javax.swing.JMenuItem mnuDeletePerson;
    private javax.swing.JMenu mnuEditPerson;
    private javax.swing.JMenu mnuEditSectionNames;
    private javax.swing.JMenuItem mnuExportItem;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuImportData;
    private javax.swing.JMenuItem mnuItemSections;
    private javax.swing.JMenuItem mnuLoad;
    private javax.swing.JMenuItem mnuMissingInAction;
    private javax.swing.JMenu mnuPeople;
    private javax.swing.JMenuItem mnuPrintAllCert;
    private javax.swing.JMenu mnuResults;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenuItem mnuSetUp;
    private javax.swing.JMenu mnuUtilities;
    private javax.swing.JMenuItem mnuViewAward;
    private javax.swing.JMenu mnuViewAwardByPerson;
    private javax.swing.JMenuItem mnuViewAwards;
    private javax.swing.JMenu mnuViewByPerson;
    private javax.swing.JMenu mnuViewBySection;
    private javax.swing.JMenuItem mnuViewContact;
    private javax.swing.JMenuItem mnuViewContacts;
    private javax.swing.JMenuItem mnuViewSummary;
    private javax.swing.JPanel pnlContactsperCountry;
    private javax.swing.JLabel statusBar;
    private javax.swing.JTextField textContactAfrica;
    private javax.swing.JTextField textContactAmericas;
    private javax.swing.JTextField textContactAsia;
    private javax.swing.JTextField textContactAustralasia;
    private javax.swing.JTextField textContactEurope;
    private javax.swing.JTextField textContactWorld;
    // End of variables declaration//GEN-END:variables

}
