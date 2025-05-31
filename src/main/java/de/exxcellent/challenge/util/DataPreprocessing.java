package de.exxcellent.challenge.util;

import de.exxcellent.challenge.model.Table;

/**
 * Class for preprocessing data before analysis
 */
public abstract class DataPreprocessing {
    /**
     * Clean all temperature values in a weather data table
     * @param table The table containing weather data
     * @return A new table with cleaned values
     */
    public static Table cleanWeatherData(Table table) {
        if (table == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }

        String[] columnNames = table.getColumnNames();
        Object[][] data = table.getData();
        Object[][] cleanedData = new Object[data.length][data[0].length];

        // Copy the data array and clean temperature values
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (columnNames[j].equals("MxT") || columnNames[j].equals("MnT")) {
                    cleanedData[i][j] = cleanTemperatureValue((String) data[i][j]);
                } else {
                    cleanedData[i][j] = data[i][j];
                }
            }
        }

        return new Table(columnNames, cleanedData);
    }

    /**
     * Clean temperature value by removing degree symbols and unit suffixes
     * @param value The temperature value to clean
     * @return The cleaned value string
     */
    public static String cleanTemperatureValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        
        // Remove degree symbols and convert to uppercase
        String cleaned = value.replaceAll("[°˚৹⁰º]", "").trim().toUpperCase();
        
        // Remove unit suffixes
        if (cleaned.endsWith("C") || cleaned.endsWith("F") || cleaned.endsWith("K")) {
            cleaned = cleaned.substring(0, cleaned.length() - 1).trim();
        }
        
        return cleaned;
    }
} 