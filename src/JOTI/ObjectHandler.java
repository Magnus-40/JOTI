/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.Objects;

/**
 * A Helper Class for sorting the order of any list of Object and integer by the
 * integer value.
 *
 * @author Andrew Whitelaw
 */
public class ObjectHandler implements Comparable<ObjectHandler> {

    private Integer num;
    private Object myObject;

    //##############################################################
    // Constructors
    //##############################################################
    /**
     * Constructor for supplied integer and generic object
     *
     * @param num integer
     * @param myObject generic object
     */
    public ObjectHandler(Integer num, Object myObject) {
        this.num = num;
        this.myObject = myObject;
    }

    /**
     * Default Constructor
     */
    public ObjectHandler() {
    }


    //##############################################################
    // Getters and Setters
    //##############################################################
    /**
     * Getter for num variable
     *
     * @return integer value of num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * Setter for num variable
     *
     * @param num integer value of num
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * Getter for myObject variable
     *
     * @return String value of name
     */
    public Object getMyObject() {
        return myObject;
    }

    /**
     * Setter for myObject variable
     *
     * @param myObject Generic object
     */
    public void setMyObject(Object myObject) {
        this.myObject = myObject;
    }

    /**
     * Comparator override. Ensure that the list is sorted by the integer value.
     *
     * @param that ObjectHandler object
     * @return boolean higher/lower value.
     */
    @Override
    public int compareTo(ObjectHandler that) {
        if (this.num > that.num) {
            return -1;
        } else if (this.num < that.num) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Overridden Hash method
     * @return integer value for the Hash
     */
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Overridden equals for sorting
     * @param obj Object to be compared to this
     * @return boolean. True is equal
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
        final ObjectHandler other = (ObjectHandler) obj;
        if (!Objects.equals(this.num, other.num)) {
            return false;
        }
        if (!Objects.equals(this.myObject, other.myObject)) {
            return false;
        }
        return true;
    }
    
    
}
