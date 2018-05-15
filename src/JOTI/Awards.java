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
import java.util.Objects;

/**
 * The Awards class is a class of methods and objects used to define and store
 * the various level of achievement of the person.
 *
 * @author Andrew Whitelaw
 */
public final class Awards {

    private Boolean continentAward = false;
    private String commonwealthLevel;
    // private PersonManager myPeople;
    private CountryManager myCountries;
    private ContactManager myContacts;
    private List<Contact> personalContacts;
    // All Contacts
    private Map<String, Integer> allContacts = new HashMap();
    private Map<String, String> allLevels = new HashMap();
    //Unique Contacts
    private Map<String, Integer> uniqueContacts = new HashMap();
    private Map<String, String> uniqueLevels = new HashMap();
    ContinentAwards myCont;
    private String continentString;
    private List<Award> allAwardList;
    private List<Award> uniAwardList;

    /**
     * Basic Constructor
     */
    public Awards() {
        myCountries = new CountryManager();
        myContacts = new ContactManager();
        myCont = new ContinentAwards();
        allAwardList = new ArrayList();
        uniAwardList = new ArrayList();
    }

    /**
     * Constructor when an Person object is supplied
     *
     * @param myPerson Person object for the person concerned.
     */
    public Awards(Person myPerson) {
        this();
        personalContacts = myPerson.getContactList();
        this.initMaps();
        this.setContactsMap(myPerson);
        this.setAllLevelsMap();
        this.setUniqueContactsMap(myPerson);
        this.setUniqueLevelsMap();
        this.setContinentAward(this.ContinentAward(myPerson));
        allAwardList = this.setAllAwardList(myPerson);
        uniAwardList = this.setUniqueAwardList(myPerson);
    }

    /**
     * Initialises all the maps and sets up default values
     */
    private void initMaps() {
        this.allContacts.put("Global", 0);
        this.allContacts.put("Africa", 0);
        this.allContacts.put("Americas", 0);
        this.allContacts.put("Asia", 0);
        this.allContacts.put("Australasia", 0);
        this.allContacts.put("Europe", 0);
        this.allLevels.put("Global", "");
        this.allLevels.put("Africa", "");
        this.allLevels.put("Americas", "");
        this.allLevels.put("Asia", "");
        this.allLevels.put("Australasia", "");
        this.allLevels.put("Europe", "");
        this.uniqueContacts.put("Global", 0);
        this.uniqueContacts.put("Africa", 0);
        this.uniqueContacts.put("Americas", 0);
        this.uniqueContacts.put("Asia", 0);
        this.uniqueContacts.put("Australasia", 0);
        this.uniqueContacts.put("Europe", 0);
        this.uniqueLevels.put("Global", "");
        this.uniqueLevels.put("Africa", "");
        this.uniqueLevels.put("Americas", "");
        this.uniqueLevels.put("Asia", "");
        this.uniqueLevels.put("Australasia", "");
        this.uniqueLevels.put("Europe", "");
    }

    /**
     * Returns a string object representing the commonwealth level attained for
     * the supplied person object
     *
     * @param aPerson The person object to be analysed
     * @return String object representing the Commonwealth attainment level
     */
    protected String getCommonwealthString(Person aPerson) {
        String myLevel = "";
        int commCount = this.getCommonwealthCount(aPerson);

        if (commCount == 0) {
            myLevel = "-";
        }
        if (commCount > 0) {
            myLevel = "Bronze";
        }
        if (commCount > 5) {
            myLevel = "Silver";
        }
        if (commCount > 10) {
            myLevel = "Gold";
        }
        if (commCount > 20) {
            myLevel = "Platinum";
        }
        if (commCount > 30) {
            myLevel = "Diamond";
        }
        return myLevel;
    }

    /**
     * Returns a count of all the commonwealth countries contacted, Returned
     * value is for the Person object supplied or if the Person is null, for all
     * contacts
     *
     * @param aPerson Person object
     * @return int count of all the unique commonwealth countries
     */
    protected int getCommonwealthCount(Person aPerson) {
        return this.getCommonwealthList(aPerson).size();
    }

    /**
     * Returns a count of all the commonwealth countries
     *
     * @return int count of commonwealth countries
     */
    protected int getCountofAllCommonwealth() {
        int count = 0;
        for (Country aCountry : myCountries.getFullCountryList()) {
            if (aCountry.isIsCommonwealth()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a List of all the commonwealth countries contacted, Returned List
     * is for the Person object supplied or if the Person is null, for all
     * contacts
     *
     * @param aPerson person object
     * @return ArrayList of Country objects
     */
    protected List<Country> getCommonwealthList(Person aPerson) {
        List<Country> myList;
        List<Country> returnList = new ArrayList();
        if (aPerson == null) {
            myList = myContacts.getUniqueContactsList(myContacts.getAllUniqueContacts());
        } else {
            myList = myContacts.getUniqueContactsPerPerson(aPerson);
        }
        for (Country myCountry : myList) {
            if (myCountry.isIsCommonwealth()) {
                returnList.add(myCountry);
            }
        }
        return returnList;
    }

    /**
     * Counts the number of contacts that have been made with unique initial
     * letters
     *
     * @return integer value of number of 'letters' contacted.
     */
    protected int getAlphabetAwardCount() {
        List<String> letterList = new ArrayList();
        String tempString;
        //step through all contacts
        for (Contact aCont : personalContacts) {
            tempString = String.valueOf(aCont.getMyCountry().getCountryName().charAt(0));
            //add to letter list if not already there
            if (!letterList.contains(tempString)) {
                letterList.add(tempString);
            }
        }
        return letterList.size();
    }

    /**
     * Returns a String value for the level of Alphabet Award achieved. Calls:
     * GetAlphabetAwardCount
     *
     * @return String value for the achievement level
     */
    protected String getAlphabetAward() {
        int alphabetCount = this.getAlphabetAwardCount();
        String returnString = " ";
        if (alphabetCount == 0) {
            returnString = "-";
        }
        if (alphabetCount > 0) {
            returnString = "Bronze";
        }
        if (alphabetCount > 4) {
            returnString = "Silver";
        }
        if (alphabetCount > 9) {
            returnString = "Gold";
        }
        if (alphabetCount > 14) {
            returnString = "Platinum";
        }
        if (alphabetCount > 19) {
            returnString = "Diamond";
        }
        return returnString;
    }

    /**
     * Continent award is true is the person has more than 0 contacts in each
     * continent
     *
     * @param myPerson the person object representing the person to be checked
     * @return true is award is valid
     */
    private Boolean ContinentAward(Person myPerson) {
        Boolean africaContacted = false;
        Boolean americaContacted = false;
        Boolean asiaContacted = false;
        Boolean australasiaContacted = false;
        Boolean europeContacted = false;
        String continentName;
        StringBuilder awardString = new StringBuilder();

        //get the list of contacts for the supplied person
        List<Contact> cont;
        cont = myPerson.getContactList();
        // Step through the list
        for (Contact aContact : cont) {
            // get the country and continent of the contact
            continentName = aContact.getMyCountry().getContinentName();
            if (continentName.equals("Africa")) {
                africaContacted = true;
            }
            if (continentName.equals("Americas")) {
                americaContacted = true;
            }
            if (continentName.equals("Asia")) {
                asiaContacted = true;
            }
            if (continentName.equals("Australasia")) {
                australasiaContacted = true;
            }
            if (continentName.equals("Europe")) {
                europeContacted = true;
            }
        }
        if (africaContacted) {
            awardString.append("Africa Award.\t\tFor making contact with at least one person in Africa.\n");
        }
        if (americaContacted) {
            awardString.append("America Award.\t\tFor making contact with at least one person in the Americas.\n");
        }
        if (asiaContacted) {
            awardString.append("Asia Award.\t\tFor making contact with at least one person in Asia.\n");
        }
        if (australasiaContacted) {
            awardString.append("Australasia Award.\tFor making contact with at least one person in Australasia.\n");
        }
        if (europeContacted) {
            awardString.append("Europe Award.\t\tFor making contact with at least one person in Europe.\n");
        }
        this.continentString = awardString.toString();
        if (africaContacted && americaContacted && asiaContacted && australasiaContacted && europeContacted) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Set the number of contacts per continent per person
     *
     * @param myPerson the person object representing the person to be checked
     */
    private void setContactsMap(Person myPerson) {
        String continent;
        //Get the contacts list for the supplied person
        List<Contact> aContacts = myPerson.getContactList();
        //assign global contacts
        this.allContacts.put("Global", aContacts.size());
        //step through contacts and assign each per continent
        for (Contact myContact : aContacts) {
            continent = myContact.getMyCountry().getContinentName();
            allContacts.put(continent, allContacts.get(continent) + 1);
        }
    }

    /**
     * Getter for All Award List
     *
     * @return List of All Award Objects
     */
    public List<Award> getAllAwardList() {
        return allAwardList;
    }

    /**
     * Getter for Unique Award List
     *
     * @return List of Unique Award Objects
     */
    public List<Award> getUniAwardList() {
        return uniAwardList;
    }

    /**
     * Sets the All Award List based on the supplied Person
     *
     * @param myPerson Person Object for the required person
     * @return List of Award objects containing All the contacts
     */
    public List<Award> setAllAwardList(Person myPerson) {
        List<Award> awd = new ArrayList();
        Award award;
        int cnt;
        cnt = this.getAllContacts("Global");
        award = new Award("Global", cnt, myCont.getAward("globalLevels", cnt));
        awd.add(award);
        cnt = this.getAllContacts("Africa");
        award = new Award("Africa", cnt, myCont.getAward("africaLevels", cnt));
        awd.add(award);
        cnt = this.getAllContacts("Americas");
        award = new Award("Americas", cnt, myCont.getAward("americaLevels", cnt));
        awd.add(award);
        cnt = this.getAllContacts("Asia");
        award = new Award("Asia", cnt, myCont.getAward("asiaLevels", cnt));
        awd.add(award);
        cnt = this.getAllContacts("Australasia");
        award = new Award("Australasia", cnt, myCont.getAward("australasiaLevels", cnt));
        awd.add(award);
        cnt = this.getAllContacts("Europe");
        award = new Award("Europe", cnt, myCont.getAward("europeLevels", cnt));
        awd.add(award);
        return awd;
    }

    /**
     * Sets the Unique Award List based on the supplied Person
     *
     * @param myPerson Person Object for the required person
     * @return List of Award objects containing unique contacts
     */
    public List<Award> setUniqueAwardList(Person myPerson) {
        List<Award> awd = new ArrayList();
        Award award;
        int cnt;
        cnt = this.getUniqueContacts("Global");
        award = new Award("Global", cnt, myCont.getAward("globalLevels", cnt));
        awd.add(award);
        cnt = this.getUniqueContacts("Africa");
        award = new Award("Africa", cnt, myCont.getAward("africaLevels", cnt));
        awd.add(award);
        cnt = this.getUniqueContacts("Americas");
        award = new Award("Americas", cnt, myCont.getAward("americaLevels", cnt));
        awd.add(award);
        cnt = this.getUniqueContacts("Asia");
        award = new Award("Asia", cnt, myCont.getAward("asiaLevels", cnt));
        awd.add(award);
        cnt = this.getUniqueContacts("Australasia");
        award = new Award("Australasia", cnt, myCont.getAward("australasiaLevels", cnt));
        awd.add(award);
        cnt = this.getUniqueContacts("Europe");
        award = new Award("Europe", cnt, myCont.getAward("europeLevels", cnt));
        awd.add(award);
        return awd;
    }

    /**
     * Set the number of unique contacts per continent per person
     *
     * @param myPerson the person object representing the person to be checked
     */
    private void setUniqueContactsMap(Person myPerson) {
        String continent;
        int numContacts;
        //Get the contacts list for the supplied person
        List<Country> aContacts = myContacts.getUniqueContactsPerPerson(myPerson);
        //assign global contacts
        this.uniqueContacts.put("Global", aContacts.size());
        //step through contacts and assign per continenet
        for (Country myCountry : aContacts) {
            continent = myCountry.getContinentName();
            numContacts = uniqueContacts.get(continent) + 1;
            uniqueContacts.put(continent, numContacts);
        }
    }

    /**
     * Sets the allLevels Map with the continent name as the key and a string
     * representing the level attained as the value
     */
    private void setAllLevelsMap() {
        this.allLevels.put("Global", getAward("Global", this.getAllContacts("Global")));
        this.allLevels.put("Africa", getAward("Africa", this.getAllContacts("Africa")));
        this.allLevels.put("Americas", getAward("Americas", this.getAllContacts("Americas")));
        this.allLevels.put("Asia", getAward("Asia", this.getAllContacts("Asia")));
        this.allLevels.put("Australasia", getAward("Australasia", this.getAllContacts("Australasia")));
        this.allLevels.put("Europe", getAward("Europe", this.getAllContacts("Europe")));
    }

    private void setUniqueLevelsMap() {
        this.uniqueLevels.put("Global", getAward("Global", this.getUniqueContacts("Global")));
        this.uniqueLevels.put("Africa", getAward("Africa", this.getUniqueContacts("Africa")));
        this.uniqueLevels.put("Americas", getAward("Americas", this.getUniqueContacts("Americas")));
        this.uniqueLevels.put("Asia", getAward("Asia", this.getUniqueContacts("Asia")));
        this.uniqueLevels.put("Australasia", getAward("Australasia", this.getUniqueContacts("Australasia")));
        this.uniqueLevels.put("Europe", getAward("Europe", this.getUniqueContacts("Europe")));
    }

    /**
     * Returns the string describing the levels for all contacts for the
     * supplied Continent
     *
     * @param continent String value representing the name of the Continent
     * @return String containing the level text for the supplied continent.
     */
    protected String getAllLevels(String continent) {
        return this.allLevels.get(continent);
    }

    /**
     * Returns the integer for the total number of contacts for the supplied
     * Continent
     *
     * @param continent String value representing the name of the Continent
     * @return integer value for total number of contacts per supplies continent
     */
    protected int getAllContacts(String continent) {
        return this.allContacts.get(continent);
    }

    /**
     * Returns the string describing the levels for unique contacts for the
     * supplied Continent
     *
     * @param continent String value representing the name of the Continent
     * @return String containing the level text for the supplied continent.
     */
    protected String getUniqueLevels(String continent) {
        return this.uniqueLevels.get(continent);
    }

    /**
     * Returns the integer for the unique number of contacts for the supplied
     * Continent
     *
     * @param continent String value representing the name of the Continent
     * @return integer value for unique number of contacts per supplies
     * continent
     */
    protected int getUniqueContacts(String continent) {
        return this.uniqueContacts.get(continent);
    }

    /**
     * Returns a string object for the award level for the supplied continent
     * name
     *
     * @param cont String object containing the continent name
     * @param numContacts integer value representing the number of contacts
     * @return String object for award level
     */
    private String getAward(String cont, int numContacts) {

        String myAward = "";
        int myLevel;
        Map<Integer, String> myMap = new HashMap();
        if (cont.equals("Global")) {
            myMap = myCont.getGlobalLevels();
        }
        if (cont.equals("Africa")) {
            myMap = myCont.getAfricaLevels();
        }
        if (cont.equals("Americas")) {
            myMap = myCont.getAmericaLevels();
        }
        if (cont.equals("Asia")) {
            myMap = myCont.getAsiaLevels();
        }
        if (cont.equals("Australasia")) {
            myMap = myCont.getAustralasiaLevels();
        }
        if (cont.equals("Europe")) {
            myMap = myCont.getEuropeLevels();
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
     * Returns a String of the total number of contacts globally and per
     * continent separated by newline
     *
     * @return String of contact counts
     */
    public String getAllContactsString() {
        StringBuilder awardText = new StringBuilder();
        awardText.append("");
        //AllContacts
        awardText.append(this.getAllContacts("Global"));
        awardText.append("\n").append(this.getAllContacts("Africa"));
        awardText.append("\n").append(this.getAllContacts("Americas"));
        awardText.append("\n").append(this.getAllContacts("Asia"));
        awardText.append("\n").append(this.getAllContacts("Australasia"));
        awardText.append("\n").append(this.getAllContacts("Europe"));
        return awardText.toString();
    }

    /**
     * Returns a String of the award levels globally and per continent separated
     * by newline
     *
     * @return String of contact counts
     */
    public String getAllLevelsString() {
        StringBuilder awardText = new StringBuilder();
        awardText.append("");
        //AllContacts
        awardText.append(this.getAllLevels("Global"));
        awardText.append("\n").append(this.getAllLevels("Africa"));
        awardText.append("\n").append(this.getAllLevels("Americas"));
        awardText.append("\n").append(this.getAllLevels("Asia"));
        awardText.append("\n").append(this.getAllLevels("Australasia"));
        awardText.append("\n").append(this.getAllLevels("Europe"));
        return awardText.toString();
    }

    /**
     * Returns a String of the total number of contacts globally and per
     * continent separated by newline
     *
     * @return String of contact counts
     */
    public String getUniqueContactsString() {
        StringBuilder awardText = new StringBuilder();
        awardText.append("");
        //UniqueContacts
        awardText.append(this.getUniqueContacts("Global"));
        awardText.append("\n").append(this.getUniqueContacts("Africa"));
        awardText.append("\n").append(this.getUniqueContacts("Americas"));
        awardText.append("\n").append(this.getUniqueContacts("Asia"));
        awardText.append("\n").append(this.getUniqueContacts("Australasia"));
        awardText.append("\n").append(this.getUniqueContacts("Europe"));
        return awardText.toString();
    }

    /**
     * Returns a String of the award levels globally and per continent separated
     * by newline
     *
     * @return String of contact counts
     */
    public String getUniqueLevelsString() {
        StringBuilder awardText = new StringBuilder();
        awardText.append("");
        //UniqueContacts
        awardText.append(this.getUniqueLevels("Global"));
        awardText.append("\n").append(this.getUniqueLevels("Africa"));
        awardText.append("\n").append(this.getUniqueLevels("Americas"));
        awardText.append("\n").append(this.getUniqueLevels("Asia"));
        awardText.append("\n").append(this.getUniqueLevels("Australasia"));
        awardText.append("\n").append(this.getUniqueLevels("Europe"));
        return awardText.toString();
    }

    /**
     * Standard Getter for the Award Class
     *
     * @return An Award object
     */
    public Boolean getContinentAward() {
        return continentAward;
    }

    /**
     * Getter for the continentString property
     *
     * @return String representing the name of the continent
     */
    public String getContinentString() {
        return continentString;
    }

    /**
     * Setter for the continentString property
     *
     * @param continentString String representing the name of the continent
     */
    public void setContinentString(String continentString) {
        this.continentString = continentString;
    }

    /**
     * Standard Setter for the Award class
     *
     * @param continentAward Boolean value representing whether or not the
     * person has gained the Continent Award
     */
    public void setContinentAward(Boolean continentAward) {
        this.continentAward = continentAward;
    }

    /**
     * Getter for Commonwealth levels
     *
     * @return commonwealthLevel String value representing the text level for
     * commonwealth
     */
    public String getCommonwealthLevel() {
        return commonwealthLevel;
    }

    /**
     * Getter for the personalContacts property
     *
     * @return List of Contacts objects
     */
    public List<Contact> getPersonalContacts() {
        return personalContacts;
    }

    /**
     * Overrridden Hash value
     *
     * @return Integer value for the hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Overridden equals object
     *
     * @param obj General object
     * @return boolean object. True if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Awards other = (Awards) obj;
        if (!Objects.equals(this.continentAward, other.continentAward)) {
            return false;
        }
        return true;
    }
}
