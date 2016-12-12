package com.daipresents.tsundoku.com.daipresents.tsundoku.bookapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.daipresents.tsundoku.R;

public class BookDetailActivity extends AppCompatActivity {

    private static final String TAG = BookDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate: start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent receivedIntent = getIntent();
        Log.v(TAG, "onCreate: start");

        Log.v(TAG, "onCreate: id is " + receivedIntent.getStringExtra("volumeID"));
    }
}
