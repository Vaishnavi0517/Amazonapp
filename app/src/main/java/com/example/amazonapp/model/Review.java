package com.example.amazonapp.model;

public class Review {

    private String userId;
    private String reviewText;
    private float rating;


    public Review() {

    }


    public Review(String userId, String reviewText, float rating) {
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getUserId() {
        return userId;
    }



    public float getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }
}
