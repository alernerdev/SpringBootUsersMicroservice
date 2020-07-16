package com.pragmaticbitbucket.app.ws.shared;

import com.pragmaticbitbucket.app.ws.ui.model.response.AlbumsResponseModel;
import java.util.List;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String userId;
    private List<AlbumsResponseModel> albums;

    public List<AlbumsResponseModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsResponseModel> albums) {
        this.albums = albums;
    }

    private static final long serialVersionUID = 1L;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
