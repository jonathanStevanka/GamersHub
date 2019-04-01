package com.example.gamershub.objectPackage;

public class commentObject {


    private int gameID;
    private int commentID;
    private int userID;
    private int category;
    private String reviewContent;
    private String createdAt;
    private String updatedAt;
    private double reviewLikes;

    public commentObject() {

    }

    public commentObject(int gameID, int commentID, int userID, String reviewContent, String createdAt, double reviewLikes) {
        this.gameID = gameID;
        this.commentID = commentID;
        this.userID = userID;
        this.reviewContent = reviewContent;
        this.createdAt = createdAt;
        this.reviewLikes = reviewLikes;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getReviewLikes() {
        return reviewLikes;
    }

    public void setReviewLikes(double reviewLikes) {
        this.reviewLikes = reviewLikes;
    }
}
