package com.example.gamershub.objectPackage;

//import the java Serializable Java class so that we can serialize this class later on in the application
import java.io.Serializable;

//make sure this class implements 'Serializable'
public class gameHome implements Serializable {

    private int id;
    private String name;
    private String description;
    private String websiteUrl;
    private String imageViewUrl;
    private Double rating;

    private int gameCover;
    private int platform;
    private String releaseDate;

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
     * @param platform
     * @param releaseDate
     */
    public gameHome(int id, String name, String description, String websiteUrl, String imageViewUrl, Double rating, int gameCover, int platform, String releaseDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
        this.rating = rating;
        this.gameCover = gameCover;
        this.platform = platform;
        this.releaseDate = releaseDate;
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

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
