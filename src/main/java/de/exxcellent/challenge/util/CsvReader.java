package de.exxcellent.challenge.util;

import de.exxcellent.challenge.model.Table;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads CSV files and returns a Table object.
 */
public class CsvReader implements Reader {
    
    @Override
    public Table readData(String path) {
        
        // Check if it is a csv file
        if (!path.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("File must end with .csv");
        }

        // Get the file from the resources folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalArgumentException("File " + path + " was not found");
        }

        try (inputStream;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            // Read header
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("File " + path + " is empty");
            }
            String[] columnNames = headerLine.split(",");
            
            // Read data
            List<String[]> rows = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != columnNames.length) {
                    throw new IllegalArgumentException(
                        String.format("Invalid CSV format: line has %d values, expected %d", 
                            values.length, columnNames.length));
                }
                rows.add(values);
            }
            
            // Convert to Object matrix
            Object[][] data = new Object[rows.size()][columnNames.length];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }
            
            return new Table(columnNames, data);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file: " + path, e);
        }
    }
}
