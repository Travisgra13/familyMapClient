package com.example.travis.familymapclient;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.travis.familymapclient.Model.Person;
import com.example.travis.familymapclient.Model.User;
import com.example.travis.familymapclient.Requests.LoginRequest;
import com.example.travis.familymapclient.Requests.PersonRequest;
import com.example.travis.familymapclient.Result.LoginResult;
import com.example.travis.familymapclient.Result.PersonResult;
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

    private final int LOGIN_REQ = 1;
    private final int GET_PEOPLE_REQ = 2;

    public MyAsyncTask(String serverHost, String serverPort, String userName, String password, Integer reqCode, LoginFragment frag) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.userName = userName;
        this.password = password;
        this.reqCode = reqCode;
        this.serverProxy = ServerProxy.getInstance();
        this.serverProxy.setServerHost(serverHost);
        this.serverProxy.setServerPort(serverPort);
        this.fragment = frag;
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
        return null;
    }
    @Override
    protected void onPostExecute(Object result) {
        if (reqCode == LOGIN_REQ) {
            LoginResult loginResult = (LoginResult) result;
            fragment.setResult(loginResult);
        }
        else if (reqCode == GET_PEOPLE_REQ) {
            Person userPerson = fragment.getUserFamily().getAssociatedPerson();
            Toast.makeText(fragment.getApplicationContext(), "Logged in " + userPerson.getFirstName() + " " + userPerson.getLastName(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
