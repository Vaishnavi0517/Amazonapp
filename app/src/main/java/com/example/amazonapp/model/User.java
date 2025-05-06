package com.example.amazonapp.model;

public class User {
    private String userId;
    private String profilePicture;

    public User(String userId, String profilePicture) {
        this.userId = userId;
        this.profilePicture = profilePicture;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}

