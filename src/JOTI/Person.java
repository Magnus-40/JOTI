/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO for the Person object
 *
 * @author Andrew Whitelaw
 */
@Entity
@Table(name = "Person")
public class Person implements Serializable {

    @Id
    @Column(name = "ID", unique = true)
    @GeneratedValue
    private int ID;
    private String foreName;
    private String surName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contactList;
    @ManyToOne(cascade = CascadeType.ALL)
    private Sections mySection;

    /**
     * default Constructor
     */
    public Person() {
    }

    /**
     *
     * @param foreName String representing the first name
     * @param surName String representing the surname
     * @param aSectionName String representing the Section name
     */
    public Person(String foreName, String surName, String aSectionName) {
        this.foreName = foreName;
        this.surName = surName;
        //Get the Sections object from the supplied name
        Sections aSection = new SectionsManager().getSectionListfromName(aSectionName);
        //Does the section exist? If not create a new section
        if (aSection == null) { //Section does not exist
            //Create section
            this.mySection = new Sections(aSectionName);
        } else {
            //save returened section to this
            this.mySection = aSection;
        }
        this.contactList = new ArrayList();
    }

    /**
     * Method to clear the contact list
     */
    public void deleteContactList() {
        this.contactList = null;
    }

    /**
     * Getter for foreName
     *
     * @return String representing the forename
     */
    public String getForeName() {
        return foreName;
    }

    /**
     * Setter for foreName
     *
     * @param foreName String representing the forename
     */
    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    /**
     * Getter for surName
     *
     * @return String representing the surname
     */
    public String getSurName() {
        return surName;
    }

    /**
     * Setter for surName
     *
     * @param surName String representing the surname
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }

    /**
     * Getter for contactList
     *
     * @return ContactList Object
     */
    public List<Contact> getContactList() {
        return contactList;
    }

    /**
     * Setter for ContactList
     *
     * @param contactList ContactList Object
     */
    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    /**
     * Getter for ID
     *
     * @return integer representing the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter for Section name
     *
     * @return String representing the Section name
     */
    public Sections getMySection() {
        return mySection;
    }

    /**
     * Setter for Sectionname
     *
     * @param mySection String representing the sectionname
     */
    public void setMySection(Sections mySection) {
        this.mySection = mySection;
    }
    
    /**
     * Gets Section name as a String
     * @return String object representing the Section name
     */
    public String getMySectionName(){
        return this.mySection.getMySectionName();
    }

    /**
     * COmpare To to permit ordering
     * @param that Person object for comparison
     * @return 
     */
    public int compareTo(Person that) {
        int comparison = 0;

        if (this.surName.compareTo(that.surName) > 0) {
            comparison = 1;
        } else if (this.surName.compareTo(that.surName) < 0) {
            comparison = -1;
        } else if (this.surName.compareTo(that.surName) == 0) {
            if (this.foreName.compareTo(that.foreName) > 0) {
                comparison = 1;
            } else if (this.foreName.compareTo(that.foreName) < 0) {
                comparison = -1;
            }
        }
        return comparison;
    }

    /**
     * Overridden toString method
     *
     * @return String object representing the Person object
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(foreName).append(" ");
        str.append(surName).append(", ");
        str.append(mySection.getMySectionName());
        return str.toString();
    }

}
