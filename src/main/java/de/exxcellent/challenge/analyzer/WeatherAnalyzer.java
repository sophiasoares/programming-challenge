package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;

/**
 * Implementation of the Analyzer interface for weather data
 */
public class WeatherAnalyzer implements Analyzer {
    
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
        double minSpread = Double.MAX_VALUE;
        int minSpreadIndex = -1;
        
        for (int i = 0; i < data.length; i++) {
            try {
                double val1 = parseTemperature((String) data[i][col1Index]);
                double val2 = parseTemperature((String) data[i][col2Index]);
                double spread = Math.abs(val1 - val2);
                
                if (spread < minSpread) {
                    minSpread = spread;
                    minSpreadIndex = i;
                }
            } catch (NumberFormatException e) {
                // Skip rows with invalid numbers
                System.err.println("Could not parse temperature in row " + i);
            }
        }
        
        if (minSpreadIndex == -1) {
            throw new IllegalArgumentException("No valid data found for spread calculation");
        }
        
        return minSpreadIndex;
    }
    
    /**
     * Parses a temperature value from a string, handling common temperature formats
     *
     * @param value The string value to parse
     * @return The temperature as a double
     * @throws NumberFormatException if the value cannot be parsed as a number
     */
    private double parseTemperature(String value) {
        if (value == null) {
            throw new NumberFormatException("Temperature value cannot be null");
        }
        
        // Remove degree symbols
        String cleaned = value.replaceAll("[°˚৹⁰º]", "").trim().toUpperCase();          
                            
        // Remove unit suffixes
        if (cleaned.endsWith("C") || cleaned.endsWith("F") || cleaned.endsWith("K")) {
            cleaned = cleaned.substring(0, cleaned.length() - 1).trim();
        }
        
        return Double.parseDouble(cleaned);
    }
} 