package com.example.gamershub.objectPackage;

public class commentObject {

    //create a placeholder for the gameID that this comment is linked to
    private int gameID;
    //create a placeholder for the commentID
    private int commentID;
    //create a placeholder for the userID that is connected to this comment
    private int userID;
    //create a placeholder for the category that the comment is. view API doc for more info on FEED-CATEGORY
    private int category;
    //create a placeholder for the content of the review
    private String reviewContent;
    //create a placeholder for the createdAT column that way we can see when the comments were made at
    private String createdAt;
    //create a placeholder for the updatedAT column that way we can see when the comment was updated at
    private String updatedAt;
    //create a placeholder for the reviewlikes that are attached to certain comments
    private double reviewLikes;


    /**
     * Empty constructor for the CommentObject class
     */
    public commentObject() {

    }

    /**
     *
     * @param gameID - the gameID that this comment is linked too
     * @param commentID - the commentID that represents this comment
     * @param userID - the userID that represents the user who posted the comment
     * @param reviewContent - the reviewcontent is the user review itself
     * @param createdAt - the createdAt is for the created at date of the comment
     * @param reviewLikes - the reviewLikes is for the total likes on a comment itself
     */
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
