package com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi;

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
import android.widget.Toast;

import com.daipresents.tsundoku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookSearchResultActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject>{

    private static final String TAG = BookSearchResultActivity.class.getSimpleName();
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search_result);

        this.activity = this;

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

        BookSearchAsyncTaskLoader loader = new BookSearchAsyncTaskLoader(this, bundle.getString("url"));
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject bookSearchData) {
        Log.v(TAG, "onLoadFinished: start");

        Log.v(TAG, "onLoadFinished: JSON data is " + bookSearchData.toString());

        // convert json to list.
        List bookList = new ArrayList<BookSearchResultItem>();
        try {
            JSONArray items = bookSearchData.getJSONArray("items");
            for (int i = 0; i < items.length(); i++){
                JSONObject item = (JSONObject) items.get(i);
                Log.v(TAG, "onLoadFinished: item is " + item.toString());

                JSONObject volumeInfo = (JSONObject) item.getJSONObject("volumeInfo");
                Log.v(TAG, "onLoadFinished: volumeInfo is " + volumeInfo.toString());

                String title = volumeInfo.getString("title");
                Log.v(TAG, "onLoadFinished: title is " + title);

                // TODO now only one author.
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String author = authors.get(0).toString();
                Log.v(TAG, "onLoadFinished: author is " + author);

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String smallThumbnail = imageLinks.getString("smallThumbnail");
                Log.v(TAG, "onLoadFinished: smallThumbnail is " + author);

                bookList.add(new BookSearchResultItem(title, author, smallThumbnail));
            }
        } catch (JSONException e){
            e.printStackTrace();
            return;
        }

        ListView listView = (ListView) findViewById(R.id.bookSearchResultListView);
        listView.setAdapter(new BookSearchAdapter(activity, bookList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        Log.v(TAG, "onLoaderReset: start");
    }

}
