/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;


import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * {@link Comparator} for {@link Game}s based on the release year
 * value with a tolerance of 1 year.
 */
public class ReleaseYearComparatorOne implements Comparator<Game, Attribute> {

    private static final long serialVersionUID = 1L;
    private int tolerance = 1;  // Tolerance of 1 year

    @Override
    public double compare(Game record1, Game record2, Correspondence<Attribute, Matchable> schemaCorrespondences) {
        // Retrieve release years from the two records, ensuring null safety
        Integer year1 = record1.getReleaseYear();
        Integer year2 = record2.getReleaseYear();

        // Handle cases where one or both years are null
        if (year1 == null || year2 == null) {
            return 0.0; // No similarity if one or both years are missing
        }

        // Calculate absolute difference in years
        int diff = Math.abs(year1 - year2);

        // If the difference is within the tolerance, return a similarity score of 1 (similar)
        if (diff <= tolerance) {
            return 1.0;
        } else {
            // Otherwise, return a similarity score of 0 (not similar)
            return 0.0;
        }
    }
}
