package com.jkdevelopment.astronomy.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Cover implements Parcelable{

    String imageUrl;
    Uri imageResource;
    String coverTitle;
    String imageTitle;
    Apod apod;
    ArrayList<Epic> epicImageList;


    public Cover(String coverTitle) {
        this.coverTitle = coverTitle;
    }

    protected Cover(Parcel in) {
        imageUrl = in.readString();
        imageResource = in.readParcelable(Uri.class.getClassLoader());
        coverTitle = in.readString();
        imageTitle = in.readString();
        apod = in.readParcelable(Apod.class.getClassLoader());
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
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

    public ArrayList<Epic> getEpicImageList() {
        return epicImageList;
    }

    public void setEpicImageList(ArrayList<Epic> epicImageList) {
        this.epicImageList = epicImageList;
    }

    public Apod getApod() {
        return apod;
    }

    public void setApod(Apod apod) {
        this.apod = apod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUrl);
        parcel.writeParcelable(imageResource, i);
        parcel.writeString(coverTitle);
        parcel.writeString(imageTitle);
        parcel.writeParcelable(apod, i);
    }
}
