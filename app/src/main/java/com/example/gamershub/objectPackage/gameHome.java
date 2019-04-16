package com.example.gamershub.objectPackage;

//import the java Serializable Java class so that we can serialize this class later on in the application
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

//make sure this class implements 'Parcelable'
public class gameHome implements Parcelable {

    //create a placeholder for the id that this game represents
    private int id;
    //create a placeholder for the localDBID that this game is linked too
    private int localDBID;
    //create a placeholder for the name of this game
    private String name;
    //create a placeholder for the description of this game
    private String description;
    //create a placeholder for the summary of this game
    private String summary;
    //create a placeholder for the websiteURL of this game
    private String websiteUrl;
    //create a placeholder for the coverImageURL of this game
    private String imageViewUrl;
    //create a placeholder for the rating of this game
    private Double rating;
    //create a placeholder for the aggervatedRating of this game
    private Double aggervatedRating;
    //create a placeholder for the totalRating of this game
    private Double totalRating;
    //create a placeholder for the popularity of this game
    private Double popularity;
    //create a placeholder for the price of this game
    private Double price;
    //create a placeholder to store the updated at date
    private String updated_at;
    //create a placeholder to store the created at date
    private String created_at;
    //create a placeholder for the gameCover of this game
    private int gameCover;
    //create a placeholder for the different platforms of this game
    private String platformsTest;
    //create a placeholder for the release date of this game
    private String releaseDate;
    //create a placeholder for the gameCoverURL of this game
    private String gameCoverURL;
    //create a placeholder for the screenshot's from this game
    private String[] gameScreenshots;
    //create a placeholder for the the extended URL from a link
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

    public gameHome(int id, int localDBID, String name, String description, String summary, String websiteUrl, String imageViewUrl, Double rating, Double aggervatedRating, Double totalRating, Double popularity, Double price, String updated_at, String created_at, int gameCover, String platformsTest, String releaseDate, String gameCoverURL, String[] gameScreenshots, String gameScreenshotExtendedURL, String timestamp, String ispinned, String recyclerviewTopic, double height, double width) {
        this.id = id;
        this.localDBID = localDBID;
        this.name = name;
        this.description = description;
        this.summary = summary;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
        this.rating = rating;
        this.aggervatedRating = aggervatedRating;
        this.totalRating = totalRating;
        this.popularity = popularity;
        this.price = price;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.gameCover = gameCover;
        this.platformsTest = platformsTest;
        this.releaseDate = releaseDate;
        this.gameCoverURL = gameCoverURL;
        this.gameScreenshots = gameScreenshots;
        this.gameScreenshotExtendedURL = gameScreenshotExtendedURL;
        this.timestamp = timestamp;
        this.ispinned = ispinned;
        this.recyclerviewTopic = recyclerviewTopic;
        this.height = height;
        this.width = width;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel goingOut, int flags) {
        goingOut.writeInt(this.id);
        goingOut.writeInt(this.localDBID);
        goingOut.writeString(this.name);
        goingOut.writeString(this.description);
        goingOut.writeString(this.summary);
        goingOut.writeString(this.websiteUrl);
        goingOut.writeString(this.imageViewUrl);
        if (this.rating!=null){
            goingOut.writeDouble(this.rating);
        }
        if (this.aggervatedRating!=null){
            goingOut.writeDouble(this.aggervatedRating);
        }
        if (this.totalRating!=null){
            goingOut.writeDouble(this.totalRating);
        }
        if (this.price!=null){
            goingOut.writeDouble(this.price);
        }
        if (this.gameCover!=0){
            goingOut.writeInt(this.gameCover);
        }
        goingOut.writeString(this.platformsTest);
        goingOut.writeString(this.releaseDate);
        goingOut.writeString(this.gameCoverURL);
        goingOut.writeArray(this.gameScreenshots);
        goingOut.writeString(this.gameScreenshotExtendedURL);
        goingOut.writeString(this.timestamp);
        goingOut.writeString(this.ispinned);
        goingOut.writeString(this.recyclerviewTopic);
        goingOut.writeDouble(this.height);
        goingOut.writeDouble(this.width);
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }



    protected gameHome(Parcel comingIN) {
        this.id = comingIN.readInt();
        this.localDBID = comingIN.readInt();
        this.name = comingIN.readString();
        this.description = comingIN.readString();
        this.summary = comingIN.readString();
        this.websiteUrl = comingIN.readString();
        this.imageViewUrl = comingIN.readString();
        this.rating = comingIN.readDouble();
        this.aggervatedRating = comingIN.readDouble();
        this.totalRating = comingIN.readDouble();
        this.popularity = comingIN.readDouble();
        this.price = comingIN.readDouble();
        this.gameCover = comingIN.readInt();
        this.platformsTest = comingIN.readString();
        this.releaseDate = comingIN.readString();
        this.gameCoverURL = comingIN.readString();
        this.gameScreenshots = comingIN.createStringArray();
        this.gameScreenshotExtendedURL = comingIN.readString();
        this.timestamp = comingIN.readString();
        this.ispinned = comingIN.readString();
        this.recyclerviewTopic = comingIN.readString();
        this.height = comingIN.readDouble();
        this.width = comingIN.readDouble();
    }

    public static final Creator<gameHome> CREATOR = new Creator<gameHome>() {
        @Override
        public gameHome createFromParcel(Parcel source) {
            return new gameHome(source);
        }

        @Override
        public gameHome[] newArray(int size) {
            return new gameHome[size];
        }
    };



}
