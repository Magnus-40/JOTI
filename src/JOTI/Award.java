/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

/**
 *  Award Class for handling award based data for display
 * @author Andrew Whitelaw
 */
public class Award {
    private String award;
    private int qty;
    private String level;

    /**
     * Constructor for Award Class with supplied Data
     * @param award String representing the type of award (continent)
     * @param qty   Integer representing the number of contacts
     * @param level String representing the level of the award
     */
    public Award(String award, int qty, String level) {
        this.award = award;
        this.qty = qty;
        this.level = level;
    }
    
    

    /**
     * Getter for Award
     * @return String representing the type of award (continent)
     */
    public String getAward() {
        return award;
    }

    /**
     * Setter for Award
     * @param award String representing the type of award (continent)
     */
    public void setAward(String award) {
        this.award = award;
    }

    /**
     * Getter for quantity
     * @return Integer representing the number of contacts
     */
    public int getQty() {
        return qty;
    }

    /**
     * Setter for quantity
     * @param qty Integer representing the number of contacts
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * Getter for level
     * @return String representing the level of the award
     */
    public String getLevel() {
        return level;
    }

    /**
     * Setter for level
     * @param level String representing the level of the award
     */
    public void setLevel(String level) {
        this.level = level;
    }
    
    /**
     * Overridden toString
     * @return String representing the Award Object
     */
    @Override
    public String toString(){
        String returnString=null;
        returnString=this.getAward() + " " + this.getQty() + " " + this.getLevel();
                return returnString;
    }
    
}
