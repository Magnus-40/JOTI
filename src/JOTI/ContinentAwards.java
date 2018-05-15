/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ContinentAwards is a class for handling the awards based on countries and
 * continents
 *
 * @author Andrew Whitelaw
 */
public class ContinentAwards {

    private Map<Integer, String> globalLevels;
    private Map<Integer, String> africaLevels;
    private Map<Integer, String> americaLevels;
    private Map<Integer, String> asiaLevels;
    private Map<Integer, String> australasiaLevels;
    private Map<Integer, String> europeLevels;

    /**
     * Constructor for ContinentAwards. Creates Object and loads data
     * Uses the Singleton Class
     */
    public ContinentAwards() {
        globalLevels = SGL_ContinentAwards.getInstance().getGlobalLevels();
        africaLevels = SGL_ContinentAwards.getInstance().getAfricaLevels();
        americaLevels = SGL_ContinentAwards.getInstance().getAmericaLevels();
        asiaLevels = SGL_ContinentAwards.getInstance().getAsiaLevels();
        australasiaLevels = SGL_ContinentAwards.getInstance().getAustralasiaLevels();
        europeLevels = SGL_ContinentAwards.getInstance().getEuropeLevels();
    }

    /**
     * GetAward creates a string value representing all the awards and levels
     *
     * @param cont String value representing the continent
     * @param numContacts integer value for the number of continents
     * @return String value representing all the awards and levels
     */
    public String getAward(String cont, int numContacts) {
        String myAward = "";
        int myLevel;
        Map<Integer, String> myMap = new HashMap();
        if (cont.equals("globalLevels")) {
            myMap = globalLevels;
        }
        if (cont.equals("africaLevels")) {
            myMap = africaLevels;
        }
        if (cont.equals("americaLevels")) {
            myMap = americaLevels;
        }
        if (cont.equals("asiaLevels")) {
            myMap = asiaLevels;
        }
        if (cont.equals("australasiaLevels")) {
            myMap = australasiaLevels;
        }
        if (cont.equals("europeLevels")) {
            myMap = europeLevels;
        }
        //get list of levels from required Map
        List<Integer> myList = new ArrayList(myMap.keySet());
        Collections.sort(myList);   //order list
        //Step through list until the set entry > supplied level
        myLevel = 0;
        for (Integer level : myList) {
            if (!(numContacts < level)) {
                myLevel = level;
            }
            myAward = myMap.get(myLevel);
        }
        return myAward;
    }

    /**
     * Getter for Global Levels
     *
     * @return Map Collection of levels and Strings representing
     */
    public Map<Integer, String> getGlobalLevels() {
        return globalLevels;
    }

    /**
     * Setter for global levels
     *
     * @param globalLevels Map of contacts and continent names
     */
    public void setGlobalLevels(Map<Integer, String> globalLevels) {
        this.globalLevels = globalLevels;
    }

    /**
     * Getter for African levels
     *
     * @return Map of contacts and continent names
     */
    public Map<Integer, String> getAfricaLevels() {
        return africaLevels;
    }

    /**
     * Setter for African levels
     *
     * @param africaLevels Setter for African levels
     */
    public void setAfricaLevels(Map<Integer, String> africaLevels) {
        this.africaLevels = africaLevels;
    }

    /**
     * Getter for American levels
     *
     * @return Map of American levels
     */
    public Map<Integer, String> getAmericaLevels() {
        return americaLevels;
    }

    /**
     * Setter for African levels
     *
     * @param americaLevels Map of American levels
     */
    public void setAmericaLevels(Map<Integer, String> americaLevels) {
        this.americaLevels = americaLevels;
    }

    /**
     * Getter for Asian levels
     *
     * @return Map of Asian
     */
    public Map<Integer, String> getAsiaLevels() {
        return asiaLevels;
    }

    /**
     * Setter for Asian levels
     *
     * @param asiaLevels Map of Asian
     */
    public void setAsiaLevels(Map<Integer, String> asiaLevels) {
        this.asiaLevels = asiaLevels;
    }

    /**
     * Getter for Australasian levels
     *
     * @return Map of Australasian levels
     */
    public Map<Integer, String> getAustralasiaLevels() {
        return australasiaLevels;
    }

    /**
     * Setter for Australasian levels
     *
     * @param australasiaLevels Map of Australasian levels
     */
    public void setAustralasiaLevels(Map<Integer, String> australasiaLevels) {
        this.australasiaLevels = australasiaLevels;
    }

    /**
     * Setter for European levels
     *
     * @return Map of European levels
     */
    public Map<Integer, String> getEuropeLevels() {
        return europeLevels;
    }

    /**
     * Setter for African levels
     *
     * @param europeLevels Map of European levels
     */
    public void setEuropeLevels(Map<Integer, String> europeLevels) {
        this.europeLevels = europeLevels;
    }
}
