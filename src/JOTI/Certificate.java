/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFTextParagraph;
import org.apache.poi.xssf.usermodel.XSSFTextRun;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Certificate is a class for generating a personal achievement certificate and
 * outputting the result to Excel for formatting and printing.
 *
 * @author Andrew Whitelaw
 */
public final class Certificate {

    private static final String OUT_FILE_NAME = "Certificate.xlsx";
    private static final String TEMP_FILE_NAME = "Certificate_Working.xlsx";
    private static final String EXCEL_FILE_NAME = "Certificate_Master.xlsx";
    private String personName;
    private Person myPerson;
    private List<String> countriesList;
    private String myCountries;
    private String totalContacts;
    private String countryTotals;
    private String uniqueLevels;
    private String uniqueCounts;
    private String contactCounts;
    private String contactLevels;
    private int commonwealthCounts;
    private String commonwealthLevels;
    private Awards awd;
    private ContactManager myContacts;

    private XSSFDrawing drawing;
    private XSSFWorkbook wb;

    /**
     * Constructor for a List object, Generates a Certificate and saves it to a
     * data file
     *
     * @param aPeople List of Person Objects
     */
    public Certificate(List<Person> aPeople) {
        Certificate myCert = null;
        this.copyFile();
        this.openCertificate();
        for (Person pers : aPeople) {
            myCert = new Certificate(pers);
            myCert.wb=wb;
            myCert.certificateToExcel();
        }
        this.saveToDataFile();
    }

    /**
     * Constructor for Certificate
     *
     * @param aPerson Person object for the Certificate to be printed.
     */
    public Certificate(Person aPerson) {
        //Declarations
        myPerson = aPerson;
        myContacts = new ContactManager();
        countriesList = new ArrayList();
        myCountries = "";
        totalContacts = String.valueOf(myPerson.getContactList().size());
        //Set name
        this.personName = myPerson.getForeName() + " " + myPerson.getSurName();
        //get awards for person
        awd = new Awards(myPerson);
        //Set Commonwealth award
        this.setCommonwealthCounts(awd.getCommonwealthCount(aPerson));
        this.setCommonwealthLevels(awd.getCommonwealthString(aPerson));
        //Set Countries List
        for (Country cnt : myContacts.getUniqueContactsPerPerson(myPerson)) {
            //Get uniquecontracts and save countries to a list.
            this.countriesList.add(cnt.getCountryName());
        }//sort and export to a String
        Collections.sort(this.countriesList);
        for (String myStr : this.countriesList) {
            myCountries = myCountries + myStr + "\n";
        }
    }

    /**
     * Copies the master file into the working excel file
     */
    private void copyFile() {
        try {
            FileUtils.copyFile(new File(EXCEL_FILE_NAME), new File(TEMP_FILE_NAME));
        } catch (IOException ex) {
            Logger.getLogger(Certificate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens the temp Excel file as a WorkBook object
     */
    private void openCertificate() {
        try {
            wb = new XSSFWorkbook(new File(TEMP_FILE_NAME));
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(Certificate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Accepts a Workbook and copies a master sheet with a name supplied in Certificate
     * @param myCert Certificate object
     * @param myWorkBook Workbook object to be altered
     * @return Returns the new Sheet object
     */
    private Sheet createNewSheet(Certificate myCert, XSSFWorkbook myWorkBook) {
        Sheet newSheet;
        Sheet existingSheet;
        existingSheet = myWorkBook.getSheet("Certificate");
        newSheet = myWorkBook.cloneSheet(myWorkBook.getSheetIndex(existingSheet), myCert.personName);
        return newSheet;
    }

    private void insertText(Sheet mySheet, String textBoxName, String text) {

        drawing = (XSSFDrawing) mySheet.getDrawingPatriarch();
        for (XSSFShape shp : drawing.getShapes()) {
            //System.out.println("txtBox name " + shp.getShapeName());
            if (shp.getShapeName().equals(textBoxName)) {
                XSSFSimpleShape textbox = (XSSFSimpleShape) shp;
                List<XSSFTextParagraph> paras = textbox.getTextParagraphs();
                XSSFTextParagraph para = paras.get(0);
                List<XSSFTextRun> textRuns = para.getTextRuns();
                XSSFTextRun myTextRun = textRuns.get(0);
                myTextRun.setText(text);
            }
        }
    }

    /**
     * Saves the Spreadsheet to the OUT_FILE_NAME and closes workbook.
     */
    public void saveToDataFile() {
        try {
            wb.removeSheetAt(wb.getSheetIndex("Certificate"));
            FileOutputStream out = null;
            out = new FileOutputStream(OUT_FILE_NAME);
            wb.write(out);
            wb.close();
            Desktop.getDesktop().open(new File(OUT_FILE_NAME));
        } catch (IOException ex) {
            Logger.getLogger(Certificate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Simple method to show a dialog box with a person summary, also writes the
     * data string to the clipboard
     */
    public void summaryDialog() {
        String myString = this.buildCertificate();
        Utils.writeToClipboard(myString, null);
        JOptionPane.showMessageDialog(null, "This summary has been copied to the clipboard\n\n" + myString);
    }

    private String buildCertificate() {
        //ToDo Output these data to the excel spreadsheet
        this.getLevelsText();
        StringBuilder certStr = new StringBuilder();
        certStr.append(personName).append("\n");
        certStr.append(this.getIntroText()).append("\n");
        certStr.append(this.getAwardsText()).append("\n");
        certStr.append(this.getCommonwealthText()).append("\n");
        certStr.append(myCountries);
        return certStr.toString();
    }

    /**
     * Builds the new Certificate by inserting text into named textboxes
     */
    public void certificateToExcel() {
        Sheet aSheet = this.createNewSheet(this, wb);
        this.insertText(aSheet, "txtPersonName", personName);
        this.getLevelsText();
        this.insertText(aSheet, "allContactCount", this.contactCounts);
        this.insertText(aSheet, "allContactLevels", this.contactLevels);
        this.insertText(aSheet, "uniqueContactCount", this.uniqueCounts);
        this.insertText(aSheet, "uniqueContactLevels", this.uniqueLevels);
        this.insertText(aSheet, "txtTotalContacts", this.getIntroText());
        this.insertText(aSheet, "txtAwardsList", this.getAwardsText() + "\n"
                + this.getCommonwealthText());
        this.insertText(aSheet, "txtCountriesList", myCountries);
            }

    /**
     * Returns a String of the introductory text for the certificate built from
     * total and unique number of contacts.
     *
     * @return String representing the intro text
     */
    private String getIntroText() {
        StringBuilder awardText = new StringBuilder();
        awardText.append("");
        awardText.append("Has participated in the above event and has communicated with a total of ");
        awardText.append(totalContacts);
        awardText.append(" Scouting contacts in ");
        awardText.append(myContacts.getUniqueContactsPerPerson(myPerson).size());
        awardText.append(" unique countries and has achieved the following levels:");
        return awardText.toString();
    }

    private void getLevelsText() {
        //AllContacts
        this.setContactCounts(awd.getAllContactsString());
        //AllLevels
        this.setContactLevels(awd.getAllLevelsString());
        //UniqueContacts
        this.setUniqueCounts(awd.getUniqueContactsString());
        //UniqueLevels
        this.setUniqueLevels(awd.getUniqueLevelsString());
    }

    /**
     * Return a String listing the awards earned
     *
     * @return String listing the Awards earned.
     */
    private String getAwardsText() {
        StringBuilder awardText = new StringBuilder();
        if (awd.getContinentString().length() > 0) {
            awardText.append(awd.getContinentString());
        }
        if (awd.getContinentAward()) {
            awardText.append("The Continent Award. \tFor making at least one contact per populated continent.\n");
        }
        if (myContacts.getAllContacts().size() > 0) {
            awardText.append(awd.getAlphabetAward());
            awardText.append(" Alphabet Award.\tFor making contact with Countries beginning with ");
            awardText.append(String.valueOf(awd.getAlphabetAwardCount()));
            awardText.append(" of the 24 possible letters of the alphabet.");
        }
        return awardText.toString();
    }

    private String getCommonwealthText() {
        StringBuilder awardText = new StringBuilder();
        if (this.getCommonwealthCounts() > 0) {
            awardText.append(awd.getCommonwealthString(myPerson));
            awardText.append(" Commonwealth Award.\tFor making contact with ");
            awardText.append(this.getCommonwealthCounts());
            awardText.append(" unique countries out of a maximum of ");
            awardText.append(awd.getCountofAllCommonwealth()).append(" in the Commonwealth.");
        }
        return awardText.toString();
    }

    /**
     * Getter for personName
     *
     * @return String representing the person's name
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * Setter for personName
     *
     * @param personName String representing the person's name
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * Getter for myPerson
     *
     * @return Person object
     */
    public Person getMyPerson() {
        return myPerson;
    }

    /**
     * Setter for myPerson
     *
     * @param myPerson Person object
     */
    public void setMyPerson(Person myPerson) {
        this.myPerson = myPerson;
    }

    /**
     *
     * @return
     */
    public List<String> getCountriesList() {
        return countriesList;
    }

    /**
     *
     * @param countriesList
     */
    public void setCountriesList(List<String> countriesList) {
        this.countriesList = countriesList;
    }

    /**
     *
     * @return
     */
    public String getMyCountries() {
        return myCountries;
    }

    /**
     *
     * @param myCountries
     */
    public void setMyCountries(String myCountries) {
        this.myCountries = myCountries;
    }

    /**
     *
     * @return
     */
    public String getTotalContacts() {
        return totalContacts;
    }

    /**
     *
     * @param totalContacts
     */
    public void setTotalContacts(String totalContacts) {
        this.totalContacts = totalContacts;
    }

    /**
     *
     * @return
     */
    public String getCountryTotals() {
        return countryTotals;
    }

    /**
     *
     * @param countryTotals
     */
    public void setCountryTotals(String countryTotals) {
        this.countryTotals = countryTotals;
    }

    /**
     *
     * @return
     */
    public String getUniqueLevels() {
        return uniqueLevels;
    }

    /**
     *
     * @param uniqueLevels
     */
    public void setUniqueLevels(String uniqueLevels) {
        this.uniqueLevels = uniqueLevels;
    }

    /**
     *
     * @return
     */
    public String getUniqueCounts() {
        return uniqueCounts;
    }

    /**
     *
     * @param uniqueCounts
     */
    public void setUniqueCounts(String uniqueCounts) {
        this.uniqueCounts = uniqueCounts;
    }

    /**
     *
     * @return
     */
    public String getContactCounts() {
        return contactCounts;
    }

    /**
     *
     * @param contactCounts
     */
    public void setContactCounts(String contactCounts) {
        this.contactCounts = contactCounts;
    }

    /**
     *
     * @return
     */
    public String getContactLevels() {
        return contactLevels;
    }

    /**
     *
     * @param contactLevels
     */
    public void setContactLevels(String contactLevels) {
        this.contactLevels = contactLevels;
    }

    /**
     *
     * @return
     */
    public int getCommonwealthCounts() {
        return commonwealthCounts;
    }

    /**
     *
     * @param commonwealthCounts
     */
    public void setCommonwealthCounts(int commonwealthCounts) {
        this.commonwealthCounts = commonwealthCounts;
    }

    /**
     *
     * @return
     */
    public String getCommonwealthLevels() {
        return commonwealthLevels;
    }

    /**
     *
     * @param commonwealthLevels
     */
    public void setCommonwealthLevels(String commonwealthLevels) {
        this.commonwealthLevels = commonwealthLevels;
    }

    /**
     *
     * @return
     */
    public ContactManager getMyContacts() {
        return myContacts;
    }

    /**
     *
     * @param myContacts
     */
    public void setMyContacts(ContactManager myContacts) {
        this.myContacts = myContacts;
    }

    /**
     * Copies a new sheet from the master and opens it read for use
     */
    public void prepareSheet() {
        this.copyFile();
        this.openCertificate();
    }

}
