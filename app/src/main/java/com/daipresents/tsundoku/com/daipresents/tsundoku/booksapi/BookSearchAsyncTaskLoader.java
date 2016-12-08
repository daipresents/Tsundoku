package com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi;

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
public class BookSearchAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {

    private static final String TAG = BookSearchAsyncTaskLoader.class.getSimpleName();
    private URL url;

    public BookSearchAsyncTaskLoader(Context context, String keyword) {
        super(context);
        try {
            this.url = new java.net.URL("https://www.googleapis.com/books/v1/volumes?startIndex=0&q=" + keyword);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject loadInBackground() {

        Log.v(TAG, "loadInBackground: start");
        Log.v(TAG, "loadInBackground: API URL is " + this.url.toString());
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.connect();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                Log.v(TAG, "loadInBackground: HTTP_OK");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;

                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        String resultJSONString = result.toString();

        Log.v(TAG, "loadInBackground: " + resultJSONString);

        try {
            return new JSONObject(resultJSONString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
