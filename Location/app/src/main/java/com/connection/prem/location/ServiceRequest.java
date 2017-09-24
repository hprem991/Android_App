package com.connection.prem.location;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.String;
import java.io.*;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.json.JSONObject;
import org.w3c.dom.DOMStringList;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.data;
//import com.example.webserviceactivity.R;
//import com.google.gson.Gson;

/**
 * Created by prem on 9/21/17.
 */

// http://programmerguru.com/android-tutorial/android-json-web-service-tutorial/
// https://www.ssaurel.com/blog/learn-to-consume-a-rest-web-service-and-parse-json-result-in-android/

public class ServiceRequest extends AsyncTask<String, Void, Void> {

    private URL url;
    String content = "";
    Map<String, String> value = new HashMap<String, String>();
    String error = "";
    public ServiceRequest() {
        super();
    }

    @Override
    protected Void doInBackground(String... params) {
        try
        {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                content = readStream(in); //This converts server response to String
                parseJSON(content); // This fills the map which need to redesign as we make aware of file exchange structure
            } catch (MalformedURLException e){
                  error = e.getMessage();
            }
        }
        catch(Exception ex)
        {
           String Error = ex.getMessage();
        }
        return null;
    }

    public void parseJSON(String content){
        try {
            JSONObject object = new JSONObject(content);
            value.put("username",object.getString("username"));
            value.put("biography", object.getString("biography"));
        } catch(Exception e){
            String error = e.getMessage();
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
