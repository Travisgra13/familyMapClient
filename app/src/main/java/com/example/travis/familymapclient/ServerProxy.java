package com.example.travis.familymapclient;

import android.util.Base64;
import android.webkit.HttpAuthHandler;

import com.example.travis.familymapclient.Requests.LoginRequest;
import com.example.travis.familymapclient.Requests.PersonRequest;
import com.example.travis.familymapclient.Result.LoadResult;
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

public class ServerProxy {
    private static ServerProxy instance;

    private String serverHost;

    private String serverPort;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public static ServerProxy getInstance() {
        if (instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }

    public LoginResult login(LoginRequest loginRequest) {
        String url = "http://" + serverHost + ":" + serverPort +
                "/user/login";
        try {
            URL address = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) address.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            JSONObject preRequestBody = new JSONObject();
            preRequestBody.put("userName", loginRequest.getUserName());
            preRequestBody.put("password", loginRequest.getPassword());
            OutputStream OSS = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(OSS, "UTF-8");
            osw.write(preRequestBody.toString());
            osw.flush();
            osw.close();
            Gson gson = new Gson();
            LoginResult loginResult = gson.fromJson(getResponse(conn), LoginResult.class);
            return loginResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (org.json.JSONException exc) {
            exc.printStackTrace();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public PersonResult getPeople(PersonRequest personRequest) {
        String url = "http://" + serverHost + ":" + serverPort +
                "/person";
        try {
            URL address = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) address.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", personRequest.getAuthToken());
            conn.connect();

            Gson gson = new Gson();
            PersonResult personResult = gson.fromJson(getResponse(conn), PersonResult.class);
            return personResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
        }
    private String getResponse(HttpURLConnection conn) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
