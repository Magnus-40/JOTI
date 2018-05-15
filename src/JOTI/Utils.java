/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Utils is a class that gathers general static methods to perform misc Utility
 * functions
 *
 * @author Andrew Whitelaw
 */
public class Utils {

    /**
     * Static method that accepts a string and capitalises the first letter
     *
     * @param str String value to capitalise
     * @return String value as per param but with the first letter capitalised.
     */
    public static String capitaliseString(String str) {
        String cap;
        if (str.isEmpty()) {
            cap = "";
        } else {
            cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return cap;
    }
    
    /**
     * method to copy the supplied string to the system clipboard
     * @param s String to copy
     * @param owner normally set to null
     */
    public static void writeToClipboard(String s, ClipboardOwner owner) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable transferable = new StringSelection(s);
    clipboard.setContents(transferable, owner);
}

    /**
     * Accepts a string containing a forename and surname separated by a space
     *
     * @param name A string containing a fore name and surname
     * @return String containing the forename
     */
    public static String getForename(String name) {
        String foreName;
        if (!name.isEmpty() && (name.contains(" "))) {
            foreName = name.substring(0, name.indexOf(" "));
        } else {
            foreName = "";
        }
        return foreName;
    }

    /**
     * Accepts a string containing a forename and surname separated by a space
     * and returns just the surname assuming that the supplied string is in the
     * form forename surname
     *
     * @param name A string containing a forename and surname
     * @return String containing the surname
     */
    public static String getSurname(String name) {
        String surName;
        if (!name.isEmpty() && (name.contains(" "))) {
            surName = name.substring(name.indexOf(" ") + 1, name.length());
        } else {
            surName = "";
        }
        return surName;
    }

    /**
     * addDataToCombo adds all the entries in the List to the comboBox
     *
     * @param box Combo box object
     * @param data List object of all the countries
     * @return Returns a JComboBox containing the objects in the supplied list
     */
    public static javax.swing.JComboBox addDataToCombo(javax.swing.JComboBox box, List data) {
        int counter=0;
        if ((box != null) && (data.size() > 0)) {
            for (String countryName:(List<String>) data){
                box.insertItemAt(countryName, counter);
                counter++;
            }
            //for (int x = 0; x < data.size() - 1; x++) {
                // using insertAt because add triggers an event
              //  box.insertItemAt(data.get(x), x);
            //}
        }
        return box;
    }

    /**
     * Imports a file of comma-separated first names, surname and section and
     * adds them to the database. For mass import into the application
     *
     * @param fileName String value representing the File and path name
     */
    public static void importPeople(String fileName) {
        PersonManager myPersonList = new PersonManager();
        String line;
        if (Utils.verifyImportPeople(fileName)) {
            System.out.println("Data valid!");
            try {
                File file = new File(fileName);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                while ((line = bufferedReader.readLine()) != null) {
                    String[] bits = line.split(",");

                    //myPersonList.addNewPerson(new Person(bits[0], bits[1], bits[2]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks a datafile for any null data
     *
     * @param fileName Name of file to check
     * @return boolean value True if the data is valid otherwise false
     */
    private static boolean verifyImportPeople(String fileName) {
        String line;
        boolean dataValid = true;
        try {
            File file = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] bits = line.split(",");
                if ((bits[0].length() == 0) || (bits[1].length() == 0) || (bits[2].length() == 0)) {
                    dataValid = false;
                    JOptionPane.showMessageDialog(null,
                            "Error: The import file has one or more blanks and cannot be imported.",
                            "Data validation error.",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataValid;
    }

    /**
     * importData imports the entire log from a Text file into objects
     *
     * @param fileName String value representing the file name to be imported
     * @return returns a Jambo Objects containing the data in the Text file
     */
    public static Jambo importData(String fileName) {
        //System.out.println(fileName);
        Jambo myJambo = new Jambo();
        SectionsManager mySectionList = new SectionsManager();
        CountryManager myCountryManager = new CountryManager();
        PersonManager myPersonList;
        Person myPerson;
        Contact myContact;
        Country myCountry;
        Boolean isCommonwealth;
        String foreName;
        String surName;
        String sectionName;
        String countryName;
        String continentName;
        String myNotes;
        String line;

        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Section: ")) {
                    String[] bits = line.split(",");
                    mySectionList.addNewSection(new Sections(bits[1]));
                }
                if (line.startsWith("Country: ")) {
                    String[] bits = line.split(",");
                    countryName = bits[1];
                    continentName = bits[2];

                    if (bits[3].equals("false")) {
                        isCommonwealth = false;
                    } else {
                        isCommonwealth = true;
                    }
                    myCountry = new Country(countryName, continentName, isCommonwealth);
                    
                    myCountryManager.addNewCountry(myCountry);
                }
                if (line.startsWith("Contact: ")) {
                    String[] contactBits = line.split(",");

                    //Check to see if the Person Object exists and add if not exist
                    foreName = contactBits[1];
                    surName = contactBits[2];
                    sectionName = contactBits[3];
                    myPerson = new Person(foreName, surName, sectionName);
                    myPersonList = myJambo.myPeople;
                    if (!myPersonList.personExists(myPerson)) {
                        myPersonList.addNewPerson(myPerson);
                    }
                    //Get the personobject
                    myPerson = myPersonList.getPerson(foreName, surName, sectionName);
                    //Create a contact
                    countryName = contactBits[4];
                    myNotes = contactBits[5];
                    myCountry = new CountryManager().getCountryfromString(countryName);
                    myContact = new Contact(myCountry, myNotes);
                    myPerson.getContactList().add(myContact);
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myJambo;
    }

    /**
     * Returns a String representing the path name of the jar and therefore the
     * base locations of the application for loading files.
     *
     * @return String representing the base location of the application.
     */
    public static String getPathName() {
        String fullPath = "";
        int endOfPath = 0;
        try {
            fullPath = new File(JOTI_App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString();
            endOfPath = fullPath.lastIndexOf(File.separatorChar);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullPath.substring(0, endOfPath);
    }

}
