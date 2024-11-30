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
 * key based on the game name.
 */
public class GameBlockingKeyByTitleGenerator extends
        RecordBlockingKeyGenerator<Game, Attribute> {

    private static final long serialVersionUID = 1L;

    @Override
    public void generateBlockingKeys(Game record, Processable<Correspondence<Attribute, Matchable>> correspondences,
            DataIterator<Pair<String, Game>> resultCollector) {

        String name = record.getName();
        if (name != null && !name.isEmpty()) {
            // Normalize the name: remove special characters and convert to uppercase
            String normalizedName = name.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            // Use the first three characters as the blocking key
            String blockingKeyValue = normalizedName.length() >= 3 ? normalizedName.substring(0, 3) : normalizedName;

            System.out.println("Blocking key for " + record.getIdentifier() + ": " + blockingKeyValue);
            resultCollector.next(new Pair<>(blockingKeyValue, record));
        }
    }
}
