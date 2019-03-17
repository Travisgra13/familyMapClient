package com.example.travis.familymapclient.Result;

import com.example.travis.familymapclient.Model.Event;
/**
 * Holds the response body for EventID
 */
public class EventIDResult {
    /**
     * the event object
     */
    private Event myEvent;
    /**
     * whether or not the response was successful
     */
    private boolean successful;
    private String message;

    public EventIDResult(Event myEvent, boolean successful) {
        this.myEvent = myEvent;
        this.successful = successful;
        setMessage();
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
            message = "Error";
        }

    }

    public Event getMyEvent() {
        return myEvent;
    }

    public void setMyEvent(Event myEvent) {
        this.myEvent = myEvent;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
