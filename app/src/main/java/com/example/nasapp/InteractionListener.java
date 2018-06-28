package com.example.nasapp;

import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Rover;
import com.google.android.gms.maps.model.LatLng;

public interface InteractionListener {

    void onCoverSelectInteraction(Cover cover);
    void onRoverSelectInteraction(Rover rover);
    void onStartEarthImageryInteraction(LatLng currentLocation, String date, String period);
    void onGetRoverImageryInteraction(boolean click);
    void onSearchImageryInteraction(String keyword);
}
