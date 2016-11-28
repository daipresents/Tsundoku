package com.daipresents.tsundoku;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daipresents.tsundoku.R;

public class MainActivity extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
        Log.v(MainActivity.class.getName(), "MainActivity.searchBooks: keyword is " + keyword);

        //http://api.calil.jp/check?appkey=b265995d49720d914ea391fe8ce3b665


        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        startActivity(intent);
    }
}
