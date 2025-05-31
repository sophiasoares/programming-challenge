package de.exxcellent.challenge;

import de.exxcellent.challenge.analyzer.WeatherAnalyzer;
import de.exxcellent.challenge.model.Table;
import de.exxcellent.challenge.util.CsvReader;
import de.exxcellent.challenge.util.Reader;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        Reader reader = new CsvReader();
        WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();
        
        // Analyze weather data
        Table weatherTable = reader.readData("de/exxcellent/challenge/weather.csv");
        int dayIndex = weatherAnalyzer.getMinimumSpreadIndex(weatherTable, "MxT", "MnT");
        String dayWithSmallestTempSpread = (String) weatherTable.getData()[dayIndex][0]; // Day is first column
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
    }
}
