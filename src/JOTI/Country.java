package JOTI;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO for Country including hibernate annotations
 * @author Andrew Whitelaw
 */
@Entity
@Table(name = "COUNTRY", schema = "JOTI")
public class Country implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private int id;
    @Column( unique = true, nullable = false, length = 50)
    private String countryName;
    private String continentName;
    private boolean isCommonwealth;

    /**
     * default constructor
     */
    public Country() {
    }

    /**
     * Constructor for the Country object
     * @param countryName String representing the country name
     * @param myCont String representing the continent name
     */
    public Country( String countryName, String myCont) {
        this.countryName = countryName;
        this.continentName = myCont;
    }

    /**
     * Constructor for the COuntry object
     * @param countryName String representing the country name
     * @param myCont String representing the continent name
     * @param isComm Boolean. True is the country is a member of the commonwealth
     */
    public Country(String countryName, String myCont, Boolean isComm) {
        this.countryName = countryName;
        this.continentName = myCont;
        this.isCommonwealth=isComm;
    }

    /**
     * Getter for the table ID
     * @return int representing the table ID
     */
    protected int getId() {
        return this.id;
    }

    /**
     * Setter for the table ID
     * @param id int representing the table ID
     */
    protected void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the country name
     * @return String representing the country name
     */
    protected String getCountryName() {
        return this.countryName;
    }

    /**
     * Setter for the country name
     * @param countryName String representing the country name
     */
    protected void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for the continent name
     * @return String representing the continent name
     */
    protected String getContinentName() {
        return this.continentName;
    }

    /**
     * Setter for the continent name
     * @param contId String representing the continent name
     */
    protected void setCont(String contId) {
        this.continentName = contId;
    }

    /**
     * Getter for is commonwealth
     * @return boolean value true if a member of the commonwealth
     */
    protected boolean isIsCommonwealth() {
        return isCommonwealth;
    }

    /**
     * Setter for is commonwealth
     * @param isCommonwealth boolean value true if a member of the commonwealth
     */
    protected void setIsCommonwealth(boolean isCommonwealth) {
        this.isCommonwealth = isCommonwealth;
    }
    
    /**
     * overidden toString
     * @return String of Object
     */
    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", countryName=" + countryName + ", cont=" + continentName + '}';
    }

    /**
     * Overridden Hash code
     * @return in representing the hashcode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.countryName);
        hash = 67 * hash + Objects.hashCode(this.continentName);
        return hash;
    }

    /**
     * Overridden compareTo
     * @param t Comparison object
     * @return integer 1/0/-1
     */
    public int compareTo(Object t) {
        Country that = (Country) t;
        if (this.countryName.compareTo(that.countryName) < 0) {
            return -1;
        } else if (this.countryName.compareTo(that.countryName) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
    /**
     * Overridden equals
     * @param obj object for comparison
     * @return boolean true is equals
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
        final Country other = (Country) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.countryName, other.countryName)) {
            return false;
        }
        if (!Objects.equals(this.continentName, other.continentName)) {
            return false;
        }
        return true;
    }
    
}
