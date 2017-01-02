package com.daipresents.tsundoku.books;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daipresents.tsundoku.MainActivity;
import com.daipresents.tsundoku.R;
import com.daipresents.tsundoku.WebViewActivity;

public class BookDetailActivity extends AppCompatActivity {

    private static final String TAG = BookDetailActivity.class.getSimpleName();
    private Activity activity;
    private BookParcelable book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate: start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        this.activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent receivedIntent = getIntent();
        BookParcelable bookFromIntent = receivedIntent.getParcelableExtra("book");
        Log.v(TAG, "onCreate: book volume id is " + bookFromIntent.getVolumeID());
        this.book = bookFromIntent;

        // View

        // Image
        ImageView thumbnailImageView = (ImageView) findViewById(R.id.detailThumbnail);
        BookImageAsyncTask bookImageAsyncTask = new BookImageAsyncTask(this.activity, thumbnailImageView);
        bookImageAsyncTask.execute(this.book.getThumbnail());

        // Title
        TextView titleTextView = (TextView) findViewById(R.id.detailTitle);
        titleTextView.setText(this.book.getTitle());

        // Author
        TextView authorTextView = (TextView) findViewById(R.id.detailAuthor);
        authorTextView.setText(this.book.getAuthor());

        // Publisher
        TextView publisherTextView = (TextView) findViewById(R.id.detailPublishedDate);
        publisherTextView.setText(this.book.getPublisher());

        // PublishedDate
        TextView publishedDateTextView = (TextView) findViewById(R.id.detailPublishedDate);
        publishedDateTextView.setText(this.book.getPublishedDate());

        // Description
        TextView descriptionTextView = (TextView) findViewById(R.id.detailDescription);
        descriptionTextView.setText(this.book.getDescription());

        // Link Button
        Button booklogButton = (Button) findViewById(R.id.detailBooklogButton);
        if (book.getIsbn() == null) {
            booklogButton.setEnabled(false);
        } else {
            booklogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("url", "http://booklog.jp/item/1/" + book.getIsbn());
                    startActivity(intent);
                }
            });
        }

        Button calilButton = (Button) findViewById(R.id.detailCalilButton);
        if (book.getIsbn() == null) {
            calilButton.setEnabled(false);
        } else {
            calilButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("url", "https://calil.jp/book/" + book.getIsbn());
                    startActivity(intent);
                }
            });
        }

        Button amazonButton = (Button) findViewById(R.id.detailAmazonButton);
        if (book.getIsbn() == null) {
            amazonButton.setEnabled(false);
        } else {
            amazonButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("url", "http://www.amazon.co.jp/exec/obidos/asin/" + book.getIsbn() + "/daipresents-22/");
                    startActivity(intent);
                }
            });
        }
    }
    
}
