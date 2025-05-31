package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class for analyzing data with common functionality
 */
public abstract class Analyzer {
    
    private static final Logger logger = LogManager.getLogger(Analyzer.class);
    
    /**
     * Returns the index of the row with the minimum spread.
     * @param table The table to be analyzed
     * @param col1Name The name of the first column
     * @param col2Name The name of the second column
     * @return The index of the row with the minimum spread
     */
    public int getMinimumSpreadIndex(Table table, String col1Name, String col2Name) {
        if (table == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }
        
        Object[][] data = table.getData();
        
        // Get column indices
        int col1Index = table.getColumnIndex(col1Name);
        int col2Index = table.getColumnIndex(col2Name);
        
        if (col1Index == -1 || col2Index == -1) {
            throw new IllegalArgumentException("Column not found: " + col1Name + " or " + col2Name);
        }
        
        // Find minimum spread
        double minSpread = Double.MAX_VALUE;
        int minSpreadIndex = -1;
        
        for (int i = 0; i < data.length; i++) {
            try {
                double val1 = Double.parseDouble((String) data[i][col1Index]);
                double val2 = Double.parseDouble((String) data[i][col2Index]);
                double spread = Math.abs(val1 - val2);
                
                if (spread < minSpread) {
                    minSpread = spread;
                    minSpreadIndex = i;
                }
            } catch (NumberFormatException e) {
                // Skip rows with invalid numbers but log which values caused the problem
                logger.warn("Row {}: Could not parse values '{}' and '{}' from columns {} and {}", 
                    i + 1, data[i][col1Index], data[i][col2Index], col1Name, col2Name);
            }
        }
        
        if (minSpreadIndex == -1) {
            throw new IllegalArgumentException("No valid data found for spread calculation");
        }
        
        return minSpreadIndex;
    }
}
