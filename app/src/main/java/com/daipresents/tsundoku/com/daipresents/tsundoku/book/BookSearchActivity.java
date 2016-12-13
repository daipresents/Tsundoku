package com.daipresents.tsundoku.com.daipresents.tsundoku.book;

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

import com.daipresents.tsundoku.JSONAPIAsyncTaskLoader;
import com.daipresents.tsundoku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookSearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject>{

    private static final String TAG = BookSearchActivity.class.getSimpleName();
    private static final String GOOGLE_BOOK_SEARCH_API = "https://www.googleapis.com/books/v1/volumes?startIndex=0&q=";
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
        bundle.putString("url", GOOGLE_BOOK_SEARCH_API + keyword);

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
        List bookList = new ArrayList<Book>();
        try {
            JSONArray items = bookSearchData.getJSONArray("items");
            for (int i = 0; i < items.length(); i++){
                Book book = new Book();

                JSONObject item = (JSONObject) items.get(i);
                Log.v(TAG, "onLoadFinished: item is " + item.toString());

                // VolumeID
                book.setVolumeID(item.getString("id"));

                JSONObject volumeInfo = (JSONObject) item.getJSONObject("volumeInfo");
                Log.v(TAG, "onLoadFinished: volumeInfo is " + volumeInfo.toString());

                // Title
                book.setTitle(volumeInfo.getString("title"));

                // Author
                // TODO now only one author.
                JSONArray authors = volumeInfo.getJSONArray("authors");
                if (authors != null || authors.length() != 0) {
                   book.setAuthor(authors.get(0).toString());
                }

                // Publisher
                try{
                    book.setPublisher(volumeInfo.getString("publisher"));
                } catch (JSONException e){
                    Log.v(TAG, "onLoadFinished: no publisher.");
                }

                // PublishedDate
                try{
                    book.setPublishedDate(volumeInfo.getString("publishedDate"));
                } catch (JSONException e){
                    Log.v(TAG, "onLoadFinished: no publishedDate.");
                }

                // Description
                try {
                    book.setDescription(volumeInfo.getString("description"));
                } catch (JSONException e){
                    Log.v(TAG, "onLoadFinished: no description.");
                }

                //industryIdentifiers => ISBN10
                try{
                    JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    for (int j = 0; j < industryIdentifiers.length(); j++){
                        JSONObject isbn = (JSONObject) industryIdentifiers.get(j);
                        book.setIsbn(isbn.getString("identifier"));
                        break;
                    }
                } catch (JSONException e){
                    Log.v(TAG, "onLoadFinished: no ISBN.");
                }

                // Thumbnail
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                book.setThumbnail(imageLinks.getString("smallThumbnail"));
                Log.v(TAG, "onLoadFinished: thumbnail is " + book.getDescription());

                bookList.add(book);
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
