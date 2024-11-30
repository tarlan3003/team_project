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
 * key based on the publisher name.
 */
public class GameBlockingKeyByPublisherGenerator extends
        RecordBlockingKeyGenerator<Game, Attribute> {

    private static final long serialVersionUID = 1L;

    @Override
    public void generateBlockingKeys(Game record, Processable<Correspondence<Attribute, Matchable>> correspondences,
            DataIterator<Pair<String, Game>> resultCollector) {

        String publisherName = record.getPublisherName();
        if (publisherName != null && !publisherName.isEmpty()) {
            // Normalize the publisher name: remove special characters and convert to uppercase
            String normalizedPublisher = publisherName.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            // Use the first three characters as the blocking key
            String blockingKeyValue = normalizedPublisher.length() >= 3 ? normalizedPublisher.substring(0, 3) : normalizedPublisher;

            System.out.println("Blocking key for Publisher " + record.getIdentifier() + ": " + blockingKeyValue);
            resultCollector.next(new Pair<>(blockingKeyValue, record));
        }
    }
}
