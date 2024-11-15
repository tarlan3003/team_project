package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
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
        regionalSales = new LinkedList<>();
    }

    private String gameId;
    private String name;
    private Integer releaseYear;
    private String mainGenre;
    private String publisherName;
    private String developerName;
    private List<String> platforms; // List of platforms, e.g., "PC", "Xbox", "PlayStation"
    private List<SalesRegion> regionalSales; // List of sales by region
    private double averageRating;
    private int reviewCount;
    private double price;

    // Inner class to represent sales by region
    public static class SalesRegion {
        private String regionName;
        private double copiesSold;

        public SalesRegion(String regionName, double copiesSold) {
            this.regionName = regionName;
            this.copiesSold = copiesSold;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public double getCopiesSold() {
            return copiesSold;
        }

        public void setCopiesSold(double copiesSold) {
            this.copiesSold = copiesSold;
        }
    }

    // Getters and Setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && name.isEmpty()) {
            // Replace empty string with 'null'
            name = null;
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

    public List<SalesRegion> getRegionalSales() {
        return regionalSales;
    }

    public void setRegionalSales(List<SalesRegion> regionalSales) {
        this.regionalSales = regionalSales;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
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

    public void setAttributeProvenance(Attribute attribute,
                                       Collection<String> provenance) {
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
    public static final Attribute REGIONAL_SALES = new Attribute("RegionalSales");
    public static final Attribute AVERAGE_RATING = new Attribute("AverageRating");
    public static final Attribute REVIEW_COUNT = new Attribute("ReviewCount");
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
        else if (attribute == REGIONAL_SALES)
            return getRegionalSales() != null && !getRegionalSales().isEmpty();
        else if (attribute == AVERAGE_RATING)
            return getAverageRating() > 0;
        else if (attribute == REVIEW_COUNT)
            return getReviewCount() > 0;
        else if (attribute == PRICE)
            return getPrice() > 0;
        else
            return false;
    }

    @Override
    public String toString() {
        return String.format("[Game %s: %s / %s / %d]", getIdentifier(), getName(),
                getPublisherName(), getReleaseYear() != null ? getReleaseYear() : -1);
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game) {
            return this.getIdentifier().equals(((Game) obj).getIdentifier());
        } else
            return false;
    }
}
