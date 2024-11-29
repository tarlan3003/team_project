package de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
public class SteamGameXMLReader extends XMLMatchableReader<SteamGame, Attribute> {

    // Manually setting the simplified attributes for SteamGame
    @Override
    protected void initialiseDataset(DataSet<SteamGame, Attribute> dataset) {
        super.initialiseDataset(dataset);
        // Add only the attributes relevant to our simplified SteamGame model
        dataset.addAttribute(SteamGame.ID);
        dataset.addAttribute(SteamGame.GAME_NAME);
        dataset.addAttribute(SteamGame.RELEASE_YEAR);
        dataset.addAttribute(SteamGame.PUBLISHER);
    }

    // Extracting data from the XML and creating the simplified SteamGame object
    @Override
    public SteamGame createModelFromElement(Node node, String provenanceInfo) {
        // Extracting the game information from XML
        String id = getValueFromChildElement(node, "id");

        // Create the SteamGame object with ID and provenance info
        SteamGame game = new SteamGame(id, provenanceInfo);

        // Fill the attributes for the simplified SteamGame object
        game.setGameName(getValueFromChildElement(node, "Game_Name"));
        game.setPublisher(getValueFromChildElement(node, "Publisher"));
        game.setReleaseYear(getIntValueFromChildElement(node, "Release_Year"));

        return game;
    }

    // Helper method to extract an int value from a child element
    private int getIntValueFromChildElement(Node node, String tagName) {
        String value = getValueFromChildElement(node, tagName);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();  // Log any parsing error
            }
        }
        return 0; // Return a default value (0) if conversion fails or the value is missing
    }
}
