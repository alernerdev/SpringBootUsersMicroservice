package com.pragmaticbitbucket.app.ws.ui.model.response;

import java.util.List;

public class UserDetailsResponseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String userId; // public userid
    private List<AlbumsResponseModel> albums;

    public List<AlbumsResponseModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsResponseModel> albums) {
        this.albums = albums;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
