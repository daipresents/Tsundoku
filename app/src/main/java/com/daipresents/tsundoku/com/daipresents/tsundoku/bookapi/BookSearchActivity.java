package com.daipresents.tsundoku.com.daipresents.tsundoku.bookapi;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daipresents.tsundoku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookSearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject>{

    private static final String TAG = BookSearchActivity.class.getSimpleName();
    private Activity activity;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate: start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search_result);

        this.activity = this;
        this.listView = (ListView) findViewById(R.id.bookSearchResultListView);


        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "onItemClick: start.");

                Book book = (Book) parent.getItemAtPosition(position);

                Intent intent = new Intent(BookSearchActivity.this, BookDetailActivity.class);
                intent.putExtra("volumeID", book.getVolumeID());
                startActivity(intent);

            }
        });

        Intent receivedIntent = getIntent();
        searchBooks(receivedIntent.getStringExtra("keyword"));
    }

    void searchBooks(String keyword){
        Log.v(TAG, "searchBooks: keyword is " + keyword);

        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);

        getLoaderManager().initLoader(0, bundle, this);

    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle bundle) {
        Log.v(TAG, "onCreateLoader: start");

        BookSearchAsyncTaskLoader loader = new BookSearchAsyncTaskLoader(activity, bundle.getString("keyword"));
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

        // convert json to list.
        List bookList = new ArrayList<Book>();
        try {
            JSONArray items = bookSearchData.getJSONArray("items");
            for (int i = 0; i < items.length(); i++){
                JSONObject item = (JSONObject) items.get(i);
                Log.v(TAG, "onLoadFinished: item is " + item.toString());

                // VolumeID
                String id = item.getString("id");
                Log.v(TAG, "onLoadFinished: id is " + id);

                JSONObject volumeInfo = (JSONObject) item.getJSONObject("volumeInfo");
                Log.v(TAG, "onLoadFinished: volumeInfo is " + volumeInfo.toString());

                // Thumbnail
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String smallThumbnailURL = imageLinks.getString("smallThumbnail");
                Log.v(TAG, "onLoadFinished: smallThumbnailURL is " + smallThumbnailURL);

                // Title
                String title = volumeInfo.getString("title");
                Log.v(TAG, "onLoadFinished: title is " + title);

                // Author
                // TODO now only one author.
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String author = "";
                if (authors != null || authors.length() != 0) {
                   author = authors.get(0).toString();
                }
                Log.v(TAG, "onLoadFinished: author is " + author);

                bookList.add(new Book(id, smallThumbnailURL, title, author));
            }
        } catch (JSONException e){
            e.printStackTrace();
            return;
        }

        ListView listView = (ListView) findViewById(R.id.bookSearchResultListView);
        this.listView.setAdapter(new BookSearchAdapter(activity, bookList));
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        Log.v(TAG, "onLoaderReset: start");
    }

}
