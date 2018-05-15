/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Singleton Class for loading the Award Levels from File.
 *
 * @author Andrew Whitelaw
 */
public class SGL_ContinentAwards {

    private final Map<Integer, String> globalLevels;
    private final Map<Integer, String> africaLevels;
    private final Map<Integer, String> americaLevels;
    private final Map<Integer, String> asiaLevels;
    private final Map<Integer, String> australasiaLevels;
    private final Map<Integer, String> europeLevels;

    private SGL_ContinentAwards() {
        globalLevels = new HashMap();
        africaLevels = new HashMap();
        americaLevels = new HashMap();
        asiaLevels = new HashMap();
        australasiaLevels = new HashMap();
        europeLevels = new HashMap();
        this.loadLevels();
    }

    /**
     * Loads the data from the text file Awards.txt into the global and
     * continental HashMaps
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private void loadLevels() {
        //System.out.println("Loading award levels");
        BufferedReader br = null;
        String fileName = "";
        try {
            String sCurrentLine;
            //System.out.println("Pathname " + Utils.getPathName());
            //Utils.getPathName() + File.separatorChar + 
            fileName = "Awards.txt";
            File f = new File(fileName);
            if (!f.exists()) {
                JFileChooser chooser = new JFileChooser();
                chooser.setApproveButtonText("Load");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Awards", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    br = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
                }
            } else {
                br = new BufferedReader(new FileReader(fileName));
            }
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 0) {
                    String[] parts = sCurrentLine.split("\t");
                    globalLevels.put(Integer.parseInt(parts[0]), parts[1].trim());
                    asiaLevels.put(Integer.parseInt(parts[2]), parts[3].trim());
                    africaLevels.put(Integer.parseInt(parts[4]), parts[5].trim());
                    americaLevels.put(Integer.parseInt(parts[6]), parts[7].trim());
                    australasiaLevels.put(Integer.parseInt(parts[8]), parts[9].trim());
                    europeLevels.put(Integer.parseInt(parts[10]), parts[11].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Getter for the singleton instance of the Continent awards
     *
     * @return Instance of the ContinentAwards object
     */
    public static SGL_ContinentAwards getInstance() {
        return SGLContinentAwardsHolder.INSTANCE;

    }

    private static class SGLContinentAwardsHolder {

        private static final SGL_ContinentAwards INSTANCE = new SGL_ContinentAwards();
    }

    /**
     * Getter for Global Awards
     *
     * @return Maps object of levels and string meaning of the level
     */
    public Map<Integer, String> getGlobalLevels() {
        return globalLevels;
    }

    /**
     * Getter for Africa Level
     *
     * @return Maps object of levels and string meaning of the level
     */
    public Map<Integer, String> getAfricaLevels() {
        return africaLevels;
    }

    /**
     * Getter for America Level
     *
     * @return Maps object of levels and string meaning of the level
     */
    public Map<Integer, String> getAmericaLevels() {
        return americaLevels;
    }

    /**
     * Getter for Asia Level
     *
     * @return Maps object of levels and string meaning of the level
     */
    public Map<Integer, String> getAsiaLevels() {
        return asiaLevels;
    }

    /**
     * Getter for Australasia Level
     *
     * @return Maps object of levels and string meaning of the level
     */
    public Map<Integer, String> getAustralasiaLevels() {
        return australasiaLevels;
    }

    /**
     * Getter for Europe Level
     *
     * @return Maps object of levels and string meaning of the level
     */
    public Map<Integer, String> getEuropeLevels() {
        return europeLevels;
    }
}
