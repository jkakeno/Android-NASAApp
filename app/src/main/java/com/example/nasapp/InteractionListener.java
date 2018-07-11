package com.example.nasapp;

import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Rover;
import com.google.android.gms.maps.model.LatLng;

public interface InteractionListener {

    void onCoverSelectInteraction(Cover cover);
    void onRoverSelectInteraction(Rover rover);
//    void onGetEarthImageryInteraction(LatLng currentLocation, String date, String period);
    void onGetEarthImageryInteraction(LatLng currentLocation, String date);
    void onGetRoverImageryInteraction(Rover rover);
    void onSearchImageryInteraction(String keyword);
}
