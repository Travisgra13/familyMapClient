package com.example.travis.familymapclient;

import com.example.travis.familymapclient.Model.Event;
import com.example.travis.familymapclient.Model.Person;

import java.util.ArrayList;

public class DataCache {
    private ArrayList<Person> peopleData;
    private ArrayList<Event> eventData;

    private static DataCache instance;

    public ArrayList<Person> getPeopleData() {
        return peopleData;
    }

    public void setPeopleData(ArrayList<Person> peopleData) {
        this.peopleData = peopleData;
    }

    public ArrayList<Event> getEventData() {
        return eventData;
    }

    public void setEventData(ArrayList<Event> eventData) {
        this.eventData = eventData;
    }

    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;

    }
}
