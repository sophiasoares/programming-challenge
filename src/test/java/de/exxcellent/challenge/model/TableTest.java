package de.exxcellent.challenge.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Table class
 */
class TableTest {
    
    /**
     * Tests if the correct column index is found for existing column
     */
    @Test
    void shouldFindExistingColumnIndex() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {{"1", "20", "10"}};
        Table table = new Table(columnNames, data);
        
        assertEquals(0, table.getColumnIndex("Day"));
        assertEquals(1, table.getColumnIndex("MxT"));
        assertEquals(2, table.getColumnIndex("MnT"));
    }
    
    /**
     * Tests if -1 is returned for non-existent column
     */
    @Test
    void shouldReturnMinusOneForNonExistentColumn() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {{"1", "20", "10"}};
        Table table = new Table(columnNames, data);
        
        assertEquals(-1, table.getColumnIndex("NonExistent"));
    }
    
    /**
     * Tests if -1 is returned for null column name
     */
    @Test
    void shouldReturnMinusOneForNullColumnName() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {{"1", "20", "10"}};
        Table table = new Table(columnNames, data);
        
        assertEquals(-1, table.getColumnIndex(null));
    }
    
    /**
     * Tests if column search is case sensitive
     */
    @Test
    void shouldBeCaseSensitive() {
        String[] columnNames = {"Day", "MxT", "MnT"};
        Object[][] data = {{"1", "20", "10"}};
        Table table = new Table(columnNames, data);
        
        assertEquals(-1, table.getColumnIndex("day"));
        assertEquals(-1, table.getColumnIndex("MXT"));
        assertEquals(-1, table.getColumnIndex("mnt"));
    }
} 