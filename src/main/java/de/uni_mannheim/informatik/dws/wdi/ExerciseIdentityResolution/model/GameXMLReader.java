package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * XML reader for Game records.
 */
public class GameXMLReader extends XMLMatchableReader<Game, Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Game createModelFromElement(Node node, String provenanceInfo) {
        // Extract the ID for each game
        String gameId = getValueFromChildElement(node, "ID");
        Game game = new Game(gameId, provenanceInfo);

        // Set the game name
        game.setName(getValueFromChildElement(node, "Game_Name"));

        // Extract and parse release year from Release_Date
        String releaseDate = getValueFromChildElement(node, "Release_Date");
        if (releaseDate != null && !releaseDate.isEmpty()) {
            try {
                // Extract the year (first 4 characters of the date)
                game.setReleaseYear(Integer.parseInt(releaseDate.substring(6, 10)));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                game.setReleaseYear(null); // Handle invalid or malformed dates
            }
        }

        // Set the genre
        game.setMainGenre(getValueFromChildElement(node, "Genre"));

        // Set the publisher
        game.setPublisherName(getValueFromChildElement(node, "Publisher"));

        // Set the developer
        game.setDeveloperName(getValueFromChildElement(node, "Developer"));

        // Set the global sales (if present; optional in this dataset)
        String globalSales = getValueFromChildElement(node, "Global_Sales");
        if (globalSales != null && !globalSales.isEmpty()) {
            try {
                game.setGlobalSales(Double.parseDouble(globalSales));
            } catch (NumberFormatException e) {
                game.setGlobalSales(0.0); // Handle invalid sales data
            }
        }

        // Set the price
        String price = getValueFromChildElement(node, "Price");
        if (price != null && !price.isEmpty()) {
            try {
                game.setPrice(Double.parseDouble(price));
            } catch (NumberFormatException e) {
                game.setPrice(0.0); // Handle invalid price data
            }
        }

        return game;
    }
}
