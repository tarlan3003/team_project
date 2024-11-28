package de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
/**
 * A {@link XMLMatchableReader} for {@link GamespotGame}s based on the XML structure.
 * 
 * @author Your Name
 * 
 */
public class GamespotGameXMLReader extends XMLMatchableReader<GamespotGame, Attribute> {

    /* (non-Javadoc)
     * @see de.uni.mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(de.uni.mannheim.informatik.wdi.model.DataSet)
     */
    @Override
    protected void initialiseDataset(DataSet<GamespotGame, Attribute> dataset) {
        super.initialiseDataset(dataset);

        // Add necessary attributes
        dataset.addAttribute(GamespotGame.ID);
        dataset.addAttribute(GamespotGame.GAME_NAME);
        dataset.addAttribute(GamespotGame.PLATFORM);
    }

    @Override
    public GamespotGame createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "Games_Updated_ID");

        // Create the object with id and provenance information
        GamespotGame game = new GamespotGame(id, provenanceInfo);

        // Fill the game name and platform attributes
        game.setGameName(getValueFromChildElement(node, "Game_Name"));
        game.setPlatform(getValueFromChildElement(node, "Platform"));

        return game;
    }
}
