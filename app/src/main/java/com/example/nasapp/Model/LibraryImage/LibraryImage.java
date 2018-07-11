package com.example.nasapp.Model.LibraryImage;

import android.os.Parcel;
import android.os.Parcelable;

public class LibraryImage implements Parcelable{
    String title;
    String date;
    String description;
    String imageUrl;

    public LibraryImage(String title, String date, String description, String imageUrl) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    protected LibraryImage(Parcel in) {
        title = in.readString();
        date = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<LibraryImage> CREATOR = new Creator<LibraryImage>() {
        @Override
        public LibraryImage createFromParcel(Parcel in) {
            return new LibraryImage(in);
        }

        @Override
        public LibraryImage[] newArray(int size) {
            return new LibraryImage[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
    }
}
