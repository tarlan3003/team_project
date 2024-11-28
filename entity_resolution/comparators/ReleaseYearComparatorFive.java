package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.SteamGame;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * {@link Comparator} for {@link SteamGame}s based on the release year
 * value with a tolerance of 5 years.
 */
public class ReleaseYearComparatorFive implements Comparator<SteamGame, Attribute> {

    private static final long serialVersionUID = 1L;
    private int tolerance = 5;  // Tolerance of 5 years

    @Override
    public double compare(SteamGame record1, SteamGame record2, Correspondence<Attribute, Matchable> schemaCorrespondences) {
        // Retrieve release years from the two records
        int year1 = record1.getReleaseYear();
        int year2 = record2.getReleaseYear();
        
        // Calculate absolute difference in years
        int diff = Math.abs(year1 - year2);

        // If the difference is within the tolerance, return a similarity score of 1 (similar)
        // Otherwise, return a similarity score of 0 (not similar)
        if (diff <= tolerance) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
