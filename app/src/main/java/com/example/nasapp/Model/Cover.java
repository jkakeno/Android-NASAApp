package com.example.nasapp.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import retrofit2.http.Url;

public class Cover implements Parcelable{

    Url imageUrl;
    Uri imageResource;
    String coverTitle;
    String imageTitle;

    public Cover(String coverTitle, String imageTitle) {
        this.coverTitle = coverTitle;
        this.imageTitle = imageTitle;
    }

    protected Cover(Parcel in) {
        imageResource = in.readParcelable(Uri.class.getClassLoader());
        coverTitle = in.readString();
        imageTitle = in.readString();
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel in) {
            return new Cover(in);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };

    public Url getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Url imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Uri getImageResource() {
        return imageResource;
    }

    public void setImageResource(Uri imageResource) {
        this.imageResource = imageResource;
    }

    public String getCoverTitle() {
        return coverTitle;
    }

    public void setCoverTitle(String coverTitle) {
        this.coverTitle = coverTitle;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(imageResource, i);
        parcel.writeString(coverTitle);
        parcel.writeString(imageTitle);
    }
}
