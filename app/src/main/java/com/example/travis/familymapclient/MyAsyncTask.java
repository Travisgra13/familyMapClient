package com.example.travis.familymapclient;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.travis.familymapclient.Model.Event;
import com.example.travis.familymapclient.Model.Person;
import com.example.travis.familymapclient.Model.User;
import com.example.travis.familymapclient.Requests.EventRequest;
import com.example.travis.familymapclient.Requests.LoginRequest;
import com.example.travis.familymapclient.Requests.PersonRequest;
import com.example.travis.familymapclient.Requests.RegisterRequest;
import com.example.travis.familymapclient.Result.EventResult;
import com.example.travis.familymapclient.Result.LoginResult;
import com.example.travis.familymapclient.Result.PersonResult;
import com.example.travis.familymapclient.Result.RegisterResult;
import com.example.travis.familymapclient.UserFamilyData.PersonFamily;

import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<Void, Void, Object> {
    private String serverHost;
    private String serverPort;
    private String userName;
    private String password;
    private String authToken;

    private Integer reqCode;
    private ServerProxy serverProxy;
    private LoginFragment fragment;
    private boolean registering;

    private final int REGISTER_REQ = 0;
    private final int LOGIN_REQ = 1;
    private final int GET_PEOPLE_REQ = 2;
    private final int GET_EVENTS_REQ = 3;

    public MyAsyncTask(String serverHost, String serverPort, String userName, String password,
                       Integer reqCode, LoginFragment frag) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.userName = userName;
        this.password = password;
        this.reqCode = reqCode;
        this.serverProxy = ServerProxy.getInstance();
        this.serverProxy.setServerHost(serverHost);
        this.serverProxy.setServerPort(serverPort);
        this.fragment = frag;
        this.registering = false;
    }
    @Override
    protected Object doInBackground(Void... voids) {
        if (reqCode == LOGIN_REQ) {
            LoginRequest loginRequest = new LoginRequest(userName, password);
            LoginResult result = serverProxy.login(loginRequest);
            return result;
        }
        else if (reqCode == GET_PEOPLE_REQ) {
            PersonRequest peopleRequest = new PersonRequest(userName, authToken);
            PersonResult result = serverProxy.getPeople(peopleRequest);
            ArrayList<Person> family = result.getFamilyMembers();
            PersonFamily personFamily = new PersonFamily(family.get(0), family);
            fragment.setUserFamily(personFamily);
            return result;
        }
        else if (reqCode == GET_EVENTS_REQ) {
            EventRequest eventRequest = new EventRequest(userName, authToken);
            EventResult result = serverProxy.getEvents(eventRequest);
            ArrayList<Event> events = result.getEvents();
            fragment.setAllEvents(events);
            return result;
        }
        else if (reqCode == REGISTER_REQ) {
            String email = fragment.getEmailInput().getText().toString();
            String firstName = fragment.getFirstNameInput().getText().toString();
            String lastName = fragment.getLastNameInput().getText().toString();
            String gender = fragment.getGenderSelected();
            RegisterRequest registerRequest = new RegisterRequest(userName, password, email,
                    firstName, lastName, gender);
            RegisterResult result = serverProxy.register(registerRequest);
            fragment.setRegisterResult(result);
            return result;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Object result) {
        if (reqCode == LOGIN_REQ) {
            LoginResult loginResult = (LoginResult) result;
            fragment.setResult(loginResult);
            if (((LoginResult) result).isSuccessful() == false) {
                Toast.makeText(fragment.getContext(), "Log in Failed, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }
        else if (reqCode == GET_PEOPLE_REQ) {
            PersonResult personResult = (PersonResult) result;
            if (personResult.isSuccessful() == false) {

            }
        }
        else if (reqCode == GET_EVENTS_REQ) {
           EventResult eventResult = (EventResult) result;
           if (eventResult.isSuccessful() == true) {
               fragment.setDataCache();
               Person userPerson = fragment.getUserFamily().getAssociatedPerson();
               if (registering == true) {
                   Toast.makeText(fragment.getContext(), "Successfully Registered "
                                   + userPerson.getFirstName() + " " + userPerson.getLastName(),
                           Toast.LENGTH_LONG).show();
               }
               else {
                   Toast.makeText(fragment.getContext(), "Logged in " + userPerson.getFirstName() + " " + userPerson.getLastName(),
                           Toast.LENGTH_LONG).show();
               }
               fragment.runMapFrag();
           }
        }

        else if (reqCode == REGISTER_REQ) {
            RegisterResult registerResult = (RegisterResult) result;
            if (registerResult.isSuccessful() == false) {
                Toast.makeText(fragment.getContext(), "Register Failed, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isRegistering() {
        return registering;
    }

    public void setRegistering(boolean register) {
        this.registering = register;
    }
}
