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
    private static final String GOOGLE_BOOK_API = "https://www.googleapis.com/books/v1/volumes?startIndex=0&q=";
    private URL url;

    public BookSearchAsyncTaskLoader(Context context, String keyword) {
        super(context);

        Log.v(TAG, "BookSearchAsyncTaskLoader: request URL is " + GOOGLE_BOOK_API + keyword);

        try {
            this.url = new java.net.URL(GOOGLE_BOOK_API + keyword);
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
            if (status == HttpURLConnection.HTTP_OK) {
                Log.v(TAG, "loadInBackground: HTTP_OK");

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

        return resultJSON;

    }
}
