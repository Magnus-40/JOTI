/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Helper Class for formatting the Contact Tables
 *
 * @author Andrew Whitelaw
 */
public class ContactTableModel extends AbstractTableModel {

    /**
     * Array for the column Header names
     */
    protected static final String[] COLUMN_NAMES = {
        "Qty",
        "Country",
        "Continent"};

    private List<Country> rowData;

    /**
     * Generic Constructor
     */
    public ContactTableModel() {
        rowData = new ArrayList<>(25);
    }

    /**
     * Adds the Contact object to the model
     *
     * @param cont single Contact object
     */
    public void add(Country cont) {
        add(Arrays.asList(cont));
    }

    /**
     * Adds the Contact List to the model
     *
     * @param cont List of Contact Objects
     */
    public void add(List<Country> cont) {
        rowData.addAll(cont);
        fireTableDataChanged();
    }
    
    /**
     * Removes all the row data
     */
    public void removeData(){
        rowData.clear();
    }

    /**
     * Returns and integer representing the number of items in the list
     *
     * @return integer representing the number of items in the list
     */
    @Override
    public int getRowCount() {
        return rowData.size();
    }

    /**
     * Returns and integer representing the number of columns
     *
     * @return number of columns
     */
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * Accepts an integer representing the desired rownumber and returns the
     * Contact in the row
     *
     * @param row integer representing the row
     * @return Contact object
     */
    public Country getContactDataAt(int row) {
        return rowData.get(row);
    }

    /**
     * Accepts an integer and returns a String representing the column name for
     * the desired column
     *
     * @param index integer for the column number
     * @return String representing the column name
     */
    @Override
    public String getColumnName(int index) {
        return COLUMN_NAMES[index];
    }

    /**
     * Returns the value in the indicated cell of the table
     *
     * @param rowIndex integer for the row
     * @param columnIndex integer for the column
     * @return Object of the cell contents
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Country cont = this.getContactDataAt(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = new ContactManager().getContactsPerCountry(cont.getCountryName()).size();
                break;
            case 1:
                value = cont.getCountryName();
                break;
            case 2:
                value = cont.getContinentName();
                break;
        }
        return value;
    }
}
