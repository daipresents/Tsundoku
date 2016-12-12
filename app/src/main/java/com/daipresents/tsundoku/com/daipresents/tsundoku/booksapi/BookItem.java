package com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi;

import android.graphics.Bitmap;

/**
 * Created by daipr on 2016/12/08.
 */
public class BookItem {
    private String smallThumbnail;
    private String title;
    private String author;

    public BookItem(String smallThumbnail, String title, String author){
        this.smallThumbnail = smallThumbnail;
        this.title = title;
        this.author = author;
    }

    public String getSmallThumbnail() { return smallThumbnail; }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }


}
