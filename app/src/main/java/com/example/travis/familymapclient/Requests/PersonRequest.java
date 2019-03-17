package com.example.travis.familymapclient.Requests;
/**
 * Holds the request body for Person
 */
public class PersonRequest {
    /**
     * Unique user name (non-empty string)
     */
    private String userName;

    private String authToken;

    public PersonRequest(String userName, String authToken) {
        this.userName = userName;
        this.authToken = authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {

        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
