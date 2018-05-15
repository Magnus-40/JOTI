/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Certificate is a class for generating a personal achievement certificate and
 outputting the result to Excel for formatting and printing.
 *
 * @author Andrew Whitelaw
 */
public final class Certificate_bak {

    private static final String OUT_FILE_NAME = "Data.txt";
    private static final String EXCEL_FILE_NAME = "Certificate.xlsm";
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
    private StringBuilder str;

    /**
     * Constructor for a List object, Generates a Certificate_bak and saves it to a
 data file
     *
     * @param aPeople List of Person Objects
     */
    public Certificate_bak(List<Person> aPeople) {
        Certificate_bak myCert;
        str = new StringBuilder();
        for (Person pers : aPeople) {
            myCert = new Certificate_bak(pers);
            str.append(myCert.buildString(pers));
        }
        this.saveToDataFile(str);
    }

    /**
     * Constructor for Certificate_bak
     *
     * @param aPerson Person object for the Certificate_bak to be printed.
     */
    public Certificate_bak(Person aPerson) {
        str = new StringBuilder();
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
     * buildString creates a string summary of all awards and achievements from
     * the supplies Person object
     *
     * @param aPers A Person object
     * @return StringBuilder Object
     */
    private StringBuilder buildString(Person aPers) {
        Certificate_bak myCert = new Certificate_bak(aPers);
        str.append("PersonName:");
        str.append(myCert.personName);
        str.append("@@@\nIntroText:");
        str.append(myCert.getIntroText());
        str.append("@@@\nAwardsText:");
        str.append(myCert.getAwardsText());
        str.append("@@@\nCommonwealthText:");
        str.append(myCert.getCommonwealthText());
        myCert.getLevelsText();
        str.append("@@@\nCountriesList:");
        str.append(myCert.myCountries);
        str.append("@@@\nAllContactsCounts:");
        str.append(myCert.contactCounts);
        str.append("@@@\nAllContactsLevels:");
        str.append(myCert.contactLevels);
        str.append("@@@\nUniqueContactsCOunts:");
        str.append(myCert.uniqueCounts);
        str.append("@@@\nUniqueContactsLevels:");
        str.append(myCert.uniqueLevels);
        str.append("XXX\n");
        return str;
    }

    private void saveToDataFile(StringBuilder str) {
        //System.out.println(str.toString());
        try {
            FileWriter myFile = new FileWriter(OUT_FILE_NAME);
            myFile.write(str.toString());
            myFile.close();
            Desktop.getDesktop().open(new File(EXCEL_FILE_NAME));
        } catch (IOException ex) {
            Logger.getLogger(Certificate_bak.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Simple method to show a dialog box with a person summary, also writes the data string to the clipboard
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
     * @return String representing the person's name
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * Setter for personName
     * @param personName String representing the person's name
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * Getter for myPerson
     * @return Person object
     */
    public Person getMyPerson() {
        return myPerson;
    }

    /**
     * Setter for myPerson
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

}
