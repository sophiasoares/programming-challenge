package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;

/**
 * Interface for analyzing data
 */
public interface Analyzer {
    /**
     * Returns the index of the row with the minimum spread.
     * @param table The table to be analyzed
     * @param col1Name The name of the first column
     * @param col2Name The name of the second column
     * @return The index of the row with the minimum spread
     */
    int getMinimumSpreadIndex(Table table, String col1Name, String col2Name);
}
