package com.example.travis.familymapclient.Requests;

import com.example.travis.familymapclient.Model.Event;

import java.util.ArrayList;

/**
 * Holds the request body for Event
 */
public class EventRequest {
    /**
     * Unique user name (non-empty string)
     */
    private String userName;
    private String authToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public EventRequest(String userName, String authToken) {

        this.userName = userName;
        this.authToken = authToken;

    }

}
