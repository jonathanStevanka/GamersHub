package com.example.gamershub.objectPackage;

//import the java Serializable Java class so that we can serialize this class later on in the application
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

//make sure this class implements 'Serializable'
public class gameHome implements Serializable {

    private int id;
    private int localDBID;
    private String name;
    private String description;
    private String websiteUrl;
    private String imageViewUrl;
    private Double rating;
    private Double aggervatedRating;
    private Double totalRating;
    private Double popularity;
    private Double price;
    private int gameCover;
    private String platformsTest;
    private String releaseDate;
    private String gameCoverURL;
    private String[] gameScreenshots;
    private String gameScreenshotExtendedURL;
    private String timestamp;
    private String ispinned;
    private String recyclerviewTopic;
    //create variables for game COVER properties
    private double height;
    private double width;



    /**
     * Create a empty public constructer for the GameHome class
     */
    public gameHome(){

    }


    /**
     *
     * @param name
     */
    public gameHome(String name) {
        this.name = name;
    }

    /**
     *
     * @param id
     * @param name
     * @param rating
     */
    public gameHome(int id, String name, Double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    /**
     *
     * @param id
     * @param name
     * @param rating
     * @param imageViewUrl
     */
    public gameHome(int id, String name, Double rating ,String imageViewUrl) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.imageViewUrl = imageViewUrl;
    }

    /**
     *
     * @param id
     * @param name
     * @param websiteUrl
     * @param imageViewUrl
     */
    public gameHome(int id, String name, String websiteUrl, String imageViewUrl) {
        this.id = id;
        this.name = name;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
    }


    /**
     *
     * @param id
     * @param name
     * @param description
     * @param websiteUrl
     * @param imageViewUrl
     */
    public gameHome(int id, String name, String description, String websiteUrl, String imageViewUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
    }


    /**
     *
     * @param id
     * @param name
     * @param description
     * @param websiteUrl
     * @param imageViewUrl
     * @param rating
     * @param gameCover
     * @param releaseDate
     */
    public gameHome(int id, String name, String description, String websiteUrl, String imageViewUrl, Double rating, int gameCover, String releaseDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
        this.rating = rating;
        this.gameCover = gameCover;
        this.releaseDate = releaseDate;
    }

    /**
     * @param id
     * @param name
     * @param description
     * @param websiteUrl
     * @param imageViewUrl
     * @param rating
     * @param popularity
     * @param price
     * @param gameCover
     * @param releaseDate
     * @param gameCoverURL
     */
    public gameHome(int id, String name, String description, String websiteUrl, String imageViewUrl, Double rating, Double popularity, Double price, int gameCover, String releaseDate, String gameCoverURL,String[] gameScreenshots) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
        this.rating = rating;
        this.popularity = popularity;
        this.price = price;
        this.gameCover = gameCover;
        this.releaseDate = releaseDate;
        this.gameCoverURL = gameCoverURL;
        this.gameScreenshots = gameScreenshots;
    }

    /**
     * CREATE GETTER AND SETTER METHODS BELOW
     */


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getImageViewUrl() {
        return imageViewUrl;
    }

    public void setImageViewUrl(String imageViewUrl) {
        this.imageViewUrl = imageViewUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getGameCover() {
        return gameCover;
    }

    public void setGameCover(int gameCover) {
        this.gameCover = gameCover;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGameCoverURL() {
        return gameCoverURL;
    }

    public void setGameCoverURL(String gameCoverURL) {
        this.gameCoverURL = gameCoverURL;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String[] getGameScreenshots() {
        return gameScreenshots;
    }

    public String setGameScreenFromURLStringArray(){
        String test  = Arrays.toString(gameScreenshots);
        this.setGameScreenshotExtendedURL(test);
        return test;
    }

    public void setGameScreenshots(String[] gameScreenshots) {
        this.gameScreenshots = gameScreenshots;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getGameScreenshotExtendedURL() {
        return gameScreenshotExtendedURL;
    }

    public void setGameScreenshotExtendedURL(String gameScreenshotExtendedURL) {
        this.gameScreenshotExtendedURL = gameScreenshotExtendedURL;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAggervatedRating() {
        return aggervatedRating;
    }

    public void setAggervatedRating(Double aggervatedRating) {
        this.aggervatedRating = aggervatedRating;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getIspinned() {
        return ispinned;
    }

    public void setIspinned(String ispinned) {
        this.ispinned = ispinned;
    }

    public int getLocalDBID() {
        return localDBID;
    }

    public void setLocalDBID(int localDBID) {
        this.localDBID = localDBID;
    }

    public String getRecyclerviewTopic() {
        return recyclerviewTopic;
    }

    public void setRecyclerviewTopic(String recyclerviewTopic) {
        this.recyclerviewTopic = recyclerviewTopic;
    }

    public String getPlatformsTest() {
        return platformsTest;
    }

    public void setPlatformsTest(String platformsTest) {
        this.platformsTest = platformsTest;
    }
}
