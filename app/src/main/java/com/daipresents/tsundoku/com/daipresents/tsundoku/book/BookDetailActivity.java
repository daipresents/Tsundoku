package com.daipresents.tsundoku.com.daipresents.tsundoku.book;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.daipresents.tsundoku.JSONAPIAsyncTaskLoader;
import com.daipresents.tsundoku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject>{

    private static final String TAG = BookDetailActivity.class.getSimpleName();
    private static final String GOOGLE_BOOK_DETAIL_API = "https://www.googleapis.com/books/v1/volumes/";
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate: start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        this.activity = this;

        Intent receivedIntent = getIntent();
        String volumeID = receivedIntent.getStringExtra("volumeID");
        Log.v(TAG, "onCreate: id is " + volumeID);

        Bundle bundle = new Bundle();
        bundle.putString("url", GOOGLE_BOOK_DETAIL_API + volumeID);
        getLoaderManager().initLoader(0, bundle, this);

    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle bundle) {
        Log.v(TAG, "onCreateLoader: start");

        JSONAPIAsyncTaskLoader loader = new JSONAPIAsyncTaskLoader(activity, bundle.getString("url"));
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject bookSearchData) {
        Log.v(TAG, "onLoadFinished: start");

        if (bookSearchData == null) {
            Log.v(TAG, "onLoadFinished: end because bookSearchData is null.");
            return;
        }

        // convert json to list. TODO: create JSON to book object converter.
        Log.v(TAG, "onLoadFinished: bookSearchData is " + bookSearchData.toString());
        Book book = new Book();
        JSONObject volumeInfo = null;

        try {
            volumeInfo = (JSONObject) bookSearchData.getJSONObject("volumeInfo");
            Log.v(TAG, "onLoadFinished: volumeInfo is " + volumeInfo.toString());
        } catch (JSONException e){
            e.printStackTrace();
            return;
        }

        try{
            // Thumbnail
            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
            try{
                book.setThumbnail(imageLinks.getString("small"));
            }catch(JSONException e1){
                Log.v(TAG, "onLoadFinished: no small image. " + e1.getMessage());

                try {
                    book.setThumbnail(imageLinks.getString("thumbnail"));
                } catch(JSONException e2) {
                    Log.v(TAG, "onLoadFinished: no thumbnail image. " + e2.getMessage());
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
            return;
        }

        try{
            // Title
            book.setTitle(volumeInfo.getString("title"));
            Log.v(TAG, "onLoadFinished: title is " + book.getTitle());

            // Author
            // TODO now only one author.
            JSONArray authors = volumeInfo.getJSONArray("authors");
            if (authors != null || authors.length() != 0) {
                book.setAuthor(authors.get(0).toString());
            }
            Log.v(TAG, "onLoadFinished: author is " + book.getAuthor());

        } catch (JSONException e){
            e.printStackTrace();
            return;
        }

        // View

        // Image
        ImageView thumbnailImageView = (ImageView) findViewById(R.id.detailviewThumbnail);
        BookImageAsyncTask bookImageAsyncTask = new BookImageAsyncTask(this.activity, thumbnailImageView);
        bookImageAsyncTask.execute(book.getThumbnail());

        // Title
        TextView titleTextView = (TextView) findViewById(R.id.detailviewTitle);
        titleTextView.setText(book.getTitle());

        // Author
        TextView authorTextView = (TextView) findViewById(R.id.detailViewAuthor);
        authorTextView.setText(book.getAuthor());

    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        Log.v(TAG, "onLoaderReset: start");
    }
}
