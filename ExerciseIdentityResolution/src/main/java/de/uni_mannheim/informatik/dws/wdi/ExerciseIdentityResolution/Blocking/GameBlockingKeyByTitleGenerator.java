package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.BlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

/**
 * {@link BlockingKeyGenerator} for {@link Game}s, which generates a blocking
 * key based on the title.
 * 
 */
public class GameBlockingKeyByTitleGenerator extends
        RecordBlockingKeyGenerator<Game, Attribute> {

    private static final long serialVersionUID = 1L;

    @Override
    public void generateBlockingKeys(Game record, Processable<Correspondence<Attribute, Matchable>> correspondences,
            DataIterator<Pair<String, Game>> resultCollector) {

        // Split the game title into tokens
        String[] tokens = record.getTitle().split(" ");

        // Create the blocking key using the first two characters of the first 3 words (if available)
        String blockingKeyValue = "";

        for (int i = 0; i < Math.min(3, tokens.length); i++) {
            blockingKeyValue += tokens[i].substring(0, Math.min(2, tokens[i].length())).toUpperCase();
        }

        // Collect the blocking key and the game record
        resultCollector.next(new Pair<>(blockingKeyValue, record));
    }
}
