package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Analyzer implementations
 */
class AnalyzerTest {
    
    private Analyzer weatherAnalyzer;
    private Analyzer footballAnalyzer;
    
    @BeforeEach
    void setUp() {
        weatherAnalyzer = new WeatherAnalyzer();
        footballAnalyzer = new FootballAnalyzer();
    }
    
    /**
     * Tests if the minimum temperature spread is found correctly
     */
    @Test
    void shouldFindMinimumTemperatureSpread() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {
            {"1", "20", "10"},  // spread 10
            {"2", "25", "5"},   // spread 20
            {"3", "15", "12"},  // spread 3 (minimum)
            {"4", "30", "15"}   // spread 15
        };
        Table table = new Table(columnNames, data);
        
        int result = weatherAnalyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(2, result);
    }
    
    /**
     * Tests if the minimum goal spread is found correctly
     */
    @Test
    void shouldFindMinimumGoalSpread() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "10", "5"},     // spread 5
            {"TeamB", "15", "5"},     // spread 10
            {"TeamC", "7", "6"},      // spread 1 (minimum)
            {"TeamD", "20", "8"}      // spread 12
        };
        Table table = new Table(columnNames, data);
        
        int result = footballAnalyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(2, result); // TeamC has minimum spread
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
        
        int result = weatherAnalyzer.getMinimumSpreadIndex(table, "MxT", "MnT");
        
        assertEquals(0, result); // Return first occurrence
    }
    
    /**
     * Tests if the minimum spread is found correctly when there is only one row
     */
    @Test
    void shouldHandleSingleRow() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "10", "5"}
        };
        Table table = new Table(columnNames, data);
        
        int result = footballAnalyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(0, result);
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
            weatherAnalyzer.getMinimumSpreadIndex(table, "MxT", "MnT"));
    }
    
    /**
     * Tests if an exception is thrown when the table is null
     */
    @Test
    void shouldThrowExceptionForNullTable() {
        assertThrows(IllegalArgumentException.class, () ->
            footballAnalyzer.getMinimumSpreadIndex(null, "Goals", "Goals Allowed"));
    }
} 