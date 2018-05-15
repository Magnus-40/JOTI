/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Manager Class for the Person class, handles all the database interaction with
 * associated helper methods.
 *
 * @author Andrew Whitelaw
 */
public final class PersonManager implements Serializable {

    private static List<Person> personList;
    private Session session;

    /**
     * Basic Constructor
     */
    public PersonManager() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        session = HibernateSession.getSession();
        PersonManager.personList = new ArrayList();

    }

    /**
     * Constructor for CountryList
     *
     * @param countryList HashMap object
     */
    public PersonManager(List countryList) {
        PersonManager.personList = countryList;
    }

//######################################################################
// Methods
//######################################################################
    /**
     * This method adds the passed object to the database
     *
     * @param myPerson Contact Object
     * @return the integer value for the database ID of the new Country object.
     */
    protected Integer addNewPerson(Person myPerson) {
        Integer contID = null;
        if (!this.personExists(myPerson)) { //Does this person already exist?
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                contID = (Integer) session.save(myPerson);
                tx.commit();
                PersonManager.personList.add(myPerson);
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
     * This method removes the passed object from the database
     *
     * @param myPerson Contact Object
     */
    protected void removePerson(Person myPerson) {
        if (this.personExists(myPerson)) { //Does this person already exist?
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.delete(myPerson);
                tx.commit();
                PersonManager.personList.remove(myPerson);
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                //session.close();
            }
        }
    }

    /**
     * This method updates the passed object to the database
     *
     * @param myPerson Contact Object
     */
    protected void updatePerson(Person myPerson) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(myPerson);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
    }

    /**
     * rowCount returns counts the number of records in the Person table.
     *
     * @return Long value for the number of lines.
     */
    protected Long rowCount() {
        Long countRows;
        countRows = ((Long) session.createQuery("select count(*) from Person").iterate().next());
        return countRows;
    }

    /**
     * Returns a complete list of Person Objects from the database
     *
     * @return List of Person objects
     */
    protected List<Person> getPersonList() {
        List<Person> myPersonList = new ArrayList();
        try {
            myPersonList = (List<Person>) session.createCriteria(Person.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return myPersonList;
    }

    /**
     * Returns a list of country objects for the countries contacted by the
     * supplied person
     *
     * @param pers The required person object
     * @return List of Country objects
     */
    protected List<Country> getCountryList(Person pers) {
        List<Country> myCountries = new ArrayList();
        for (Contact cnt : pers.getContactList()) {
            if (myCountries.contains(cnt.getMyCountry())) {
            } else {
                myCountries.add(cnt.getMyCountry());
            }
        }
        return myCountries;
    }

    /**
     * Removes all contacts in the contactList for the supplied person object
     *
     *
     * @param aPerson
     */
    protected void deletePersonList(Person aPerson) {
        ContactManager myContacts = new ContactManager();
        myContacts.setContactList(aPerson.getContactList());
        for (Contact aContact : myContacts.getContactList()) {
            myContacts.deleteContact(aContact);
            session.flush();
        }
    }

    /**
     * Returns a list of the number of contacts in descending order and their
     * corresponding people.
     *
     * @return List(ObjectHandler) containing a list of people and the number of
     * their contacts in order of descending quantity
     */
    public List<ObjectHandler> getTopTenPeople() {
        int maxNum;
        int myCounter = 0;
        List<ObjectHandler> myList = this.getPeopleByNumContacts();
        List<ObjectHandler> topTen = new ArrayList();
        Collections.sort(myList);
        //Set the maximum number of lies to display
        if (myList.size() > 10) {
            maxNum = 10;
        } else {
            maxNum = myList.size();
        }
        //Step through the list and add the first 10 items of data to the returnList
        for (ObjectHandler obj : myList) {
            if (myCounter < maxNum) {
                topTen.add(obj);
            }
            myCounter++;
        }
        Collections.sort(topTen);
        return topTen;
    }

    /**
     * Retrieves the Forenames corresponding to the supplied Surname
     *
     * @param mySurname a string representing the surname
     * @return List object of strings representing forenames
     */
    protected List<String> getForenamesforSurname(String mySurname) {
        List<String> mySurnames = new ArrayList();

        try {
            Criteria crit = session.createCriteria(Person.class);
            crit.setProjection(Projections.property("foreName"));
            crit.add(Restrictions.like("surName", mySurname));
            mySurnames = (List<String>) crit.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        //Remove duplicates and sort list before returning
        Set<String> mySet = new HashSet(mySurnames);
        List<String> returnnames = new ArrayList(mySet);
        Collections.sort(returnnames);

        return returnnames;
    }

    /**
     * Returns a List of Person Objects who are members of the Section supplied
     *
     * @param sectin Sections object
     * @return List of Person objects
     */
    protected List<Person> getPeopleFromSection(String sectin) {
        List<Person> myPeopleList = new ArrayList();
        for (Person aPer : this.getPersonList()) {
            if (aPer.getMySection().getMySectionName().equals(sectin)) {
                myPeopleList.add(aPer);
            }
        }
        return myPeopleList;
    }

    /**
     * Returns a sorted, unique List Object of Strings representing the list of
     * all surnames in the database
     *
     * @return List of String objects.
     */
    protected List<String> getSurnameList() {
        List<String> mySurnames = new ArrayList();
        try {
            Criteria crit = session.createCriteria(Person.class);
            crit.setProjection(Projections.property("surName"));
            mySurnames = (List<String>) crit.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        //Remove duplicates and sort list before returning
        Set<String> mySet = new HashSet(mySurnames);
        List<String> returnnames = new ArrayList(mySet);
        Collections.sort(returnnames);
        return returnnames;
    }

    /**
     * Returns a list of applicable section for the supplied name
     *
     * @param myForename String representing the forename
     * @param mySurname String representing the surname
     * @return List of Section objects
     */
    protected List<String> getSectionsforNames(String myForename, String mySurname) {
        List<Sections> mySections;
        List<String> mySectionList = new ArrayList();
        try {
            //Get all Sections with the supplied surname and forename
            Criteria crit = session.createCriteria(Person.class);
            crit.setProjection(Projections.property("mySection"));
            crit.add(Restrictions.like("surName", mySurname));
            crit.add(Restrictions.like("foreName", myForename));
            mySections = (List<Sections>) crit.list();
            //extract the section names from the list of section objects
            for (Sections mySec : mySections) {
                mySectionList.add(mySec.getMySectionName());
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        //Remove duplicates and sort list before returning
        Set<String> mySet = new HashSet(mySectionList);
        List<String> returnSection = new ArrayList(mySet);
        Collections.sort(returnSection);

        return returnSection;
    }

    boolean personExists(Person pers) {
        Boolean exists = false;
        Criteria crit = session.createCriteria(Person.class, "people");
        crit.add(Restrictions.like("surName", pers.getSurName()));
        crit.add(Restrictions.like("foreName", pers.getForeName()));
        crit.createAlias("people.mySection", "mySect");
        crit.add(Restrictions.eq("mySect.mySectionName", pers.getMySection().getMySectionName()));
        if (crit.list().isEmpty()) {
            exists = false;
        } else {
            exists = true;
        }
        return exists;
    }

    /**
     * Retrieves the person from the supplied data
     *
     * @param fore String representing the forename
     * @param sur String representing the surname
     * @param sect String representing the section name
     * @return Person object
     */
    protected Person getPerson(String fore, String sur, String sect) {
        Person myPerson = new Person();
        try {
            Criteria crit = session.createCriteria(Person.class, "people");
            crit.add(Restrictions.like("surName", sur));
            crit.add(Restrictions.like("foreName", fore));
            crit.createAlias("people.mySection", "mySect");
            crit.add(Restrictions.eq("mySect.mySectionName", sect));
            myPerson = (Person) crit.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return myPerson;
    }

    private List<Person> sortPeopleByNumContacts(List<Person> personList) {
        Map<Integer, Person> myMap = new TreeMap(Collections.reverseOrder());
        List<Person> myPers = new ArrayList();
        //Add to map for reverse sorting
        for (Person aPers : personList) {
            myMap.put(aPers.getContactList().size(), aPers);
        }
        // extract to List
        for (Map.Entry<Integer, Person> pair : myMap.entrySet()) {
            myPers.add(pair.getValue());
        }

        return myPers;
    }

    /**
     * Gets a list of People and number of Contacts made from the database Uses
     * ObjectHandler class to deal with the comparisons and sorting of the lists
     *
     * @return List of ObjectHandler of people names and number of contacts.
     */
    protected List<ObjectHandler> getPeopleByNumContacts() {
        //declarations
        List<Person> myList;
        String myName;
        ObjectHandler obj;
        List<ObjectHandler> myMap = new ArrayList();
        //get raw data from database
        Criteria crit = session.createCriteria(Person.class).addOrder(SizeOrder.desc("contactList"));
        myList = (List<Person>) crit.list();
        //build return Map. Add unique names.
        for (Person myPers : myList) {
            myName = myPers.getForeName() + " " + myPers.getSurName();
            //if data does not apready exist in the list
            obj = new ObjectHandler(myPers.getContactList().size(), myName);
            if (!myMap.contains(obj)) {
                myMap.add(new ObjectHandler(myPers.getContactList().size(), myName));
            }
        }
        return myMap;
    }

    /**
     * Adds a Contact object to the Person object supplied
     *
     * @param myPerson Person object
     * @param cnt Contact object
     */
    protected void addContact(Person myPerson, Contact cnt) {
//Tried this as a single command but for some reason it fails to work properly.
//For some reason this does so I am leaving it.
        //Get contact list
        List<Contact> lst = myPerson.getContactList();
        //Add contact
        lst.add(cnt);
        //add the new list to the Person
        myPerson.setContactList(lst);
    }

    public void exportDatabase() {
        StringBuilder str = new StringBuilder();
        for (Person pers : this.getPersonList()) {
            str.append(pers.getForeName()).append(",");
            str.append(pers.getSurName()).append(",");
            str.append(pers.getMySectionName()).append("\n");
            for (Contact myContact : pers.getContactList()) {
                str.append(",");
                str.append(myContact.getMyCountryName()).append(",");
                str.append(myContact.getNotes());
                str.append("\n");
            }
        }
        System.out.println(str.toString());
    }
}
