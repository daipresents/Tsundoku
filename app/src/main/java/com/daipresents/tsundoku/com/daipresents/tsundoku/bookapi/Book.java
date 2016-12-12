package com.daipresents.tsundoku.com.daipresents.tsundoku.bookapi;

/**
 * Created by daipr on 2016/12/08.
 */
public class Book {
    private String volumeID;
    private String smallThumbnail;
    private String title;
    private String author;

    public Book(String volumeID, String smallThumbnail, String title, String author){
        this.volumeID = volumeID;
        this.smallThumbnail = smallThumbnail;
        this.title = title;
        this.author = author;
    }

    public String getVolumeID() { return volumeID; }
    public String getSmallThumbnail() { return smallThumbnail; }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }


}
