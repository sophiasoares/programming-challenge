package de.exxcellent.challenge;

import de.exxcellent.challenge.analyzer.Analyzer;
import de.exxcellent.challenge.analyzer.WeatherAnalyzer;
import de.exxcellent.challenge.analyzer.FootballAnalyzer;
import de.exxcellent.challenge.model.Table;
import de.exxcellent.challenge.util.CsvReader;
import de.exxcellent.challenge.util.DataPreprocessing;
import de.exxcellent.challenge.util.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {
    
    private static final Logger logger = LogManager.getLogger(App.class);

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        Reader reader = new CsvReader();
        Analyzer weatherAnalyzer = new WeatherAnalyzer();
        Analyzer footballAnalyzer = new FootballAnalyzer();
        
        // Read and preprocess weather data
        Table weatherTable = reader.readData("de/exxcellent/challenge/weather.csv");
        weatherTable = DataPreprocessing.cleanWeatherData(weatherTable);
        
        // Analyze weather data
        int dayIndex = weatherAnalyzer.getMinimumSpreadIndex(weatherTable, "MxT", "MnT");
        String dayWithSmallestTempSpread = (String) weatherTable.getData()[dayIndex][0]; // Day is first column
        logger.info("Day with smallest temperature spread : {}", dayWithSmallestTempSpread);
    
        // Analyze football data
        Table footballTable = reader.readData("de/exxcellent/challenge/football.csv");
        int teamIndex = footballAnalyzer.getMinimumSpreadIndex(footballTable, "Goals", "Goals Allowed");
        String teamWithSmallestGoalSpread = (String) footballTable.getData()[teamIndex][0]; // Team is first column
        logger.info("Team with smallest goal spread       : {}", teamWithSmallestGoalSpread);
    }
}
