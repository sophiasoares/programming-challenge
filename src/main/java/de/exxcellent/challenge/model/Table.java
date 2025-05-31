package de.exxcellent.challenge.model;

/**
 * Represents tabular data with column names and a matrix of values.
 * Can store any type of data since the matrix is of type Object.
 */
public class Table {
    private final String[] columnNames;
    private final Object[][] data;

    public Table(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }
}
