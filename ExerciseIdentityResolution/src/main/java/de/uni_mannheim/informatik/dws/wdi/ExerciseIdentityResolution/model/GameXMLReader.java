package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * XML reader for Game records.
 */
public class GameXMLReader extends XMLMatchableReader<Game, Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Game createModelFromElement(Node node, String provenanceInfo) {
        // Extract the ID for each game
        String gameId = getValueFromChildElement(node, "game_id");
        Game game = new Game(gameId, provenanceInfo);

        // Set name
        game.setName(getValueFromChildElement(node, "name"));

        // Set release year
        String releaseYearStr = getValueFromChildElement(node, "release_year");
        if (releaseYearStr != null && !releaseYearStr.isEmpty()) {
            game.setReleaseYear(Integer.parseInt(releaseYearStr));
        }

        // Set main genre
        game.setMainGenre(getValueFromChildElement(node, "main_genre"));

        // Set publisher and developer names
        game.setPublisherName(getValueFromChildElement(node, "publisher_name"));
        game.setDeveloperName(getValueFromChildElement(node, "developer_name"));

        // Set platforms as a list of strings
        NodeList platformNodes = getNodesFromChildElement(node, "platform_list", "platform");
        List<String> platforms = new LinkedList<>();
        for (int i = 0; i < platformNodes.getLength(); i++) {
            platforms.add(platformNodes.item(i).getTextContent());
        }
        game.setPlatforms(platforms);

        // Set regional sales
        NodeList salesNodes = getNodesFromChildElement(node, "regional_sales", "sales_region");
        List<Game.SalesRegion> regionalSales = new LinkedList<>();
        for (int i = 0; i < salesNodes.getLength(); i++) {
            Node salesNode = salesNodes.item(i);
            String regionName = getValueFromChildElement(salesNode, "region_name");
            String copiesSoldStr = getValueFromChildElement(salesNode, "copies_sold");

            if (regionName != null && copiesSoldStr != null) {
                double copiesSold = Double.parseDouble(copiesSoldStr);
                regionalSales.add(new Game.SalesRegion(regionName, copiesSold));
            }
        }
        game.setRegionalSales(regionalSales);

        // Set average rating
        String averageRatingStr = getValueFromChildElement(node, "average_rating");
        if (averageRatingStr != null && !averageRatingStr.isEmpty()) {
            game.setAverageRating(Double.parseDouble(averageRatingStr));
        }

        // Set review count
        String reviewCountStr = getValueFromChildElement(node, "review_count");
        if (reviewCountStr != null && !reviewCountStr.isEmpty()) {
            game.setReviewCount(Integer.parseInt(reviewCountStr));
        }

        // Set price
        String priceStr = getValueFromChildElement(node, "price_value");
        if (priceStr != null && !priceStr.isEmpty()) {
            game.setPrice(Double.parseDouble(priceStr));
        }

        return game;
    }
}
