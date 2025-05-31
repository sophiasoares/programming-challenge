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

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object[][] getData() {
        return data;
    }

    /**
     * Returns the index of the column with the given name
     * @param columnName The name of the column to find
     * @return The index of the column, or -1 if not found
     */
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(columnName)) return i;
        }
        return -1;
    }
}
