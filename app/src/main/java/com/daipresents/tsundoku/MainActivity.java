package com.daipresents.tsundoku;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi.BookSearchAdapter;
import com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi.BookSearchAsyncTaskLoader;
import com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi.BookSearchResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

                String keyword = textView.getText().toString();
                Log.v(TAG, "onCreate.onClick: keyword is " + keyword);

                Intent intent = new Intent(MainActivity.this, BookSearchResultActivity.class);
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });
    }
}
