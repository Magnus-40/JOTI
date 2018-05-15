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
 * Helper Class for formatting the Person Tables
 *
 * @author Andrew Whitelaw
 */
public class PersonTableModel extends AbstractTableModel {

    /**
     * Array for the column Header names
     */
    protected static final String[] COLUMN_NAMES = {
        "Qty",
        "Name",
        "Section",};

    private List<Person> rowData;

    /**
     * Generic Constructor
     */
    public PersonTableModel() {
        rowData = new ArrayList<>(25);
    }

    /**
     * Adds the Person object to the model
     *
     * @param pers single Person object
     */
    public void add(Person pers) {
        add(Arrays.asList(pers));
    }

    /**
     * Adds the Person List to the model
     *
     * @param pers List of Person Objects
     */
    public void add(List<Person> pers) {
        rowData.addAll(pers);
        fireTableDataChanged();
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
     * Person in the row
     *
     * @param row integer representing the row
     * @return Person object
     */
    public Person getPersonDataAt(int row) {
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
        Person pers = this.getPersonDataAt(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = pers.getContactList().size();
                break;
            case 1:
                value = pers.getForeName() + " " + pers.getSurName();
                break;
            case 2:
                value = pers.getMySection().getMySectionName();
                break;
        }
        return value;
    }
}
