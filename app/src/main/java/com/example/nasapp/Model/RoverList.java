package com.example.nasapp.Model;

import android.net.Uri;

import com.example.nasapp.R;

import java.util.ArrayList;

public class RoverList {

    ArrayList<Rover> roverList;
    Rover curiosity;
    Rover opportunity;
    Rover spirit;

    public RoverList() {
    }


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

}
