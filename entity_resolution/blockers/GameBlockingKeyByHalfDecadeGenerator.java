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

package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.SteamGame;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.BlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

/**
 * {@link BlockingKeyGenerator} for {@link SteamGame}s, which generates a blocking
 * key based on the half-decade. E.g., 2001 --> 200, 2006 --> 200, 2010 --> 201
 * 
 */
public class GameBlockingKeyByHalfDecadeGenerator extends
        RecordBlockingKeyGenerator<SteamGame, Attribute> {

    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see de.uni.mannheim.informatik.dws.winter.matching.blockers.generators.BlockingKeyGenerator#generateBlockingKeys(de.uni.mannheim.informatik.dws.winter.model.Matchable, de.uni.mannheim.informatik.dws.winter.model.Processable, de.uni.mannheim.informatik.dws.winter.processing.DataIterator)
     */
    @Override
    public void generateBlockingKeys(SteamGame record, Processable<Correspondence<Attribute, Matchable>> correspondences,
            DataIterator<Pair<String, SteamGame>> resultCollector) {
        
        // Get the release year directly from the SteamGame object
        int year = record.getReleaseYear();
        
        // Calculate the half-decade (grouping the years by 5 years interval)
        int halfDecade = year / 5;

        // Use the half-decade as the blocking key
        resultCollector.next(new Pair<>(Integer.toString(halfDecade), record));
    }
}
