package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} representing a game.
 */
public class Game extends AbstractRecord<Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Game(String identifier, String provenance) {
        super(identifier, provenance);
        platforms = new LinkedList<>();
    }

    private String gameId;
    private String name;
    private Integer releaseYear;
    private String mainGenre;
    private String publisherName;
    private String developerName;
    private List<String> platforms; // List of platforms, e.g., "PC", "Xbox", "PlayStation"
    private double globalSales; // Global sales in millions of copies
    private double price;

    // Getters and Setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameIdFromSource(String id, String source) {
        if ("steam".equalsIgnoreCase(source)) {
            this.gameId = "steam_ID_" + id;
        } else if ("vgsales".equalsIgnoreCase(source)) {
            this.gameId = "vgsales_ID_" + id;
        } else {
            this.gameId = id; // Default case
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = null; // Replace empty or blank strings with null
        } else {
            this.name = name;
        }
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setReleaseYearFromString(String releaseDate) {
        if (releaseDate != null && !releaseDate.isEmpty()) {
            try {
                // Assume releaseDate is formatted as YYYY-MM-DD or YYYY
                String year = releaseDate.length() > 4 ? releaseDate.substring(0, 4) : releaseDate;
                this.releaseYear = Integer.parseInt(year);
            } catch (NumberFormatException e) {
                this.releaseYear = null; // Handle invalid dates
            }
        } else {
            this.releaseYear = null;
        }
    }

    public String getMainGenre() {
        return mainGenre;
    }

    public void setMainGenre(String mainGenre) {
        this.mainGenre = mainGenre;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public void setPlatformsFromString(String platformsString) {
        if (platformsString != null && !platformsString.isEmpty()) {
            this.platforms = new LinkedList<>(Arrays.asList(platformsString.split(";|,"))); // Split by both semicolon and comma
        }
    }

    public double getGlobalSales() {
        return globalSales;
    }

    public void setGlobalSales(double globalSales) {
        this.globalSales = globalSales;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Provenance tracking
    private Map<Attribute, Collection<String>> provenance = new HashMap<>();
    private Collection<String> recordProvenance;

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public Collection<String> getRecordProvenance() {
        return recordProvenance;
    }

    public void setAttributeProvenance(Attribute attribute, Collection<String> provenance) {
        this.provenance.put(attribute, provenance);
    }

    public Collection<String> getAttributeProvenance(Attribute attribute) {
        return provenance.get(attribute);
    }

    public String getMergedAttributeProvenance(Attribute attribute) {
        Collection<String> prov = provenance.get(attribute);

        if (prov != null) {
            return StringUtils.join(prov, "+");
        } else {
            return "";
        }
    }

    // Attribute constants for referencing
    public static final Attribute GAME_ID = new Attribute("GameId");
    public static final Attribute NAME = new Attribute("Name");
    public static final Attribute RELEASE_YEAR = new Attribute("ReleaseYear");
    public static final Attribute MAIN_GENRE = new Attribute("MainGenre");
    public static final Attribute PUBLISHER_NAME = new Attribute("PublisherName");
    public static final Attribute DEVELOPER_NAME = new Attribute("DeveloperName");
    public static final Attribute PLATFORMS = new Attribute("Platforms");
    public static final Attribute GLOBAL_SALES = new Attribute("GlobalSales");
    public static final Attribute PRICE = new Attribute("Price");

    @Override
    public boolean hasValue(Attribute attribute) {
        if (attribute == GAME_ID)
            return getGameId() != null && !getGameId().isEmpty();
        else if (attribute == NAME)
            return getName() != null && !getName().isEmpty();
        else if (attribute == RELEASE_YEAR)
            return getReleaseYear() != null;
        else if (attribute == MAIN_GENRE)
            return getMainGenre() != null && !getMainGenre().isEmpty();
        else if (attribute == PUBLISHER_NAME)
            return getPublisherName() != null && !getPublisherName().isEmpty();
        else if (attribute == DEVELOPER_NAME)
            return getDeveloperName() != null && !getDeveloperName().isEmpty();
        else if (attribute == PLATFORMS)
            return getPlatforms() != null && !getPlatforms().isEmpty();
        else if (attribute == GLOBAL_SALES)
            return getGlobalSales() > 0;
        else if (attribute == PRICE)
            return getPrice() > 0;
        else
            return false;
    }

    @Override
    public String toString() {
        return String.format("[Game %s: %s / %s / %d / %.2fM]", getIdentifier(), getName(),
                getPublisherName(), getReleaseYear() != null ? getReleaseYear() : -1, getGlobalSales());
    }

    @Override
    public int hashCode() {
        return (getIdentifier() != null ? getIdentifier().hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game) {
            return this.getIdentifier() != null && this.getIdentifier().equals(((Game) obj).getIdentifier());
        } else
            return false;
    }
}
