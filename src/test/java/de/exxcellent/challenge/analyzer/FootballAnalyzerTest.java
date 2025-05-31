package de.exxcellent.challenge.analyzer;

import de.exxcellent.challenge.model.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the FootballAnalyzer class
 */
class FootballAnalyzerTest {
    
    private FootballAnalyzer analyzer;
    
    @BeforeEach
    void setUp() {
        analyzer = new FootballAnalyzer();
    }
    
    /**
     * Tests if the minimum spread is found correctly
     */
    @Test
    void shouldFindMinimumSpread() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "10", "5"},     // spread 5
            {"TeamB", "15", "5"},     // spread 10
            {"TeamC", "7", "6"},      // spread 1 (minimum)
            {"TeamD", "20", "8"}      // spread 12
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(2, result); // 2 -> TeamC
    }
    
    /**
     * Tests if the minimum spread is found correctly when teams have equal spreads
     */
    @Test
    void shouldHandleEqualSpreads() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "10", "5"},     // spread 5
            {"TeamB", "15", "10"},    // spread 5
            {"TeamC", "25", "20"}     // spread 5
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(0, result); // return first occurrence
    }
    
    /**
     * Tests if the minimum spread is found correctly with a single team
     */
    @Test
    void shouldHandleSingleTeam() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "10", "5"}
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(0, result);
    }
    
    /**
     * Tests if an exception is thrown when the required columns are not found
     */
    @Test
    void shouldThrowExceptionForMissingColumns() {
        String[] columnNames = {"Team", "Scored", "Conceded"}; // Wrong column names
        Object[][] data = {
            {"TeamA", "10", "5"}
        };
        Table table = new Table(columnNames, data);
        
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed"));
    }
    
    /**
     * Tests if invalid number formats are handled correctly by skipping the invalid row
     */
    @Test
    void shouldSkipRowWithInvalidNumber() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "invalid", "5"},
            {"TeamB", "10", "5"}      // spread 5
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(1, result); // should use the valid row
    }
    
    /**
     * Tests if an exception is thrown when all rows contain invalid numbers
     */
    @Test
    void shouldThrowExceptionForAllInvalidRows() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "invalid", "5"},
            {"TeamB", "10", "invalid"}
        };
        Table table = new Table(columnNames, data);
        
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed"));
    }
    
    /**
     * Tests if an exception is thrown when the table is null
     */
    @Test
    void shouldThrowExceptionForNullTable() {
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.getMinimumSpreadIndex(null, "Goals", "Goals Allowed"));
    }
    
    /**
     * Tests if the minimum spread is found correctly with zero goals
     */
    @Test
    void shouldHandleZeroGoals() {
        String[] columnNames = {"Team", "Goals", "Goals Allowed"};
        Object[][] data = {
            {"TeamA", "0", "5"},      // spread 5
            {"TeamB", "10", "10"},    // spread 0 (minimum)
            {"TeamC", "5", "0"}       // spread 5
        };
        Table table = new Table(columnNames, data);
        
        int result = analyzer.getMinimumSpreadIndex(table, "Goals", "Goals Allowed");
        
        assertEquals(1, result);
    }
} 