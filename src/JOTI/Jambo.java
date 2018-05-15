/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.Serializable;
import javax.swing.JFrame;

/**
 * The Jambo class packages the application data ready for serializing. It is
 * the top level Class of the application.
 *
 * @author Andrew Whitelaw
 */
public class Jambo implements Serializable {

    PersonManager myPeople;

    /**
     * Basic constructor
     */
    public Jambo() {
        this.myPeople = new PersonManager();
        FrmMainForm mainForm = FrmMainForm.getMainformInstance();
        mainForm.init(this);
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Overridden toString method for exporting the data to a text file. Returns
     * a String object
     *
     * @return String object containing the Jambo data
     */
    @Override
    public String toString() {
        String textFile;
        textFile = "\n --- Section List ---\n";
        textFile = textFile + this.myPeople.toString();
        return textFile;
    }

    /*
     * Getter and Setters
     */
}
