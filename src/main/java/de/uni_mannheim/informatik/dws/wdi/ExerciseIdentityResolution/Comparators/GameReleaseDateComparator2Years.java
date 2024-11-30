package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;

import java.time.LocalDateTime;

/**
 * {@link Comparator} for {@link Game}s based on the {@link Game#getReleaseYear()}
 * value, with a maximal difference of 2 years.
 */
public class GameReleaseDateComparator2Years implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    private YearSimilarity sim = new YearSimilarity(2); // 2 years tolerance

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Game record1,
            Game record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        Integer year1 = record1.getReleaseYear();
        Integer year2 = record2.getReleaseYear();

        if (year1 == null || year2 == null) {
            return 0.0; // If either year is null, similarity is 0
        }

        // Convert Integer years to LocalDateTime for comparison
        LocalDateTime date1 = LocalDateTime.of(year1, 1, 1, 0, 0);
        LocalDateTime date2 = LocalDateTime.of(year2, 1, 1, 0, 0);

        // Calculate similarity
        double similarity = sim.calculate(date1, date2);

        // Log the comparison if logging is enabled
        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());
            this.comparisonLog.setRecord1Value(year1.toString());
            this.comparisonLog.setRecord2Value(year2.toString());
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
