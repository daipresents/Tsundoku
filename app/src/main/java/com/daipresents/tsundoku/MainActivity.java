package com.daipresents.tsundoku;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daipresents.tsundoku.book.BookSearchActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                String keyword = null;
                try {
                    keyword = URLEncoder.encode(textView.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(MainActivity.this, "Invalid search keyword.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.v(TAG, "onCreate.onClick: keyword is " + keyword);

                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });
    }
}
