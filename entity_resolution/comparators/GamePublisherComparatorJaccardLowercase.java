package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;
import de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.SteamGame;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

/**
 * {@link Comparator} for {@link SteamGame}s based on the {@link SteamGame#getPublisher()}
 * values (converted to lowercase), and their {@link TokenizingJaccardSimilarity} similarity.
 */
public class GamePublisherComparatorJaccardLowercase implements Comparator<SteamGame, Attribute> {

    private static final long serialVersionUID = 1L;
    private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
    
    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            SteamGame record1,
            SteamGame record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {
        
        // Get the publisher for both records and check for null values before applying toLowerCase
        String s1 = record1.getPublisher() != null ? record1.getPublisher().toLowerCase() : "";
        String s2 = record2.getPublisher() != null ? record2.getPublisher().toLowerCase() : "";
        
        // Calculate Jaccard similarity
        double similarity = sim.calculate(s1, s2);
        
        // Log the comparison if logging is enabled
        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());
            this.comparisonLog.setRecord1Value(s1);
            this.comparisonLog.setRecord2Value(s2);
            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        
        return similarity;
    }

    @Override
    public ComparatorLogger getComparisonLog() {
        return this.comparisonLog;
    }

    @Override
    public void setComparisonLog(ComparatorLogger comparatorLog) {
        // Initialize the comparison log if it is not set
        this.comparisonLog = comparatorLog;

        // Ensure that the comparison log is initialized
        if (this.comparisonLog == null) {
            this.comparisonLog = new ComparatorLogger(); // Initialize a new instance if null
        }
    }
}
