package de.exxcellent.challenge.util;

import de.exxcellent.challenge.model.Table;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the DataPreprocessor class
 */
class DataPreprocessingTest {
        
    /**
     * Tests if the temperature values are cleaned correctly
     */
    @Test
    void shouldCleanTemperatureValues() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20°C", "10°F"},
            {"2", "25 ˚", "5K"},
            {"3", "15⁰", "12º"}
        };
        Table table = new Table(columnNames, data);
        
        Table cleanedTable = DataPreprocessing.cleanWeatherData(table);
        
        // Day column should be unchanged
        assertEquals("1", cleanedTable.getData()[0][0]);
        assertEquals("2", cleanedTable.getData()[1][0]);
        assertEquals("3", cleanedTable.getData()[2][0]);
        
        // Temperature columns should be cleaned
        assertEquals("20", cleanedTable.getData()[0][1]);
        assertEquals("10", cleanedTable.getData()[0][2]);
        assertEquals("25", cleanedTable.getData()[1][1]);
        assertEquals("5", cleanedTable.getData()[1][2]);
        assertEquals("15", cleanedTable.getData()[2][1]);
        assertEquals("12", cleanedTable.getData()[2][2]);
    }
    
    /**
     * Tests if an exception is thrown when the table is null
     */
    @Test
    void shouldThrowExceptionForNullTable() {
        assertThrows(IllegalArgumentException.class, () -> 
        DataPreprocessing.cleanWeatherData(null));
    }
} 