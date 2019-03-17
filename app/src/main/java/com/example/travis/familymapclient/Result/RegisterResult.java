package com.example.travis.familymapclient.Result;
/**
 * Holds the response body for Register
 */
public class RegisterResult {
    /**
     * Non-empty auth token string
     */
    private String authToken;
    /**
     * Non-empty auth token string
     */
    private String userName;
    /**
     * Non-empty string containing the Person ID of the
     * user’s generated Person object
     */
    private String personID;
    /**
     *whether or not the response was successful
     */
    private boolean successful;

    private String message;

    public RegisterResult(String authToken, String userName, String personID, boolean successful) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.successful = successful;
        setMessage();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * This returns the message associated with the result
     * @return
     */
    public void setMessage() {
       if (successful) {
           message = null;
       }
       else {
           message = "Either userName is already taken or some other error";
       }
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
