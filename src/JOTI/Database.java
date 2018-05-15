/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * General class for saving and loading the database from and to the Jambo
 * object
 *
 * @author Andrew Whitelaw
 *
 * TODO add file selector
 * TODO Timestamp the files and allow for file cleanups
 */
public class Database {

    /**
     * Reads data from the saved data file into the Jambo object and returns it
     *
     * @param myJambo Top level Jambo object Used to save database
     * @return Jambo Object
     * @throws java.io.FileNotFoundException missing file error
     * @throws java.io.IOException general IO error
     * @throws java.lang.ClassNotFoundException missing class
     * 
     *
     */
    public static Jambo LoadDatabase(Jambo myJambo) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream("JOTI.data");
        ObjectInputStream ois = new ObjectInputStream(fin);
        myJambo = (Jambo) ois.readObject();
        return myJambo;
    }

    /**
     * Accepts a Jambo object and saves the object to file
     *
     * @param myJam Jambo Object
     * @throws java.io.FileNotFoundException missing file error
     */
    public static void SaveDatabase(Jambo myJam) throws FileNotFoundException, IOException {

        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream("JOTI.data");
        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
        // Write object out to disk
        obj_out.writeObject(myJam);

    }
}
