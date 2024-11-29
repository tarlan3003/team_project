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
import de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.SteamGame;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;


/**
 * {@link Comparator} for {@link SteamGame}s based on the {@link SteamGame#getGameName()} value
 * and an exact match using {@link EqualsSimilarity}.
 * 
 */
public class GameTitleComparatorEqual implements Comparator<SteamGame, Attribute> {

    private static final long serialVersionUID = 1L;
    private EqualsSimilarity<String> sim = new EqualsSimilarity<String>();
    
    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            SteamGame record1,
            SteamGame record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {
        
        String s1 = record1.getGameName();
        String s2 = record2.getGameName();
        
        // Calculate exact match similarity
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
