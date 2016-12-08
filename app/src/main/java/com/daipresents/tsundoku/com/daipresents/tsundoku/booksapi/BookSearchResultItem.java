package com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi;

/**
 * Created by daipr on 2016/12/08.
 */
public class BookSearchResultItem {
    private String title;
    private String author;
    private String smallThumbnail;

    public BookSearchResultItem (String title, String author, String smallThumbnail){
        this.title = title;
        this.author = author;
        this.smallThumbnail = smallThumbnail;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getSmallThumbnail() { return smallThumbnail; }

}
