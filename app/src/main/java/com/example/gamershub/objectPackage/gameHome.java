package com.example.gamershub.objectPackage;

public class gameHome {
    private int id;
    private String name;
    private String description;
    private String websiteUrl;
    private String imageViewUrl;
    private Double rating;

    gameHome(){

    }

    public gameHome(String name) {
        this.name = name;
    }

    public gameHome(int id, String name, Double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }


    public gameHome(int id, String name, Double rating ,String imageViewUrl) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.imageViewUrl = imageViewUrl;
    }

    public gameHome(int id, String name, String websiteUrl, String imageViewUrl) {
        this.id = id;
        this.name = name;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
    }

    public gameHome(int id, String name, String description, String websiteUrl, String imageViewUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.imageViewUrl = imageViewUrl;
    }

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
}