/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Manager class for handling the Sections Class
 * @author Andrew Whitelaw
 */
public final class SectionsManager {

    private static List<Sections> sectionList;
    private final Session session;

    /**
     * Default Constructor
     */
    public SectionsManager() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        sectionList = new ArrayList();
        session = HibernateSession.getSession();
    }

    /**
     * This method adds the passed object to the database
     *
     * @param mySection Section object to be added
     * @return the integer value for the database ID of the new Section object.
     */
    public Integer addNewSection(Sections mySection) {
//Session session = NewHibernateUtil.getSession();
        Transaction tx = null;
        Integer contID = null;
        try {
            tx = session.beginTransaction();
            contID = (Integer) session.save(mySection);
            tx.commit();
            sectionList.add(mySection);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return contID;
    }

    /**
     * Accepts a Sections Object and updates the database with that object
     *
     * @param mySection Sections Object to update
     * @return returns an integer value of the record ID
     */
    public Integer updateSection(Sections mySection) {
//Session session = NewHibernateUtil.getSession();
        Transaction tx = null;
        Integer contID = null;
        try {
            tx = session.beginTransaction();
            session.update(mySection);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return contID;
    }
    /**
     * Delete a section from the database
     * @param mySection Section object to be deleted
     */
    public void deleteSection(Sections mySection){
        //@TODO check to see if anyone is attached to the section before deletion
         Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(mySection);
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
     * rowCount returns counts the number of records in the Section table.
     *
     * @return Long value for the number of lines.
     */
    public Long rowCount() {
//Session session = NewHibernateUtil.getSession();
        Long countRows;
        countRows = ((Long) session.createQuery("select count(*) from Section").iterate().next());
        return countRows;
    }

    /**
     * Returns a complete list of Sections Objects from the database
     *
     * @return List of Sections objects
     */
    public List<Sections> getSectionList() {
        List<Sections> mySectionList = new ArrayList();
//Session session = NewHibernateUtil.getSession();
        try {
            mySectionList = (List<Sections>) session.createCriteria(Sections.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        Collections.sort(mySectionList);
        return mySectionList;
    }

    /**
     * Returns a map of Section and number of contacts for that section
     *
     * @return Map of section names and number of contacts keyed on section name
     */
    protected Map<String, Integer> getSectionContacts() {
        ContactManager myContacts = new ContactManager();
        Map<String, Integer> myMap = new HashMap();
        Sections aSection = null;
        List<String> mySections = this.getSectionNames();
        for (String name : mySections) {
            aSection = this.getSectionListfromName(name);
            if (!myMap.containsKey(name)) {
                myMap.put(name, myContacts.getContactsPerSection(aSection).size());
            }
        }
        return myMap;
    }

    /**
     * Retrieves the sectionList Object corresponding to the supplied section
     * name
     *
     * @param sectionName String value representing the Section name
     * @return Sections object or null is no object exists
     */
    public Sections getSectionListfromName(String sectionName) {
        Sections mySectionList = null;
//Session session = NewHibernateUtil.getSession();
        try {
            Query query = session.createQuery("from Sections where mySectionName=:name");
            query.setParameter("name", sectionName);
            List aList = query.list();
            //mySectionList=(Sections)aList.get(0);
            mySectionList = (Sections) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return mySectionList;
    }

    /**
     * Return a List of string objects representing the Section name
     *
     * @return List of String objects
     */
    public List<String> getSectionNames() {
        List<String> mySections = new ArrayList();
        List<Sections> mySectionList;
        try {
            mySectionList = (List<Sections>) session.createCriteria(Sections.class).list();
            for (Sections str : mySectionList) {
                mySections.add(str.getMySectionName());
            };
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        //System.out.println("Sections: \n" + mySections.toString());
        Collections.sort(mySections);
        return mySections;
    }

    /**
     * Overridden ToString method
     *
     * @return String of the Section names
     */
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        List<Sections> tempList = SectionsManager.sectionList;
        for (Sections str : tempList) {
            returnString.append(str.getMySectionName());
            returnString.append("\n");
        }
        return returnString.toString();
    }

}
