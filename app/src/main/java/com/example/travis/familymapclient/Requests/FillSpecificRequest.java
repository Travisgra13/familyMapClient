package com.example.travis.familymapclient.Requests;

public class FillSpecificRequest {
    private String userName;
    private Integer numGenerations;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(Integer numGenerations) {
        this.numGenerations = numGenerations;
    }

    public FillSpecificRequest(String userName, Integer numGenerations) {
        this.userName = userName;
        this.numGenerations = numGenerations;
    }
}
