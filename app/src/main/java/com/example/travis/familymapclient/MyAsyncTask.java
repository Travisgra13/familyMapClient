package com.example.travis.familymapclient;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

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

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
    private String serverHost;
    private String serverPort;
    private String userName;
    private String password;

    public MyAsyncTask(String serverHost, String serverPort, String userName, String password) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.userName = userName;
        this.password = password;
    }
    @Override
    protected Void doInBackground(Void... voids) {
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
            preRequestBody.put("userName", userName);
            preRequestBody.put("password", password);

            OutputStream OSS = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(OSS, "UTF-8");
            osw.write(preRequestBody.toString());
            osw.flush();
            osw.close();

            System.out.println(getResponse(conn));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (org.json.JSONException exc) {
            exc.printStackTrace();
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
