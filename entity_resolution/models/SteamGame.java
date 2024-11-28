package de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
/**
 * A {@link AbstractRecord} representing a simplified Steam Game.
 */
public class SteamGame extends AbstractRecord<Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    // Simplified fields corresponding to the XML elements
    private String id;  // id from XML
    private String gameName;  // Game name from XML
    private int releaseYear;  // Release year from XML
    private String publisher;  // Publisher from XML

    // Constructor
    public SteamGame(String identifier, String provenance) {
        super(identifier, provenance);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    // HashCode and Equals (based only on gameName for simplicity)
    @Override
    public int hashCode() {
        int result = 31 + ((gameName == null) ? 0 : gameName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SteamGame other = (SteamGame) obj;
        if (gameName == null) {
            if (other.gameName != null)
                return false;
        } else if (!gameName.equals(other.gameName))
            return false;
        return true;
    }

    // Define simplified Attributes (for matching)
    public static final Attribute ID = new Attribute("id");
    public static final Attribute GAME_NAME = new Attribute("Game_Name");
    public static final Attribute RELEASE_YEAR = new Attribute("Release_Year");
    public static final Attribute PUBLISHER = new Attribute("Publisher");

    // Implementing the hasValue method to check if the value for an attribute is present
    @Override
    public boolean hasValue(Attribute attribute) {
        if (attribute == ID)
            return id != null;
        else if (attribute == GAME_NAME)
            return gameName != null;
        else if (attribute == RELEASE_YEAR)
            return releaseYear != 0;
        else if (attribute == PUBLISHER)
            return publisher != null;
        return false;
    }
}
