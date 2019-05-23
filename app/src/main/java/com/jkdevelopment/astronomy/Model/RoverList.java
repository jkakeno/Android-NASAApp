package com.jkdevelopment.astronomy.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.jkdevelopment.astronomy.R;

import java.util.ArrayList;

public class RoverList implements Parcelable{

    ArrayList<Rover> roverList;
    Rover curiosity;
    Rover opportunity;
    Rover spirit;

    public RoverList() {
    }


    protected RoverList(Parcel in) {
        roverList = in.createTypedArrayList(Rover.CREATOR);
        curiosity = in.readParcelable(Rover.class.getClassLoader());
        opportunity = in.readParcelable(Rover.class.getClassLoader());
        spirit = in.readParcelable(Rover.class.getClassLoader());
    }

    public static final Creator<RoverList> CREATOR = new Creator<RoverList>() {
        @Override
        public RoverList createFromParcel(Parcel in) {
            return new RoverList(in);
        }

        @Override
        public RoverList[] newArray(int size) {
            return new RoverList[size];
        }
    };

    public ArrayList<Rover> getRoverList() {
        roverList = new ArrayList<>();
        curiosity = new Rover("curiosity", Uri.parse("android.resource://com.example.nasapp/" + R.drawable.rover_curiosity));
        opportunity = new Rover("opportunity", Uri.parse("android.resource://com.example.nasapp/" + R.drawable.rover_opportunity));
        spirit = new Rover("spirit", Uri.parse("android.resource://com.example.nasapp/" + R.drawable.rover_spirit));
        roverList.add(curiosity);
        roverList.add(opportunity);
        roverList.add(spirit);
        return roverList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(roverList);
        parcel.writeParcelable(curiosity, i);
        parcel.writeParcelable(opportunity, i);
        parcel.writeParcelable(spirit, i);
    }
}
