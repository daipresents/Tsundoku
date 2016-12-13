package com.daipresents.tsundoku;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by daipr on 2016/12/04.
 */
public class JSONAPIAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {

    private static final String TAG = JSONAPIAsyncTaskLoader.class.getSimpleName();
    private URL url;

    public JSONAPIAsyncTaskLoader(Context context, String url) {
        super(context);

        Log.v(TAG, "JSONAPIAsyncTaskLoader: request URL is " + url);

        try {
            this.url = new java.net.URL(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    /**
     * Request to BOOK API by using keyword and get response as JSON object.
     * @return
     */
    @Override
    public JSONObject loadInBackground() {

        Log.v(TAG, "loadInBackground: start");

        JSONObject resultJSON = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.connect();

            int status = connection.getResponseCode();
            Log.v(TAG, "loadInBackground: status: " + status);

            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;

                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                String resultJSONString = result.toString();
                Log.v(TAG, "loadInBackground: " + resultJSONString);

                try {
                    resultJSON =  new JSONObject(resultJSONString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (resultJSON == null ){
            Log.v(TAG, "loadInBackground: JSON data is null.");
        } else {
            Log.v(TAG, "loadInBackground: JSON data is " + resultJSON.toString());
        }

        return resultJSON;

    }
}
