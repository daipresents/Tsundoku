package com.daipresents.tsundoku.books;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daipr on 2016/12/08.
 */
public class BookParcelable implements Parcelable {
    private String volumeID;
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private String description;
    // TODO: Bitmapにしたい
    private String thumbnail;
    private String isbn;

    public BookParcelable() {

    }

    public static final Parcelable.Creator<BookParcelable> CREATOR
            = new Parcelable.Creator<BookParcelable>() {
        public BookParcelable createFromParcel(Parcel in) {
            return new BookParcelable(in);
        }

        public BookParcelable[] newArray(int size) {
            return new BookParcelable[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.volumeID);
        out.writeString(this.title);
        out.writeString(this.author);
        out.writeString(this.publisher);
        out.writeString(this.publishedDate);
        out.writeString(this.description);
        out.writeString(this.thumbnail);
        out.writeString(this.isbn);
    }

    private BookParcelable(Parcel in) {
        this.volumeID = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.publisher = in.readString();
        this.publishedDate = in.readString();
        this.description = in.readString();
        this.thumbnail = in.readString();
        this.isbn = in.readString();
    }

    public String getVolumeID() {
        return volumeID;
    }

    public void setVolumeID(String volumeID) {
        this.volumeID = volumeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
