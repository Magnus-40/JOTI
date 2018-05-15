/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO for the Sections table
 * @author Andrew Whitelaw
 */
@Entity
@Table(name = "SECTIONNAME")
public class Sections implements Serializable, Comparable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private int id;
    private String mySectionName;
    @OneToMany(mappedBy = "mySection")
    private Set<Person> myPerson;

    /**
     * Default Constructor
     */
    public Sections() {

    }

   /**
    * Constructor for a supplied SectionName
    * @param sectionName  String representing the section name
    */
    public Sections(String sectionName) {
        this.mySectionName = sectionName;
    }

    /**
     * Getter for ID
     * @return integer representing the ID
     */
    public int getId() {
        return id;
    }


    /**
     * Getter for section name
     * @return String representing the section name
     */
    public String getMySectionName() {
        return mySectionName;
    }

    /**
     * Setter for Section name
     * @param mySectionName String representing the section name
     */
    public void setMySectionName(String mySectionName) {
        this.mySectionName = mySectionName;
    }


    /**
     * Overridden compareTo
     * @param t Comparison object
     * @return integer 1/0/-1
     */

    @Override
    public int compareTo(Object t) {
        Sections that = (Sections) t;
        if (this.mySectionName.compareTo(that.mySectionName) < 0) {
            return -1;
        } else if (this.mySectionName.compareTo(that.mySectionName) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
