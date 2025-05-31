package de.exxcellent.challenge.util;

import de.exxcellent.challenge.model.Table;

/**
 * Interface for reading data from different sources.
 * The goal is to read files (csv, json, xml, etc.) or, for example, API endpoints 
 * and return a Table object.
 * For each of these sources, a different implementation of the Reader is needed.
 */
public interface Reader {
    /**
     * Reads data from a source and returns it as a Table object.
     *
     * @param path the path to the source to read
     * @return Table containing the data from the source
     */
    Table readData(String path);
} 