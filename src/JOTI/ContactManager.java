/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

//######################################################################
// Constructors
//######################################################################
/**
 * The ContactManager class is management Class for handling interactions with
 * the Contact Class
 *
 * @author Andrew Whitelaw
 */
public final class ContactManager implements Serializable {

    private static List<Contact> contactList;

    private Session session;
    private CountryManager myCountries;

    /**
     * Basic Constructor
     */
    public ContactManager() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        ContactManager.contactList = new ArrayList();
        myCountries = new CountryManager();
        session = HibernateSession.getSession();
    }

    /**
     * Constructor for CountryList
     *
     * @param countryList HashMap object
     */
    public ContactManager(List countryList) {
        ContactManager.contactList = countryList;
        myCountries = new CountryManager();
        session = HibernateSession.getSession();
    }

//######################################################################
// Methods
//######################################################################
    /**
     * This method adds the passed object to the database
     *
     * @param aCont Contact Object
     * @return the integer value for the database ID of the new Country object.
     */
    protected Integer addNewContact(Contact aCont) {
        //disable messages
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Transaction tx = null;
        Integer contID = null;
        try {
            tx = session.beginTransaction();
            contID = (Integer) session.save(aCont);
            tx.commit();
            ContactManager.contactList.add(aCont);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
//            session.close();
        }
        return contID;
    }

    /**
     * This method removes the passed object to the database
     *
     * @param aCont Contact Object
     */
    protected void deleteContact(Contact aCont) {
        //disable messages
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(aCont);
            tx.commit();
            ContactManager.contactList.remove(aCont);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
//            session.close();
        }
    }

    /**
     * rowCount returns counts the number of records in the Country table.
     *
     * @return Long value for the number of lines.
     */
    protected Long rowCount() {
        Long countRows;
        countRows = (Long) session.createQuery("select count(*) from Contact").iterate().next();
        return countRows;
    }

    /**
     * Returns a count of the number of contacts by continent
     *
     * @param myContinent. String name for the Continent
     * @return Integer representing Count of Contacts
     */
    public Long getCountbyContinent(String myContinent) {
        long mySize = this.getContactsPerContinent(myContinent).size();
        return mySize;
    }

    /**
     * Returns a List of Contact objects for the supplied Continent string.
     *
     * @param myContinent String representing the Continent name.
     * @return List of contact objects
     */
    protected List<Contact> getContactsPerContinent(String myContinent) {
        //Build criteria
        Criteria crit = session.createCriteria(Contact.class, "aContacts");
        crit.createAlias("aContacts.myCountry", "myCountry");
        crit.add(Restrictions.eq("myCountry.continentName", myContinent));
        //return data
        return crit.list();
    }

    /**
     * Returns a List of Contact objects for the supplied Country string.
     *
     * @param aCountry A string representing the requested country name 
     * @return List of contact objects
     */
    protected List<Contact> getContactsPerCountry(String aCountry) {
        //Build criteria
        Criteria crit = session.createCriteria(Contact.class, "aContacts");
        crit.createAlias("aContacts.myCountry", "myCountry");
        crit.add(Restrictions.eq("myCountry.countryName", aCountry));
        //return data
        return crit.list();
    }
    
    /**
     * Returns a List of all Contact objects.
     *
     * @return List of contact objects
     */
    protected List<Contact> getAllContacts() {
        return session.createCriteria(Contact.class, "aContacts").list();
    }

    /**
     * Returns a List of all Contact objects.
     *
     * @return List of contact objects
     */
    protected List<Contact> getAllUniqueContacts() {
        return this.getUniqueContactsList(session.createCriteria(Contact.class, "aContacts").list());

    }

    /**
     * Return a list of contacts for the requested person for the requested
     * continent
     *
     * @param myContinent String representing the Continent name
     * @param myPerson Person object for a single person
     * @return List of Contact Objects
     */
    protected List<Contact> getContactsPerContinentandPerson(String myContinent, Person myPerson) {
        //Build criteria
        Criteria crit = session.createCriteria(Contact.class, "aContacts");
        crit.createAlias("aContacts.myCountry", "myCountry");
        crit.add(Restrictions.eq("myCountry.continentName", myContinent));
        crit.add(Restrictions.eq("aContacts.myPerson", myPerson));
        return crit.list();
    }

    /**
     * Return a list of contacts for the requested Section
     *
     * @param mySection Section object
     * @return List of Contact Objects
     */
    protected List<Contact> getContactsPerSection(Sections mySection) {
        //Build criteria
        Criteria crit = session.createCriteria(Contact.class, "aContacts");
        crit.createAlias("aContacts.myPerson", "me");
        crit.add(Restrictions.eq("me.mySection", mySection));
        return crit.list();
    }

    /**
     * Accepts a person object representing the desired person and returns a
     * sorted unique list of countries contacted
     *
     * @param myPerson Person object for the required person.
     * @return List of unique countries contacted
     */
    protected List<Country> getUniqueContactsPerPerson(Person myPerson) {
        //get list for Person Object
        List<Contact> myList = myPerson.getContactList();
        List<Country> myCountries = new ArrayList();
        //step through contacts. Add country to list if not already in the list
        for (Contact aCont : myList) {
            if (!myCountries.contains(aCont.getMyCountry())) {
                myCountries.add(aCont.getMyCountry());
            }
        }
        return myCountries;
    }

    /**
     * Returns a list of all countries and the number of contacts made by that
     * country
     *
     * @return Map of COuntry Object and number of contacts
     */
    protected List<ObjectHandler> getContactsPerCountry() {
        List<Contact> myList = session.createCriteria(Contact.class).list();
        Map<Country, Integer> myMap = new HashMap();
        List<ObjectHandler> returnList = new ArrayList();
        Country myCountry;
        //step through the list and add each entry to the map
        for (Contact cnt : myList) {
            myCountry = cnt.getMyCountry();
            if (myMap.containsKey(myCountry)) {
                myMap.put(myCountry, myMap.get(myCountry) + 1);
            } else {
                myMap.put(myCountry, 1);
            }
        }
        //Convert to ObjectHandler Type
        for (Map.Entry<Country, Integer> aList : myMap.entrySet()) {
            returnList.add(new ObjectHandler(aList.getValue(), aList.getKey().getCountryName()));
        }
        return returnList;
    }

    /**
     * Returns an alphabetically sorted String List of country names from the
     * supplied continent name where the number of contacts made is zero, If the
     * supplied string is empty then it returns countries from the whole world.
     *
     * @param myContinent String representing the continent name
     * @return List object of Strings.
     */
    protected List<String> getMissingInAction(String myContinent) {
        //Declarations
        List<String> missingList;
        String countryName;
        List<Contact> myContacts;
        //Get all the contacts for the supplied continent name 
        //"" equals every country in the world
        if (myContinent.equals("")) {
            myContacts = session.createCriteria(Contact.class).list();
        } else {
            myContacts = this.getContactsPerContinent(myContinent);
        }
        //Get every name in the world/continent
        missingList = new CountryManager().getCountryNamesFromContinent(myContinent);

        //step through each contact
        for (Contact con : myContacts) {
            countryName = con.getMyCountry().getCountryName();
            //If the Missing list contains the country name then remove the 
            //country name from the missing list
            if (missingList.contains(countryName)) {
                missingList.remove(countryName);
            }
        }
        //sort alphabetically
        Collections.sort(missingList);
        return missingList;
    }

    /**
     * Returns a list of section names with corresponding counts of contacts
     *
     * @return List of ListHandler Objects
     */
    public List<ObjectHandler> getCountOfContactsBySection() {
        List<ObjectHandler> myList = new ArrayList();
        //@TODO get list ofcontacts by section
        return myList;
    }

    /**
     * Accepts a contact list and returns a Map of Country name and sum of
     * contacts for that country
     *
     * @param myCnt List of Contact objects
     * @return Map of string (country name) and total number of contacts
     */
    public Map<String, Integer> getUniqueContactsMap(List<Contact> myCnt) {
        String str;
        Map<String, Integer> myMap = new HashMap();
        for (Contact cnt : myCnt) {
            str = cnt.getMyCountry().getCountryName();
            if (myMap.containsKey(cnt.getMyCountry().getCountryName())) {
                myMap.put(str, myMap.get(str) + 1);
            } else {
                myMap.put(str, 1);
            }
        }
        return myMap;
    }

    /**
     * Accepts a contact list and returns a List of Country name and sum of
     * contacts for that country
     *
     * @param myCnt List of Contact objects
     * @return List of COuntry objects
     */
    public List<Country> getUniqueContactsList(List<Contact> myCnt) {
        String str;
        List<String> myList = new ArrayList();
        List<Country> returnList = new ArrayList();
        //build list of unique country name strings
        for (Contact cnt : myCnt) {
            str = cnt.getMyCountry().getCountryName();
            if (!myList.contains(str)) {
                myList.add(str);
            }
        }
        //build List of Countries from the List of Strings
        for (String myString : myList) {
            returnList.add(myCountries.getCountryfromString(myString));
        }
        return returnList;
    }

    /**
     * Builds a string object to summarise the contacts made
     *
     * @return String object
     */
    protected String getSummary() {
        List<String> tempString = new ArrayList();
        StringBuilder summaryString = new StringBuilder();
        summaryString.append(this.getAllContacts().size());
        summaryString.append(" Total Contacts\n");
        summaryString.append(this.getAllUniqueContacts().size());
        summaryString.append(" Unique Countries\n");
        List<Country> aCnt = this.getUniqueContactsList(this.getAllContacts());
        //extract countrynames
        for (Country myContact : aCnt) {
            tempString.add(myContact.getCountryName());
        }
        //Sort list alphabetically
        Collections.sort(tempString);
        //build string
        for (String countryName : tempString) {
            summaryString.append(countryName);
            summaryString.append(", ");
        }
        return summaryString.toString();
    }

    /**
     * Getter for the Contact List object
     *
     * @return ContactList List of Contact objects
     */
    public List<Contact> getContactList() {
        return contactList;
    }

    /**
     * Setter for the Contact List object
     *
     * @param contactList List of Contact objects
     */
    public void setContactList(List<Contact> contactList) {
        ContactManager.contactList = contactList;
    }

    /**
     * Overidden toString Method
     *
     * @return String Object
     */
    @Override
    public String toString() {
        ContactManager.contactList.size();
        String output = "";
        for (Contact myContact : ContactManager.contactList) {
            output = output + "Country: ," + myContact.toString()
                    + "\n";
        }
        return output;
    }
}
