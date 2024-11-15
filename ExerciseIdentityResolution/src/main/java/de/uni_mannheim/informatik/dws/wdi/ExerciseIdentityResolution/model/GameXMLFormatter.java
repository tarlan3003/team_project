/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim,
 * Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Game}s.
 * 
 * This class formats Game objects into XML elements.
 * 
 */
public class GameXMLFormatter extends XMLFormatter<Game> {

    DeveloperXMLFormatter developerFormatter = new DeveloperXMLFormatter();

    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("games");
    }

    @Override
    public Element createElementFromRecord(Game record, Document doc) {
        Element game = doc.createElement("game");

        game.appendChild(createTextElement("id", record.getIdentifier(), doc));
        game.appendChild(createTextElement("title", record.getTitle(), doc));
        game.appendChild(createTextElement("genre", record.getGenre(), doc));
        game.appendChild(createTextElement("releaseDate", record.getReleaseDate().toString(), doc));

        game.appendChild(createDevelopersElement(record, doc));

        return game;
    }

    protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }

    protected Element createDevelopersElement(Game record, Document doc) {
        Element developerRoot = developerFormatter.createRootElement(doc);

        for (Developer d : record.getDevelopers()) {
            developerRoot.appendChild(developerFormatter.createElementFromRecord(d, doc));
        }

        return developerRoot;
    }
}
