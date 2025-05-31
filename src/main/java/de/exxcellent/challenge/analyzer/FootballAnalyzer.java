package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;

/**
 * Implementation of the Analyzer interface for football data
 */
public class FootballAnalyzer implements Analyzer {
    
    @Override
    public int getMinimumSpreadIndex(Table table, String col1Name, String col2Name) {
        if (table == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }

        String[] columnNames = table.getColumnNames();
        Object[][] data = table.getData();
        
        // Find column indices
        int col1Index = -1;
        int col2Index = -1;
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(col1Name)) col1Index = i;
            if (columnNames[i].equals(col2Name)) col2Index = i;
        }
        
        if (col1Index == -1 || col2Index == -1) {
            throw new IllegalArgumentException("Column not found: " + col1Name + " or " + col2Name);
        }
        
        // Find minimum spread
        int minSpread = Integer.MAX_VALUE;
        int minSpreadIndex = -1;
        
        for (int i = 0; i < data.length; i++) {
            try {
                int val1 = Integer.parseInt(((String) data[i][col1Index]).trim());
                int val2 = Integer.parseInt(((String) data[i][col2Index]).trim());
                int spread = Math.abs(val1 - val2);
                
                if (spread < minSpread) {
                    minSpread = spread;
                    minSpreadIndex = i;
                }
            } catch (NumberFormatException e) {
                // Skip rows with invalid numbers
                System.err.println("Could not parse goals in row " + i);
            }
        }
        
        if (minSpreadIndex == -1) {
            throw new IllegalArgumentException("No valid data found for spread calculation");
        }
        
        return minSpreadIndex;
    }
} 