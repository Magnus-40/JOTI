/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

//######################################################################
// Constructors
//######################################################################
/**
 * The CountryManager class is list of Country objects and methods for
 * manipulating
 *
 * @author Andrew Whitelaw
 */
public final class CountryManager implements Serializable {

    private static List<Country> self;
    private Session session;

    /**
     * Basic Constructor
     */
    public CountryManager() {

        session = HibernateSession.getSession();
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        //If the database has no countrylist then build it.
        //Otherwise load the countrylist from the database

        if (this.rowCount() > 0) {
            CountryManager.self = this.getFullCountryList();
        } else {
            System.out.println("Rebuilding database");
            self = new ArrayList();
            this.loadCountry();
        }
    }

    /**
     * Constructor for CountryList
     *
     * @param countryList HashMap object
     */
    public CountryManager(List countryList) {
        CountryManager.self = countryList;

    }

    /**
     * This method adds the passed object to the database
     *
     * @param aCount Country Object
     * @return the integer value for the database ID of the new Country object.
     */
    protected Integer addNewCountry(Country aCount) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Transaction tx = null;
        Integer contID = null;

        if (!self.contains(this.getCountryfromString(aCount.getCountryName()))) {
            System.out.println("    Adding country : " + aCount.getCountryName());
            try {
                tx = session.beginTransaction();
                contID = (Integer) session.save(aCount);
                //this.logActivity.append("Country Saved: " + aCount.getCountryName() + "\n");
                tx.commit();
                self.add(aCount);
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                //session.close();
            }
        }
        return contID;
    }

    /**
     * Loads the CountryManager data from the CountryManager text file The
     * buffered line is split into tab delineated country and is then loaded
     * into a CountryManager Object and saved
     */
    private void loadCountry() {

        Country aCtry;
        int countryCount = 0;
        Boolean isComm;
        BufferedReader br = null;
        String sCurrentLine;
        String fileName = "";
        try {
            fileName = Utils.getPathName() + File.separatorChar + "Countries.txt";
            File f = new File(fileName);
            if (!f.exists()) {
                JFileChooser chooser = new JFileChooser();
                chooser.setApproveButtonText("Load");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Countries", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    br = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
                }
            } else {
                System.out.println("Found database...loading");
                br = new BufferedReader(new FileReader(fileName));
            }
            //loop through the lines in the file
            while ((sCurrentLine = br.readLine()) != null) {
                String[] parts = sCurrentLine.split("\t");
                if (parts[3].equals("0")) { //extract isCommonwealth
                    isComm = false;
                } else {
                    isComm = true;
                }
                //create country object
                aCtry = new Country(parts[1], parts[2], isComm);
                this.addNewCountry(aCtry);
                countryCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Rebuilt database with " + countryCount + " countries");
    }

    /**
     * Retrieves the Country object corresponding the the supplied country name.
     *
     * @param countryName String representing a country name
     * @return Country object
     */
    protected Country getCountryfromString(String countryName) {
        Country myCountry = null;
        //get session
        //Session session = NewHibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Country where countryName=:name");
            query.setParameter("name", countryName);
            myCountry = (Country) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return myCountry;
    }

    /**
     * Returns a list of strings representing the names of the countries in the
     * selected continent, Calls getCountriesFromContinent. Sorts alphabetically
     * before returning.
     *
     * @param myContinent String representing the desired Continent name
     * @return List of Strings representing the country names.
     */
    protected List<String> getCountryNamesFromContinent(String myContinent) {
        List<String> myCountryNames = new ArrayList();
        List<Country> myCountry = this.getCountriesFromContinent(myContinent);
        for (Country cnt : myCountry) {
            myCountryNames.add(cnt.getCountryName());
        }
        Collections.sort(myCountryNames);
        return myCountryNames;
    }

    /**
     * Returns a list of Countries for the supplied Continent. If the supplied
     * continent name is empty then it will return a full country list.
     *
     * @param myCont String name for the continent
     * @return List object of country objects
     */
    protected List<Country> getCountriesFromContinent(String myCont) {
        List<Country> myCountryList = new ArrayList();
        //Session session = NewHibernateUtil.getSession();
        if (!myCont.isEmpty()) {
            try {
                Criteria crit = session.createCriteria(Country.class);
                crit.add(Restrictions.like("continentName", myCont));
                myCountryList = crit.list();
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                //session.close();
            }
        } else {
            try {
                myCountryList = (List<Country>) session.createCriteria(Country.class).list();
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                //session.close();
            }
        }
        return myCountryList;
    }

    /**
     * rowCount returns counts the number of records in the Country table.
     *
     * @return Long value for the number of lines.
     */
    protected Long rowCount() {
        Long countRows;
        countRows = (Long) session.createCriteria(Country.class).setProjection(Projections.rowCount()).uniqueResult();
        return countRows;
    }

    /**
     * Returns a full list of countries
     *
     * @return List of Country objects
     */
    protected List<Country> getFullCountryList() {
        return this.getCountriesFromContinent("");
    }

    /**
     * Returns a list of Country objects where a contact has been made
     * @return List of Country objects
     */
    protected List<Country> getContactedCountryList() {
        List<Country> contactedCountries = new ArrayList<>();
        List<Contact> aContacts = new ContactManager().getAllContacts();
        for (Contact cnt : aContacts) {
            if(!contactedCountries.contains(cnt.getMyCountry())){
                contactedCountries.add(cnt.getMyCountry());
            }
        }
        return contactedCountries;
    }

    /**
     * Overridden toString Method
     *
     * @return String Object
     */
    @Override
    public String toString() {
        CountryManager.self.size();
        String output = "";
        for (Country myCountry : CountryManager.self) {
            output = output + "Country: ," + myCountry.toString()
                    + "\n";
        }
        return output;
    }

}
