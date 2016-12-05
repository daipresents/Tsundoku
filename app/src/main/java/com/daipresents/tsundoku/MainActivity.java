package com.daipresents.tsundoku;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daipresents.tsundoku.R;
import com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi.BookSearchAsyncLoader;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private static final String TAG = MainActivity.class.getSimpleName();
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v(TAG, "onCreate.onClick: start");
                TextView textView = (TextView) findViewById(R.id.searchTextBox);
                if (textView.getText() == null || "".equals(textView.getText())){
                    Toast.makeText(MainActivity.this, "No search keyword. Please input the name of book.", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchBooks(textView.getText().toString());
            }
        });
    }

    void searchBooks(String keyword){
        Log.v(TAG, "searchBooks: keyword is " + keyword);

        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);

        getLoaderManager().initLoader(0, bundle, this);

        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle bundle) {
        Log.v(TAG, "onCreateLoader: start");

        BookSearchAsyncLoader loader = new BookSearchAsyncLoader(this, bundle.getString("url"));
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        Log.v(TAG, "onLoadFinished: start");

        Log.v(TAG, "onLoadFinished: JSON data is " + data.toString());
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        Log.v(TAG, "onLoaderReset: start");
    }
}
