/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * POJO for Contact information including hibernate annotations
 *
 * @author Andrew Whitelaw
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    @Id
    @GeneratedValue
    private Integer ID;
    @OneToOne
    private Country myCountry;
    private String notes;
    @ManyToOne
    private Person myPerson;

    /**
     * Default Constructor
     */
    public Contact() {
    }

    /**
     * Constructor for the Contact object with parameters
     * @param myCountry COuntry Object
     * @param notes String representing the notes for the contact
     */
    public Contact(Country myCountry, String notes) {
        this.myCountry = myCountry;
        this.notes = notes;
    }

    /**
     * Getter for the Country
     * @return COuntry object
     */
    public Country getMyCountry() {
        return myCountry;
    }
    
    /**
     * Getter for CountryName
     * @return String representing the name of the country
     */
    public String getMyCountryName(){
        return myCountry.getCountryName();
    }

    /**
     * Setter for the Country object
     * @param myCountry a Country object
     */
    public void setMyCountry(Country myCountry) {
        this.myCountry = myCountry;
    }

    /**
     * Getter for the notes
     * @return String object containing the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Setter for the Notes
     * @param notes String object
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
      * Overridden Hashcode
     * @return int representing the hascode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.myCountry);
        hash = 67 * hash + Objects.hashCode(this.notes);
        return hash;
    }

    /**
     * Overridden Equals
     * @param obj Object for comparison
     * @return Boolean vale for equality
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        if (!Objects.equals(this.myCountry, other.myCountry)) {
            return false;
        }
        return true;
    }

    /**
     * Getter for ID
     * @return Integer representing the Table ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Setter for ID
     * @param ID Integer representing the Table ID
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Overridden toString
     * @return String representing the country name
     */
    @Override
    public String toString() {
        return myCountry.getCountryName() ;
    }

}
