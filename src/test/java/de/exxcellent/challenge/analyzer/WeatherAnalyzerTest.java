package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the WeatherAnalyzer class
 */
class WeatherAnalyzerTest {
    
    private WeatherAnalyzer analyzer;
    
    @BeforeEach
    void setUp() {
        analyzer = new WeatherAnalyzer();
    }
    
    /**
     * Tests if the minimum spread is found correctly
     */
    @Test
    void shouldFindMinimumSpread() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20", "10"},  // spread 10
            {"2", "25", "5"},   // spread 20
            {"3", "15", "12"},  // spread 3 (minimum)
            {"4", "30", "15"}   // spread 15
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(2, result); // 2 -> day 3
    }
    
    /**
     * Tests if the minimum spread is found correctly with various temperature formats
     */
    @Test
    void shouldHandleVariousTemperatureFormats() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20°C", "10°C"},     // spread 10
            {"2", "25 °F", "5°F"},     // spread 20
            {"3", "15˚", "12˚"},       // spread 3 (minimum)
            {"4", " 30 ", " 15 "}      // spread 15
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(2, result);
    }
    
    /**
     * Tests if the minimum spread is found correctly when there are negative temperatures
     */
    @Test
    void shouldHandleNegativeTemperatures() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20", "-10"},   // spread 30
            {"2", "-5", "-8"},    // spread 3 (minimum)
            {"3", "15", "-20"}    // spread 35
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(1, result);
    }
    
    /**
     * Tests if the minimum spread is found correctly when there is only one row
     */
    @Test
    void shouldHandleSingleRow() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20", "10"}
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(0, result);
    }
    
    /**
     * Tests if the minimum spread is found correctly when there are equal spreads
     */
    @Test
    void shouldHandleEqualSpreads() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20", "10"},  // spread 10
            {"2", "25", "15"},  // spread 10
            {"3", "30", "20"}   // spread 10
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(0, result); // return first occurrence
    }
    
    /**
     * Tests if an exception is thrown when the required columns are not found
     */
    @Test
    void shouldThrowExceptionForMissingColumns() {
        String[] columnNames = {"Day", "MaxTemp", "MinTemp"}; // Wrong column names
        Object[][] data = {
            {"1", "20", "10"}
        };
        Table table = new Table(columnNames, data);
        
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.getMinimumSpreadIndex(table, "MxT", "MnT"));
    }
    
    /**
     * Tests if invalid number formats are handled correctly by skipping the invalid row
     */
    @Test
    void shouldSkipRowWithInvalidNumber() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "invalid", "10"},
            {"2", "25", "5"}     // spread 20
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(1, result); // should use the valid row
    }
    
    /**
     * Tests if an exception is thrown when all rows contain invalid numbers
     */
    @Test
    void shouldThrowExceptionForAllInvalidRows() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "invalid", "10"},
            {"2", "25", "invalid"}
        };
        Table table = new Table(columnNames, data);
        
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.getMinimumSpreadIndex(table, "MxT", "MnT"));
    }
    
    /**
     * Tests if an exception is thrown when the table is null
     */
    @Test
    void shouldThrowExceptionForNullTable() {
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.getMinimumSpreadIndex(null, "MxT", "MnT"));
    }
} 