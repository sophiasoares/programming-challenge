package de.exxcellent.challenge.util;

import de.exxcellent.challenge.model.Table;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the CsvReader class.
 */
class CsvReaderTest {
    
    private final CsvReader csvReader = new CsvReader();
    
    /**
     * Tests if a valid csv file is read correctly
     */
    @Test
    void shouldReadValidCsvFile() {
        String path = "test.csv";
        Table table = csvReader.readData(path);
        
        assertNotNull(table);
        assertEquals(3, table.getColumnNames().length);
        assertEquals(3, table.getData().length);
        assertEquals("name", table.getColumnNames()[0]);
        assertEquals("age", table.getColumnNames()[1]);
        assertEquals("city", table.getColumnNames()[2]);
        assertEquals("John", table.getData()[0][0]);
        assertEquals("25", table.getData()[0][1]);
        assertEquals("New York", table.getData()[0][2]);
    }
    
    /**
     * Tests if an exception is thrown for a non-existent file
     */
    @Test
    void shouldThrowExceptionForNonExistentFile() {
        String path = "nonexistent.csv";
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> csvReader.readData(path)
        );
        assertTrue(exception.getMessage().contains("not found"));
    }
    
    /**
     * Tests if an exception is thrown for a non-csv file
     */
    @Test
    void shouldThrowExceptionForNonCsvFile() {
        String path = "test.txt";
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> csvReader.readData(path)
        );
        assertEquals("File must end with .csv", exception.getMessage());
    }

    /**
     * Tests if a CSV file with only headers is read correctly
     */
    @Test
    void shouldReadFileWithOnlyHeaders() {
        String path = "only_headers.csv";
        Table table = csvReader.readData(path);
        
        assertNotNull(table);
        assertEquals(3, table.getColumnNames().length);
        assertEquals(0, table.getData().length);
        assertEquals("name", table.getColumnNames()[0]);
        assertEquals("age", table.getColumnNames()[1]);
        assertEquals("city", table.getColumnNames()[2]);
    }

    /**
     * Tests if an exception is thrown for a completely empty file
     */
    @Test
    void shouldThrowExceptionForCompletelyEmptyFile() {
        String path = "completely_empty.csv";
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> csvReader.readData(path)
        );
        assertTrue(exception.getMessage().contains("empty"));
    }

    /**
     * Tests if an exception is thrown when rows have different number of columns
     */
    @Test
    void shouldThrowExceptionForInconsistentColumns() {
        String path = "invalid_columns.csv";
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> csvReader.readData(path)
        );
        assertTrue(exception.getMessage().contains("Invalid CSV format"));
    }

    /**
     * Tests how a file without a header row is handled
     * Note: This test demonstrates current behavior, but we might want to make this configurable
     */
    @Test
    void shouldTreatFirstRowAsHeader() {
        String path = "no_header.csv";
        Table table = csvReader.readData(path);
        
        assertNotNull(table);
        assertEquals(3, table.getColumnNames().length);
        assertEquals(2, table.getData().length);  // First row becomes header, so 2 data rows
        assertEquals("John", table.getColumnNames()[0]);  // First row becomes column names
        assertEquals("25", table.getColumnNames()[1]);
        assertEquals("New York", table.getColumnNames()[2]);
    }
} 