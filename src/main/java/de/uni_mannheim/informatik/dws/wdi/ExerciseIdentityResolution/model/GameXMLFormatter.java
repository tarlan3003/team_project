package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Game}s.
 * 
 */
public class GameXMLFormatter extends XMLFormatter<Game> {

    @Override
    public Element createRootElement(Document doc) {
        // The root element will be <games> for all game records
        return doc.createElement("Games");
    }

    @Override
    public Element createElementFromRecord(Game record, Document doc) {
        // Create the <game> element
        Element game = doc.createElement("game");

        // Add child elements for all game attributes
        game.appendChild(createTextElement("ID", record.getGameId(), doc));
        game.appendChild(createTextElement("Name", record.getName(), doc));
        if (record.getReleaseYear() != null) {
            game.appendChild(createTextElement("ReleaseYear", record.getReleaseYear().toString(), doc));
        }
        game.appendChild(createTextElement("MainGenre", record.getMainGenre(), doc));
        game.appendChild(createTextElement("PublisherName", record.getPublisherName(), doc));
        game.appendChild(createTextElement("DeveloperName", record.getDeveloperName(), doc));

        // Handle platforms as a semicolon-separated string
        if (record.getPlatforms() != null && !record.getPlatforms().isEmpty()) {
            game.appendChild(createTextElement("Platforms", String.join(";", record.getPlatforms()), doc));
        }

        // Add global sales if it's greater than 0
        if (record.getGlobalSales() > 0) {
            game.appendChild(createTextElement("GlobalSales", String.format("%.2f", record.getGlobalSales()), doc));
        }

        // Add price if it's greater than 0
        if (record.getPrice() > 0) {
            game.appendChild(createTextElement("Price", String.format("%.2f", record.getPrice()), doc));
        }

        return game;
    }
}
