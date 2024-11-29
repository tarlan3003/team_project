package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;

/**
 * {@link Comparator} for {@link Game}s based on the {@link Game#getReleaseYear()}
 * value, with a maximal difference of 2 years.
 * 
 */
public class GameReleaseDateComparator2Years implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    private YearSimilarity sim = new YearSimilarity(2); // Allow 2-year tolerance
    
    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {
        
        // Calculate similarity between release years
        double similarity = sim.calculate(record1.getReleaseYear(), record2.getReleaseYear());
        
        // Log the comparison if logging is enabled
        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());
            
            this.comparisonLog.setRecord1Value(record1.getReleaseYear().toString());
            this.comparisonLog.setRecord2Value(record2.getReleaseYear().toString());
            
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
        this.comparisonLog = comparatorLog;
    }
}
