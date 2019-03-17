package com.example.travis.familymapclient;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travis.familymapclient.Requests.LoginRequest;
import com.example.travis.familymapclient.Requests.PersonRequest;
import com.example.travis.familymapclient.Result.LoginResult;
import com.example.travis.familymapclient.Result.PersonResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask<Void, Void, Object> {
    private String serverHost;
    private String serverPort;
    private String userName;
    private String password;
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

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
            return result;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Object result) {
        if (reqCode == LOGIN_REQ) {
            LoginResult loginResult = (LoginResult) result;
            fragment.setResult(loginResult);
            if (((LoginResult) result).isSuccessful() == true) {
                MyAsyncTask loginTask = new MyAsyncTask(serverHost, serverPort, userName, password,2,fragment);
                loginTask.setAuthToken(loginResult.getAuthToken());
                loginTask.execute();
            }
            else {
                Toast.makeText(fragment.getApplicationContext(), "Log in Failed, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
