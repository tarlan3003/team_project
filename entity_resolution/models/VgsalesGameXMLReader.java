package de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
/**
 * A {@link XMLMatchableReader} for {@link VgsalesGame}s based on the XML structure.
 */
public class VgsalesGameXMLReader extends XMLMatchableReader<VgsalesGame, Attribute> {

    // Manually setting the attributes for VgsalesGame
    @Override
    protected void initialiseDataset(DataSet<VgsalesGame, Attribute> dataset) {
        super.initialiseDataset(dataset);
        // Add attributes specific to VgsalesGame based on XML fields
        dataset.addAttribute(VgsalesGame.GAME_NAME);
        dataset.addAttribute(VgsalesGame.RELEASE_YEAR);
        dataset.addAttribute(VgsalesGame.PUBLISHER);
    }

    // Extracting data from the XML and creating the VgsalesGame object
    @Override
    public VgsalesGame createModelFromElement(Node node, String provenanceInfo) {
        // Extracting the game information from XML
        String vgsalesID = getValueFromChildElement(node, "id");
        String gameName = getValueFromChildElement(node, "Game_Name");
        String publisher = getValueFromChildElement(node, "Publisher");

        // Create the VgsalesGame object with ID and provenance info
        VgsalesGame game = new VgsalesGame(vgsalesID, provenanceInfo);

        // Set the extracted values
        game.setGameName(gameName);
        game.setPublisher(publisher);

        // Try to parse the release year as an integer (if available)
        String releaseYearStr = getValueFromChildElement(node, "Release_Year");
        if (releaseYearStr != null && !releaseYearStr.isEmpty()) {
            try {
                game.setReleaseYear(Integer.parseInt(releaseYearStr));
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log the error if parsing fails
            }
        }

        return game;
    }
}
