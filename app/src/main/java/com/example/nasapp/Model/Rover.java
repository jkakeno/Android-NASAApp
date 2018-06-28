package com.example.nasapp.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Rover implements Parcelable{
    String roverName;
    Uri roverImageUri;
    String numberOfImages;

    public Rover(String roverName, Uri roverImageUri, String numberOfImages) {
        this.roverName = roverName;
        this.roverImageUri = roverImageUri;
        this.numberOfImages = numberOfImages;
    }

    protected Rover(Parcel in) {
        roverName = in.readString();
        roverImageUri = in.readParcelable(Uri.class.getClassLoader());
        numberOfImages = in.readString();
    }

    public static final Creator<Rover> CREATOR = new Creator<Rover>() {
        @Override
        public Rover createFromParcel(Parcel in) {
            return new Rover(in);
        }

        @Override
        public Rover[] newArray(int size) {
            return new Rover[size];
        }
    };

    public String getRoverName() {
        return roverName;
    }

    public Uri getRoverImageUri() {
        return roverImageUri;
    }

    public String getNumberOfImages() {
        return numberOfImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(roverName);
        parcel.writeParcelable(roverImageUri, i);
        parcel.writeString(numberOfImages);
    }
}
